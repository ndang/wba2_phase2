package client;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import jaxb.Genre;
import jaxb.Kategorie;
import jaxb.Theme;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import org.jivesoftware.smackx.packet.DiscoverItems;
import org.jivesoftware.smackx.pubsub.AccessModel;
import org.jivesoftware.smackx.pubsub.ConfigureForm;
import org.jivesoftware.smackx.pubsub.FormType;
import org.jivesoftware.smackx.pubsub.Item;
import org.jivesoftware.smackx.pubsub.ItemPublishEvent;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.PublishModel;
import org.jivesoftware.smackx.pubsub.SimplePayload;
import org.jivesoftware.smackx.pubsub.Subscription;

import xmpp.ItemEventCoordinator;

/**
 * Der XMPP-Client nutzt den Publish-Subscribe-Service.
 *
 */
public class XmppClient
{	
	public static Vector<String> benachrichtigungen = new Vector<String>();
	public static int anz_g = 0, anz_k = 0, anz_t = 0;
	private ItemEventCoordinator iec = new ItemEventCoordinator();
	private PubSubManager pubsub_mgr;
	private Vector<LeafNode> topics;
	
	/**
	 * Wird ein XMLPP-Client instanziiert, erstellt er zunächst einen PubSub-Manager,
	 * einen Vektor, der speichert, welche LeafNodes zur Zeit existieren und
	 * er ermittelt die Anzahl der Genres, Kategorien und Genres über den REST-Client.
	 * Danach initialisiert er alle LeafNodes.
	 */
	public XmppClient()
	{	
		this.pubsub_mgr 	= new PubSubManager( PartyClient.con, "pubsub." + PartyClient.con.getHost() );
		this.topics 		= new Vector<LeafNode>();
		benachrichtigungen 	= new Vector<String>();
		anz_g 				= PartyClient.rc.getGenres().getGenre().size();
		for ( Genre g : PartyClient.rc.getGenres().getGenre() )
			anz_k 			+= PartyClient.rc.getKategorien( g.getGenreId() ).getKategorie().size();
		anz_t 				= PartyClient.rc.getThemes().getTheme().size();
		
		initTopics();
//		deleteAllTopics();
//		unsubscribeAllUsers();
	}
	
	/**
	 * Versucht alle Nodes zu entdecken, die bereis auf dem Server liegen und
	 * speichert sie anschließend in den "topics"-Vektor. 
	 * Falls es Unstimmigkeiten gibt, erstellt er anhand der Daten des REST-Clients
	 * alle LeafNodes nochmal neu. Zudem werden den Topics wieder Listener angehängt.
	 */
	private void initTopics()
	{
		topics.clear();
		ServiceDiscoveryManager discovery_mgr = ServiceDiscoveryManager.getInstanceFor( PartyClient.con );
		DiscoverItems discovery_items;
		
		try
		{
			discovery_items = discovery_mgr.discoverItems( "pubsub." + PartyClient.con.getHost() ); // Returns the discovered items of a given XMPP entity (Service) addressed by its JID.
//			discovery_items = discovery_mgr.discoverItems( "pubsub.localhost" );
			Iterator<org.jivesoftware.smackx.packet.DiscoverItems.Item> iterator = discovery_items.getItems();
			
			while (iterator.hasNext())
			{
				DiscoverItems.Item item = (DiscoverItems.Item) iterator.next();
				topics.addElement( (LeafNode) pubsub_mgr.getNode( item.getNode() ) );
			}
		} catch (XMPPException e) {
			e.printStackTrace();
			System.err.println("Topics konnten nicht initialisiert werden!");
		}
		
		/* aller erste Initialisierung*/
		if ( anz_g+anz_k+anz_t != topics.size() ) 
		{
			deleteAllTopics();
			
			List<Genre> list_g = PartyClient.rc.getGenres().getGenre();
			for (Genre g : list_g)
			{
				System.out.println( g.getGenreId() );
				createTopic( g.getGenreId() );
				List<Kategorie> list_k = PartyClient.rc.getKategorien( g.getGenreId() ).getKategorie();
				for (Kategorie k : list_k)
				{
					System.out.println( k.getKategorieId() );
					createTopic( k.getKategorieId() );
				}
			}
			
			List<Theme> list_t = PartyClient.rc.getThemes().getTheme();
			for (Theme t : list_t)
			{
				System.out.println( t.getAllgemeines().getThemeId() );
				createTopic( t.getAllgemeines().getThemeId() );
			}
			System.out.println( topics.size() );
		}
		initListeners();
	}
	
	/**
	 * Hängt beim Start des Clients EventListeners alle abonnierten Nodes.
	 */
	private void initListeners()
	{
		Vector<String> s = getSubscriptions();
		for (String name : s)
		{
			try	{
				pubsub_mgr.getNode(name).addItemEventListener(iec);
			} catch (XMPPException e) {
				e.printStackTrace();
				System.err.println( "An die vorhandenen Abonnements können keine Listeners angehängt werden." );
			}
		}
	}

	/**
	 * Erstellt ein Node für ein neues Topic.
	 * @param id ID des neuen Topics
	 */
	public void createTopic(String id)
	{
		try
		{
			/*eigene Configureform*/
		    ConfigureForm form = new ConfigureForm(FormType.submit);
		    form.setAccessModel(AccessModel.authorize); // nur Leute, die supcripton request must be approves and only subscribers may retrieve items
		    form.setDeliverPayloads(true); // Sets whether the node will deliver payloads with event notifications.
		    form.setNotifyRetract(true); // Determines whether subscribers should be notified when items are deleted from the node.
		    form.setPersistentItems(true); // ???
		    form.setPublishModel(PublishModel.open); // jeder darf publishen
		    form.setNotifyDelete(true); // wenn node gelöscht wird, wir subscriber benachrichtigt
		    form.setSubscribe(true); // ob man das subsciben kann
		    
		    /*Condigureform von der Website*/
//		    form.setAccessModel(AccessModel.open);
//	        form.setDeliverPayloads(false);
//	        form.setNotifyRetract(true);
//	        form.setPersistentItems(true);
//	        form.setPublishModel(PublishModel.open);
		      
		    LeafNode topic = (LeafNode) pubsub_mgr.createNode(id, form);
			topics.add(topic);
		} catch (XMPPException e) {
			e.printStackTrace();
			System.err.println("Topic konnte nicht angelegt werden.");
		}	
	}
	
	/**
	 * Löscht den Node eines angegebenen Topics.
	 * @param topic
	 */
	public void deleteTopic(String topic)
	{
		try
		{
			for (LeafNode curr_topic: topics)
			{
				if (curr_topic.equals(topic))
				{
//					curr_topic.removeItemDeleteListener();
//					curr_topic.removeItemEventListener();
					pubsub_mgr.deleteNode(topic);
					topics.remove(curr_topic);
				}
			}
		} catch (XMPPException e) {
			e.printStackTrace();
			System.err.println("Topic konnte nicht gelöscht werden.");
		}
	}
	
	/**
	 * Löscht die Nodes aller Topics.
	 */
	private void deleteAllTopics()
	{
		for(LeafNode curr_topic: topics)
		{
			try
			{
				pubsub_mgr.deleteNode(curr_topic.getId());
			} catch (XMPPException e) {
				e.printStackTrace();
				System.err.println("Topics konnten nicht gelöscht werden.");
			}
		}
		
		topics.clear();
		System.out.println( "Topic Anzahl ist wieder auf "+ topics.size() );
	}
	
	/**
	 * Holt eine Liste von allen Abonnements des eingeloggten Users.
	 * @return Liste der Abos
	 */
	public Vector<String> getSubscriptions()
	{
		Vector<String> abos = new Vector<String>();
		List<Subscription> abonennten = null;
		for (LeafNode topic: topics)
		{
			try
			{
				abonennten = topic.getSubscriptions();
			} catch (XMPPException e) {
				e.printStackTrace();
				System.err.println("Abonnenments konnten nicht geholt werden.");
			}
			for (Subscription curr_abonennt: abonennten)
				if ( curr_abonennt.getJid().equals( getMyJID() ) )
					abos.add( topic.getId() );
		}
		return abos;
	}
	
	/**
	 * Holt die JID des Users, dergerade mit dem Server verbunden ist.
	 * @return JID des Users
	 */
	private String getMyJID()
	{
		return PartyClient.con.getUser();
	}
	
	/**
	 * Prüft direkt am Node, ob ein bestimmtes Topic abonniert ist.
	 * @param abo Node des Topics
	 * @return true, falls das Topics abonniert ist/ false, falls nicht
	 */
	public boolean isSubscribed(LeafNode abo)
	{
		try
		{
			List<Subscription> abonennten = abo.getSubscriptions();
			for ( Subscription curr_abonennt: abonennten )
				if ( curr_abonennt.getJid().equals( getMyJID() ) )
					return true;
		} catch (XMPPException e) {
			e.printStackTrace();
			System.err.println( "Abonnement konnte nicht überprüft werden!" );
		}
		return false;
	}
	
	/**
	 * Prüft über die ID des Topics, ob ein bestimmtes Topic abonniert ist.
	 * @param abo_name ID des Topics
	 * @return true, falls das Topics abonniert ist/ false, falls nicht
	 */
	public boolean isSubscribed(String abo_name)
	{
		try {
			return isSubscribed( (LeafNode) pubsub_mgr.getNode( abo_name ) );
		} catch (XMPPException e) {
			e.printStackTrace();
			System.err.println("Topic konnte nicht auf Subsciption gerüft werden!");
			return false;
		}
	}
	
	/**
	 * Abonniert ein Topic anhand der ID.
	 * Dabei wird zuvor geprüft, ob dieses Topics bereits abonniert wurde.
	 * @param topic_id ID des zu abonnierenden Topics
	 * @return true, falls abonnieren erfolgreich verlaufen ist/ false, falls nicht
	 */
	public boolean subscribe(String topic_id)
	{
		try
		{
			LeafNode abo = pubsub_mgr.getNode(topic_id);
			
			if ( !isSubscribed(abo) )
			{
				abo.addItemEventListener( iec ); 
//				if (abo.toString().substring(0,1).equals("t"))
//					abo.addItemDeleteListener(new ItemDeleteCoordinator());
				abo.subscribe( getMyJID() );
				return true;
			}
			else
			{
				System.err.println("Dieses Topic wurde bereits abonniert.");
				return false;
			}
				
		}	catch (XMPPException e) {
			e.printStackTrace();
			System.err.println("Topic konnte nicht abonniert werden.");
			return false;
		}	
	}
	
	/**
	 * Kündigung eines Abonnements anhand der Topic-ID.
	 * Dabei wird zuvor geprüft, ob dieses Topics überhaupt abonniert wurde.
	 * @param topic_id ID des Topics, welches unsubscribed werden soll
	 * @return true, falls unsubscriben erfolgreich verlaufen ist/ false, falls nicht
	 */
	public boolean unsubscribe(String topic_id) 
	{
		try
		{
			LeafNode abo = pubsub_mgr.getNode(topic_id);
			if (!isSubscribed(abo))
			{
				System.err.println("Dieser Topic wurde gar nicht abonniert!");
				return false;
			}
			else
			{
				abo.removeItemEventListener(iec);
//				if (abo.toString().substring(0,1).equals("t")) // falls dies ein Theme ist
//					abo.removeItemDeleteListener(this);
				abo.unsubscribe(getMyJID());
				return true;
				
			}
		} catch (XMPPException | NullPointerException e) {
			e.printStackTrace();
			System.err.println("Abonemment konnte nicht gekündigt werden!");
			return false;
		}
	}
	
	/**
	 * Kündigt alle Abonnements des eingeloggten Users.
	 */
	public boolean unsubscribeAll()
	{
		try
		{
			Vector<String> subscriptions = getSubscriptions();
			for( String abo_name : subscriptions )
			{
				LeafNode abo = pubsub_mgr.getNode(abo_name);
				if ( !isSubscribed(abo) )
				{
					System.err.println("Dieser Topic wurde gar nicht abonniert!");
					return false;
				}
				else
				{
					abo.removeItemEventListener(iec);
					abo.unsubscribe(getMyJID());
				}
			}
		} catch (XMPPException | NullPointerException e) {
			e.printStackTrace();
			System.err.println("Abonemment konnte nicht gekündigt werden!");
			return false;
		}
		return true;
	}
	
	/**
	 * Alle Abonnements von allen Usern werden entfernt.
	 * (Diese Funktion wurde zur Fehlerbehebung während der Entwicklung der Anwendung geschrieben,
	 * für die Anwendung an sich hat sie keinen nutzen.)
	 */
	@SuppressWarnings({ "finally", "unused" })
	private void unsubscribeAllUsers()
	{
		try{
			for (LeafNode topic : topics)
			{
				LeafNode node = pubsub_mgr.getNode(topic.getId());
				List<Subscription> sups = node.getSubscriptions();
				for (Subscription user : sups) 
				{
					try {
						node.unsubscribe(user.getJid());
						} catch (Exception e){
							System.err.println("ging nicht.");
							}
					finally {continue;}
				}
					
			}
		} catch (Exception e) {
			System.err.println("geht nit.");
		}
	}
	
	/**
	 * Ein Item wird an ein Node gepublished, dessen Payload vom Nutzer mitgegeben wurde.
	 * Zuvor wird der Payload in eine XML-Struktur gesetzt.
	 * @param t_id ID des Nodes, an welches gepublished wird
	 * @param payload Nutzdaten des Items
	 * @return true, falls publishing erfolgreich gelaufen ist/ false, falls nicht.
	 */
	public boolean publish(String t_id, String payload)
	{			
		payload = "<payload>" + payload + "</payload>";
		
		try
		{
			LeafNode theme = pubsub_mgr.getNode( t_id );
			PayloadItem<SimplePayload> item = new PayloadItem<SimplePayload>( null, new SimplePayload( "<payload>", "", payload ) );
			theme.send( item );
			
			/*manueller ItemEventHandle wird aufgerufen*/
//			List<Item> item_list = new ArrayList<Item>();
//			item_list.add(item);
//			ItemPublishEvent<Item> ipe = new ItemPublishEvent<Item>(t_id, item_list, null, new Date()) ;
//			iec.handlePublishedItems( ipe );
			
			return true;
			
		} catch ( XMPPException e ) {
			
			e.printStackTrace();
			System.err.println( "Es konnte nichts gepublisht werden!" );
			
			return false;
		}
	}
	
//	public void publishDelete(String t_id, String item_id)
//	{
//		try
//		{
//			LeafNode theme= pubsub_mgr.getNode(t_id);
//			theme.deleteItem(item_id);
//		} catch (XMPPException e) {
//			e.printStackTrace();
//			System.err.println("Es konnte nichts aus dem Theme gelöscht werden.");
//		}
//	}
	
}
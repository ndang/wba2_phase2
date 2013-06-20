package client;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPConnection;
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
import GUI.Login;
import app.Genre;

public class XmppClient
{	
	public static Vector<String> benachrichtigungen = new Vector<String>();
	public static int anz_g = 0, anz_k = 0, anz_t = 0;
	private ItemEventCoordinator iec = new ItemEventCoordinator();
	private PubSubManager pubsub_mgr;
	private Vector<LeafNode> topics;
	
	public XmppClient()
	{	
		this.pubsub_mgr = new PubSubManager( PartyClient.con, "pubsub." + PartyClient.con.getHost() );
		this.topics = new Vector<LeafNode>();
		this.benachrichtigungen = new Vector<String>();
		this.anz_g = PartyClient.rc.getGenres().getGenre().size();
		for ( Genre g : PartyClient.rc.getGenres().getGenre() )
			this.anz_k += PartyClient.rc.getKategorien( g.getGenreId() ).getKategorie().size();
		this.anz_t = PartyClient.rc.getThemes().getTheme().size();
		

		initTopics();
//		deleteAllTopics();
//		unsubscribeAllUsers();
	}
	
	private void initTopics()
	{
		topics.clear();
		ServiceDiscoveryManager discovery_mgr = ServiceDiscoveryManager.getInstanceFor( PartyClient.con );
		DiscoverItems discovery_items;
		
		try
		{
//			discovery_items = discovery_mgr.discoverItems( "pubsub." + PartyClient.con.getHost() ); // Returns the discovered items of a given XMPP entity (Service) addressed by its JID.
			discovery_items = discovery_mgr.discoverItems( "pubsub.localhost" );
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
		
		/* aller erste Initialisierung, bei Bedarf einkommentieren*/
		//TODO muss noch gescheit geschrieben werden und nicht so manuell
		
		if ( anz_g+anz_k+anz_t != topics.size() ) 
		{
			deleteAllTopics();
			
			for( int i=0; i<anz_g; i++)
				createTopic("g"+i);
			
			createTopic("k0_g0");
			createTopic("k1_g0");
			createTopic("k0_g1");
			createTopic("k1_g1");
			createTopic("k0_g2");
			createTopic("k0_g3");
			createTopic("k1_g3");
			createTopic("k2_g3");
			
			createTopic("t0_k0_g0");
			createTopic("t1_k0_g0");
			createTopic("t2_k0_g0");	
		}
		
		initListeners();
	}
	
	private void initListeners()
	{
		Vector<String> s = getMySubscriptions();
		for (String name : s)
		{
			try
			{
				pubsub_mgr.getNode(name).addItemEventListener(iec);
			} catch (XMPPException e) {
				e.printStackTrace();
				System.err.println( "An die vorhandenen Abonnements können keine Listeners angehängt werden." );
			}
		}
	}
	
	public String createTID(String g_id, String k_id)
	{
		return "t"+(anz_t++)+"_"+k_id+g_id;
	}
	
	public void createTopic(String id)
	{
		try
		{
		    ConfigureForm form = new ConfigureForm(FormType.submit);
//		    form.setAccessModel(AccessModel.authorize); // nur Leute, die supcripton request must be approves and only subscribers may retrieve items
//		    form.setDeliverPayloads(true); // Sets whether the node will deliver payloads with event notifications.
//		    form.setNotifyRetract(true); // Determines whether subscribers should be notified when items are deleted from the node.
//		    form.setPersistentItems(true); // ???
//		    form.setPublishModel(PublishModel.open); // jeder darf publishen
//		    form.setNotifyDelete(true); // wenn node gelöscht wird, wir subscriber benachrichtigt
//		    form.setNotifyRetract(true); // wenn items des nodes gelöscht werden
//		    form.setSubscribe(true); // ob man das subsciben kann
		    
		    form.setAccessModel(AccessModel.open);
	        form.setDeliverPayloads(false);
	        form.setNotifyRetract(true);
	        form.setPersistentItems(true);
	        form.setPublishModel(PublishModel.open);
		      
		    LeafNode topic = (LeafNode) pubsub_mgr.createNode(id, form);
			topics.add(topic);	
		} catch (XMPPException e) {
			e.printStackTrace();
			System.err.println("Topic konnte nicht angelegt werden.");
		}	
	}
		
	public void deleteTopic(String topic)
	{
		// TODO: anz_x anpassen und Typ abfragen
		
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
	
	public void deleteBenachrichtigungen()
	{
		benachrichtigungen = new Vector<String>();
	}
	
	public Vector<String> getMySubscriptions()
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
				if (curr_abonennt.getJid().equals(getMyJID()))
					abos.add(topic.getId());
		}
		return abos;
	}
	
	public Vector<String> getBenachrichtigungen()
	{
		if( !benachrichtigungen.isEmpty() )
			return benachrichtigungen;
		else
			return new Vector<String>();	
	}
	
//	public Vector<String> getGenresNodes()
//	{
//		Vector<String> topic_names = new Vector<String>();
//		for (LeafNode topic : topics)
//		{
//			String name = topic.getId();
//			if( name.substring(0,1).equals("g"))
//				topic_names.add(name);
//		}
//		return topic_names;
//	}
//	
//	public Vector<String> getKategorienNodes(String genre)
//	{
//		Vector<String> topic_names = new Vector<String>();
//		for (LeafNode topic : topics)
//		{
//			String name = topic.getId();			
//			if( name.substring(0,1).equals("k") && name.substring(3).equals(genre))
//				topic_names.add(name);
//		}
//		return topic_names;
//	}
//	
//	public Vector<String> getThemesNodes(String genre, String kategorie)
//	{
//		Vector<String> topic_names = new Vector<String>();
//		for (LeafNode topic : topics)
//		{
//			String name = topic.getId();
//			if( name.substring(0,1).equals("t") && name.substring(3,5).equals(kategorie) && name.substring(6).equals(genre) )
//				topic_names.add(name);
//		}
//		return topic_names;
//	}
	
	private String getMyJID()
	{
		String id = PartyClient.con.getUser();
//		String id = PartyClient.con.getUser() + "@" + PartyClient.con.getHost();
//		String id = Login.getUser() + "@" + PartyClient.con.getHost();
		return id;
	}

	public boolean isSubscribed(LeafNode abo)
	{
		try
		{
			List<Subscription> abonennten = abo.getSubscriptions();
			for (Subscription curr_abonennt: abonennten)
				if (curr_abonennt.getJid().equals(getMyJID()))
					return true;
		} catch (XMPPException e) {
			e.printStackTrace();
			System.err.println("Abonnement konnte nicht überprüft werden!");
		}
		return false;
	}
	
	public boolean isSubscribed(String abo_name)
	{
		try {
			return isSubscribed( (LeafNode) pubsub_mgr.getNode(abo_name));
		} catch (XMPPException e) {
			e.printStackTrace();
			System.err.println("Topic konnte nicht auf Subsciption gerüft werden.");
			return false;
		}
	}

	public void subscribe(String topic_id)
	{
		try
		{
			LeafNode abo = pubsub_mgr.getNode(topic_id);
			
			if (!isSubscribed(abo))
			{
				abo.addItemEventListener( iec ); 
//				if (abo.toString().substring(0,1).equals("t"))
//					abo.addItemDeleteListener(new ItemDeleteCoordinator());
				abo.subscribe(getMyJID());
			}
			else
				System.err.println("Dieses Topic wurde bereits abonniert.");
		}	catch (XMPPException e) {
			e.printStackTrace();
			System.err.println("Topic konnte nicht abonniert werden.");
		}	
	}
	
	public void unsubscribe(String topic_id) 
	{
		try
		{
			LeafNode abo = pubsub_mgr.getNode(topic_id);
			if (!isSubscribed(abo))
				System.err.println("Dieser Topic wurde gar nicht abonniert!");
			else
			{
				abo.removeItemEventListener(iec);
//				if (abo.toString().substring(0,1).equals("t")) // falls dies ein Theme ist
//					abo.removeItemDeleteListener(this);
				abo.unsubscribe(getMyJID());
			}
		} catch (XMPPException | NullPointerException e) {
			e.printStackTrace();
			System.err.println("Abonemment konnte nicht gekündigt werden!");
		}
	}

	public void unsubscribeAll()
	{
		try
		{
			Vector<String> subscriptions = getMySubscriptions();
			for( String abo_name : subscriptions )
			{
				LeafNode abo = pubsub_mgr.getNode(abo_name);
				if ( !isSubscribed(abo) )
					System.err.println("Dieser Topic wurde gar nicht abonniert!");
				else
				{
					abo.removeItemEventListener(iec);
					abo.unsubscribe(getMyJID());
				}
			}
		} catch (XMPPException | NullPointerException e) {
			e.printStackTrace();
			System.err.println("Abonemment konnte nicht gekündigt werden!");
		}
	}
	
	private void unsubscribeAllUsers()
	{
		try{
			for (LeafNode topic : topics)
			{
				LeafNode node = pubsub_mgr.getNode(topic.getId());
				List<Subscription> sups = node.getSubscriptions();
				for (Subscription user : sups) 
				{
					try { node.unsubscribe(user.getJid()); } catch (Exception e){ System.err.println("ging nicht.");}
					finally {continue;}
				}
					
			}
		} catch (Exception e) {
			System.err.println("geht nit.");
		}
	}
	
	public boolean publish(String t_id, String payload)
	{			
		payload = "<payload>" + payload + "</payload>";
		
		try
		{
			LeafNode theme = pubsub_mgr.getNode( t_id );
			PayloadItem item = new PayloadItem<SimplePayload>( null, new SimplePayload( "<payload>", "", payload ) );
			theme.send( item );
			
			List<Item> item_list = new ArrayList();
			item_list.add(item);
			ItemPublishEvent<Item> ipe = new ItemPublishEvent<Item>(t_id, item_list, null, new Date()) ;
			iec.handlePublishedItems( ipe );
			
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
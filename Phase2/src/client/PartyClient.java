package client;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import javax.xml.bind.JAXBException;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import org.jivesoftware.smackx.packet.DiscoverItems;
import org.jivesoftware.smackx.pubsub.AccessModel;
import org.jivesoftware.smackx.pubsub.ConfigureForm;
import org.jivesoftware.smackx.pubsub.FormType;
import org.jivesoftware.smackx.pubsub.Item;
import org.jivesoftware.smackx.pubsub.ItemDeleteEvent;
import org.jivesoftware.smackx.pubsub.ItemPublishEvent;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.PublishModel;
import org.jivesoftware.smackx.pubsub.Subscription;
import org.jivesoftware.smackx.pubsub.listener.ItemDeleteListener;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;

import webservice.GenresKategorienService;
import webservice.ThemesService;

public class PartyClient {
	
	private static GenresKategorienService gks;
	private static ThemesService ts;
	
	private static String user = "user1";
	private static String pw = "user1user1";
	private static String server = "localhost";
	
	private static Scanner in = new Scanner(System.in);
	
	private static int anz_g = 0, anz_k = 0, anz_t = 0;
	
	private Connection con;
	private PubSubManager pubsub_mgr;
	
	private Vector<LeafNode> topics;
	private Vector<String> benachrichtigungen = new Vector<String>();
	
	public PartyClient() throws XMPPException
	{
		try
		{
			con = new XMPPConnection(server);
			con.connect();
			con.login(user, pw);
			System.out.println("Login erfolgreich.");
		}
		catch (XMPPException e)
		{
			e.printStackTrace();
			System.err.println("\nLogin fehlgeschlagen.");
		}
		
		this.gks = new GenresKategorienService();
		this.ts = new ThemesService();
		
		this.pubsub_mgr = new PubSubManager(con, "pubsub."+con.getHost());
		this.topics = new Vector<LeafNode>();
		this.benachrichtigungen = new Vector<String>();
		
		this.anz_g = gks.getGenres().getGenre().size();
		for ( int i = 0; i<anz_g; i ++ )
			this.anz_k += gks.getKategorien(gks.getGenres().getGenre().get(i).getGenreId()).getKategorie().size();
		this.anz_t = ts.getThemes().getTheme().size();
		
		initTopics();	
	}
	
	/**
	 *  Alle vorhandenen Topics holen
	 * @throws XMPPException 
	 * @throws JAXBException 
	 */
	private void initTopics() throws XMPPException
	{
		topics.clear();
		ServiceDiscoveryManager discovery_mgr = ServiceDiscoveryManager.getInstanceFor(con);
		DiscoverItems discovery_items;
		
		try
		{
			discovery_items = discovery_mgr.discoverItems("pubsub."+con.getHost()); // Returns the discovered items of a given XMPP entity (Service) addressed by its JID.
			Iterator<org.jivesoftware.smackx.packet.DiscoverItems.Item> iterator = discovery_items.getItems();
			
			while (iterator.hasNext())
			{
				DiscoverItems.Item item = (DiscoverItems.Item) iterator.next();
				topics.addElement((LeafNode) pubsub_mgr.getNode(item.getNode()));
			}
		} catch (XMPPException e) {
			e.printStackTrace();
			System.err.println("Topics konnten nicht initialisiert werden!");
		}
		
		/* aller erste Initialisierung*/
		if ( anz_g+anz_k+anz_t != topics.size() ) //TODO muss noch gescheit geschrieben werden und nicht so manuell
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
		
	}
	
	public String createGID()
	{
		return  "g"+(anz_g++);
	}
	
	public String createKID(String g_id)
	{
		return "k"+(anz_k++)+"_"+g_id;
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
		    form.setAccessModel(AccessModel.authorize); // nur Leute, die supcripton request must be approves and only subscribers may retrieve items
		    form.setDeliverPayloads(true); // Sets whether the node will deliver payloads with event notifications.
		    form.setNotifyRetract(true); // Determines whether subscribers should be notified when items are deleted from the node.
		    form.setPersistentItems(true); // ???
		    form.setPublishModel(PublishModel.open); // jeder darf publishen
		    form.setNotifyDelete(true); // wenn node gelöscht wird, wir subscriber benachrichtigt
		    form.setNotifyRetract(true); // wenn items des nodes gelöscht werden
		    form.setSubscribe(true); // ob man das subsciben kann
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
		System.out.println("Topic Anzahl ist wieder auf "+ topics.size());
	}

	public void getTopicTitle(String typ)
	{
		// TODO: per WebServices!
		for(LeafNode topic : topics)
		{
			if ( topic.getId().equals(typ) )
				System.out.println(topic.getId()+" Titel");
		}
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
		if(!benachrichtigungen.isEmpty())
			return benachrichtigungen;
		else
			return new Vector<String>();	
	}
	
	public Vector<String> getTopicNames()
	{
		Vector<String> topic_names = new Vector<String>();
		for (LeafNode topic : topics)
			topic_names.add(topic.getId());
		return topic_names;
	}
	
	public Vector<String> getGenres()
	{
		Vector<String> topic_names = new Vector<String>();
		for (LeafNode topic : topics)
		{
			String name = topic.getId();
			if( name.substring(0,1).equals("g"))
				topic_names.add(name);
		}
		return topic_names;
	}
	
	public Vector<String> getKategorien(String genre)
	{
		Vector<String> topic_names = new Vector<String>();
		for (LeafNode topic : topics)
		{
			String name = topic.getId();			
			if( name.substring(0,1).equals("k") && name.substring(3).equals(genre))
				topic_names.add(name);
		}
		return topic_names;
	}
	
	public Vector<String> getThemes(String genre, String kategorie)
	{
		Vector<String> topic_names = new Vector<String>();
		for (LeafNode topic : topics)
		{
			String name = topic.getId();
			if( name.substring(0,1).equals("t") && name.substring(3,5).equals(kategorie) && name.substring(6).equals(genre))
				topic_names.add(name);
		}
		return topic_names;
	}
	
	private String getMyJID()
	{
		return con.getUser()+"@"+con.getHost();
	}
	
	private boolean isSubscribed(LeafNode abo)
	{
		try
		{
			List<Subscription> abonennten;
			abonennten = abo.getSubscriptions();
			for (Subscription curr_abonennt: abonennten)
				if (curr_abonennt.getJid().equals(getMyJID()))
					return true;
		} catch (XMPPException e) {
			e.printStackTrace();
			System.err.println("Abonnement konnte nicht überprüft werden.");
		}
		return false;
	}

	public void subscribe(String topic_id)
	{
		try
		{
			LeafNode abo = pubsub_mgr.getNode(topic_id);
			
			if (!isSubscribed(abo))
			{
				abo.addItemEventListener(new ItemEventCoordinator()); 
				if (abo.toString().substring(0,1).equals("t"))
					abo.addItemDeleteListener(new ItemDeleteCoordinator());
				abo.subscribe(getMyJID());
				System.out.println(abo.getId() + " wurde abonniert.");
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
				System.err.println("Dieser Topic wurde gar nicht abonniert.");
			else
			{
//				abo.removeItemEventListener(this);
//				if (abo.toString().substring(0,1).equals("t")) // falls dies ein Theme ist
//					abo.removeItemDeleteListener(this);
				abo.unsubscribe(getMyJID());
				System.out.println("Ihr Abo wurde erfolgreich gekündigt.");
			}
		}
		
		catch (XMPPException e)
		{
//			e.printStackTrace();
			System.err.println("Abonemment konnte nicht gekündigt werden.");
		}
		catch (NullPointerException e)
		{
//			e.printStackTrace();
			System.err.println("Topic existiert nicht.\n");
		}
	}

	public void logout()
	{
		con.disconnect();
		System.out.println(user + " ausgeloggt.");
	}
	
	public void publish(String t_id, String payload)
	{
		LeafNode theme;
		try
		{
			theme = pubsub_mgr.getNode(t_id);
			theme.send();
//			theme.send(new PayloadItem<SimplePayload>(theme.getId()+"_item_id", new SimplePayload("theme", "namespace", payload)));
		} catch (XMPPException e) {
			e.printStackTrace();
			System.err.println("Es konnte nichts zum Theme hinzugefügt werden.");
		}
	}
	
//	public void theme_item_loeschen(String t_id, String item_id)
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

	class ItemEventCoordinator implements ItemEventListener<Item>
    {
        @Override
        public void handlePublishedItems(ItemPublishEvent<Item> items)
        {
            System.out.println("Item count: " + items.getItems().size());
            System.out.println(items);
		}
       
	}
	
	class ItemDeleteCoordinator implements ItemDeleteListener
    {
        @Override
        public void handleDeletedItems(ItemDeleteEvent items)
        {
            System.out.println("Item count: " + items.getItemIds().size());
            System.out.println(items);
        }
        
        @Override
        public void handlePurge()
        {
            System.out.println("All items have been deleted from node");
        }
    }
}

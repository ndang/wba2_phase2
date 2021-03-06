//package xmpp;
//
//import java.io.FileNotFoundException;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Scanner;
//import java.util.Vector;
//
//import javax.xml.bind.JAXBException;
//
//import org.jivesoftware.smack.Connection;
//import org.jivesoftware.smack.XMPPConnection;
//import org.jivesoftware.smack.XMPPException;
//import org.jivesoftware.smackx.ServiceDiscoveryManager;
//import org.jivesoftware.smackx.packet.DiscoverItems;
//import org.jivesoftware.smackx.pubsub.AccessModel;
//import org.jivesoftware.smackx.pubsub.ConfigureForm;
//import org.jivesoftware.smackx.pubsub.FormType;
//import org.jivesoftware.smackx.pubsub.Item;
//import org.jivesoftware.smackx.pubsub.ItemDeleteEvent;
//import org.jivesoftware.smackx.pubsub.ItemPublishEvent;
//import org.jivesoftware.smackx.pubsub.LeafNode;
//import org.jivesoftware.smackx.pubsub.PubSubManager;
//import org.jivesoftware.smackx.pubsub.PublishModel;
//import org.jivesoftware.smackx.pubsub.Subscription;
//import org.jivesoftware.smackx.pubsub.listener.ItemDeleteListener;
//import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;
//
//import webservices.GenresKategorienService;
//import webservices.ThemesService;
//
//
//
//public class Alt_xmppService implements ItemDeleteListener,ItemEventListener<Item> {
//	
////	private static GenresKategorienService;
////	private static ThemesService;
//	
//	private static String user = "user1";
//	private static String pw = "user1user1";
//	private static String server = "localhost";
//	
//	private int anz_g = 0, anz_k = 0, anz_t = 0;
//	
//	private Connection con;
//	private PubSubManager pubsub_mgr;
//	
//	private Vector<LeafNode> topics;
//	private Vector<String> benachrichtigungen = new Vector<String>();
//	
//	Scanner in = new Scanner(System.in);
//	
//	public Alt_xmppService()
//	{
//		try
//		{
//			con = new XMPPConnection(server);
//			con.connect();
//			con.login(user, pw);
//			System.out.println("Login erfolgreich.");
//		}
//		catch (XMPPException e)
//		{
//			e.printStackTrace();
//			System.err.println("\nLogin fehlgeschlagen.");
//		}
////		PubSubManager p = new PubSubManager(con, "pubsub.localhost");
//		this.pubsub_mgr = new PubSubManager(con, "pubsub."+con.getHost()); //?
////		this.pubsub_mgr = p;
//		this.topics = new Vector<LeafNode>();
//		this.benachrichtigungen = new Vector<String>();
//		init_daten();
//		topics_init();		
//	}
//	
//	private void init_daten()
//	{
//		ThemesService ts = new ThemesService ();
//		try
//		{
//			anz_g = ts.getGenre().size();
//		
//			for ( int i = 0; i<anz_g; i ++ )
//				anz_k += GenresKategorienService.gibKategorienDaten(GenresKategorienService.gibGenreDaten().getGenre().get(i).getGenreId()).getKategorie().size();
//			anz_t = ts.gibThemeDaten().getTheme().size();
//		}
//		catch (FileNotFoundException | JAXBException e )
//		{
//			e.printStackTrace();
//			System.err.println("Es konnte nicht auf die XML Daten zugegriffen werden.");
//		}
//	}
//	
//	/**
//	 *  Alle vorhandenen Topics holen
//	 * @throws XMPPException 
//	 * @throws JAXBException 
//	 */
//	private void topics_init() throws JAXBException
//	{
//		topics.clear();
//		ServiceDiscoveryManager discovery_mgr = ServiceDiscoveryManager.getInstanceFor(con);   //Object has u.a. ability to discover items and infos of remote(entfernte) XMPP entities
//		DiscoverItems discovery_items; // to request and receive items associated with XMPP entities
//		try
//		{
//			discovery_items = discovery_mgr.discoverItems("pubsub."+con.getHost()); // Returns the discovered items of a given XMPP entity (Service) addressed by its JID.
//			Iterator<org.jivesoftware.smackx.packet.DiscoverItems.Item> iterator = discovery_items.getItems();
//			while (iterator.hasNext())
//			{
//				DiscoverItems.Item item = (DiscoverItems.Item) iterator.next();
//				topics.addElement((LeafNode) pubsub_mgr.getNode(item.getNode()));
//			}
//		}
//		catch (XMPPException e)
//		{
//			e.printStackTrace();
//			System.err.println("Liste von Knoten konnte nicht geholt werden.");
//		}	
//
//		try { // Alles zur�cksetzen
//			topics_clear();
//		} catch (XMPPException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.err.println("piss");
//		}
//		
//		if ( topics.size() != anz_g + anz_k + anz_t )
//		{
//			try
//			{
//				topics_clear();
//			} catch (XMPPException e) {
//				e.printStackTrace();
//				System.err.println("Topics konnten nicht gecleart werden.");
//			}
//			
//			int temp = anz_g;
//			anz_g = 0;
//			for ( int i = 0; i<temp; i++ )
//				new_topic("g");
//			
//			temp = anz_k;
//			anz_k = 0;
////			for  ( int i = 0; i<temp; i++ )
////				new_topic("k");
//			
//			/** naja, hierrum kann ich micht sp�ter k�mmern **/
//			new_topic("g0_k", 0); anz_k++;
//			new_topic("g0_k", 1); anz_k++;
//			new_topic("g1_k", 0); anz_k++;
//			new_topic("g1_k", 1); anz_k++;
//			new_topic("g2_k", 0); anz_k++;
//			new_topic("g3_k", 0); anz_k++;
//			new_topic("g3_k", 1); anz_k++;
//			new_topic("g3_k", 2); anz_k++;
//			
//			temp = anz_t;
//			anz_t = 0;
//			for  ( int i = 0; i<temp; i++ )
//				new_topic("t");
//		}
//	}
//	
//	public void new_topic(String typ)
//	{
//		switch(typ)
//		{
//			case "g": new_topic(typ, anz_g++); break;
//			case "k": new_topic(typ, anz_k++); break;
//			case "t": new_topic(typ, anz_t++); break;
//		}
//	}
//	
//	public void new_topic(String typ, int id)
//	{
//		try
//		{
//		    ConfigureForm form = new ConfigureForm(FormType.submit);
//		    form.setAccessModel(AccessModel.authorize); // nur Leute, die supcripton request must be approves and only subscribers may retrieve items
//		    form.setDeliverPayloads(true); // Sets whether the node will deliver payloads with event notifications.
//		    form.setNotifyRetract(true); // Determines whether subscribers should be notified when items are deleted from the node.
//		    form.setPersistentItems(false);
//		    form.setPublishModel(PublishModel.open); // jeder darf publishen
//		    form.setNotifyDelete(true); // wenn node gel�scht wird, wir subscriber benachrichtigt
//		    form.setNotifyRetract(true); // wenn items des nodes gel�scht werden
//		    form.setSubscribe(true); // ob man das subsciben kann
//		    LeafNode topic = (LeafNode) pubsub_mgr.createNode(typ+id, form);
//			topics.add(topic);	
//		}
//		
//		catch (XMPPException e)
//		{
//			e.printStackTrace();
//			System.err.println("Topic konnte nicht angelegt werden.");
//		}	
//	}
//		
//	public void delete_topic(String to_delete_topic)
//	{
//		try
//		{
//			for (LeafNode curr_topic: topics)
//			{
//				if (curr_topic.equals(to_delete_topic))
//				{
//					curr_topic.removeItemDeleteListener(this);
//					curr_topic.removeItemEventListener(this);
//					pubsub_mgr.deleteNode(to_delete_topic);
//					topics.remove(curr_topic);
//				}
//			}
//		}
//		
//		catch (XMPPException e)
//		{
//			e.printStackTrace();
//			System.err.println("Topic konnte nicht gel�scht werden.");
//		}
//	}
//	
//	private void topics_clear() throws XMPPException
//	{
//		for(LeafNode curr_topic: topics)
//		{
//			try
//			{
//				pubsub_mgr.deleteNode(curr_topic.getId());
//			}
//			
//			catch (XMPPException e)
//			{
//				e.printStackTrace();
//				System.err.println("Topics konnten nicht gel�scht werden.");
//			}
//		}
//		
//		topics.clear();
//		System.out.println("Topic Anzahl ist wieder auf "+ topics.size());
//	}
//
//	public void topicInfo(String typ)
//	{
//		for(LeafNode topic : topics)
//		{
//			if (topic.getId().toString().substring(0, 1).equals(typ) && topic.getId().toString().length()==2)
//				System.out.println(topic.getId().toString());
//		}
//	}
//	
//	public void topicInfo(String typ, String id)
//	{
//		for(LeafNode topic : topics)
//		{
//			if (topic.getId().toString().substring(0, 2).equals(id) && topic.getId().toString().length()>2)
//				System.out.println(topic.getId().toString());
//		}
//	}
//
//	public void abonnieren(String topic_id)
//	{
//		try {
//			LeafNode abo = pubsub_mgr.getNode(topic_id);
//			
//			if (!abonniert(abo))
//			{
//				abo.addItemEventListener(this); // f�r jedes abo ein neues?
//				if (abo.toString().substring(0,1).equals("t")) // falls dies ein Theme ist
//					abo.addItemDeleteListener(this);
//				abo.subscribe(meineJID());
//				System.out.println(abo.getId() + " wurde abonniert.");
//			}
//			
//			else
//				System.err.println("Dieses Topic wurde bereits abonniert.");
//		}
//		
//		catch (XMPPException e)
//		{
//			e.printStackTrace();
//			System.err.println("Topic konnte nicht abonniert werden.");
//		}	
//	}
//	
//	public boolean gibAbonnements()
//	{
//		String ret = "";
//		for (LeafNode topic : topics)
//		{
//			if (abonniert(topic))
//				ret += topic.getId() + "\n";
//		}
//		
//		if (ret.equals(""))
//		{
//			ret = "Sie haben noch keine Abonnements.";
//			return false;
//		}
//			 
//		System.out.println(ret);
//		return true;
//	}
//	
//	public Vector<String> gibAbos()
//	{
//		Vector<String> abos = new Vector<String>();
//		List<Subscription> abonennten = null;
//		for (LeafNode topic: topics)
//		{
//			try {
//				abonennten = topic.getSubscriptions();
//			} catch (XMPPException e) {
//				e.printStackTrace();
//				System.err.println("Abonnenments konnten nicht geholt werden.");
//			}
//			for (Subscription curr_abonennt: abonennten)
//				if (curr_abonennt.getJid().equals(meineJID()))
//					abos.add(topic.getId());
//		}
//		return abos;
//	}
//	
//	private boolean abonniert(LeafNode abo)
//	{
//		try
//		{
//			List<Subscription> abonennten;
//			abonennten = abo.getSubscriptions();
//			for (Subscription curr_abonennt: abonennten)
//				if (curr_abonennt.getJid().equals(meineJID()))
//					return true;
//		}
//		
//		catch (XMPPException e)
//		{
//			e.printStackTrace();
//			System.err.println("Abonnement konnte nicht �berpr�ft werden.");
//		}
//		return false;
//	}
//
//	public void abo_kuendigen(String id) 
//	{
//		LeafNode abo = null;
//		try
//		{
//			for (LeafNode topic : topics)
//			{
//				if ( topic.getId().equals(id) )
//					abo = topic;
//			}
//			
//			if (!abonniert(abo))
//				System.err.println("Dieser Topic wurde gar nicht abonniert.");
//			else
//			{
//				abo.removeItemEventListener(this);
//				if (abo.toString().substring(0,1).equals("t")) // falls dies ein Theme ist
//					abo.removeItemDeleteListener(this);
//				abo.unsubscribe(meineJID());
//				System.out.println("Ihr Abo wurde erfolgreich gek�ndigt.");
//			}
//		}
//		
//		catch (XMPPException e)
//		{
////			e.printStackTrace();
//			System.err.println("Abonemment konnte nicht gek�ndigt werden.");
//		}
//		catch (NullPointerException e)
//		{
////			e.printStackTrace();
//			System.err.println("Topic existiert nicht.\n");
//		}
//	}
//
//	public void ausloggen()
//	{
//		con.disconnect();
//		System.out.println(user + " ausgeloggt.");
//	}
//	
//	public void theme_erstellen(String theme_body)
//	{
//		try
//		{
//			new_topic("t");
//			LeafNode new_theme = pubsub_mgr.getNode("t"+(anz_t-1));
//			theme_item_hinzuf�gen(new_theme.getId().toString(), theme_body);
//			System.out.println("Theme wurde erstellt.");
//		}
//		catch(XMPPException e)
//		{
//			System.err.println("Theme kann nicht erstellt werden.");
//		}
//	}
//	
//	public void theme_item_hinzuf�gen(String t_id, String payload)
//	{
//		LeafNode theme;
//		try {
//			theme = pubsub_mgr.getNode(t_id);
//			theme.send();
////			//publishen ohne payload
////			node.send(new Item());
////			theme.publish(); // asynchronous call
//			// Publish an Item with payload
////			theme.publish(new PayloadItem<SimplePayload>(theme.getId()+"_item_id", new SimplePayload("theme", "namespace", payload)));
////			theme.publish();
////			theme.publish(new Item("item_id" + (int) Math.random()));
////			theme.send(new Item("item_id" + (int) Math.random()));
//			System.out.println("Theme wurde erweitert.");
//		}
//		
//		catch (XMPPException e)
//		{
//			e.printStackTrace();
//			System.err.println("Es konnte nichts zum Theme hinzugef�gt werden.");
//		}
//	}
//	
//	public void theme_item_loeschen(String t_id, String item_id)
//	{
//		try
//		{
//			LeafNode theme= pubsub_mgr.getNode(t_id);
//			theme.deleteItem(item_id);
//		}
//		
//		catch (XMPPException e)
//		{
//			e.printStackTrace();
//			System.err.println("Es konnte nichts aus dem Theme gel�scht werden.");
//		}
//	}
//	
////	public void bestimmte_items_holen(String topic_id, String []items) throws XMPPException // TODO
////	{
////	    LeafNode node = pubsub_mgr.getNode(topic_id);
////	    Collection<String> item_ids = new ArrayList<String>(items.length);
////	    for (String item_id: items)
////	    	item_ids.add(item_id);
////	      
////	    List<? extends Item> item_list = node.getItems(item_ids);
////	}
////    
//	public void benachrichtigungenAusgeben()
//	{
//		if(!benachrichtigungen.isEmpty())
//		{
//			int anz_nachrichten = 1;
//			System.out.println("\nSie haben " + benachrichtigungen.size() + " Benachrichtigungen.");
//			for (Object nachricht : benachrichtigungen)
//				System.out.println((anz_nachrichten++) + ") " + nachricht.toString() + "\n");
////			benachrichtigungen.clear();
//		}
//		else
//			System.out.println("Keine neuen Benachrichtigungen.");		
//	}
//	
//	public Vector<String> benachrichtigungenHolen()
//	{
//		if(!benachrichtigungen.isEmpty())
//		{
//			return benachrichtigungen;
//		}
//		else
//			return new Vector<String>();	
//	}
//	
//	private String meineJID() {
//		return con.getUser()+"@"+con.getHost();
//	}
//	
////	private Menue menue() throws XMPPException, JAXBException
////	{
////		return new Menue(this);
////	}
//	
//	public Vector<String> getTopicNames()
//	{
//		Vector<String> topic_names = new Vector<String>();
//		for (LeafNode topic : topics)
//			topic_names.add(topic.getId());
//		return topic_names;
//	}
//	
//	public Vector<String> getGenres()
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
//	public Vector<String> getKategorien(String genre)
//	{
//		Vector<String> topic_names = new Vector<String>();
//		for (LeafNode topic : topics)
//		{
//			String name = topic.getId();
//			if( name.substring(0,1).equals("k") && name.substring(3,4).equals(genre))
//				topic_names.add(name);
//		}
//		return topic_names;
//	}
//	
//	public Vector<String> getThemes(String genre, String kategorie)
//	{
//		Vector<String> topic_names = new Vector<String>();
//		for (LeafNode topic : topics)
//		{
//			String name = topic.getId();
//			if( name.substring(0,1).equals("t") && name.substring(3,4).equals(kategorie) && name.substring(3,4).equals(genre))
//				topic_names.add(name);
//		}
//		return topic_names;
//	}
//	
//	public static void main(String[] args) throws JAXBException {
////		try
////		{
////			XMPPConnection con = new XMPPConnection(server);
////			con.connect();
////			con.login(user, pw);
////			System.out.println("Login erfolgreich.");
////			PartyClient pc = new PartyClient(con);
////			pc.menue();
////		}
////		catch (XMPPException e)
////		{
////			e.printStackTrace();
////			System.err.println("\nLogin fehlgeschlagen.");
////		}		
//	}
//	
//	class ItemEventCoordinator implements ItemEventListener<Item>
//    {
//        @Override
//        public void handlePublishedItems(ItemPublishEvent<Item> items)
//        {
//            System.out.println("Item count: " + items.getItems().size());
//            System.out.println(items);
//		}
//       
//	}
//	
//	class ItemDeleteCoordinator implements ItemDeleteListener
//    {
//        @Override
//        public void handleDeletedItems(ItemDeleteEvent items)
//        {
//            System.out.println("Item count: " + items.getItemIds().size());
//            System.out.println(items);
//        }
//        
//        @Override
//        public void handlePurge()
//        {
//            System.out.println("All items have been deleted from node");
//        }
//    }
//
//	@Override
//	public void handlePublishedItems(ItemPublishEvent<Item> items_event) {
//		List<Item> item_list = items_event.getItems();
//		for (int i=0; i<item_list.size(); i++)
//			benachrichtigungen.add(item_list.get(i).getId());
//	}
//
//	@Override
//	public void handleDeletedItems(ItemDeleteEvent items_event) { // TODO 
//		List<String> item_list = items_event.getItemIds();
//		for (int i=0; 0<item_list.size(); i++)
//			System.out.println(item_list.get(i) + " wurde gel�scht.");
//		
//	}
//
//	@Override
//	public void handlePurge() { // TODO: Called when all items are deleted from a node the listener is registered with.
//		System.out.println("Gesamtes Theme wurde gel�scht.");
//	}
//}

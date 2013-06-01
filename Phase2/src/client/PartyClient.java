package client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
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
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.PublishModel;
import org.jivesoftware.smackx.pubsub.SimplePayload;
import org.jivesoftware.smackx.pubsub.Subscription;
import org.jivesoftware.smackx.pubsub.listener.ItemDeleteListener;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;

import app.*;

import webservice.*;

public class PartyClient implements ItemDeleteListener,ItemEventListener {
	
	private static String user = "user1";
	private static String pw = "user1user1";
	private static String server = "localhost";
	
	private Connection con;
	private PubSubManager pubsub_mgr;
	private Vector<LeafNode> topics;
	static final int ANZ_G_K = 12;
	
	public PartyClient(XMPPConnection connection) throws XMPPException, JAXBException
	{
		this.con = connection;
		this.pubsub_mgr = new PubSubManager(con, "pubsub."+con.getHost());
		this.topics = new Vector<LeafNode>();
		topics_clear();
//		topics_init();
	}
	
	/**
	 *  Alle vorhandenen Topics holen
	 * @throws XMPPException 
	 * @throws JAXBException 
	 */
	private void topics_init() throws XMPPException, JAXBException
	{
		topics.clear();
		ServiceDiscoveryManager discovery_mgr = ServiceDiscoveryManager.getInstanceFor(con);   //Object has u.a. ability to discover items and infos of remote(entfernte) XMPP entities
		DiscoverItems discovery_items; // to request and receive items associated with XMPP entities
		try
		{
			discovery_items = discovery_mgr.discoverItems("pubsub.localhost"); // Returns the discovered items of a given XMPP entity (Service) addressed by its JID.
			Iterator<org.jivesoftware.smackx.packet.DiscoverItems.Item> iterator = discovery_items.getItems();
			while (iterator.hasNext())
			{
				DiscoverItems.Item item = (DiscoverItems.Item) iterator.next();
				try
				{
					topics.addElement((LeafNode) pubsub_mgr.getNode(item.getNode()));
				}
				catch (XMPPException e)
				{
					e.printStackTrace();
				}
			}
		}
		catch (XMPPException e1)
		{
			e1.printStackTrace();
			System.err.println("Liste von Knoten konnte nicht geholt werden.");
		}	
		
//		if (topics.size()!= ANZ_G_K) // einmalig
//		{
//			GenresKategorienService gks = new GenresKategorienService();
//			int g = gks.gibGenreDaten().getGenre().size();
//			new_topic("g", g);
////			for  (int i = 0; i<g; i ++)
////			{
////				Kategorien k = gks.gibKategorienDaten(gks.gibGenreDaten().getGenre().get(i).getGenreId());
////				new_topic("g"+i+"_k", k.getKategorie().size());
////			}
//		}
//		// TODO: vorhandene Themes sind noch nicht drin
//		System.out.println(topics.size());
//		for (int i = 0 ; i< topics.size();i++)
//		System.out.println(topics.get(i));
	}
	
	public void new_topic(String typ, int anz)
	{
		for (int i=0; i<anz; i++)
		{
			try
			{
			    ConfigureForm form = new ConfigureForm(FormType.submit);
			    form.setAccessModel(AccessModel.authorize); // nur Leute, die supcripton request must be approves and only subscribers may retrieve items
			    form.setDeliverPayloads(true); // Sets whether the node will deliver payloads with event notifications.
			    form.setNotifyRetract(true); // Determines whether subscribers should be notified when items are deleted from the node.
			    form.setPersistentItems(false);
			    form.setPublishModel(PublishModel.open); // jeder darf publishen
			    form.setNotifyDelete(true); // wenn node gelöscht wird, wir subscriber benachrichtigt
			    form.setNotifyRetract(true); // wenn items des nodes gelöscht werden
			    form.setSubscribe(true); // ob man das subsciben kann
			    LeafNode topic = (LeafNode) pubsub_mgr.createNode(typ+i, form);
				topics.add(topic);	
			}
			
			catch (XMPPException e)
			{
				e.printStackTrace();
				System.err.println("Topic konnte nicht angelegt werden.");
			}	
		}
	}
	
	public void new_topic(String typ) throws XMPPException
	{
		new_topic(typ,1);
	}
		
	public void delete_topic(String to_delete_topic)
	{
		try
		{
			for (LeafNode curr_topic: topics)
			{
				if (curr_topic.equals(to_delete_topic))
				{
					curr_topic.removeItemDeleteListener(this);
					curr_topic.removeItemEventListener(this);
					pubsub_mgr.deleteNode(to_delete_topic);
					topics.remove(curr_topic);
				}
			}
		}
		
		catch (XMPPException e)
		{
			e.printStackTrace();
			System.err.println("Topic konnte nicht gelöscht werden.");
		}
	}
	
	private void topics_clear()
	{
		for(LeafNode curr_topic: topics)
		{
			try
			{
				pubsub_mgr.deleteNode(curr_topic.getId());
				topics.remove(curr_topic);
				System.out.println("Topic Anzahl ist wieder auf "+ topics.size());
			}
			
			catch (XMPPException e)
			{
				e.printStackTrace();
				System.err.println("Topics konnten nicht gelöscht werden.");
			}
		}
	}

	public void abonnieren(String topic_id)
	{
		try {
			LeafNode abo = pubsub_mgr.getNode(topic_id);
			
			if (!abonniert(abo))
			{
				abo.addItemEventListener(this); // für jedes abo ein neues?
				if (abo.toString().substring(0,1).equals("t")) // falls dies ein Theme ist
					abo.addItemDeleteListener(this);
				abo.subscribe(meineJID());	
			}
		}
		
		catch (XMPPException e)
		{
			e.printStackTrace();
			System.out.println("Topic konnte nicht abonniert werden.");
		}

		
		
		
	}
	
	private boolean abonniert(LeafNode abo)
	{
		
		try
		{
			List<Subscription> abonennten;
			abonennten = abo.getSubscriptions();
			for (Subscription curr_abonennt: abonennten)
				if (curr_abonennt.getJid().equals(meineJID()))
				{
					System.err.println("Dieser Topic wurde bereits abonniert.");
					return true;
				}
		}
		
		catch (XMPPException e)
		{
			e.printStackTrace();
			System.err.println("Abonnement konnte nicht überprüft werden.");
		}
		return false;
	}

	public void abo_kuendigen(LeafNode abo)
	{
		try {
			abo.removeItemEventListener(this);
			if (abo.toString().substring(0,1).equals("t")) // falls dies ein Theme ist
				abo.removeItemDeleteListener(this);
			abo.unsubscribe(meineJID());
		}
		
		catch (XMPPException e)
		{
			e.printStackTrace();
			System.err.println("Abonemment konnte nicht gekündigt werden.");
		}
	}

	public void ausloggen()
	{
		con.disconnect();
		System.out.println(user + "Ausgeloggt.");
	}
	
	public void theme_item_hinzufügen(String t_id, String item_id) throws XMPPException
	{
		LeafNode theme = pubsub_mgr.getNode(t_id);

//		//publishen ohne payload
//		node.send(new Item());
		
		// Publish an Item with payload
		theme.send(new PayloadItem( item_id, new SimplePayload("root element of the payload (IQ, Message, Presence)", "namespace", "(xml)payload string"))); // item = id + payload (stores payload as a string)
	}
	
	public void theme_item_loeschen(String t_id, String item_id) throws XMPPException
	{
		LeafNode theme= pubsub_mgr.getNode(t_id);
		theme.deleteItem(item_id);
	}
	
	public void bestimmte_items_holen(String topic_id, String []items) throws XMPPException // TODO
	{
	    LeafNode node = pubsub_mgr.getNode(topic_id);
	    Collection<String> item_ids = new ArrayList<String>(items.length);
	    for (String item_id: items)
	    	item_ids.add(item_id);
	      
	    List<? extends Item> item_list = node.getItems(item_ids);
	}
    
	private String meineJID() {
		return con.getUser()+"@"+con.getHost();
	}
	
	/**
	 * @param args
	 * @throws XMPPException 
	 * @throws JAXBException 
	 */
	public static void main(String[] args) throws JAXBException {
		try
		{
			XMPPConnection con = new XMPPConnection(server);
			con.connect();
			con.login(user, pw);
			new PartyClient(con);
		}
		catch (XMPPException e)
		{
			e.printStackTrace();
			System.err.println("Login nicht erfolgreich.");
		}
//		pc.ausloggen();
		System.out.println("Login erfolgreich.");
	}
	
	class ItemEventCoordinator<Item> implements ItemEventListener
    {
        @Override
        public void handlePublishedItems(ItemPublishEvent items)
        {
            System.out.println("Item count: " + items.getItems().size());
            System.out.println(items);
		}
       
	}
	
	class ItemDeleteCoordinator<Item> implements ItemDeleteListener
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

	@Override
	public void handlePublishedItems(ItemPublishEvent items_event) { // TODO 
		List<Item> item_list = items_event.getItems();
//		PayloadItem<SimplePayload> pi = null;
//		for (int i=0; i<itemlist.size(); i++) {
//			// Die Stelle kÃ¶nnte Probleme machen
//			pi = (PayloadItem<SimplePayload>) itemlist.get(i);
//			notifications.add(pi);
//		}
//		updateNotificationsJList();
		System.out.println("Neues Item wurde hinzugefügt");
		
	}

	@Override
	public void handleDeletedItems(ItemDeleteEvent items_event) { // TODO 
		List<String> item_list = items_event.getItemIds();
		for (int i=0; 0<item_list.size(); i++)
			System.out.println(item_list.get(i) + " wurde gelöscht.");
		
	}

	@Override
	public void handlePurge() { // TODO: Called when all items are deleted from a node the listener is registered with.
		System.out.println("Gesamtes Theme wurde gelöscht.");
	}
	

}

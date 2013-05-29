package client;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.pubsub.AccessModel;
import org.jivesoftware.smackx.pubsub.CollectionNode;
import org.jivesoftware.smackx.pubsub.ConfigureForm;
import org.jivesoftware.smackx.pubsub.FormType;
import org.jivesoftware.smackx.pubsub.Item;
import org.jivesoftware.smackx.pubsub.ItemDeleteEvent;
import org.jivesoftware.smackx.pubsub.ItemPublishEvent;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.PublishModel;
import org.jivesoftware.smackx.pubsub.listener.ItemDeleteListener;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;

public class PartyClient {
	
	// JID (alice@exmaple.de/home) zur Adressierung von Entitäten innerhalb einesd XMPP-Netzwerkes

	public PartyClient() throws XMPPException {	}
	
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
	
	Connection con = new XMPPConnection("example.com");
	
//	// Collectionnodes Erstellen
//	CollectionNode coll_genres = new CollectionNode(con, "genres_list");
//	CollectionNode coll_genre = new CollectionNode(con, "genre_list");
//	CollectionNode coll_kategorie = new CollectionNode(con, "kategorie_list");
	
	
	PubSubManager mgr = new PubSubManager(con);
	LeafNode genres_leaf= mgr.createNode();
    LeafNode kategorien_leaf = mgr.createNode();
    LeafNode themes_leaf = mgr.createNode();
	
	void publish_theme(String node_id, String kategorie) throws XMPPException
	{

	    LeafNode new_theme = mgr.createNode(node_id);
	    ConfigureForm form = new ConfigureForm(FormType.submit);
	    form.setAccessModel(AccessModel.authorize); // nur Leute, die supcripton request must be approves and only subscribers may retrieve items
	    form.setDeliverPayloads(true); // Sets whether the node will deliver payloads with event notifications.
	    form.setNotifyRetract(true); // Determines whether subscribers should be notified when items are deleted from the node.
	    form.setPersistentItems(false);
	    form.setPublishModel(PublishModel.open); // jeder darf publishen
	    form.setCollection(kategorie); // ??
	    form.setNotifyDelete(true); // wenn node gelöscht wird, wir subscriber benachrichtigt
	    form.setNotifyRetract(true); // wenn items des nodes gelöscht werden
	    form.setSubscribe(true); // ob man das subsciben kann
	    //form.setItemreply, setMaxItems, setNodeType,  
	     
	    new_theme.sendConfigurationForm(form); // Konfigurationen speichern            
	}
	
	void receive_pubsub(String node_id, String myJid, String to_delete) throws XMPPException
	{
		LeafNode node = mgr.getNode("node_id");
		node.addItemEventListener(new ItemEventCoordinator<Item>());
	    node.subscribe(myJid);
	    
	    node.addItemDeleteListener(new ItemDeleteCoordinator<Item>());
	    node.deleteItem(to_delete);
	}
	
	void retrieve_pubsub() throws XMPPException
	{
		
	}
	

      
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

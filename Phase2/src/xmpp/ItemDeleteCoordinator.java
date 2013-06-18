package xmpp;


import java.util.List;

import org.jivesoftware.smackx.pubsub.Item;
import org.jivesoftware.smackx.pubsub.ItemDeleteEvent;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.SimplePayload;
import org.jivesoftware.smackx.pubsub.listener.ItemDeleteListener;

class ItemDeleteCoordinator implements ItemDeleteListener
{
    @Override
    public void handleDeletedItems(ItemDeleteEvent items)
    {
//    	List<Item> itemlist = items.getItems();
//		PayloadItem<SimplePayload> pi = null;
//		for (int i=0; i<itemlist.size(); i++) {
//			pi = (PayloadItem<SimplePayload>) itemlist.get(i);
//			PartyClient.benachrichtigungen.add(pi.toString());
//			System.out.println("item deleted");
//		}
    }
    
    @Override
    public void handlePurge()
    {
        System.out.println("All items have been deleted from node");
    }
}

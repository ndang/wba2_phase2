package xmpp;

import java.util.List;

import org.jivesoftware.smackx.pubsub.Item;
import org.jivesoftware.smackx.pubsub.ItemPublishEvent;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.SimplePayload;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;

import client.XmppClient;

public class ItemEventCoordinator implements ItemEventListener<Item>
{
	// TODO: persistente Speicherung ?
    @Override
    public void handlePublishedItems(ItemPublishEvent<Item> items)
    {
    	List<Item> itemlist = items.getItems();
		PayloadItem<SimplePayload> pi = null;
//		for ( Item item : itemlist )
//		{
//			benachrichtigungen.add( items.getPublishedDate()+", " + items.getNodeId() + ": " + item.getElementName().toString() + " " + item.getNode().toString());
//			System.out.println("New item arrived!");
//		}
		for ( int i=0; i<itemlist.size(); i++ )
		{
			pi = (PayloadItem<SimplePayload>) itemlist.get(i);
			XmppClient.benachrichtigungen.add( itemlist.get(i).getId()+": " + pi.getPayload().getElementName().toString());
			System.out.println("New item arrived!"); 
		}
	}
   
}
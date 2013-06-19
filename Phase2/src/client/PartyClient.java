package client;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import GUI.Login;
import GUI.PartyGUI;

public class PartyClient {

	public static RestClient rc;
	public static XmppClient xc;
	public static PartyGUI pg;
	public static Connection con;
	private String server = "localhost";

	public PartyClient()
	{
		try
		{
//			XMPPConnection.DEBUG_ENABLED=true;
			con = new XMPPConnection( server );
			con.connect();
			con.login( Login.getUser(), Login.getPassw() );
			System.out.println( "Login erfolgreich." );
		} catch (XMPPException e) {
			e.printStackTrace();
			System.err.println("\nLogin fehlgeschlagen.");
		}
		
		this.rc = new RestClient();
		this.xc = new XmppClient();
	}
	
	public void logout()
	{
		rc.disconnectRestSrv();
		con.disconnect();
		System.out.println( Login.getUser() + " ausgeloggt." );
	}
}
	

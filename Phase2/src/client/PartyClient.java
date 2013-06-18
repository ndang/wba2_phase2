package client;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class PartyClient {

	public RestClient rc;
	public XmppClient xc;
	
	private String user = "user1";
	private String pw = "user1user1";
	private String server = "localhost";
	private Connection con;

	public PartyClient()
	{
		try
		{
//			XMPPConnection.DEBUG_ENABLED=true;
			con = new XMPPConnection( server );
			con.connect();
			con.login( user, pw );
			System.out.println( "Login erfolgreich." );
		} catch (XMPPException e) {
			e.printStackTrace();
			System.err.println("\nLogin fehlgeschlagen.");
		}
		
		this.rc = new RestClient();
		this.xc = new XmppClient( (XMPPConnection)con, rc);
	}
	
	public void logout()
	{
		rc.disconnectRestSrv();
		con.disconnect();
		System.out.println( user + " ausgeloggt." );
	}
}

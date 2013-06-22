package webservices;

import java.io.IOException;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyServerFactory;

public class RestServer
{
	SelectorThread srv;
	String url;
	
	public RestServer()
	{
		url = "http://localhost:4434";
		
		try
		{
			srv = GrizzlyServerFactory.create( url );
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
			System.err.println("REST-Server konnte nicht gestartet werden!");
		}
	}

	public void disconnect()
	{
		srv.stopEndpoint();
		System.out.println("REST-Server terminiert."); 
	}

}

package webservices;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyServerFactory;

public class RestServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
			String url = ( args.length > 0 ) ? args[0] : "http://localhost:4434";
			String sec = ( args.length > 1 ) ? args[1] : "60"; // eine Minute bis Server terminiert

	    	SelectorThread srv = GrizzlyServerFactory.create( url ); // Server instanziiert 
	      // Factory for creating and starting Grizzly SelectorThread instances.
	      // Creates a SelectorThread that registers an Adapter that in turn manages all root resource and provider classes found by searching the classes referenced in the java classpath.
	      // Returns: the select thread, with the >>>>>endpoint started<<<<<
	      // The SelectorThread class is the entry point when embedding the Grizzly Web Server. All Web Server configuration must be set on this object before invoking the listen() method. 

	    	System.out.println( "Server-URL: " + url ); // Einfache Ausgabe
	    	Thread.sleep( 1000 * Integer.parseInt( sec ) ); // Zeit bevor Server abläuft
	    	srv.stopEndpoint(); //>>>>>endpoint ended<<<<<
	    	System.out.println("Server terminiert."); // einfache Ausgabe

	}

}

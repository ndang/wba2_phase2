package webservice;

import javax.ws.rs.core.MediaType;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class PartyClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		  String url = ( args.length > 0 ) ? args[0] : "http://localhost:4434/users"; // Wenn eine übergeben wurde, dann wird die aufgerufen, wenn nicht die hier angegebene 
	      System.out.println( "URL: " + url ); 

	      WebResource wrs = Client.create().resource( url ); //es wird erst ein Client instanziiert, welcher dann mit der methode resource ein Webressouce instanziiert

	      System.out.println("\nTextausgabe:"); //Gibt die Ressource dann aus
	      //System.out.println( wrs.accept(MediaType.APPLICATION_XML).get( String.class ) );
	      System.out.println( wrs.accept(MediaType.TEXT_PLAIN).get( String.class ) );
	      // accept : Add acceptable media types.
	      // get : gibt ergebnis als String zurück
	}

}

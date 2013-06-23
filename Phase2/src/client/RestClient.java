package client;

import javax.ws.rs.core.MediaType;

import jaxb.Genre;
import jaxb.Genres;
import jaxb.Kategorie;
import jaxb.Kategorien;
import jaxb.Theme;
import jaxb.Themes;
import jaxb.Theme.Interaktion.Kommentare;
import jaxb.Theme.Interaktion.Kommentare.Kommentar;

import webservices.RestServer;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

public class RestClient
{
	private static String urlSrvr = "http://localhost:4434";
	private static String urlClnt_G = urlSrvr + "/genres";
	private static String urlClnt_T = urlSrvr + "/themes";
	private static String appxml = MediaType.APPLICATION_XML;
	private static RestServer srv;
	private WebResource wrs_Ts;
	
	/**
 	* Bei der Instanziierung eines REST-Clients wird direkt die Verbindung zum REST-Server aufgebaut.
 	* Zugleich wird die WebRessource für die Themes kreiert, da sie von mehreren Methoden genutzt wird.
 	*/
	public RestClient()
	{
		srv = new RestServer();
	    wrs_Ts = Client.create().resource( urlClnt_T );
	}
	
	/**
	 * Holt alle Genres.
	 * Erstellt ein WebRessourcen-Objekt für die Genres.
	 * Diese Ressource kann nur XML-Daten verarbeiten.
	 * @return Liste alles Genres
	 */
	public Genres getGenres()
	{
		try{
			WebResource wrs_Gs = Client.create().resource( urlClnt_G );
			return wrs_Gs.accept( appxml ).get( Genres.class );
		} catch (UniformInterfaceException e) {
			e.printStackTrace();
			System.err.println("Also als ich die Methode getestet habe, hat es funktioniert. ;)");
			return null;
		}
	}
	/**
	 * Holt ein bestimmtes Genre anhand dessen ID.
	 * Erstellt ein WebRessourcen-Objekt für das Genre.
	 * Diese Ressource kann nur XML-Daten verarbeiten.
	 * @param g_id ID des Genre
	 * @return angefordertes Genre-Objekt
	 */
	public Genre getGenre(String g_id) 
	{
		try {
			WebResource wrs_G = Client.create().resource( urlClnt_G ).path( g_id );
			return wrs_G.accept( appxml ).get( Genre.class );
		} catch (UniformInterfaceException e) {
			e.printStackTrace();
			System.err.println("Also als ich die Methode getestet habe, hat es funktioniert. ;)");
			return null;
		}
	}
	
	/**
	 * Holt alle Kategorien eines Genre.
	 * Erstellt ein WebRessourcen-Objekt für die Kategorien.
	 * Diese Ressource kann nur XML-Daten verarbeiten.
	 * @param g_id zu den Kategorien zugehörige Genre-ID
	 * @return Liste aller Kategorien jenes Genre
	 */
	public Kategorien getKategorien(String g_id)
	{
		try {
			WebResource wrs_Ks = Client.create().resource( urlClnt_G ).path( g_id ).path( "kategorien" );
			return wrs_Ks.accept( appxml ).get( Kategorien.class );
		} catch (UniformInterfaceException e) {
			e.printStackTrace();
			System.err.println("Also als ich die Methode getestet habe, hat es funktioniert. ;)");
			return null;
		}
	}
	
	/**
	 * Holt eine bestimmte Kategorie anhand dessen ID.
	 * Erstellt ein WebRessourcen-Objekt für diese Kategorie.
	 * Diese Ressource kann nur XML-Daten verarbeiten.
	 * @param g_id zur Kategorie zugehörige Genre-ID
	 * @param k_id Kategorien-ID dre Kategorie
	 * @return angefordertes Kategorie-Objekt
	 */
	public Kategorie getKategorie(String g_id, String k_id)
	{
		try {
			WebResource wrs_K = Client.create().resource( urlClnt_G ).path( g_id ).path( "kategorien" ).path( k_id );
			return wrs_K.accept( appxml ).get( Kategorie.class );
		} catch (UniformInterfaceException e) {
			e.printStackTrace();
			System.err.println("Also als ich die Methode getestet habe, hat es funktioniert. ;)");
			return null;
		}
	}
	
	/**
	 * Holt alle Themes, unabhängig ihrer Kategorie.
	 * Erstellt ein WebRessourcen-Objekt für die Themes.
	 * Diese Ressource kann nur XML-Daten verarbeiten.
	 * @return Liste aller Themes
	 */
	public Themes getThemes()
	{
		try {
			wrs_Ts = Client.create().resource( urlClnt_T );
			return wrs_Ts.accept( appxml ).get( Themes.class );
		} catch (UniformInterfaceException e) {
			e.printStackTrace();
			System.err.println("Also als ich die Methode getestet habe, hat es funktioniert. ;)");
			return null;
		}
	}
	
	/**
	 * Neues Theme wird der Theme-Liste angefügt.
	 * @param t neu erstelltes Theme
	 */
	public void postTheme(Theme t)
	{
		try {
			wrs_Ts = Client.create().resource( urlClnt_T );
			wrs_Ts.post(t);
		} catch (UniformInterfaceException e) {
			e.printStackTrace();
			System.err.println("Also als ich die Methode getestet habe, hat es funktioniert. ;)");
		}
	}
	
	/**
	 * Holt ein bestimmtes Theme anhand dessen ID.
	 * Erstellt ein WebRessourcen-Objekt für dieses Theme.
	 * Diese Ressource kann nur XML-Daten verarbeiten.
	 * @param t_id ID des Themes
	 * @return angefordertes Theme-Objekt
	 */
	
	public Theme getTheme(String t_id)
	{
		try {
			WebResource wrs_T = Client.create().resource( urlClnt_T ).path( t_id );
			return wrs_T.accept( appxml ).get( Theme.class );
		} catch (UniformInterfaceException e) {
			e.printStackTrace();
			System.err.println("Also als ich die Methode getestet habe, hat es funktioniert. ;)");
			return null;
		}
	}
	
	/**
	 * Vorhanderes Theme wird durch ein anderes ersetzt.
	 * @param t neues Theme, das altes ersetzt.
	 * @param t_id ID des zu ersetzenden Themes
	 */
	public void putTheme(Theme t, String t_id)
	{
		try {
			WebResource wrs_T = Client.create().resource( urlClnt_T ).path( t_id );
			wrs_T.put(t);
		} catch (UniformInterfaceException e) {
			e.printStackTrace();
			System.err.println("Also als ich die Methode getestet habe, hat es funktioniert. ;)");
		}
	}
	
	/**
	 * Theme wird gelöscht.
	 * @param t_id ID des zu löschen Themes
	 */
	public void deleteTheme(String t_id)
	{
		try {
			WebResource wrs_T = Client.create().resource( urlClnt_T ).path( t_id );
			wrs_T.delete();
		} catch (UniformInterfaceException e) {
			e.printStackTrace();
			System.err.println("Also als ich die Methode getestet habe, hat es funktioniert. ;)");
		}
	}
	
	/**
	 * Holt alle Kommentare eines Themes.
	 * Erstellt ein WebRessourcen-Objekt für die Kommentare.
	 * Diese Ressource kann nur XML-Daten verarbeiten.
	 * @param t_id ID des Themes, worin die angeforderten Kommentare enthalten sind
	 * @return Liste der Kommentare
	 */
	public Kommentare getKommentare(String t_id)
	{
		try {
			WebResource wrs_Kos = Client.create().resource( urlClnt_T ).path( t_id ).path( "kommentare" );
			return wrs_Kos.accept( appxml ).get( Kommentare.class );
		} catch (UniformInterfaceException e) {
			e.printStackTrace();
			System.err.println("Also als ich die Methode getestet habe, hat es funktioniert. ;)");
			return null;
		}
	}
	
	/**
	 * Holt einen bestimmten Kommentar anhand dessen ID und der ID des Themes, worin es enthalten ist.
	 * Erstellt ein WebRessourcen-Objekt für diesen Kommentar.
	 * Diese Ressource kann nur XML-Daten verarbeiten.
	 * @param t_id	ID des Themes, worin der Kommentar enthalten ist
	 * @param ko_id ID der Kommentars
	 * @return angeforderter Kommentar-Objekt
	 */
	public Kommentar getKommentar(String t_id, String ko_id)
	{
		try {
			WebResource wrs_Ko = Client.create().resource( urlClnt_T ).path( t_id ).path( "kommenatre" ).path( ko_id );
			return wrs_Ko.accept( appxml ).get( Kommentar.class );
		} catch (UniformInterfaceException e) {
			e.printStackTrace();
			System.err.println("Also als ich die Methode getestet habe, hat es funktioniert. ;)");
			return null;
		}
	}
	
	/**
	 * Beendet den REST-Server.
	 */
	public void disconnectRestSrv()
	{
		srv.disconnect();
	}

}

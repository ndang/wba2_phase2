package client;

import javax.ws.rs.core.MediaType;

import webservices.RestServer;
import app.Genre;
import app.Genres;
import app.Kategorie;
import app.Kategorien;
import app.Theme;
import app.Theme.Interaktion.Kommentare;
import app.Theme.Interaktion.Kommentare.Kommentar;
import app.Themes;

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
 	* Erstellt einen Grizzly Server
 	*/
	public RestClient()
	{
		srv = new RestServer();
	    wrs_Ts = Client.create().resource( urlClnt_T );
	}
	/**
	 * Erstellt mihilfe des Jersey Frameworks aus Ressourcen
	 * Java Objekte. Dafür wird die URI benötigt, die über ressource übergeben wird.
	 * Als mimetype appxml. Beim get muss Typ des Rückgabeobjektes übergeben werden.	
	 * @return gibt Objekt der Ressource zurück
	 */
	public Genres getGenres()
	{
		try {
			WebResource wrs_Gs = Client.create().resource( urlClnt_G );
			return wrs_Gs.accept( appxml ).get( Genres.class );
		} catch (UniformInterfaceException e) {
			e.printStackTrace();
			System.err.println("Also als ich die Methode getestet habe, hat es funktioniert. ;)");
			return null;
		}
	}
	/**
	 * Wie in public Genres getGenres(), nur dass path um die genre ID erweitert wurde.
	 * @param g_id GEnre ID
	 * @return Gibt Objekt der Ressource zurück
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
	 * Wie obere Methoden, nur das aus Kategorien Objekte erstellt werden 
	 * @param g_id Genre ID
	 * @return gibt Objekt der Ressource zurück
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
	 * 
	 * @param g_id Genre ID
	 * @param k_id Kategorien ID
	 * @return
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
	 * erstellt mit Jersey Framework aus Theme Ressource, Theme Java Objekt
	 * @return
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
	 * Post wird aufgerufen
	 * @param t theme
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
	 * siehe obige Methoden
	 * @param t_id themeID
	 * @return
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
	 * wie obige methoden, nur mit PUT
	 * @param t
	 * @param t_id
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
	 * Wie oben, nur mit DELETE
	 * @param t_id
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
	 * 
	 * @param t_id
	 * @return
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
	 * 
	 * @param t_id	ThemeID
	 * @param ko_id kommentarID
	 * @return
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
	 * Rest Server beenden
	 */
	public void disconnectRestSrv()
	{
		srv.disconnect();
	}

}

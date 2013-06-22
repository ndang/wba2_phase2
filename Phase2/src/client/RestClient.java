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

	public RestClient()
	{
		srv = new RestServer();
	    wrs_Ts = Client.create().resource( urlClnt_T );
	}
		
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
	
	public void disconnectRestSrv()
	{
		srv.disconnect();
	}

}

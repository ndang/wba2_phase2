package client;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import app.*;
import app.Genres.Genre;
import app.Kategorien.Kategorie;
import app.Themes.Theme;
import app.Themes.Theme.Interaktion.Kommentare;
import app.Themes.Theme.Interaktion.Kommentare.Kommentar;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.container.grizzly.GrizzlyServerFactory;
import org.junit.*;

public class WebServiceClient
{
	
	private static String urlSrvr = "http://localhost:4434";
	private static String urlClnt_G = urlSrvr + "/genres";
	private static String urlClnt_T = urlSrvr + "/themes";
	
	private static SelectorThread srv;
	
	private WebResource wrs_Ts;
	
	private static String appxml = MediaType.APPLICATION_XML;
	
	public WebServiceClient()
	{
	    try
	    {
			srv = GrizzlyServerFactory.create( urlSrvr );
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
			System.err.println("\n\n:(\n\n");
		}
	    
	    wrs_Ts = Client.create().resource( urlClnt_T );
	}
	
	// TODO: get's mit pathparams
	// TODO: put, post, delete testen + Fehlerfall
	
	public Genres getGenres()
	{
		WebResource wrs_Gs = Client.create().resource( urlClnt_G );
		return wrs_Gs.accept( appxml ).get( Genres.class );
	}
	
	public Genre getGenre(String g_id)
	{
		WebResource wrs_G = Client.create().resource( urlClnt_G ).path( g_id );
		return wrs_G.accept( appxml ).get( Genre.class );
	}
	
	public Kategorien getKategorien(String g_id)
	{
		WebResource wrs_Ks = Client.create().resource( urlClnt_G ).path( g_id ).path( "kategorien" );
		return wrs_Ks.accept( appxml ).get( Kategorien.class );
	}
	
	public Kategorie getKategorie(String g_id, String k_id)
	{
		WebResource wrs_K = Client.create().resource( urlClnt_G ).path( g_id ).path( "kategorien" ).path( k_id );
		return wrs_K.accept( appxml ).get( Kategorie.class );
	}
	
	public Themes getThemes()
	{
		wrs_Ts = Client.create().resource( urlClnt_T );
		return wrs_Ts.accept( appxml ).get( Themes.class );
	}
	
	public boolean postTheme(Theme t)
	{
		wrs_Ts.post(t);
		return true;
	}
	
	public Theme getTheme(String t_id)
	{
		WebResource wrs_T = Client.create().resource( urlClnt_T ).path( t_id );
		return wrs_T.accept( appxml ).get( Theme.class );
	}
	
	public boolean putTheme(Theme t, String t_id)
	{
		WebResource wrs_T = Client.create().resource( urlClnt_T ).path( t_id );
		wrs_T.put(t);
		return true;
	}
	
	public boolean deleteTheme(Theme t, String t_id)
	{
		WebResource wrs_T = Client.create().resource( urlClnt_T ).path( t_id );
		wrs_T.delete();
		return true;
	}
	
	public Kommentare getKommentare(String t_id)
	{
		WebResource wrs_Kos = Client.create().resource( urlClnt_T ).path( t_id ).path( "kommentare" );
		return wrs_Kos.accept( appxml ).get( Kommentare.class );
	}
	
	public Kommentar getKommentar(String t_id, String ko_id)
	{
		WebResource wrs_Ko = Client.create().resource( urlClnt_T ).path( t_id ).path( "kommenatre" ).path( ko_id );
		return wrs_Ko.accept( appxml ).get( Kommentar.class );
	}

}

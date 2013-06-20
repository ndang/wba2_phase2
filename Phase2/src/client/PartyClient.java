package client;

import java.util.Vector;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import app.Genre;
import app.Kategorie;
import app.Theme;

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
//			XMPPConnection.DEBUG_ENABLED = true;
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

	public String getIDByName(String name)
	{
		String id = "";
		
		if ( name.substring(0,1).equals("g") )
			id = name.substring(0,2);
		
		else if ( name.substring(0,1).equals("k") )
			id = name.substring(0, 5);
		
		else if ( name.substring(0,1).equals("t") )
			id = name.substring(0,8);
		
		return id;
	}

	public String getNameByID(String id)
	{
		String name = "";
		if ( id.substring(0,1).equals("g") )
			name = id + " " + getGenreTitel(id);
		
		else if ( id.substring(0,1).equals("k") )
			name = id + " " + getKategorieTitel( id.substring(3, 5), id.substring(0,5));
		
		else if ( id.substring(0,1).equals("t") )
			name = id + " " + getThemeTitel(id);
		
		return name;
	}
	
	public String getGenreTitel(String g_id)
	{
		return rc.getGenre(g_id).getGenreTitel();
	}
	
	public String getKategorieTitel(String g_id, String k_id)
	{
		return rc.getKategorie(g_id, k_id).getKategorieTitel();
	}
	
	public String getThemeTitel(String t_id)
	{
		return rc.getTheme(t_id).getAllgemeines().getThemeTitel().toString();
	}

	public boolean isGenre(String id)
	{
		if ( id.substring(0,1).equals("g"))
			return true;
		return false;
	}
	
	public boolean isKategorie(String id)
	{
		if ( id.substring(0,1).equals("k"))
			return true;
		return false;
	}
	
	public boolean isTheme(String id)
	{
		if ( id.substring(0,1).equals("t"))
			return true;
		return false;
	}

	public Vector<String> genresTitles()
	{
		Vector<String> genres_liste = new Vector<String>();
		for (Genre g : rc.getGenres().getGenre())
			genres_liste.add( g.getGenreId() + " " + g.getGenreTitel() );
		return genres_liste;
	}
	
	public Vector<String> kategorienTitles(String g_id)
	{
		Vector<String> kategorien_liste = new Vector<String>();
		for ( Kategorie k : rc.getKategorien(g_id).getKategorie() )
			kategorien_liste.add( k.getKategorieId() + " " + k.getKategorieTitel() );
		return kategorien_liste;
	}
	
	public Vector<String> themesTitles( String g_id, String k_id )
	{
		Vector<String> theme_liste = new Vector<String>();
		for (Theme t : rc.getThemes().getTheme())
		{
			if ( t.getAllgemeines().getThemeId().substring(6).equals(g_id) )
			{
				if (t.getAllgemeines().getThemeId().substring(3,5).equals(k_id) )
					theme_liste.add( t.getAllgemeines().getThemeId() + " " + t.getAllgemeines().getThemeTitel() );
			}		
		}
		
		if ( theme_liste.isEmpty() ) theme_liste.add("keine Themes vorhanden");
			
		return theme_liste;
	}
}
	

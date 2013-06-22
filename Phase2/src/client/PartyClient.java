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

	/**
	 * Eine Verbindung zum REST-Client und XMPP Client wird hergestellt
	 * Login Daten werden angefordert: Login Name + Passwort
	 * Bei erfolgreichem Einloggen, entsprechende Meldung
	 * Bei falschen Daten, entsprechende Meldung 
	 */
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

/**
 * Holt die IDs �ber die zugeh�rigen Namen
 * @param name 
 * @return gibt die ID zum passenden Namen zur�ck
 */
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
/**
 * Holt die Namen der Ressourcen �ber die IDs
 * @param id der Ressource
 * @return gitb den Namen der Ressource zur�ck
 */
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
	
	/**
	 * Pr�ft, um welchen Titel es sich handelt
	 * Holt den Titel der entsprechenden Ressource 
	 * @param id der Ressource
	 * @return gibt passende id, den dazugeh�rigen string zur�ck,
	 * 			
	 */
	public String getTitle(String id)
	{
		if ( isGenre(id) )
			return id + " " + getGenreTitel(id);
		else if ( isKategorie(id) )
			return id + " " + getKategorieTitel( id.substring(3), id);
		return id + " " + getThemeTitel(id);
	}
	
	/**
	 * Holt den Titel des Genre, greift dabei auf den RESTclient zu
	 * @param g_id GenreID
	 * @return gibt den Titel des Genres aus
	 */
	public String getGenreTitel(String g_id)
	{
		return rc.getGenre(g_id).getGenreTitel();
	}
	
	/**
	 * Holt den Titel der Kategorie, greift dabei auf den RESTclient zu
	 * @param g_id GenreID
	 * @param k_id ist abh�ngig von der GenreID
	 * @return gibt den Titel der Kategorie aus
	 */
	public String getKategorieTitel(String g_id, String k_id)
	{
		return rc.getKategorie(g_id, k_id).getKategorieTitel();
	}
	/**
	 * Holt den Titel eines Themes, greift dabei auf den RESTclient zu
	 * @param t_id ThemeID
	 * @return gibt den Titel aus
	 */
	public String getThemeTitel(String t_id)
	{
		return rc.getTheme(t_id).getAllgemeines().getThemeTitel().toString();
	}
	/**
 	* Pr�ft ob eingegebene ID ein Genre ist
 	* @param id
 	* @return Wenn ja, true
 	*/
	public boolean isGenre(String id)
	{
		if ( id.substring(0,1).equals("g"))
			return true;
		return false;
	}
	/**
	 * Pr�ft ob eingegebene ID eine Kategorie ist
	 * @param id
	 * @return
	 */
	public boolean isKategorie(String id)
	{
		if ( id.substring(0,1).equals("k"))
			return true;
		return false;
	}
	/**
	 * Pr�ft, ob eingebene ID ein Theme ist.
	 * @param id
	 * @return
	 */
	public boolean isTheme(String id)
	{
		if ( id.substring(0,1).equals("t"))
			return true;
		return false;
	}
	/**
	 * Legt einen neuen Vektor an, indem die Titel hinzugef�gt werden
	 * @param ids
	 * @return gibt Titel
	 */
	public Vector<String> getTitles(Vector<String> ids)
	{
		Vector<String> names = new Vector<String>();
		for ( String id : ids )
			names.add( getTitle(id) );
		return names;
	}
 	/**
 	 * Legt einen neuen Vektor an, holt �ber RESTclient die Titel der Genres, f�gt diese hinzu
 	 * @return gibt GenreTitel aus
 	 */
	public Vector<String> getGenresTitles()
	{
		Vector<String> genres_liste = new Vector<String>();
		for (Genre g : rc.getGenres().getGenre())
			genres_liste.add( g.getGenreId() + " " + g.getGenreTitel() );
		return genres_liste;
	}
	/**
	 * Legt einen neuen Vektor an, holt �ber RESTclient die Titel der Kategorien, 
	 * f�gt diese hinzu
	 * @param g_id
	 * @return Liste der Kategorien wird ausgegeben
	 */
	public Vector<String> getKategorienTitles(String g_id)
	{
		Vector<String> kategorien_liste = new Vector<String>();
		for ( Kategorie k : rc.getKategorien(g_id).getKategorie() )
			kategorien_liste.add( k.getKategorieId() + " " + k.getKategorieTitel() );
		return kategorien_liste;
	}
	/**
	 * Legt einen neuen Vektor an, holt �ber RESTclient die Themes Titel
	 * Pr�ft, ob die IDs aus den Listen �bereinstimmen
	 * oder ob �berhaupt Themes �berhaupt vorhanden sind
	 * @param g_id GenreID
	 * @param k_id KategorienID
	 * @return gibt liste aller themes zur�ck
	 */
	public Vector<String> getThemesTitles( String g_id, String k_id )
	{
		Vector<String> theme_liste = new Vector<String>();
		for (Theme t : rc.getThemes().getTheme())
		{
			System.out.println( t.getAllgemeines().getThemeId().substring(6));
			if ( t.getAllgemeines().getThemeId().substring(6).equals(g_id) )
			{
				if (t.getAllgemeines().getThemeId().substring(3).equals(k_id) )
					theme_liste.add( t.getAllgemeines().getThemeId() + " " + t.getAllgemeines().getThemeTitel() );
			}		
		}
		
		if ( theme_liste.isEmpty() ) theme_liste.add("keine Themes vorhanden");
			
		return theme_liste;
	}
	/**
	 * Pr�ft ob Benachrichtigungen vorhanden sind, greift auf XMPP client zu
	 * wenn nicht vorhanden wird ein neuer Vektor zum speichern von Benachrichtigungen
	 * angelegt.
	 * @return Vektor wird erstellt
	 */
	public Vector<String> getBenachrichtigungen()
	{
		if( !xc.benachrichtigungen.isEmpty() )
			return xc.benachrichtigungen;
		else
			return new Vector<String>();	
	}
	/**
	 * l�scht Benachrichtigungen
	 */
	public void deleteBenachrichtigungen()
	{
		xc.benachrichtigungen = new Vector<String>();
	}
	/**
	 * Greift auf den XMPP Client zu undgibt alle subcriptions aus
	 * @return
	 */
	public Vector<String> getMySubscriptions()
	{
		return xc.getSubscriptions();
	}
}
	

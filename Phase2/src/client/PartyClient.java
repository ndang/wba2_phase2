package client;

import java.util.Vector;

import jaxb.Beschreibung;
import jaxb.Beschreibung.Text;
import jaxb.Genre;
import jaxb.Kategorie;
import jaxb.Rezept;
import jaxb.Theme;
import jaxb.Theme.Allgemeines;
import jaxb.Theme.Allgemeines.Genres;
import jaxb.Theme.Allgemeines.Kategorien;
import jaxb.Theme.Module;
import jaxb.Theme.Module.Catering;
import jaxb.Theme.Module.Dekoration;
import jaxb.Theme.Module.Locations;
import jaxb.Theme.Module.Musik;
import jaxb.Theme.Module.Musik.Song;
import jaxb.Theme.Module.Outfits;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import GUI.Login;
import GUI.PartyGUI;

/**
 * Client der Party-App. Dient als Schnittstelle zwischen der GUI und den Services.
 *
 */
public class PartyClient
{
	public static RestClient rc;
	public static XmppClient xc;
	public static PartyGUI pg;
	public static Connection con;
	private String server = "localhost";

	/**
	 * Bei der Instantziierung eines Clients wird direkt versucht eine Verbindung zum XMPP- und REST-Server herzustellen.
	 * Die LogIn Daten bekommt der Client aus dem zuvor gestarteten LogIn-Screen.
	 * Je nach Erfolg der Anmeldung am XMPP-Server gibt der Client an die Kommandozeile eine Nachricht aus.
	 * REST-Client und XMPP-Client werden instanziiert, um später Anfragen von der GUI zu deligieren.
	 * Startet außerdem optional (auskommentierte Zeile) den Smack-Debugger.
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
		
		rc = new RestClient();
		xc = new XmppClient();
	}
	
	/**
	 * Beendet den REST-Server und trennt die Verbindung zum XMPP-Server.
	 * Gibt anschließend eine Bestätigungs-Meldung aus.
	 */
	public void logout()
	{
		rc.disconnectRestSrv();
		con.disconnect();
		System.out.println( Login.getUser() + " ausgeloggt." );
	}
	
	/**
	 * Holt die ID eines Topics anhand des Names.
	 * @param name Name des Topics
	 * @return ID des Topics
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
	 * Holt den Namen des Topics anhand der ID.
	 * @param id ID des Topics
	 * @return Name des Topics
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
	 * Holt den Titel eines Topics anhand dessen ID.
	 * Dabei besteht der Titel aus der vorangestellten ID und dem Namen des Topcis.
	 * Prüft dabei vorher, ob es sich um ein Genre, eine Kategorie oder ein Theme handelt.
	 * @param id ID des Topics
	 * @return gibt passende id, den dazugehörigen string zurück,			
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
	 * Holt den Titel des Genre.
	 * @param g_id ID des Genres
	 * @return Titel des Genre
	 */
	public String getGenreTitel(String g_id)
	{
		return rc.getGenre(g_id).getGenreTitel();
	}
	
	/**
	 * Holt den Titel der Kategorie.
	 * @param g_id ID des Genre
	 * @param k_id ID der Lategorie
	 * @return Titel der Kategorie
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
 	* Prüft ob das Topic mit der übergebenen ID ein Genre ist.
 	* @param id zu prüfende ID
 	* @return true, falls es sich um ein Genre handelt/ false, falls nicht
 	*/
	public boolean isGenre(String id)
	{
		if ( id.substring(0,1).equals("g") )
			return true;
		return false;
	}
	
	/**
	 * Prüft ob das Topic mit der übergebenen ID eine Kategorie ist.
	 * @param id zu prüfende ID
	 * @return true, falls es sich um eine Kategorie handelt/ false, falls nicht
	 */
	public boolean isKategorie(String id)
	{
		if ( id.substring(0,1).equals("k") )
			return true;
		return false;
	}
	
	/**
	 * Prüft, ob das Topic mit der übergebenen ID ein Theme ist.
	 * @param id zu prüfende ID
	 * @return true, falls es sich um ein Theme handelt/ false, falls nicht
	 */
	public boolean isTheme(String id)
	{
		if ( id.substring(0,1).equals("t"))
			return true;
		return false;
	}
	
	/**
	 * Holt eine Liste von Titeln. Ob von Genres, Kategorien oder Themes ist unbekannt.
	 * @param ids Liste der ID's von denen die Titel ermittelt werden sollen.
	 * @return Liste mit den verlangten Titel
	 */
	public Vector<String> getTitles(Vector<String> ids)
	{
		Vector<String> names = new Vector<String>();
		for ( String id : ids )
			names.add( getTitle(id) );
		return names;
	}
	
 	/**
 	 * Holt eine Liste der Titel aller Genres.
 	 * @return Liste der Titel aller Genres
 	 */
	public Vector<String> getGenresTitles()
	{
		Vector<String> genres_liste = new Vector<String>();
		for (Genre g : rc.getGenres().getGenre())
			genres_liste.add( g.getGenreId() + " " + g.getGenreTitel() );
		return genres_liste;
	}
	
	/**
	 * Holt eine Liste der Titel aller Kategorien.
	 * @param g_id zur Kategorie zugehörige Genre-ID
	 * @return Liste der Titel aller Kategorien
	 */
	public Vector<String> getKategorienTitles(String g_id)
	{
		Vector<String> kategorien_liste = new Vector<String>();
		for ( Kategorie k : rc.getKategorien(g_id).getKategorie() )
			kategorien_liste.add( k.getKategorieId() + " " + k.getKategorieTitel() );
		return kategorien_liste;
	}
	
	/**
	 * Holt eine Liste der Titel aller Themes.
	 * @param g_id zum Theme zugehörige Genre-ID
	 * @param k_id zum Theme zugehörige Kategorie-ID
	 * @return Liste der Titel aller Themes
	 */
	public Vector<String> getThemesTitles( String g_id, String k_id )
	{
		Vector<String> theme_liste = new Vector<String>();
		for ( Theme t : rc.getThemes().getTheme() )
		{
			if ( t.getAllgemeines().getThemeId().substring(6).equals(g_id) )
			{
				if ( t.getAllgemeines().getThemeId().substring(3).equals(k_id) )
					theme_liste.add( t.getAllgemeines().getThemeId() + " " + t.getAllgemeines().getThemeTitel() );
			}		
		}
		
		if ( theme_liste.isEmpty() ) theme_liste.add("keine Themes vorhanden");
			
		return theme_liste;
	}
	
	/**
	 * Holt die Liste der Benachrichtigungen.
	 * Falls keine vorhanden ist, wird eine leere Liste zurückgegeben.
	 * @return Liste der Benachrichtigungen
	 */
	public Vector<String> getBenachrichtigungen()
	{
		if( !XmppClient.benachrichtigungen.isEmpty() )
			return XmppClient.benachrichtigungen;
		else
			return new Vector<String>();	
	}
	
	/**
	 * Löscht alle Benachrichtigungen.
	 */
	public void deleteBenachrichtigungen()
	{
		XmppClient.benachrichtigungen = new Vector<String>();
	}
	
	/**
	 * Holt alle Abonnements des eingeloggten Users.
	 * @return Lister der Abonnements
	 */
	public Vector<String> getMySubscriptions()
	{
		return xc.getSubscriptions();
	}
	
	/**
	 * Erstellt eine ID für ein neu angelegtes Theme.
	 * Die ID ist abhängig vom zugehörigem Genre und von der zugehörigen Kategorie.
	 * @param g_id zum Theme zugehörige Genre-ID
	 * @param k_id zum Theme zugehörige Kategorie-ID
	 * @return ID für das neue Theme
	 */
	public String createTID(String k_id)
	{
		return "t"+ (XmppClient.anz_t++) + "_" + k_id;
	}
	
	/**
	 * Verändert die Daten eines Themes.
	 * Erstellt neue JAXB Elemente und fügt diese einem vorhandenem Theme an.
	 * @param t_id ID des Themes
	 * @param deko Dekorations-Daten an das Theme
	 * @param cate Catering-Daten an das Theme
	 * @param music Musik-Daten an das Theme
	 * @param outfits Outfits-Daten an das Theme
	 * @param loca Location-Daten an das Theme
	 * @return true, falls Theme geändert werden konnte
	 */
	public boolean putTheme(String t_id, String deko, String cate, String music, String outfits, String loca)
	{
		Theme t = rc.getTheme(t_id);
		
		Beschreibung b_D = new Beschreibung();
		b_D.setText(new Text ());
		b_D.getText().getSchritt().add(deko);
		b_D.setTitel(deko);
		t.getModule().getDekoration().getDekorationElement().add(b_D);
		
		Rezept r1 = new Rezept();
		r1.setRezeptname(cate);
		r1.setRezeptLink("www.chefkoch.de");
		t.getModule().getCatering().getGericht().add(r1);
		
		Song s1 = new Song();
		s1.setLink("www.youtube.de");
		s1.setSongInterpret("Interpret");
		s1.setSongTitel(music);
		t.getModule().getMusik().getSong().add(s1);
		
		Beschreibung b_O = new Beschreibung();
		b_O.setText(new Text());
		b_O.getText().getSchritt().add(outfits);
		b_O.setTitel(outfits);	
		t.getModule().getOutfits().getOutfit().add(b_O);
		
		Beschreibung b_L = new Beschreibung();
		b_L.setText(new Text());
		b_L.getText().getSchritt().add(loca);
		b_L.setTitel(loca);
		t.getModule().getLocations().getLocation().add(b_L);
		
		rc.putTheme(t, t_id);
		
		return true;
	}
	
	/**
	 * Erstellt anhand übergebener Daten ein neues Theme.
	 * Ein erstelltes Theme wird automatisch auch abonniert.
	 * @param k_id zum Theme zugehörige Kategorie, die notwendig ist um eine neue ID zu erstellen.
	 * @param titel Titel des neuen Themes
	 * @param deko Dekorations-Daten an das Theme
	 * @param cate cate Catering-Daten an das Theme
	 * @param music music Musik-Daten an das Theme
	 * @param outfits outfits Outfits-Daten an das Theme
	 * @param loca loca Location-Daten an das Theme
	 * @return ID des neuen Themes
	 */
	public String postTheme(String k_id, String titel, String deko, String cate, String music, String outfits, String loca)
	{
		String id = createTID( k_id );
		Theme t = new Theme();
		
		t.setAllgemeines(new Allgemeines());
		t.getAllgemeines().setBewertung(0);
		t.getAllgemeines().setGenres(new Genres());
//		t.getAllgemeines().getGenres().getGenre().get(0).setValue(g_id);  // Fehler im Schema! können keine Werte übergeben!
		t.getAllgemeines().setKategorien(new Kategorien());
//		t.getAllgemeines().getKategorien().getKategorie().get(0).setValue(k_id); // Fehler im Schema! können keine Werte übergeben!
		t.getAllgemeines().setThemeId(id);
		t.getAllgemeines().setThemeTitel(titel);
		
		t.setModule(new Module());
		t.getModule().setCatering(new Catering());
		Rezept r = new Rezept();
		r.setRezeptname(cate);
		r.setRezeptLink("www.chefkoch.de");
		t.getModule().getCatering().getGericht().add(r);
		
		t.getModule().setDekoration(new Dekoration());
		Beschreibung b = new Beschreibung();
		b.setText( new Text() ); // Fehler im Schema! können keine Werte übergeben!
		b.getText().getSchritt().add(deko);
		b.setTitel(deko);
		t.getModule().getDekoration().getDekorationElement().add(b);
		
		t.getModule().setMusik(new Musik());
		t.getModule().getMusik().getSong().add(new Song()); // Fehler im Schema! können keine Werte übergeben!
		
		t.getModule().setOutfits(new Outfits());
		Beschreibung b1 = new Beschreibung();
		b1.setText(new Text());
		b1.setTitel(outfits);
		t.getModule().getOutfits().getOutfit().add(b1);
		
		t.getModule().setLocations(new Locations());
		Beschreibung b2 = new Beschreibung();
		b2.setText(new Text());// Fehler im Schema! können keine Werte übergeben!
		b2.setTitel(loca);
		t.getModule().getLocations().getLocation().add(b2);
		
		rc.postTheme(t);
		
		xc.createTopic(id);
		xc.subscribe(id);
		
		return id;
	}
}
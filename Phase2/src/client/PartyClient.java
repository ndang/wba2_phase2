package client;

import java.util.List;
import java.util.Vector;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import app.Beschreibung;
import app.Beschreibung.Text;
import app.Genre;
import app.Kategorie;
import app.Rezept;
import app.Theme;
import app.Theme.Allgemeines;
import app.Theme.Allgemeines.Genres;
import app.Theme.Allgemeines.Kategorien;
import app.Theme.Module;
import app.Theme.Module.Catering;
import app.Theme.Module.Dekoration;
import app.Theme.Module.Locations;
import app.Theme.Module.Musik;
import app.Theme.Module.Musik.Song;
import app.Theme.Module.Outfits;

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
 * Holt die IDs über die zugehörigen Namen
 * @param name 
 * @return gibt die ID zum passenden Namen zurück
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
 * Holt die Namen der Ressourcen über die IDs
 * @param id der Ressource
 * @return gitb den Namen der Ressource zurück
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
	 * Prüft, um welchen Titel es sich handelt
	 * Holt den Titel der entsprechenden Ressource 
	 * @param id der Ressource
	 * @return gibt passende id, den dazugehörigen string zurück,
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
	 * @param k_id ist abhängig von der GenreID
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
 	* Prüft ob eingegebene ID ein Genre ist
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
	 * Prüft ob eingegebene ID eine Kategorie ist
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
	 * Prüft, ob eingebene ID ein Theme ist.
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
	 * Legt einen neuen Vektor an, indem die Titel hinzugefügt werden
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
 	 * Legt einen neuen Vektor an, holt über RESTclient die Titel der Genres, fügt diese hinzu
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
	 * Legt einen neuen Vektor an, holt über RESTclient die Titel der Kategorien, 
	 * fügt diese hinzu
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
	 * Legt einen neuen Vektor an, holt über RESTclient die Themes Titel
	 * Prüft, ob die IDs aus den Listen übereinstimmen
	 * oder ob überhaupt Themes überhaupt vorhanden sind
	 * @param g_id GenreID
	 * @param k_id KategorienID
	 * @return gibt liste aller themes zurück
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
	 * Prüft ob Benachrichtigungen vorhanden sind, greift auf XMPP client zu
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
	 * löscht Benachrichtigungen
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
	
	/**
	 * Erstellt topicID aus Genre und Kategorien ID
	 * @param g_id genre ID
	 * @param k_id KategorienID
	 * @return gitb TopicID zurück
	 */
	public String createTID(String g_id, String k_id)
	{
		return "t" + ( xc.anz_t++ ) + "_" + k_id + g_id;
	}
	
	public boolean putTheme(String t_id, String deko, String cate, String music, String outfits, String loca)
	{
		Theme t = rc.getTheme(t_id);
		
		List<Beschreibung> d_list = t.getModule().getDekoration().getDekorationElement();
		for ( Beschreibung b : t.getModule().getDekoration().getDekorationElement())
			t.getModule().getDekoration().getDekorationElement().remove(b);
		Beschreibung b_D = new Beschreibung();
		b_D.setText(new Text ()); // !!! FEHLER
		b_D.setTitel(deko);
		t.getModule().getDekoration().getDekorationElement().add(b_D);
		
		List<Rezept> r_list = t.getModule().getCatering().getGericht();
		for (Rezept r : r_list)
			t.getModule().getCatering().getGericht().remove(r);
		Rezept r1 = new Rezept();
		r1.setRezeptname(cate);
		r1.setRezeptLink("www.chefkoch.de");
		t.getModule().getCatering().getGericht().add(r1);
		
		List<Song> m_list = t.getModule().getMusik().getSong();
		for (Song s : m_list)
			t.getModule().getMusik().getSong().remove(s);
		Song s1 = new Song();
		s1.setLink("www.youtube.de");
		s1.setSongInterpret("Interpret");
		s1.setSongTitel(music);
		t.getModule().getMusik().getSong().add(s1);
		
		List<Beschreibung> o_list = t.getModule().getOutfits().getOutfit();
		for (Beschreibung b : o_list)
			 t.getModule().getOutfits().getOutfit().remove(b);
		Beschreibung b_O = new Beschreibung();
		b_O.setText(new Text());// !!! FEHLER
		b_O.setTitel(outfits);	
		t.getModule().getOutfits().getOutfit().add(b_O);
		
		List<Beschreibung> l_list = t.getModule().getLocations().getLocation();
		for (Beschreibung b : l_list)
			t.getModule().getLocations().getLocation().remove(b);
		Beschreibung b_L = new Beschreibung();
		b_L.setText(new Text());// !!! FEHLER
		b_L.setTitel(loca);
		t.getModule().getLocations().getLocation().add(b_L);
		
		return true;
	}
	
	public String postTheme(String g_id, String k_id, String titel, String deko, String cate, String music, String outfits, String loca)
	{
		String id = createTID( g_id, k_id );
		Theme t = new Theme();
		
		t.setAllgemeines(new Allgemeines());
		t.getAllgemeines().setBewertung(0);
		t.getAllgemeines().setGenres(new Genres());
		t.getAllgemeines().getGenres().getGenre().get(0).setValue(g_id);  // Fehler im Schema! können keine Werte übergeben!
		t.getAllgemeines().setKategorien(new Kategorien());
		t.getAllgemeines().getKategorien().getKategorie().get(0).setValue(k_id); // Fehler im Schema! können keine Werte übergeben!
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
		
		return id;
	}
}
	

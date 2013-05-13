package webservice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
//import app.*;
import app.Genres;
import app.Themes;
import app.Themes.Theme;
import app.Themes.Theme.Interaktion.Kommentare.Kommentar;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

@Path("/themes")
public class ThemesService {
		
	/**
	 * Unmarshallt "theme.xml" zum Bearbeiten zu einem Java-Objekt.
	 * 
	 * @return gibt das die unmarshallte "theme.xml" als Typ Themes zurück
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	private Themes gibThemeDaten() throws JAXBException, FileNotFoundException
	{
		JAXBContext context = JAXBContext.newInstance(Themes.class);
	    Unmarshaller um = context.createUnmarshaller();
	    Themes theme_data = (Themes) um.unmarshal(new FileInputStream("XSD/themes.xml"));
		return theme_data;
	}
	
	/**
	 * Marshallt (speichert) die evtl. veränderte "theme.xml".
	 * 
	 * @param t veränderte Liste der Themes als Java Objekt, welches gemarshallt werden soll
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	private void setzeThemeDaten(Themes t) throws JAXBException, FileNotFoundException
	{
		JAXBContext context = JAXBContext.newInstance(Themes.class);
		Marshaller m = context.createMarshaller();
	    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    //m.marshal(b, System.out);
		m.marshal(t, new FileOutputStream("XSD/themes.xml"));
	}
	
	/**
	 * 
	 * @param id falls in einem Query nach einem Theme gefragt wird
	 * @return gibt eine Liste von allen Themes als ein String zurück
	 * @throws FileNotFoundException
	 * @throws JAXBException
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getThemes(@QueryParam("id") String id) throws FileNotFoundException, JAXBException
	{
		if(id==null)
		{
			String themes_list = "";
			Themes themes_data = gibThemeDaten();
		    
		    for (int i=0; i<themes_data.getTheme().size(); i ++)
		    	themes_list += themes_data.getTheme().get(i).getAllgemeines().getThemeTitel() + "\n";
		    
			return themes_list;
		}
		return getTheme(id);
	}
	
	/**
	 * 
	 * @param theme_id
	 * @return gibt ein einziges Theme als String aus
	 * @throws FileNotFoundException
	 * @throws JAXBException
	 */
	@GET
	@Path("/{theme_id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getTheme(@PathParam("theme_id") String theme_id) throws FileNotFoundException, JAXBException
	{
		String theme_info = ""; 
		int nr = Integer.parseInt(theme_id.substring(1));
		Themes themes_data = gibThemeDaten();
	    
		theme_info = "Titel:" + themes_data.getTheme().get(nr).getAllgemeines().getThemeTitel()+ "\n";
		theme_info += "Bewertung:" + themes_data.getTheme().get(nr).getAllgemeines().getBewertung()+ "\n";
		theme_info += "Genre(s):\n";
		for(int i=0; i<themes_data.getTheme().get(nr).getAllgemeines().getGenres().getGenre().size(); i++)
			theme_info += themes_data.getTheme().get(nr).getAllgemeines().getGenres().getGenre().get(i).getValue()+ "\n";
		theme_info += "Kategorie(n):\n";
		for(int i=0; i<themes_data.getTheme().get(nr).getAllgemeines().getGenres().getGenre().size(); i++)
			theme_info += themes_data.getTheme().get(nr).getAllgemeines().getKategorien().getKategorie().get(i).getValue()+ "\n";
		
		return theme_info;
	}
	
	/**
	 * 
	 * @param theme_id
	 * @return den HTTP-Code 200 ok als "Theme wurde gelöscht", oder 404 wenn Fehler
	 * @throws FileNotFoundException
	 * @throws JAXBException
	 */
	@DELETE
	@Path("/{theme_id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteTheme(@PathParam("theme_id") String theme_id) throws FileNotFoundException, JAXBException
	{
		Themes daten = gibThemeDaten();
		for (int i=0; i<daten.getTheme().size(); i++)
		{
			if(theme_id.equals(daten.getTheme().get(i).getAllgemeines().getThemeId()))
			{
				daten.getTheme().remove(i);
				setzeThemeDaten(daten);
				System.out.println("Theme wurde gelöscht.");
				return Response.ok().build();
			}
		}
		System.out.println("Theme existiert nicht.");
		return Response.status(404).build();
	}
	
//	@POST
//	@Produces(MediaType.TEXT_PLAIN)
//	@Consumes(MediaType.TEXT_PLAIN) // erlaubte MIME-types, die diese Methode verarbeiten kann
//	public Response addTheme(@QueryParam("titel") String titel,
//							 @QueryParam("genre") String genre,
//							 List<Genres.Genre> genres, String[] kategorien) throws FileNotFoundException, JAXBException
//	{
//		Themes daten = gibThemeDaten();
//		Theme t_neu = new Theme();
//		Genres g_neu = new Genres();
//		
//		//neue ID
//		String id = "t"+daten.getTheme().size();		
//		t_neu.getAllgemeines().setThemeId(id);
//		t_neu.getAllgemeines().setThemeTitel(titel); 
//		t_neu.getAllgemeines().setBewertung(0);
//		
//		for(int i=0; i<genres.size();i++)
//			g_neu.getGenre().add(genres.get(i));
//		
//		t_neu.getAllgemeines().setGenres((Themes.Theme.Allgemeines.Genres) g_neu);
//		return null;
//	}
	
	/**
	 * Verändert ein vorhanderes Theme.
	 * 
	 * @param newname
	 * @param number
	 * @return
	 * @throws JAXBException 
	 * @throws FileNotFoundException 
	 */
	@PUT
	@Path("/{theme_id}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public Response replaceTheme(@PathParam("theme_id") String theme_id, String theme) throws FileNotFoundException, JAXBException
	{
//		Themes themes_data = gibThemeDaten();
//		themes_data.getTheme().get(Integer.parseInt(theme_id.substring(1))).getAllgemeines().setThemeTitel(newtitel);

		
		return Response.ok().build();
	}
	
	//********************** Kommentare ***********************************
	
	/**
	 * Hilfsfunktion um ein Datum zu generien, an welchem ein neuer Kommentar verfasst wurde.
	 * 
	 * @return die aktuelle Zeit vom Typ XMLGregorianCalender
	 * @throws DatatypeConfigurationException
	 */
	private XMLGregorianCalendar gibDatum() throws DatatypeConfigurationException
	{
		GregorianCalendar kalender = new GregorianCalendar();
		kalender.setTime(new Date());
        XMLGregorianCalendar zeit = DatatypeFactory.newInstance().newXMLGregorianCalendar(kalender);
		return zeit;
	}
	
	
	/**
	 * Gibt alle Kommentare zu einem bestimmten Theme aus.
	 * 
	 * @param theme_id ID des Themes in dem die Kommentare gespeichert sind
	 * @return Liste aller Kommentare als konkatenierter String
	 * @throws FileNotFoundException
	 * @throws JAXBException
	 */
	@GET
	@Path("{theme_id}/kommentare")
	@Produces(MediaType.TEXT_PLAIN)
	public String getKommentare(@PathParam("theme_id") String theme_id) throws FileNotFoundException, JAXBException
	{
		Themes themes_data = gibThemeDaten();
		
		// if (theme_id.equals("t"+themes_data.getTheme().size())) --- richtige theme-id 
			
		String kommi_list = "";
		   
		for (int i=0; i<themes_data.getTheme().get(Integer.parseInt(theme_id.substring(1))).getInteraktion().getKommentare().getKommentar().size(); i ++)
		{
			String k = // abkürzen
			kommi_list += "User: "+themes_data.getTheme().get(Integer.parseInt(theme_id.substring(1))).getInteraktion().getKommentare().getKommentar().get(i).getUser();
			kommi_list += " um: "+themes_data.getTheme().get(Integer.parseInt(theme_id.substring(1))).getInteraktion().getKommentare().getKommentar().get(i).getUhrzeit().toString()+ "\n";
			kommi_list += "Nachricht: "+themes_data.getTheme().get(Integer.parseInt(theme_id.substring(1))).getInteraktion().getKommentare().getKommentar().get(i).getNachricht()+ "\n\n";
		}
	 
		return kommi_list;
	}
	
	/**
	 * Gibt einen bestimmen Kommentar zu einem Theme aus.
	 * 
	 * @param theme_id ID des Themes
	 * @param kommentar_id ID des Kommentars
	 * @return Kommentar-Nachricht mit dem Namen des Users, der die Nachricht gepostet hat und die Uhrzeit zu der dieser erstellt wurde. 
	 * @throws FileNotFoundException
	 * @throws JAXBException
	 */
	@GET
	@Path("{theme_id}/kommentare/{kommentar_id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getKommentar(@PathParam("theme_id") String theme_id, @PathParam("kommentar_id") String kommentar_id) throws FileNotFoundException, JAXBException
	{
		int t_id = Integer.parseInt(theme_id.substring(1));
		int k_id = Integer.parseInt(kommentar_id.substring(1));
		Themes themes_data = gibThemeDaten();
		
		String kommentar = themes_data.getTheme().get(t_id).getInteraktion().getKommentare().getKommentar().get(k_id).getUser();
		kommentar += " schrieb am " + themes_data.getTheme().get(t_id).getInteraktion().getKommentare().getKommentar().get(k_id).getUhrzeit() + "/n";
		kommentar += ": " + themes_data.getTheme().get(t_id).getInteraktion().getKommentare().getKommentar().get(k_id).getNachricht();	
		
		return kommentar;
	}
	
	/**
	 * Fügt einen neuen Kommentar zu einem Theme hinzu. Dabei wird die Nachricht erfasst, der Username des Verfassers und die Uhrzeit zu welcher der Kommentar gepostet wird.
	 * 
	 * @param theme_id ID des Themes
	 * @param kommentar Kommentar-Nachricht
	 * @param user Name des Users
	 * @param uhrzeit Uhrzeit des Posts.
	 * @return statuscode ok bei Erfolg, statuscode 404 bei Misserfolg
	 * @throws FileNotFoundException
	 * @throws JAXBException
	 */
//	@POST
//	@Path("/{theme_id}/kommentare")
//	@Produces(MediaType.TEXT_PLAIN)
//	@Consumes(MediaType.TEXT_PLAIN) // ?
//	public Response addKommentar(@PathParam("theme_id") String theme_id, 
//								 @QueryParam("kommentar") String kommentar,
//								 @QueryParam("user") String user,
//								 @QueryParam("uhrzeit") String uhrzeit) throws FileNotFoundException, JAXBException
//	{
//	// vorhandene Daten holen
//			Themes daten = gibThemeDaten();
//			
//			// neuen Kommentar anlegen und füllen
//			Themes.Theme.Interaktion.Kommentare.Kommentar k = new Themes.Theme.Interaktion.Kommentare.Kommentar();
//			k.setNachricht(kommentar);
//			k.setUhrzeit(null);
//			k.setUser(user);
//			
//			//neuen Kommentar dem Theme hinzufügen
//			daten.getTheme().get(Integer.parseInt(theme_id.substring(1))).getInteraktion().getKommentare().getKommentar().add(k);
//			
//			// Theme mit neuem Kommentar speichern
//			setzeThemeDaten(daten);
//			
//			return Response.status(201).build();
//		}
	
	@POST
	@Path("/{theme_id}/kommentare")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN) // ?
	public Response addKommentar(@PathParam("theme_id") String theme_id, String k) throws FileNotFoundException, JAXBException
	{
		JAXBContext context = JAXBContext.newInstance(Themes.class);
	    Unmarshaller um = context.createUnmarshaller();
	    Kommentar kommentar = (Kommentar) um.unmarshal(new FileInputStream(k));
	    
	    Themes daten = gibThemeDaten();
	    daten.getTheme().get(Integer.parseInt(theme_id.substring(1))).getInteraktion().getKommentare().getKommentar().add(kommentar);
	    
	    setzeThemeDaten(daten);
	
		return Response.status(201).build();
	}
}

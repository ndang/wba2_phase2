package webservices;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import app.Theme;
import app.Theme.Interaktion.Kommentare;
import app.Theme.Interaktion.Kommentare.Kommentar;
import app.Themes;


@Path("/themes")
public class ThemesService {
		
	static private JAXBContext context;
	static private Unmarshaller um;
	static private Marshaller m;
	
	public ThemesService()
	{
		try
		{
			context = JAXBContext.newInstance(Themes.class);
			um = context.createUnmarshaller();
			m = context.createMarshaller();
		} catch (JAXBException e) {
			e.printStackTrace();
			System.err.println("Kein JAXBContext konnte intanziiert werden.");
		}
	}
	
	/**
	 * Unmarshallt "themes.xml" zum Bearbeiten zu einem Java-Objekt.
	 * 
	 * @return gibt das die unmarshallte "themes.xml" als Typ Themes zurück
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	static public Themes gibThemeDaten()
	{
		Themes themes = null;
		
		try
		{
			themes = (Themes) um.unmarshal(new FileInputStream("XSD/themes.xml"));
		} catch (JAXBException | FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Themes konnten nicht aus den XML-Dateien gelesen werden!");
		}
	    
		return themes;
	}
	
	/**
	 * Marshallt (speichert) die evtl. veränderte "themes.xml".
	 * 
	 * @param t veränderte Liste der Themes als Java Objekt, welches gemarshallt werden soll
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	private void setzeThemeDaten(Themes t)
	{
		try 
		{			
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(t, new FileOutputStream("XSD/themes.xml"));
		} catch (FileNotFoundException | JAXBException e) {
			System.out.println("Theme(s) konnte(n) nicht gespeichert werden.");
		}
	}
		
	/**
	 * Gibt eine Liste von allen Theme-Titel als ein String zurück.
	 * 
	 * @param id falls in einem Query nach einem Theme gefragt wird, wird die Theme-ID übergeben
	 * @return Liste aller Theme-Titel
	 * @throws FileNotFoundException
	 * @throws JAXBException
	 */
	@GET
	@Produces( MediaType.APPLICATION_XML )
	public Themes getThemes()
	{	
		return gibThemeDaten();
	}
	
	/**
	 * Gibt Titel und Beschreibung eines Themes als String aus.
	 * 
	 * @param theme_id identifiziert ein Theme eindeutig in der Theme-Liste
	 * @return Theme Titel und Beschreibung
	 * @throws FileNotFoundException
	 * @throws JAXBException
	 */
	@GET
	@Path("/{theme_id}")
	@Produces( MediaType.APPLICATION_XML )
	public Theme getTheme( @PathParam("theme_id") String theme_id )
	{
		Themes themes_daten = gibThemeDaten();
		Theme t = null;
		
		for (int i=0; i<themes_daten.getTheme().size(); i++)
		{
			if(theme_id.equals(themes_daten.getTheme().get(i).getAllgemeines().getThemeId()))
				t = themes_daten.getTheme().get(i);
		}
		return t;
	}
	
	/**
	 * Löscht ein Theme aus der Liste
	 * 
	 * @param theme_id identifiziert ein Theme eindeutig in der Theme-Liste
	 * @return den HTTP-Code 200 ok als "Theme wurde gelöscht", oder 404 wenn Fehler
	 * @throws FileNotFoundException
	 * @throws JAXBException
	 */
	@DELETE
	@Path("/{theme_id}")
	@Produces(MediaType.APPLICATION_XML)
	public Response deleteTheme(@PathParam("theme_id") String theme_id) throws FileNotFoundException, JAXBException
	{
		Themes daten = gibThemeDaten();
		for ( int i=0; i<daten.getTheme().size(); i++ )
		{
			if( theme_id.equals(daten.getTheme().get(i).getAllgemeines().getThemeId()) )
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
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_XML)
	public void addTheme(Theme t)
	{
		Themes daten = gibThemeDaten();		
		daten.getTheme().add(t);
		setzeThemeDaten(daten);
	}
	
	/**
	 * Verändert ein vorhanderes Theme.
	 * 
	 * @param theme_id
	 * @param v_theme verändertes Theme, welches übergeben wird
	 * @return OK, wenn Änderungen erfolgreich gespeichert wurden
	 * 		   XX, wenn nicht erfolgreich
	 * @throws FileNotFoundException
	 * @throws JAXBException
	 */
	@PUT
	@Path("/{theme_id}")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response replaceTheme(@PathParam("theme_id") String theme_id, Theme v_theme) throws FileNotFoundException, JAXBException
	{
		Themes daten = gibThemeDaten();
		Theme zu_ersetzen = null;
		
		for ( int i=0; i<daten.getTheme().size(); i++)
			if ( daten.getTheme().get(i).getAllgemeines().getThemeId().equals(theme_id) )
				zu_ersetzen = daten.getTheme().get(i); 
		
		daten.getTheme().remove(zu_ersetzen);
		daten.getTheme().add(v_theme);
		setzeThemeDaten(daten);
		
		return Response.ok().build();
	}
	
	/*********************************************************************/
	/********************** Kommentare ***********************************/
	/*********************************************************************/
	
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
	@Produces(MediaType.APPLICATION_XML)
	public Kommentare getKommentare(@PathParam("theme_id") String theme_id)
	{
		Theme t = getTheme(theme_id);
		Kommentare k = t.getInteraktion().getKommentare();
	 
		return k;
	}
	
	/**
	 * Gibt einen bestimmen Kommentar zu einem Theme aus.
	 * 
	 * @param theme_id ID des Themes
	 * @param kommentar_id i-ter Kommentar
	 * @return Kommentar-Nachricht mit dem Namen des Users, der die Nachricht gepostet hat und die Uhrzeit zu der dieser erstellt wurde. 
	 * @throws FileNotFoundException
	 * @throws JAXBException
	 */
	@GET
	@Path("{theme_id}/kommentare/{kommentar_id}")
	@Produces(MediaType.APPLICATION_XML)
	public Kommentar getKommentar(@PathParam("theme_id") String theme_id, @PathParam("kommentar_id") String kommentar_id)
	{
		Kommentare k_list = getKommentare(theme_id);
		Kommentar k = k_list.getKommentar().get(Integer.parseInt(kommentar_id));
		
		return k;
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
	@POST
	@Path("/{theme_id}/kommentare")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response addKommentar(@PathParam("theme_id") String theme_id, Kommentar new_kommi) throws FileNotFoundException, JAXBException
	{
	    Kommentare k_list = getKommentare(theme_id);
	    k_list.getKommentar().add(new_kommi);
	    setzeThemeDaten(gibThemeDaten());
		return Response.status(201).build();
	}
}

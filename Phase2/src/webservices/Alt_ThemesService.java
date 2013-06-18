package webservices;
//package webservice;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.StringReader;
//import java.util.List;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
//import javax.xml.bind.Unmarshaller;
//import javax.xml.transform.stream.StreamSource;
//
//import app.Genres;
//import app.Kategorien;
//import app.Themes;
//import app.Themes.Theme;
//import app.Themes.Theme.Interaktion.Kommentare.Kommentar;
////import java.util.Date;
////import java.util.GregorianCalendar;
////import javax.xml.datatype.DatatypeConfigurationException;
////import javax.xml.datatype.DatatypeFactory;
////import javax.xml.datatype.XMLGregorianCalendar;
//
//@Path("/themes")
//public class Alt_ThemesService {
//		
//	/**
//	 * Unmarshallt "themes.xml" zum Bearbeiten zu einem Java-Objekt.
//	 * 
//	 * @return gibt das die unmarshallte "themes.xml" als Typ Themes zurück
//	 * @throws JAXBException
//	 * @throws FileNotFoundException
//	 */
//	public Themes gibThemeDaten() throws JAXBException, FileNotFoundException
//	{
//		JAXBContext context = JAXBContext.newInstance(Themes.class);
//	    Unmarshaller um = context.createUnmarshaller();
//	    Themes theme_data = (Themes) um.unmarshal(new FileInputStream("XSD/themes.xml"));
//		return theme_data;
//	}
//	
//	/**
//	 * Marshallt (speichert) die evtl. veränderte "themes.xml".
//	 * 
//	 * @param t veränderte Liste der Themes als Java Objekt, welches gemarshallt werden soll
//	 * @throws JAXBException
//	 * @throws FileNotFoundException
//	 */
//	private void setzeThemeDaten(Themes t) throws JAXBException
//	{
//		JAXBContext context = JAXBContext.newInstance(Themes.class);
//		Marshaller m = context.createMarshaller();
//	    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//		try {
//			m.marshal(t, new FileOutputStream("XSD/themes.xml"));
//		} catch (FileNotFoundException e) {
//			System.out.println("Theme(s) konnte(n) nicht gespeichert werden.");
//		}
//	}
//	
////	/**
////	 * Hilfsfunktion zur Überprüfung der Korrektheit der Theme-ID.
////	 * 
////	 * @param id theme-id
////	 * @return boolean Wert, ob ID gültig ist
////	 * @throws JAXBException
////	 * @throws FileNotFoundException 
////	 */
////	private boolean gueltigID(int id) throws JAXBException, FileNotFoundException 
////	{
////		if ( (id < 0) || (id>gibThemeDaten().getTheme().size()))
////		{
////			System.out.println("Diese Theme-ID ist nicht zulässig.");
////			return false;
////		}
////		return true;
////	}
//	
//	
//	/**
//	 * Gibt eine Liste von allen Theme-Titel als ein String zurück.
//	 * 
//	 * @param id falls in einem Query nach einem Theme gefragt wird, wird die Theme-ID übergeben
//	 * @return Liste aller Theme-Titel
//	 * @throws FileNotFoundException
//	 * @throws JAXBException
//	 */
//	@GET
//	@Produces(MediaType.TEXT_PLAIN)
//	public String getThemes(@QueryParam("theme_id") String theme_id) throws FileNotFoundException, JAXBException
//	{	
//
//		if(theme_id==null)
//		{
//			String themes_liste = "";
//			Themes themes_daten = gibThemeDaten();
//					    
//		    for (int i=0; i<themes_daten.getTheme().size(); i ++)
//		    	themes_liste += themes_daten.getTheme().get(i).getAllgemeines().getThemeTitel() + "\n";
//		    
//			return themes_liste;
//		}
//		return getTheme(theme_id);
//	}
//	
//	/**
//	 * Gibt Titel und Beschreibung eines Themes als String aus.
//	 * 
//	 * @param theme_id identifiziert ein Theme eindeutig in der Theme-Liste
//	 * @return Theme Titel und Beschreibung
//	 * @throws FileNotFoundException
//	 * @throws JAXBException
//	 */
//	@GET
//	@Path("/{theme_id}")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String getTheme(@PathParam("theme_id") String theme_id) throws FileNotFoundException, JAXBException
//	{
//		Themes themes_daten = gibThemeDaten();
//		String theme_info = ""; 
//		
//		for (int i=0; i<themes_daten.getTheme().size(); i++)
//		{
//			if(theme_id.equals(themes_daten.getTheme().get(i).getAllgemeines().getThemeId()))
//			{
//				Themes.Theme.Allgemeines allg_daten = themes_daten.getTheme().get(i).getAllgemeines();
//				
//				theme_info = "Titel:" + allg_daten.getThemeTitel()+ "\n";
//				theme_info += "Bewertung:" + allg_daten.getBewertung()+ "\n";
//				theme_info += "Genre(s):\n";
//				for(int j=0; i<allg_daten.getGenres().getGenre().size(); i++)
//					theme_info += allg_daten.getGenres().getGenre().get(j).getValue()+ "\n";
//				theme_info += "Kategorie(n):\n";
//				for(int j=0; i<allg_daten.getKategorien().getKategorie().size(); i++)
//					theme_info += allg_daten.getKategorien().getKategorie().get(j).getValue()+ "\n";
//			}
//		}
//			
//		return theme_info;
//	}
//	
//	/**
//	 * Löscht ein Theme aus der Liste
//	 * 
//	 * @param theme_id identifiziert ein Theme eindeutig in der Theme-Liste
//	 * @return den HTTP-Code 200 ok als "Theme wurde gelöscht", oder 404 wenn Fehler
//	 * @throws FileNotFoundException
//	 * @throws JAXBException
//	 */
//	@DELETE
//	@Path("/{theme_id}")
//	@Produces(MediaType.TEXT_PLAIN)
//	public Response deleteTheme(@PathParam("theme_id") String theme_id) throws FileNotFoundException, JAXBException
//	{
//		Themes daten = gibThemeDaten();
//		for (int i=0; i<daten.getTheme().size(); i++)
//		{
//			if(theme_id.equals(daten.getTheme().get(i).getAllgemeines().getThemeId()))
//			{
//				daten.getTheme().remove(i);
//				setzeThemeDaten(daten);
//				System.out.println("Theme wurde gelöscht.");
//				return Response.ok().build();
//			}
//		}
//		System.out.println("Theme existiert nicht.");
//		return Response.status(404).build();
//	}
//	
////  Alternative Methode?
////	@POST
////	@Produces(MediaType.TEXT_PLAIN)
////	@Consumes(MediaType.TEXT_PLAIN) // erlaubte MIME-types, die diese Methode verarbeiten kann
////	public Response addTheme(@QueryParam("titel") String titel,
////							 @QueryParam("genre") String genre,
////							 List<Genres.Genre> genres, String[] kategorien) throws FileNotFoundException, JAXBException
////	{
////		Themes daten = gibThemeDaten();
////		Theme t_neu = new Theme();
////		Genres g_neu = new Genres();
////		
////		//neue ID
////		String id = "t"+daten.getTheme().size();		
////		t_neu.getAllgemeines().setThemeId(id);
////		t_neu.getAllgemeines().setThemeTitel(titel); 
////		t_neu.getAllgemeines().setBewertung(0);
////		
////		for(int i=0; i<genres.size();i++)
////			g_neu.getGenre().add(genres.get(i));
////		
////		t_neu.getAllgemeines().setGenres((Themes.Theme.Allgemeines.Genres) g_neu);
////		return null;
////	}
//	
//	@POST // Überprüfungen fehlen (ob Genre auch existiert, .. etc)
//	@Produces(MediaType.TEXT_PLAIN)
//	@Consumes(MediaType.TEXT_PLAIN) // erlaubte MIME-types, die diese Methode verarbeiten kann
//	public Response addTheme(String t) throws FileNotFoundException, JAXBException
//	{
//		JAXBContext context = JAXBContext.newInstance(Theme.class);
//	    Unmarshaller um = context.createUnmarshaller();
//		Theme neues_theme = (Theme) um.unmarshal(new StreamSource(new StringReader(t)), Theme.class).getValue();
//		Themes daten = gibThemeDaten();
//		
//		//Überprüfung auf Richtigkeit der Genres und Kategorien
//		Genres genres_daten = GenresKategorienService.gibGenreDaten();
//		boolean genre_gueltig = false;
//		int anz_kategorien = neues_theme.getAllgemeines().getKategorien().getKategorie().size();
//		
//		for(int i=0; i<neues_theme.getAllgemeines().getGenres().getGenre().size(); i++)
//		{
//			for (int j=0; j<genres_daten.getGenre().size(); j++)
//			{
//				// Prüfung der Existenz des Genre
//				if (neues_theme.getAllgemeines().getGenres().getGenre().get(i).getValue().equals(genres_daten.getGenre().get(j).getGenreId()))
//				{
//					genre_gueltig = true;
//					
//					// Prüfung Korrektheit der Kategorien
//					Kategorien kategorien_daten = GenresKategorienService.gibKategorienDaten(genres_daten.getGenre().get(j).getGenreId());
//					int zaehler = 0;
//					for (int k=0; k<neues_theme.getAllgemeines().getKategorien().getKategorie().size(); k++)
//					{
//						for(int l=0; l<kategorien_daten.getKategorie().size(); l++)
//						if (neues_theme.getAllgemeines().getKategorien().getKategorie().get(k).getValue().equals(kategorien_daten.getKategorie().get(l).getKategorieId()))
//						{
//							zaehler++;
//							anz_kategorien--;
//						}		
//					}
//					
//					if (zaehler==0) // wenn in diesem Genre keine Kateggorie ausgewählt wurde
//						return Response.status(420).build();
//					
//					break;
//				}
//			}
//			
//			if ( (genre_gueltig == false) || (anz_kategorien>0)) //wenn es das Genre nicht gibt oder Kategorien nicht dem Genre angehören
//				return Response.status(420).build(); // Policy Not Fulfilled: In W3C PEP (Working Draft 21. November 1997)[6] wird dieser Code vorgeschlagen, um mitzuteilen, dass eine Bedingung nicht erfüllt wurde.
//		}
//		
//		daten.getTheme().add(neues_theme);
//		setzeThemeDaten(daten);
//	    
//		return Response.status(201).build();
//	}
//	
//	/**
//	 * Verändert ein vorhanderes Theme.
//	 * 
//	 * @param theme_id
//	 * @param v_theme verändertes Theme, welches übergeben wird
//	 * @return OK, wenn Änderungen erfolgreich gespeichert wurden
//	 * 		   XX, wenn nicht erfolgreich
//	 * @throws FileNotFoundException
//	 * @throws JAXBException
//	 */
//	@PUT
//	@Path("/{theme_id}")
//	@Produces(MediaType.TEXT_PLAIN)
//	@Consumes(MediaType.TEXT_PLAIN)
//	public Response replaceTheme(@PathParam("theme_id") String theme_id, String v_theme) throws FileNotFoundException, JAXBException
//	{
//		JAXBContext context = JAXBContext.newInstance(Theme.class);
//	    Unmarshaller um = context.createUnmarshaller();
//		Theme veraendertes_theme = (Theme) um.unmarshal(new StreamSource(new StringReader(v_theme)), Theme.class).getValue();		
//		Themes daten = gibThemeDaten();
//		
//		//Überprüfung auf Richtigkeit der Genres und Kategorien
//		Genres genres_daten = GenresKategorienService.gibGenreDaten();
//		boolean genre_gueltig = false;
//		int anz_kategorien = veraendertes_theme.getAllgemeines().getKategorien().getKategorie().size();
//		for(int i=0; i<veraendertes_theme.getAllgemeines().getGenres().getGenre().size(); i++)
//		{
//			for (int j=0; j<genres_daten.getGenre().size(); j++)
//			{
//				// Prüfung der Existenz des Genre
//				if (veraendertes_theme.getAllgemeines().getGenres().getGenre().get(i).getValue().equals(genres_daten.getGenre().get(j).getGenreId()))
//				{
//					genre_gueltig = true;
//					
//					// Prüfung Korrektheit der Kategorien
//					Kategorien kategorien_daten = GenresKategorienService.gibKategorienDaten(genres_daten.getGenre().get(j).getGenreId());
//					int zaehler = 0;
//					for (int k=0; k<veraendertes_theme.getAllgemeines().getKategorien().getKategorie().size(); k++)
//					{
//						for(int l=0; l<kategorien_daten.getKategorie().size(); l++)
//						if (veraendertes_theme.getAllgemeines().getKategorien().getKategorie().get(k).getValue().equals(kategorien_daten.getKategorie().get(l).getKategorieId()))
//							zaehler++;
//							anz_kategorien--;
//					}
//					
//					if (zaehler==0) // wenn in diesem Genre keine Kateggorie ausgewählt wurde
//						return Response.status(420).build();
//					
//					break;
//				}
//			}
//					
//			if (genre_gueltig == false) //wenn es das Genre nicht gibt
//				return Response.status(420).build(); // Policy Not Fulfilled: In W3C PEP (Working Draft 21. November 1997)[6] wird dieser Code vorgeschlagen, um mitzuteilen, dass eine Bedingung nicht erfüllt wurde.
//			if ((anz_kategorien>0)) // wenn diese Kategorien nicht dem Genre angehören
//				return Response.status(420).build();
//		}
//
//		int pos = -1;
//		for (int i=0; i< daten.getTheme().size(); i++)
//		{
//			if (daten.getTheme().get(i).getAllgemeines().getThemeId().equals(veraendertes_theme.getAllgemeines().getThemeId()))
//				pos = i;
//		}
//		daten.getTheme().remove(pos);
//		daten.getTheme().add(veraendertes_theme);
//		setzeThemeDaten(daten);		
//		return Response.ok().build();
//	}
//	
//	//********************** Kommentare ***********************************//
//	
//	/**
//	 * Hilfsfunktion um ein Datum zu generien, an welchem ein neuer Kommentar verfasst wurde.
//	 * 
//	 * @return die aktuelle Zeit vom Typ XMLGregorianCalender
//	 * @throws DatatypeConfigurationException
//	 */
////	private XMLGregorianCalendar gibDatum() throws DatatypeConfigurationException
////	{
////		GregorianCalendar kalender = new GregorianCalendar();
////		kalender.setTime(new Date());
////        XMLGregorianCalendar zeit = DatatypeFactory.newInstance().newXMLGregorianCalendar(kalender);
////		return zeit;
////	}
////	
//	
//	/**
//	 * Gibt alle Kommentare zu einem bestimmten Theme aus.
//	 * 
//	 * @param theme_id ID des Themes in dem die Kommentare gespeichert sind
//	 * @return Liste aller Kommentare als konkatenierter String
//	 * @throws FileNotFoundException
//	 * @throws JAXBException
//	 */
//	@GET
//	@Path("{theme_id}/kommentare")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String getKommentare(@PathParam("theme_id") String theme_id) throws FileNotFoundException, JAXBException
//	{
//		List<Themes.Theme.Interaktion.Kommentare.Kommentar> old_kommi_list = gibThemeDaten().getTheme().get(Integer.parseInt(theme_id.substring(1))).getInteraktion().getKommentare().getKommentar();			
//		String new_kommi_list = "";
//		   
//		for (int i=0; i<old_kommi_list.size(); i ++)
//		{
//			new_kommi_list += "User: "+ old_kommi_list.get(i).getUser();
//			new_kommi_list += " um: "+ old_kommi_list.get(i).getUhrzeit().toString()+ "\n";
//			new_kommi_list += "Nachricht: "+ old_kommi_list.get(i).getNachricht()+ "\n\n";
//		}
//	 
//		return new_kommi_list;
//	}
//	
//	/**
//	 * Gibt einen bestimmen Kommentar zu einem Theme aus.
//	 * 
//	 * @param theme_id ID des Themes
//	 * @param kommentar_id ID des Kommentars
//	 * @return Kommentar-Nachricht mit dem Namen des Users, der die Nachricht gepostet hat und die Uhrzeit zu der dieser erstellt wurde. 
//	 * @throws FileNotFoundException
//	 * @throws JAXBException
//	 */
//	@GET
//	@Path("{theme_id}/kommentare/{kommentar_id}")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String getKommentar(@PathParam("theme_id") String theme_id, @PathParam("kommentar_id") String kommentar_id) throws FileNotFoundException, JAXBException
//	{
//		Themes.Theme.Interaktion.Kommentare.Kommentar kommi_daten = gibThemeDaten().getTheme().get(Integer.parseInt(theme_id.substring(1))).getInteraktion().getKommentare().getKommentar().get(Integer.parseInt(kommentar_id.substring(1)));
//		
//		String kommentar = kommi_daten.getUser();
//		kommentar += " schrieb am " + kommi_daten.getUhrzeit() + "/n";
//		kommentar += ": " + kommi_daten.getNachricht();	
//		
//		return kommentar;
//	}
//	
//	/**
//	 * Fügt einen neuen Kommentar zu einem Theme hinzu. Dabei wird die Nachricht erfasst, der Username des Verfassers und die Uhrzeit zu welcher der Kommentar gepostet wird.
//	 * 
//	 * @param theme_id ID des Themes
//	 * @param kommentar Kommentar-Nachricht
//	 * @param user Name des Users
//	 * @param uhrzeit Uhrzeit des Posts.
//	 * @return statuscode ok bei Erfolg, statuscode 404 bei Misserfolg
//	 * @throws FileNotFoundException
//	 * @throws JAXBException
//	 */	
//	@POST
//	@Path("/{theme_id}/kommentare")
//	@Produces(MediaType.TEXT_PLAIN)
//	@Consumes(MediaType.TEXT_PLAIN)
//	public Response addKommentar(@PathParam("theme_id") String theme_id, String new_kommi) throws FileNotFoundException, JAXBException
//	{
//		JAXBContext context = JAXBContext.newInstance(Kommentar.class);
//	    Unmarshaller um = context.createUnmarshaller();
//	    Kommentar kommentar = (Kommentar) um.unmarshal(new StreamSource(new StringReader(new_kommi)), Kommentar.class).getValue();
//	    Themes daten = gibThemeDaten();
//	    daten.getTheme().get(Integer.parseInt(theme_id.substring(1))).getInteraktion().getKommentare().getKommentar().add(kommentar);
//	    setzeThemeDaten(daten);
//		return Response.status(201).build();
//	}
//}

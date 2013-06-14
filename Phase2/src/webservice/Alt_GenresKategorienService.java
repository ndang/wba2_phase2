//package webservice;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.MediaType;
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Unmarshaller;
//import javax.xml.datatype.DatatypeConfigurationException;
//
//import app.Genres;
//import app.Genres.Genre;
//import app.Kategorien;
//import app.Kategorien.Kategorie;
//
//@Path("/genres")
//public class Alt_GenresKategorienService {
//	
//	/**
//	 * Unmarshalt XML-Liste aller Genres.
//	 * 
//	 * @return Genres-Listen-Objekt
//	 * @throws JAXBException
//	 * @throws FileNotFoundException
//	 */
//	public static Genres gibGenreDaten()
//	{
//		Genres genres_data = null;
//		
//		try
//		{
//			JAXBContext context = JAXBContext.newInstance(Genres.class);
//			Unmarshaller um = context.createUnmarshaller();
//			genres_data = (Genres) um.unmarshal(new FileInputStream("XSD/genres.xml"));
//		}
//		
//		catch (FileNotFoundException | JAXBException e)
//		{
//			System.out.println("Liste der Genres konnte nicht gefunden oder gelesen werden.");
//		}
//		
//		return genres_data;
//	}
//	
//	/**
//	 * Unmarshalt Liste aller Kategorien.
//	 * 
//	 * @param genre_id in welcher sich die Kategorie befindet
//	 * @return Kategorien-Listen-Objekt
//	 * @throws JAXBException
//	 * @throws FileNotFoundException
//	 */
//	public static Kategorien gibKategorienDaten(String genre_id)
//	{
//		Kategorien kategorien_data = null;
//		
//		try
//		{
//			JAXBContext context = JAXBContext.newInstance(Kategorien.class);
//			Unmarshaller um = context.createUnmarshaller();
//	    	kategorien_data = (Kategorien) um.unmarshal(new FileInputStream("XSD/kategorien_"+ genre_id +".xml"));
//		}
//		catch (FileNotFoundException | JAXBException e)
//		{
//			System.out.println("Liste der Kategorien konnte nicht gefunden oder gelesen werden.");
//		}
//		
//		return kategorien_data;
//	}
//
//	/**
//	 * Gibt Liste aller Genres zurück.
//	 * 
//	 * @param id falls per Query eine genre-id mitgegeben wird, wird ein bestimmtes Genre zurück gegeben
//	 * @return Liste alle Genres
//	 * @throws JAXBException
//	 * @throws FileNotFoundException
//	 * @throws DatatypeConfigurationException
//	 */
//	@GET
//	@Produces(MediaType.APPLICATION_XML)
//	public Genres getGenres(@QueryParam("genre_id") String genre_id)
//	{
//		return gibGenreDaten();
//	}
////	@GET    **********ALTES GET MIT QUERY PARAMETER ****************************
////	@Produces(MediaType.TEXT_PLAIN)
////	public String getGenres(@QueryParam("genre_id") String genre_id) throws JAXBException, FileNotFoundException
////	{
////		// wenn kein QueryParameter übergeben wird
////		if(genre_id==null)
////		{
////			String genres_liste = "";
////			Genres genres_daten = gibGenreDaten();
////		    
////			// Alle Genre-Titel werden in einem String konkateniert
////		    for (int i=0; i<genres_daten.getGenre().size(); i ++)
////		    	genres_liste += genres_daten.getGenre().get(i).getGenreTitel() + "\n";
////		    
////			return genres_liste;
////		}
////		
////		// wenn QueryParameter übergeben wird
////		return getGenre(genre_id);
////	}
//	
//	/**
//	 * Titel und Beschreibung eines Genre konkatiniert in einem String.
//	 * 
//	 * @param genre_id identifiziert ein Genre aus der Liste aller Genres
//	 * @return Titel und Beschreibung eines Genres
//	 * @throws JAXBException
//	 * @throws FileNotFoundException
//	 */
//	@GET
//	@Path("/{genre_id}")
//	@Produces(MediaType.APPLICATION_XML)
//	public Genre getGenre(@PathParam("genre_id") String genre_id)
//	{
//		Genres genres_daten = gibGenreDaten();
//		Genre genre = null;
//		for ( int i=0; i<genres_daten.getGenre().size(); i++)
//		{
//			if ( genres_daten.getGenre().get(i).getGenreId().equals(genre_id) )
//				genre = genres_daten.getGenre().get(i);
//		}
//		
//		if ( genre == null )
//			System.err.println("Das Genre existert nicht.");
//		
//		return genre;
//	}
////	@GET
////	@Path("/{genre_id}")
////	@Produces(MediaType.TEXT_PLAIN)
////	public String getGenre(@PathParam("genre_id") String genre_id) throws JAXBException, FileNotFoundException
////	{
////		String genres_info = "";
////		int eingabe_id = Integer.parseInt(genre_id.substring(1));
////		Genres genres_daten = gibGenreDaten();
////	    
////		for (int i=0; i<genres_daten.getGenre().size(); i++)
////		{
////			int real_id = Integer.parseInt(genres_daten.getGenre().get(i).getGenreId().substring(1));
////			if (eingabe_id == real_id)
////			{
////				genres_info = genres_daten.getGenre().get(i).getGenreTitel() + "\n";
////			    genres_info += genres_daten.getGenre().get(i).getGenreBeschreibung();
////			}
////		}
////		
////		if (genres_info.equals(""))
////			genres_info = "Ungültige ID.";
////		
////		return genres_info;
////	}
//	
//	/**
//	 * Gibt die Liste aller Kategorien eines Genres als String zurück.
//	 * 
//	 * @param genre_id ist die ID in der alle Kategorien angehörig ist
//	 * @param id falls eine Kategorie per Query anhand ihrer ID abgerufen wird
//	 * @return alle Kategorien eines Genres
//	 * @throws FileNotFoundException
//	 * @throws JAXBException
//	 */
//	@GET
//	@Path("/{genre_id}/kategorien")
//	@Produces(MediaType.TEXT_PLAIN)
//	public Kategorien getKategorien(@PathParam("genre_id") String genre_id, @QueryParam("kategorie_id") String kategorie_id) 
//	{
//		return gibKategorienDaten(genre_id);	
//	}
////	@GET  ****************ALTE KATEGORIE METHODE *************************************
////	@Path("/{genre_id}/kategorien")
////	@Produces(MediaType.TEXT_PLAIN)
////	public String getKategorien(@PathParam("genre_id") String genre_id, @QueryParam("kategorie_id") String kategorie_id) throws FileNotFoundException, JAXBException
////	{
////		if (kategorie_id==null)
////		{
////			String kategorien_liste = "";
////			Kategorien kategorien_daten = gibKategorienDaten(genre_id);
////		    
////		    for (int i=0; i<kategorien_daten.getKategorie().size(); i ++)
////		    	kategorien_liste += kategorien_daten.getKategorie().get(i).getKategorieTitel() + "\n";
////		    
////			return kategorien_liste;
////		}
////		
////		return getKategorie(genre_id, kategorie_id);
////	}
//	
//	/**
//	 *  Gibt Titel und Beschreibung einer Kategorie konkatiniert in einem String zurück.
//	 * 
//	 * @param genre_id ist die ID in der alle Kategorien angehörig ist
//	 * @param kategorien_id  ist die ID in der die angefragte Kategorie angehörig ist
//	 * @return Titel und Beschreibung einer Kategorie
//	 * @throws FileNotFoundException
//	 * @throws JAXBException
//	 */
//	@GET
//	@Path("/{genre_id}/kategorien/{kategorien_id}")
//	@Produces(MediaType.TEXT_PLAIN)
//	public Kategorie getKategorie(@PathParam("genre_id") String genre_id, @PathParam("kategorien_id") String kategorien_id) throws FileNotFoundException, JAXBException
//	{
//		Kategorien kategorien_daten = gibKategorienDaten(genre_id);
//		Kategorie k = null;
//		for (int i=0; i<kategorien_daten.getKategorie().size(); i++)
//		{
//			if (kategorien_daten.getKategorie().get(i).getKategorieId().equals(kategorien_id))
//				k = kategorien_daten.getKategorie().get(i);
//		}
//		
//		if ( k == null )
//			System.err.println("Eine solche Kategorie existiert nicht.");
//	    
//		return k;
//	}
//}

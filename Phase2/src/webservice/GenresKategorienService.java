package webservice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;

import app.*;

@Path("/genres")
public class GenresKategorienService {
	
	/**
	 * Unmarshalt Liste aller Genres.
	 * 
	 * @return Genres-Listen-Objekt
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	public Genres gibGenreDaten() throws JAXBException
	{
		JAXBContext context = JAXBContext.newInstance(Genres.class);
	    Unmarshaller um = context.createUnmarshaller();
	    Genres genres_data = null;
		try {
			genres_data = (Genres) um.unmarshal(new FileInputStream("XSD/genres.xml"));
		} catch (FileNotFoundException e) {
			System.out.println("Liste der Genres konnte nicht gefunden werden.");
		}
		return genres_data;
	}
	
	/**
	 * Unmarshalt Liste aller Kategorien.
	 * 
	 * @param genre_id in welcher sich die Kategorie befindet
	 * @return Kategorien-Listen-Objekt
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	public Kategorien gibKategorienDaten(String genre_id) throws JAXBException
	{
		JAXBContext context = JAXBContext.newInstance(Kategorien.class);
	    Unmarshaller um = context.createUnmarshaller();
	    Kategorien kategorien_data = null;
		try {
			kategorien_data = (Kategorien) um.unmarshal(new FileInputStream("XSD/kategorien_"+ genre_id +".xml"));
		} catch (FileNotFoundException e) {
			System.out.println("Liste der Kategorien konnte nicht gefunden werden.");
		}
		return kategorien_data;
	}

	/**
	 * Gibt Liste aller Genres zurück.
	 * 
	 * @param id falls per Query eine genre-id mitgegeben wird, wird ein bestimmtes Genre zurück gegeben
	 * @return Liste alle Genres
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 * @throws DatatypeConfigurationException
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getGenres(@QueryParam("genre_id") String genre_id) throws JAXBException, FileNotFoundException
	{
		// wenn kein QueryParameter übergeben wird
		if(genre_id==null)
		{
			String genres_liste = "";
			Genres genres_daten = gibGenreDaten();
		    
			// Alle Genre-Titel werden in einem String konkateniert
		    for (int i=0; i<genres_daten.getGenre().size(); i ++)
		    	genres_liste += genres_daten.getGenre().get(i).getGenreTitel() + "\n";
		    
			return genres_liste;
		}
		
		// wenn QueryParameter übergeben wird
		return getGenre(genre_id);
	}
	
//  GET-Methode (wie oben) nur ohne Queryparams
//	@GET
//	@Produces(MediaType.TEXT_PLAIN)
//	public String getGenres() throws JAXBException, FileNotFoundException
//	{
//		String genres_list = "";
//		Genres genres_data = gibGenreDaten();
//	    
//	    for (int i=0; i<genres_data.getGenre().size(); i ++)
//	    	genres_list += genres_data.getGenre().get(i).getGenreTitel() + "\n";
//	    
//		return genres_list;
//	}
	
	/**
	 * Titel und Beschreibung eines Genre konkatiniert in einem String.
	 * 
	 * @param genre_id identifiziert ein Genre aus der Liste aller Genres
	 * @return Titel und Beschreibung eines Genres
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@GET
	@Path("/{genre_id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getGenre(@PathParam("genre_id") String genre_id) throws JAXBException, FileNotFoundException
	{
		String genres_info = "";
		int nr = Integer.parseInt(genre_id.substring(1));
		Genres genres_daten = gibGenreDaten();
	    
	    genres_info = genres_daten.getGenre().get(nr).getGenreTitel() + "\n";
	    genres_info += genres_daten.getGenre().get(nr).getGenreBeschreibung();
	    
		return genres_info;
	}
	
	/**
	 * Gibt die Liste aller Kategorien eines Genres als String zurück.
	 * 
	 * @param genre_id ist die ID in der alle Kategorien angehörig ist
	 * @param id falls eine Kategorie per Query anhand ihrer ID abgerufen wird
	 * @return alle Kategorien eines Genres
	 * @throws FileNotFoundException
	 * @throws JAXBException
	 */
	@GET
	@Path("/{genre_id}/kategorien")
	@Produces(MediaType.TEXT_PLAIN)
	public String getKategorien(@PathParam("genre_id") String genre_id, @QueryParam("kategorie_id") String kategorie_id) throws FileNotFoundException, JAXBException
	{
		if (kategorie_id==null)
		{
			String kategorien_liste = "";
			Kategorien kategorien_daten = gibKategorienDaten(genre_id);
		    
		    for (int i=0; i<kategorien_daten.getKategorie().size(); i ++)
		    	kategorien_liste += kategorien_daten.getKategorie().get(i).getKategorieTitel() + "\n";
		    
			return kategorien_liste;
		}
		
		return getKategorie(genre_id, kategorie_id);
	}
	
	/**
	 *  Gibt Titel und Beschreibung einer Kategorie konkatiniert in einem String zurück.
	 * 
	 * @param genre_id ist die ID in der alle Kategorien angehörig ist
	 * @param kategorien_id  ist die ID in der die angefragte Kategorie angehörig ist
	 * @return Titel und Beschreibung einer Kategorie
	 * @throws FileNotFoundException
	 * @throws JAXBException
	 */
	@GET
	@Path("/{genre_id}/kategorien/{kategorien_id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getKategorie(@PathParam("genre_id") String genre_id, @PathParam("kategorien_id") String kategorien_id) throws FileNotFoundException, JAXBException
	{
		String kategorie_info = ""; 
		int nr = Integer.parseInt(kategorien_id.substring(1));
		Kategorien kategorien_daten = gibKategorienDaten(genre_id);
		
		kategorie_info = kategorien_daten.getKategorie().get(nr).getKategorieTitel() + "\n";
		kategorie_info += kategorien_daten.getKategorie().get(nr).getKategorieBeschreibung();
	    
		return kategorie_info;
	}
}

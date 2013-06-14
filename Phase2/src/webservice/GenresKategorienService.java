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

import app.Genres;
import app.Genres.Genre;
import app.Kategorien;
import app.Kategorien.Kategorie;

@Path("/genres")
public class GenresKategorienService {
	
	static private JAXBContext context;
	
	/**
	 * Unmarshalt XML-Liste aller Genres.
	 * 
	 * @return Genres-Listen-Objekt
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	private static Genres gibGenreDaten()
	{
		Genres genres_data = null;
		
		try
		{
			context = JAXBContext.newInstance(Genres.class);
			Unmarshaller um = context.createUnmarshaller();
			genres_data = (Genres) um.unmarshal(new FileInputStream("XSD/genres.xml"));
		}
		catch (FileNotFoundException | JAXBException e)
		{
			System.out.println("Liste der Genres konnte nicht gefunden oder gelesen werden.");
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
	private static Kategorien gibKategorienDaten(String genre_id)
	{
		Kategorien kategorien_data = null;
		
		try
		{
			context = JAXBContext.newInstance(Kategorien.class);
			Unmarshaller um = context.createUnmarshaller();
	    	kategorien_data = (Kategorien) um.unmarshal(new FileInputStream("XSD/kategorien_"+ genre_id +".xml"));
		}
		catch (FileNotFoundException | JAXBException e)
		{
			System.out.println("Liste der Kategorien konnte nicht gefunden oder gelesen werden.");
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
	@Produces(MediaType.APPLICATION_XML)
	public Genres getGenres()
	{
		return gibGenreDaten();
	}
	
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
	@Produces(MediaType.APPLICATION_XML)
	public Genre getGenre(@PathParam("genre_id") String genre_id)
	{
		Genres genres_daten = gibGenreDaten();
		Genre genre = null;
		
		for ( int i=0; i<genres_daten.getGenre().size(); i++)
		{
			if ( genres_daten.getGenre().get(i).getGenreId().equals(genre_id) )
				genre = genres_daten.getGenre().get(i);
		}
		
		if ( genre == null )
			System.err.println("Das Genre existert nicht.");
		
		return genre;
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
	public Kategorien getKategorien(@PathParam("genre_id") String genre_id) 
	{
		return gibKategorienDaten(genre_id);	
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
	public Kategorie getKategorie(@PathParam("genre_id") String genre_id, @PathParam("kategorien_id") String kategorien_id) throws FileNotFoundException, JAXBException
	{
		Kategorien kategorien_daten = gibKategorienDaten(genre_id);
		Kategorie k = null;
		for (int i=0; i<kategorien_daten.getKategorie().size(); i++)
		{
			if (kategorien_daten.getKategorie().get(i).getKategorieId().equals(kategorien_id))
				k = kategorien_daten.getKategorie().get(i);
		}
		
		if ( k == null )
			System.err.println("Eine solche Kategorie existiert nicht.");
	    
		return k;
	}
}

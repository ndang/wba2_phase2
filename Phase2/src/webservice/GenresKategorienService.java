package webservice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.GregorianCalendar;

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
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import app.*;

@Path("/genres")
public class GenresKategorienService {
	
	/**
	 * 
	 * @return gibt geunmarshallte Liste aller Genres zurück
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	private Genres gibGenreDaten() throws JAXBException, FileNotFoundException
	{
		JAXBContext context = JAXBContext.newInstance(Genres.class);
	    Unmarshaller um = context.createUnmarshaller();
	    Genres genres_data = (Genres) um.unmarshal(new FileInputStream("XSD/genres.xml"));
		return genres_data;
	}
	
	/**
	 * 
	 * @param genre_id in welcher sich die Kategorie befindet
	 * @return gibt geunmarshallte Liste aller Kategorien zurück
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	private Kategorien gibKategorienDaten(String genre_id) throws JAXBException, FileNotFoundException
	{
		JAXBContext context = JAXBContext.newInstance(Kategorien.class);
	    Unmarshaller um = context.createUnmarshaller();
	    Kategorien kategorien_data = (Kategorien) um.unmarshal(new FileInputStream("XSD/kategorien_"+ genre_id +".xml"));
		return kategorien_data;
	}

	/**
	 * 
	 * @param id falls per Query eine genre-id mitgegeben wird, wird ein bestimmtes Genre zurück gegeben
	 * @return die Liste alle Genres
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 * @throws DatatypeConfigurationException
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getGenres(@QueryParam("id") String id) throws JAXBException, FileNotFoundException, DatatypeConfigurationException
	{
		if(id==null)
		{
			String genres_list = "";
			Genres genres_data = gibGenreDaten();
		    
		    for (int i=0; i<genres_data.getGenre().size(); i ++)
		    	genres_list += genres_data.getGenre().get(i).getGenreTitel() + "\n";
		    
			return genres_list;

		}
		return getGenre(id);
	}
	
// dieselbe GET wie oben nur ohne Queryparams
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
	 * 
	 * @param genre_id identifiziert ein Genre aus der Liste aller Genres
	 * @return Titel und Beschreibung eines Genre konkatiniert in einem String
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
		Genres genres_data = gibGenreDaten();
	    
	    genres_info = genres_data.getGenre().get(nr).getGenreTitel() + "\n";
	    genres_info += genres_data.getGenre().get(nr).getGenreBeschreibung();
	    
		return genres_info;
	}
	
	/**
	 * 
	 * @param genre_id ist die ID in der alle Kategorien angehörig ist
	 * @param id falls eine Kategorie per Query anhand ihrer ID abgerufen wird
	 * @return die Liste aller Kategorien eines Genres
	 * @throws FileNotFoundException
	 * @throws JAXBException
	 */
	@GET
	@Path("/{genre_id}/kategorien")
	@Produces(MediaType.TEXT_PLAIN)
	public String getKategorien(@PathParam("genre_id") String genre_id, @QueryParam("id") String id) throws FileNotFoundException, JAXBException
	{
		if (id==null)
		{
			String kategorien_list = "";
			Kategorien kategorien_data = gibKategorienDaten(genre_id);
		    
		    for (int i=0; i<kategorien_data.getKategorie().size(); i ++)
		    	kategorien_list += kategorien_data.getKategorie().get(i).getKategorieTitel() + "\n";
		    
			return kategorien_list;
		}
		
		return getKategorie(genre_id, id);
	}
	
	/**
	 * 
	 * @param genre_id ist die ID in der alle Kategorien angehörig ist
	 * @param kategorien_id  ist die ID in der die angefragte Kategorie angehörig ist
	 * @return Titel und Beschreibung einer Kategorie konkatiniert in einem String
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
		Kategorien kategorien_data = gibKategorienDaten(genre_id);
		
		kategorie_info = kategorien_data.getKategorie().get(nr).getKategorieTitel() + "\n";
		kategorie_info += kategorien_data.getKategorie().get(nr).getKategorieBeschreibung();
	    
		return kategorie_info;
	}
}

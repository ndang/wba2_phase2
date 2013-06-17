package webservice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import app.Genre;
import app.Genres;
import app.Kategorie;
import app.Kategorien;

@Path("/genres")
public class GenresKategorienService {
	
	static private JAXBContext context;

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Genres getGenres()
	{
		Genres genres_data = new Genres();
		System.out.println("Start der get-Methode");
		try
		{
			context = JAXBContext.newInstance(Genres.class);
			Unmarshaller um = context.createUnmarshaller();
			genres_data = (Genres) um.unmarshal(new FileInputStream("XSD/genres.xml"));
		} catch (FileNotFoundException | JAXBException e) {
			System.out.println("Liste der Genres konnte nicht gefunden oder gelesen werden.");
		}
		
		return genres_data;
	}
	
	@GET 
	@Path("/{genre_id}")
	@Produces(MediaType.APPLICATION_XML)
	public String getGenre( @PathParam( "genre_id" ) String genre_id )
	{
		
		Genres genres_daten = getGenres();
		Genre genre = null;
		
		for (Genre genre_item: genres_daten.getGenre()){
			if (genre_item.getGenreId().compareTo(genre_id)==0){
				genre = genre_item;
			}
		}
		
		System.out.println("Nach for-Schleife");
		
		if ( genre == null )
			System.err.println( "Das Genre existert nicht." );
		
//		/*** test***/
//		try {
//			
//			JAXBContext context = JAXBContext.newInstance(Themes.class);
//			Marshaller m = context.createMarshaller();		
//			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//			m.marshal(genre, new FileOutputStream( "XSD/genre_test.xml" ));
//			
//		} catch (FileNotFoundException | JAXBException e) {
//			System.out.println( "Theme(s) konnte(n) nicht gespeichert werden." );
//		}
//		/*********/
		return "hi";
	}
	 
	@GET
	@Path("/{genre_id}/kategorien")
	@Produces(MediaType.APPLICATION_XML)
	public Kategorien getKategorien( @PathParam( "genre_id" ) String genre_id ) 
	{
		Kategorien kategorien_data = null;
		
		try
		{
			context = JAXBContext.newInstance( Kategorien.class );
			Unmarshaller um = context.createUnmarshaller();
	    	kategorien_data = (Kategorien) um.unmarshal( new FileInputStream("XSD/kategorien_"+ genre_id +".xml" ));
		} catch (FileNotFoundException | JAXBException e) {
			System.out.println( "Liste der Kategorien konnte nicht gefunden oder gelesen werden." );
		}
		
		return kategorien_data;
	}
	
	@GET
	@Path( "/{genre_id}/kategorien/{kategorien_id}" )
	@Produces(MediaType.APPLICATION_XML)
	public Kategorie getKategorie( @PathParam( "genre_id" ) String genre_id, @PathParam( "kategorien_id" ) String kategorien_id )
	{
		Kategorien kategorien_daten = getKategorien( genre_id );
		Kategorie k = null;
		for ( int i=0; i<kategorien_daten.getKategorie().size(); i++ )
		{
			if ( kategorien_daten.getKategorie().get(i).getKategorieId().equals(kategorien_id) )
				k = kategorien_daten.getKategorie().get(i);
		}
		
		if ( k == null )
			System.err.println( "Eine solche Kategorie existiert nicht." );
	    
		return k;
	}
	
}

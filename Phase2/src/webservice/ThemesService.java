package webservice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
//import app.*;
import app.Genres;
import app.Themes;
import app.Themes.Theme;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@Path("/themes")
public class ThemesService {
	
	private Themes gibThemeDaten() throws JAXBException, FileNotFoundException
	{
		JAXBContext context = JAXBContext.newInstance(Themes.class);
	    Unmarshaller um = context.createUnmarshaller();
	    Themes theme_data = (Themes) um.unmarshal(new FileInputStream("XSD/themes.xml"));
		return theme_data;
	}
	
	private void setzeThemeDaten(Themes t) throws JAXBException, FileNotFoundException
	{
		JAXBContext context = JAXBContext.newInstance(Themes.class);
		Marshaller m = context.createMarshaller();
	    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    //m.marshal(b, System.out);
		m.marshal(t, new FileOutputStream("XSD/themes.xml"));
	}
	
//	@GET
//	@Produces(MediaType.TEXT_PLAIN)
//	public String getThemes(String number)
//	{
//		return null;
//	}
//	
//	@GET
//	@Path("/{theme_id}")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String getTheme(@PathParam("theme_id") String number)
//	{
//		return null;
//	}
	
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
				return Response.ok().build();
			}
		}
		return Response.status(404).build();
	}
	
//	@POST
//	@Produces(MediaType.TEXT_PLAIN)
//	@Consumes(MediaType.TEXT_PLAIN) // erlaubte MIME-types, die diese Methode verarbeiten kann
//	public Response addTheme(String titel,  List<Genres.Genre> genres, String[] kategorien) throws FileNotFoundException, JAXBException
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
	
//	@POST // haben wir in unseren Notizen anders
//	@Path("{theme_id}")
//	@Produces(MediaType.TEXT_PLAIN)
//	@Consumes(MediaType.TEXT_PLAIN)
//	public Response replaceTheme(String newname ,@PathParam("theme_id") int number)
//	{
//		return null;
//	}
//	
//	//********************** Kommentare ***********************************
//	
//	@GET
//	@Path("{theme_id}/kommentare")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String getKommentare(@PathParam("theme_id") String number)
//	{
//		return null;
//	}
//	
//	@GET
//	@Path("{theme_id}/kommentare/{kommentar_id}")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String getKommentar(@PathParam("theme_id") String number, @PathParam("kommentar_id") String nummer2)
//	{
//		return null;
//	}
	
//	@POST
//	@Path("/{theme_id}/kommentare")
//	@Produces(MediaType.TEXT_PLAIN)
//	@Consumes(MediaType.TEXT_PLAIN) // ?
//	public Response addKommentar(@PathParam("theme_id") String theme_id, String kommentar) throws FileNotFoundException, JAXBException
//	{
//		Themes daten = gibThemeDaten();
//		Theme t_neu = new Theme();
//		
//		t_neu.getInteraktion().getKommentare().getKommentar().add(kommentar);
//		
//		return null;
//	}

}

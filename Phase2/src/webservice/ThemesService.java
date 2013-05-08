package webservice;

import java.io.FileNotFoundException;
import java.util.List;

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
import javax.xml.bind.JAXBException;

import app.*;

@Path("/themes")
public class ThemesService {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getThemes(String number)
	{
		return null;
	}
	
	@GET
	@Path("{theme_id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getTheme(@PathParam("theme_id") String number)
	{
		return null;
	}
	
	@DELETE
	@Path("{theme_id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteTheme(@PathParam("theme_id") int number)
	{
		return null;
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN) // ?
	public Response addTheme(String name)
	{
		return null;
	}
	
	@POST // haben wir in unseren Notizen anders
	@Path("{theme_id}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public Response replaceTheme(String newname ,@PathParam("theme_id") int number)
	{
		return null;
	}
	
	//********************** Kommentare ***********************************
	
	@GET
	@Path("{theme_id}/kommentare")
	@Produces(MediaType.TEXT_PLAIN)
	public String getKommentare(@PathParam("theme_id") String number)
	{
		return null;
	}
	
	@GET
	@Path("{theme_id}/kommentare/{kommentar_id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getKommentar(@PathParam("theme_id") String number, @PathParam("kommentar_id") String nummer2)
	{
		return null;
	}
	
	@POST
	@Path("{theme_id}/kommentare")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN) // ?
	public Response addKommentar(@PathParam("theme_id") String number, String name)
	{
		return null;
	}

}

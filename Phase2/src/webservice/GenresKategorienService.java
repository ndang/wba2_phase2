package webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/genres")
public class GenresKategorienService {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getGenres(String number)
	{
		return null;
	}
	
	@GET
	@Path("{genre_id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getGenre(@PathParam("genre_id") String number)
	{
		return null;
	}
	
	@GET
	@Path("/kategorien")
	@Produces(MediaType.TEXT_PLAIN)
	public String getKategorien(String number)
	{
		return null;
	}
	
	@GET
	@Path("/kategorien/{kategorien_id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getKategorie(@PathParam("kategorien_id") String number)
	{
		return null;
	}
}

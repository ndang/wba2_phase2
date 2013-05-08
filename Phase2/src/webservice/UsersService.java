package webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UsersService {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getUsers(String number)
	{
		return null;
	}
	
	@GET
	@Path("{user_id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getKommentar(@PathParam("user_id") String number)
	{
		return null;
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN) // ?
	public Response addKommentar(String name)
	{
		return null;
	}

}

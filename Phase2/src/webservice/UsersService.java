package webservice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import app.*;
import app.Themes.Theme.Interaktion.Kommentare.Kommentar;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


@Path("/users")
public class UsersService {
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getUsers() throws JAXBException, FileNotFoundException
	{
		JAXBContext context = JAXBContext.newInstance(Themes.class);
	    Unmarshaller um = context.createUnmarshaller();
	    Themes themes = (Themes) um.unmarshal(new FileInputStream("XSD/themes.xsd"));
	    
	    String u = themes.getTheme().get(0).getInteraktion().getAbonennten().getUser().get(0);
	    
		return u;
	}
	
//	@GET
//	@Path("/{user_id}")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String getKommentar(@PathParam("user_id") String user_id)
//	{
//		return null;
//	}
//	
//	@POST
//	@Produces(MediaType.TEXT_PLAIN)
//	@Consumes(MediaType.TEXT_PLAIN)
//	public Response addKommentar(String name)
//	{
//		JAXBContext context = JAXBContext.newInstance(Themes.class);
//	    Unmarshaller um = context.createUnmarshaller();
//	    User u = (User) um.unmarshal(new FileInputStream(name));
//	    
//	    Users daten = (Users) um.unmarshal(new FileInputStream("XSD/users.xml"));
//	    daten.getTheme().get(0).add(u);
//	    
//	    Marshaller m = context.createMarshaller();
//	    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//		m.marshal(daten, new FileOutputStream("XSD/users.xml"));
//	
//		return Response.status(201).build();
//	}

}

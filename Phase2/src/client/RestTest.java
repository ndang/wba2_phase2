package client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import app.*;
import app.Genres.Genre;
import app.Themes.Theme;
import app.Themes.Theme.Interaktion.Kommentare;

public class RestTest {

	public static void main(String[] args) {
		WebServiceClient wsc = new WebServiceClient();
		
		Genres gs = wsc.getGenres();
		System.out.println(gs.getGenre().get(0).getGenreId().toString());
		
		Themes  ts = wsc.getThemes();
		System.out.println(ts.getTheme().get(0).getAllgemeines().getThemeTitel().toString());
		
		Genre g = wsc.getGenre("g0");
		System.out.println(g.getGenreId().toString());
		
//		Theme t = null;
//		Themes themes;
//		try
//		{
//			JAXBContext context = JAXBContext.newInstance( Theme.class );
//			Unmarshaller um = context.createUnmarshaller();
//			Marshaller m = context.createMarshaller();
//			t = (Theme) um.unmarshal( new FileInputStream( "XSD/theme_post.xml" ) );
//		} catch ( JAXBException | FileNotFoundException e ) {
//			e.printStackTrace();
//			System.err.println("Kein JAXBContext konnte intanziiert werden.");
//		}
//
//		String result = wsc.postTheme(t);
//		System.out.println(result);
		
//		Kommentare kos = wsc.getKommentare("t0_k0_g0");
//		System.out.println(kos.getKommentar().get(0).getNachricht().toString());
		

		
//		Kategorien ks = wsc.getKategorien("g0");
//		System.out.println(ks.getKategorie().get(0).getKategorieId().toString());
		
//		System.out.println(wsc.getKategorie("g0", "k0_g0").getKategorieId().toString());

	}

}

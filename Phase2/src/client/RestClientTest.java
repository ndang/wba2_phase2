package client;

import jaxb.Genre;
import jaxb.Genres;
import jaxb.Kategorie;
import jaxb.Kategorien;
import jaxb.Theme;
import jaxb.Themes;

/**
 * Testet jede implementierte HTTP-Methode.
 * @param args
 */
public class RestClientTest
{
	public static void main(String[] args)
	{
		/********** GET - Methoden ******************/
		
		RestClient wsc = new RestClient();
		
		Genres gs = wsc.getGenres();
		System.out.println( "Genre-Anzahl: " + gs.getGenre().size());

		Kategorien ks = wsc.getKategorien("g0");
		System.out.println( "Kategorie-Anzahl: " + ks.getKategorie().size());
		
		Themes ts = wsc.getThemes();
		System.out.println( "Theme-Anzahl: " + ts.getTheme().size());
		
		Genre g = wsc.getGenre("g0");
		System.out.println( "Irgendeine Genre-ID: " + g.getGenreId().toString());
		
		Kategorie k = wsc.getKategorie("g0", "k0_g0");
		System.out.println( "Irgendeine Kategorie-ID: " + k.getKategorieId().toString());
		
		Theme t = wsc.getTheme("t0_k0_g0");
		System.out.println( "Irgendeine Theme-ID: " + t.getAllgemeines().getThemeId().toString());
		
//		Kommentare kos = wsc.getKommentare("t0_k0_g0");
//		System.out.println(kos.getKommentar().size());
		
		/********** POST - Methode ******************/
		
		Theme newT = new Theme();
		newT.setAllgemeines(new Theme.Allgemeines());
		newT.getAllgemeines().setThemeId("test");
		wsc.postTheme(newT);
		System.out.println(newT.getAllgemeines().getThemeId().toString());
		System.out.println("Neue Theme-Anzahl: " + ts.getTheme().size());
		
		/********** PUT - Methode ******************/
		
		newT.setModule(new Theme.Module());
		wsc.putTheme(newT, newT.getAllgemeines().getThemeId());
		System.out.println(newT.getModule().toString());
		
		/********** DELETE - Methode ******************/
		
		wsc.deleteTheme(newT.getAllgemeines().getThemeId());
		int truuuuth = 0;
		for ( Theme t_items : ts.getTheme() )
		{
			if ( t_items.equals(newT) )
				truuuuth = 1;
		}
		
		System.out.println(truuuuth);
	}

}

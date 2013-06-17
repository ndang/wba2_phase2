package client;

import app.Genres;
import app.Themes;


public class RestTest {

	public static void main(String[] args)
	{
		WebServiceClient wsc = new WebServiceClient();
		
		Genres gs = wsc.getGenres();
		System.out.println(gs.getGenre().get(0).getGenreId().toString());
		
		Themes  ts = wsc.getThemes();
		System.out.println(ts.getTheme().get(0).getAllgemeines().getThemeTitel().toString());
		
	}

}

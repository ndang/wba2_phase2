package client;

import java.util.Scanner;

import javax.xml.bind.JAXBException;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class Menue {
	
	Scanner in = new Scanner(System.in);
	PartyClient app;

	public Menue(PartyClient app) throws XMPPException, JAXBException
	{
		this.app = app;
	}
	
	private void home()
	{
		System.out.println();
		System.out.println("Welche Option möchten Sie ausführen?");
		System.out.println();
		
		System.out.println("1 - Meine Abonnements ansehen");
		System.out.println("2 - Meine Themes verwalten (ansehen, bearbeiten, erstellen)");
		System.out.println("3 - Nach interessanten Party-Themes suchen (Genres anzeigen)");
		System.out.println("4 - Programm beenden");
		
		System.out.println();
		System.out.print("Ihre Auswahl: ");
		int auswahl = in.nextInt();
		
		switch(auswahl)
		{
//			case 1: app.gibAbonnements(); break; //TODO
//			case 2: app.gibMyThemes(); break; // TODO
		
			case 3:
				System.out.println("Diese Genres sind verfügbar:");
				app.topicInfo("g");
				g_menue();
				break;
				
			case 4:
				app.ausloggen();
				break;
				
			default:
				System.out.println("Diese Option ist unzulässig");
				home();
				break;
		}
	}

	private void g_menue()
	{	
		System.out.println();
		System.out.println("Welche Option möchten Sie ausführen?");
		System.out.println();
		
		System.out.println("1 - Ein Genre abonnieren");
		System.out.println("2 - Kategorien des Genre anzeigen");
		System.out.println("3 - Programm beenden");
		
		System.out.println();
		System.out.print("Ihre Auswahl: ");
		int auswahl = in.nextInt();
		
		String id;
		
		switch(auswahl)
		{
			case 1:
				id = id_waehlen();
				app.abonnieren(id);
				break; //TODO
				
			case 2:
				id = id_waehlen();
				app.topicInfo("g", id);
				k_menue();
				break; // TODO
	
			case 3:
				app.ausloggen();
				break;
				
			default:
				System.out.println("Diese Option ist unzulässig");
				g_menue();
				break;
		}
	}
	
	private void k_menue()
	{	
		System.out.println();
		System.out.println("Welche Option möchten Sie ausführen?");
		System.out.println();
		
		System.out.println("1 - Eine Kategorie abonnieren");
		System.out.println("2 - Themes der Kategorie anzeigen");
		System.out.println("3 - Programm beenden");
		
		System.out.println();
		System.out.print("Ihre Auswahl: ");
		int auswahl = in.nextInt();
		
		String id;
		
		switch(auswahl)
		{
			case 1:
				id = id_waehlen();
				app.abonnieren(id);
				break; //TODO
				
			case 2:
				id = id_waehlen();
				app.topicInfo("t");
				t_menue();
				break; // TODO
	
			case 3:
				app.ausloggen();
				break;
				
			default:
				System.out.println("Diese Option ist unzulässig");
				k_menue();
				break;
		}
	}
	
	private void t_menue()
	{	
		System.out.println();
		System.out.println("Welche Option möchten Sie ausführen?");
		System.out.println();
		
		System.out.println("1 - Ein Theme abonnieren");
		System.out.println("2 - Ein Theme modifizieren");
		System.out.println("3 - Programm beenden");
		
		System.out.println();
		System.out.print("Ihre Auswahl: ");
		int auswahl = in.nextInt();
		
		String id;
		
		switch(auswahl)
		{
			case 1:
				id = id_waehlen();
				app.abonnieren(id);
				break; //TODO
				
			case 2:
				id = id_waehlen();
//				theme_bearb();
				break; // TODO
	
			case 3:
				app.ausloggen();
				break;
				
			default:
				System.out.println("Diese Option ist unzulässig");
				k_menue();
				break;
		}
	}
	
	private String id_waehlen()
	{
		System.out.print("Geben Sie die gewünschte ID ein: ");
		return in.next();
	}

	
}

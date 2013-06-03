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
		home();
	}
	
	private void home()
	{
		System.out.println();
		System.out.println("__________________________________________________________________");
		System.out.println();
		
		System.out.println("1 - Meine Abonnements ansehen");
		System.out.println("2 - Ein Abonnements kündigen");
		System.out.println("3 - Meine Themes verwalten (ansehen, bearbeiten, erstellen)");
		System.out.println("4 - Nach interessanten Party-Themes suchen (Genres anzeigen)");
		System.out.println("5 - Programm beenden");
		
		int auswahl = 0;
		
		do 
		{
			System.out.println();
			System.out.print("Ihre Auswahl: ");
			auswahl = in.nextInt();
		} while (auswahl == 0);
		
		String id;
		
		switch(auswahl)
		{
			case 1:
				app.gibAbonnements();
				home();
				break;
				
			case 2:
				if (app.gibAbonnements())
				{
					id = id_waehlen();
					app.abo_kuendigen(id);
				}
				else
					System.out.println("Keine Abos zum kündigen vorhanden.");
				home();
				break;
				
			case 3:
				t_publish_menue();
				break;
		
			case 4:
				System.out.println("Diese Genres sind verfügbar:");
				app.topicInfo("g");
				g_menue();
				break;
				
			case 5:
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
		System.out.println("__________________________________________________________________");
		System.out.println();
		
		System.out.println("1 - Ein Genre abonnieren");
		System.out.println("2 - Kategorien des Genre anzeigen");
		System.out.println("3 - zum Hauptmenü");
		System.out.println("4 - Programm beenden");
				
		int auswahl = 0;
		
		do 
		{
			System.out.println();
			System.out.print("Ihre Auswahl: ");
			auswahl = in.nextInt();
		} while (auswahl == 0);
		
		String id;
		
		switch(auswahl)
		{
			case 1:
				id = id_waehlen();
				app.abonnieren(id);
				home();
				break;
				
			case 2:
				id = id_waehlen();
				app.topicInfo("g", id);
				k_menue();
				break;
				
			case 3:
				home();
				break;
	
			case 4:
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
		System.out.println("__________________________________________________________________");
		System.out.println();
		
		System.out.println("1 - Eine Kategorie abonnieren");
		System.out.println("2 - Themes der Kategorie anzeigen");
		System.out.println("3 - zum Hauptmenü");
		System.out.println("4 - Programm beenden");
		
		int auswahl = 0;
		
		do 
		{
			System.out.println();
			System.out.print("Ihre Auswahl: ");
			auswahl = in.nextInt();
		} while (auswahl == 0);
		
		String id;
		
		switch(auswahl)
		{
			case 1:
				id = id_waehlen();
				app.abonnieren(id);
				home();
				break;
				
			case 2:
				id = id_waehlen();
				app.topicInfo("t");
				t_menue();
				break;
	
			case 3:
				home();
				break;
				
			case 4:
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
		System.out.println("__________________________________________________________________");
		System.out.println();
		
		System.out.println("1 - Ein Theme abonnieren");
		System.out.println("2 - Ein Theme modifizieren");
		System.out.println("3 - zum Hauptmenü");
		System.out.println("4 - Programm beenden");
		
		
		int auswahl = 0;
		
		do 
		{
			System.out.println();
			System.out.print("Ihre Auswahl: ");
			auswahl = in.nextInt();
		} while (auswahl == 0);
		
		String id;
		
		switch(auswahl)
		{
			case 1:
				id = id_waehlen();
				app.abonnieren(id);
				home();
				break;
				
			case 2:
				id = id_waehlen();
//				theme_bearb();
				break; 
	
			case 3:
				home();
				break;
	
			case 4:
				app.ausloggen();
				break;
				
			default:
				System.out.println("Diese Option ist unzulässig");
				k_menue();
				break;
		}
	}
	
	private void t_publish_menue()
	{
		System.out.println();
		System.out.println("__________________________________________________________________");
		System.out.println();
		
		System.out.println("1 - Ein Theme erstellen");
		System.out.println("2 - Ein Theme modifizieren");
		System.out.println("3 - zum Hauptmenü");
		System.out.println("4 - Programm beenden");
		
		int auswahl = 0;
		
		do 
		{
			System.out.println();
			System.out.print("Ihre Auswahl: ");
			auswahl = in.nextInt();
		} while (auswahl == 0);
		
		String id;
		
		switch(auswahl)
		{
			case 1:
				in.nextLine();
				System.out.println("Item:");
				id = in.nextLine();
//				app.theme_item_hinzufügen(null, null);
//				app.new_topic("t");
				break;
				
			case 2:
//				id = id_waehlen();
//				app.theme_item_hinzufügen(id, null);
				break;
				
			case 3:
				home();
				break;
	
			case 4:
				app.ausloggen();
				break;
				
			default:
				System.out.println("Diese Option ist unzulässig");
				g_menue();
				break;
		}
	}
	
	private String id_waehlen()
	{
		String auswahl = "";
		do 
		{
			System.out.println();
			System.out.print("Geben Sie die gewünschte ID ein: ");
			auswahl = in.next();
		} while (auswahl.equals(""));
		
		return auswahl;
	}
}

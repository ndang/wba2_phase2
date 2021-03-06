package client;

import java.util.Scanner;

import jaxb.Genre;

import org.jivesoftware.smack.XMPPException;

public class KonsolenClient
{
	
	Scanner in = new Scanner(System.in);
	XmppClient xc;
	PartyClient pc;

	public KonsolenClient() throws XMPPException
	{
		this.xc = new XmppClient();
		this.pc = new PartyClient();
		home();
	}
	
	private void home()
	{
		System.out.println();
		System.out.println("__________________________________________________________________");
		System.out.println();
		
		System.out.println("1 - Meine Abonnements ansehen");
		System.out.println("2 - Ein Abonnement k�ndigen");
		System.out.println("3 - Meine Themes verwalten (ansehen, bearbeiten, erstellen)");
		System.out.println("4 - Nach interessanten Party-Themes suchen (Genres anzeigen)");
		System.out.println("5 - Benachrichtigungen verwalten");
		System.out.println("6 - Programm beenden");
		System.out.println("7 - Etwas publishen");
		
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
				for (String s: pc.xc.getSubscriptions())
					System.out.println(s);
				home();
				break;
				
			case 2:
//				if (jaxb.getMySubscriptions())
//				{
					id = id_waehlen();
					xc.unsubscribe(id);
//				}
//				else
					System.out.println("Keine Abos zum k�ndigen vorhanden.");
				home();
				break;
				
			case 3:
			try {
				t_publish_menue();
			} catch (XMPPException e) {
				e.printStackTrace();
			}
				break;
		
			case 4:
				System.out.println("Diese Genres sind verf�gbar:");
//				jaxb.getTopicTitle("g");
				g_menue();
				break;
				
			case 5:
				System.out.println(xc.benachrichtigungen);
				home();
				break;
				
			case 6:
				pc.logout();
				break;
				
			case 7:
				String node = id_waehlen();
				System.out.print("Text: ");
				String payload = in.next();
				
				xc.publish(node, payload);
				break;
			default:
				System.out.println("Diese Option ist unzul�ssig");
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
		System.out.println("3 - zum Hauptmen�");
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
				xc.subscribe(id);
				home();
				break;
				
			case 2:
				id = id_waehlen();
				for (Genre g : pc.rc.getGenres().getGenre())
					System.out.println( g.getGenreId() );
					
				k_menue();
				break;
				
			case 3:
				home();
				break;
	
			case 4:
				pc.logout();
				break;
				
			default:
				System.out.println("Diese Option ist unzul�ssig");
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
		System.out.println("3 - zum Hauptmen�");
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
				xc.subscribe(id);
				home();
				break;
				
			case 2:
				id = id_waehlen();
//				jaxb.getTopicTitle("t");
//				t_menue();
				break;
	
			case 3:
				home();
				break;
				
			case 4:
				pc.logout();
				break;
				
			default:
				System.out.println("Diese Option ist unzul�ssig");
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
		System.out.println("3 - zum Hauptmen�");
		System.out.println("4 - Programm beenden");
		
		
		int auswahl = 0;
		
		do 
		{
			System.out.println();
			System.out.print("Ihre Auswahl: ");
			auswahl = in.nextInt();
		} while (auswahl == 0);
		
		String id;
		String item_payload;
		
		switch(auswahl)
		{
			case 1:
				id = id_waehlen();
				xc.subscribe(id);
				home();
				break;
				
			case 2:
				id = id_waehlen();
				System.out.println("");
				item_payload = in.nextLine();
//				theme_bearb(id, itemPayload);
				break; 
	
			case 3:
				home();
				break;
	
			case 4:
				pc.logout();
				break;
				
			default:
				System.out.println("Diese Option ist unzul�ssig");
				k_menue();
				break;
		}
	}
	
	private void t_publish_menue() throws XMPPException
	{
		System.out.println();
		System.out.println("__________________________________________________________________");
		System.out.println();
		
		System.out.println("1 - Ein Theme erstellen");
		System.out.println("2 - Ein Theme erweitern");
		System.out.println("3 - Etwas aus einem Theme l�schen");
		System.out.println("4 - zum Hauptmen�");
		System.out.println("5 - Programm beenden");
		
		int auswahl = 0;
		
		do 
		{
			System.out.println();
			System.out.print("Ihre Auswahl: ");
			auswahl = in.nextInt();
		} while (auswahl == 0);
		
		String id;
		String theme_body;
		String zu_loeschen;
		
		switch(auswahl)
		{
			case 1:
				in.nextLine();
				System.out.println("Theme:");
				theme_body = in.nextLine();
//				jaxb.createTopic(id);
				t_publish_menue();
				break;
				
			case 2:
				id = id_waehlen();
				in.nextLine();
				System.out.println("Neu:");
				theme_body = in.nextLine();
				xc.publish(id, "new");
				t_publish_menue();
				break;
				
			case 3:
				id = id_waehlen();
				in.nextLine();
				System.out.println("Was soll gel�scht werden? :");
				zu_loeschen = in.nextLine();
//				jaxb.theme_item_loeschen(id, zu_loeschen);
				t_publish_menue();
				break;
				
			case 4:
				home();
				break;
	
			case 5:
				pc.logout();
				break;
				
			default:
				System.out.println("Diese Option ist unzul�ssig");
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
			System.out.print("Geben Sie die gew�nschte ID ein: ");
			auswahl = in.next();
		} while (auswahl.equals(""));
		
		return auswahl;
	}
	
	public static void main(String [] args) throws XMPPException
	{
		new KonsolenClient();
	}
}

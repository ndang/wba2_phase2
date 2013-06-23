package GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import app.Genre;
import app.Kategorie;
import app.Theme;

import client.PartyClient;

public class PartyGUI extends JFrame {

	private JPanel contentPane;
	private JPanel main_panel;
	private JTextField txtTitelHierEingeben;
	private PartyClient partyc;
		
	// Buttons
	JButton btnAusloggen;
	JButton btnHilfe;
	
	// TabbedPanes
	JTabbedPane mainTabbedPane;
	
	//Panels
	JPanel panel_news;
	JPanel panel_abos;
	JPanel panel_search;
	JPanel panel_create;
	
	//Listen
	JList list_benachrichtigungen;
	Vector<String> genres_liste;
	JList list_genre;
	Vector<String> kategorien_liste;
	JList list_kategorien;
	Vector<String> theme_topics;
	JList list_themes;
	JScrollPane scrollPane_theme;
	Vector<String> benachrichtigungen_v;
	String nachricht;
	JLabel lblZurZeitHaben;
	JScrollPane scrollPane_news;
	Vector<String> mySubscriptions;
	
	// Tree
	JTree tree_abos;
	
	// Checkbox
	JComboBox<String> checkBox_subscriptions;
	JComboBox<String> comboBox_auswaehlen;
	JComboBox<String> comboBox_genre;
	JComboBox<String> comboBox_kategorie;
	
	//Labels
	JLabel lblGenre;
	JLabel lblKategorien;
	JLabel lblThemes;
	JLabel lblThemeTitel;
	JLabel lblKategorie;
 
	public static void start()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					PartyGUI frame = new PartyGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
			}
		}
	});
}
	/**
	 * Constructor 
	 * holt aus dem Vektor die Abonnements
	 */
	public PartyGUI()
	{
		partyc = new PartyClient();
		mySubscriptions = partyc.getMySubscriptions();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 679, 460);
		
		main_panel = new JPanel();
		main_panel.setLayout(null);
		contentPane = new JPanel();
		contentPane.setBorder( new EmptyBorder(5, 5, 5, 5) );
		contentPane.setLayout( new BoxLayout(contentPane, BoxLayout.X_AXIS) );
		contentPane.add(main_panel);
		
		setContentPane(contentPane);
		fixedMenue();
		mainTabbedMenue();
	}
	
	private void fixedMenue()
	{
		btnAusloggen = new JButton("Logout");
		btnAusloggen.setBounds(554, 376, 89, 23);
		btnHilfe = new JButton("Hilfe");
		btnHilfe.setBounds(455, 376, 89, 23);
		main_panel.add(btnAusloggen);
		main_panel.add(btnHilfe);
		
		/**************** ACTIONLISTENER *********************/
		
		btnAusloggen.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		
		btnHilfe.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				InfoPopup.start("In WBA verdienen wir eine 1,0..!");
			}
		});
	}

	private void mainTabbedMenue()
	{
		mainTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		mainTabbedPane.setBounds(0, 0, 653, 356);
		main_panel.add(mainTabbedPane);
		
		panel_news = new JPanel();
		mainTabbedPane.addTab("Benachrichtigungen", null, panel_news, null);
		newsMenue(panel_news);
		
		panel_abos = new JPanel();
		mainTabbedPane.addTab("Meine Abonnements", null, panel_abos, null);
		aboMenue(panel_abos);
		
		panel_search = new JPanel();
		mainTabbedPane.addTab("Stöbern", null, panel_search, null);
		searchMenue(panel_search);
		
		panel_create = new JPanel();
		mainTabbedPane.addTab("Theme bearbeiten/erstellen", null, panel_create, null);
		themesMenue(panel_create);
		
		JPanel panel_publish = new JPanel();
		panel_publish.setToolTipText("publish something");
		mainTabbedPane.addTab("Publishing", null, panel_publish, null);
		publishMenue(panel_publish);
		
		JLabel lblHinweisManMuss = new JLabel("Ein Theme muss abonniert sein, um es zu bearbeiten/dazu zu publishen.");
		lblHinweisManMuss.setBounds(10, 380, 435, 14);
		main_panel.add(lblHinweisManMuss);
	}
	
	private void newsMenue(JPanel panel_news)
	{
		// TODO: Feld auch anzeigen, was Neues passiert ist während er offline war
		
		/**************************** BenachrichtigungenFeld **********************************/
		
		nachricht = "Zur Zeit haben Sie keine Benachrichtigungen.";
		benachrichtigungen_v = partyc.getBenachrichtigungen();
		
		if (!benachrichtigungen_v.isEmpty())
			nachricht = "Sie haben " + benachrichtigungen_v.size() + " Benachrichtigung(en).";
		
		lblZurZeitHaben = new JLabel(nachricht);
		
		list_benachrichtigungen = new JList( benachrichtigungen_v );
		scrollPane_news = new JScrollPane( list_benachrichtigungen );
		
		JButton btnAllesLoeschen = new JButton("Benachrichtigungen löschen");
		
		/**************************** LAYOUT ************************************************/
		
		GroupLayout gl_panel_news = new GroupLayout(panel_news);
		gl_panel_news.setHorizontalGroup(
			gl_panel_news.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_news.createSequentialGroup()
					.addGap(204)
					.addComponent(lblZurZeitHaben)
					.addContainerGap(228, Short.MAX_VALUE))
				.addComponent(scrollPane_news, GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
				.addGroup(Alignment.LEADING, gl_panel_news.createSequentialGroup()
					.addGap(234)
					.addComponent(btnAllesLoeschen)
					.addContainerGap(249, Short.MAX_VALUE))
		);
		gl_panel_news.setVerticalGroup(
			gl_panel_news.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_news.createSequentialGroup()
					.addGap(28)
					.addComponent(lblZurZeitHaben)
					.addGap(11)
					.addComponent(scrollPane_news, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnAllesLoeschen)
					.addContainerGap(13, Short.MAX_VALUE))
		);
		panel_news.setLayout(gl_panel_news);	
		
		/************************************ ACTIONLISTENER ****************************************************/
		
		btnAllesLoeschen.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				partyc.deleteBenachrichtigungen();
				updateNews();
			}
		});
		
	}
	/**
	 * 
	 * @param panel_abos
	 */
	private void aboMenue(JPanel panel_abos)
	{
		JButton btnThemeAnsehen = new JButton("Theme ansehen");
		JButton btnAbonnementKndigen = new JButton("Abonnement k\u00FCndigen");
		JButton btnAlleAbonnementsKndigen = new JButton("Alle Abonnements k\u00FCndigen");
		
		tree_abos = new JTree();
		
		//TODO: Genres, Kategorien und Themes sollen hierarschisch angezeigt werden.
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Meine Abos");
		
//		updateAbos();
		
		for (String abo : mySubscriptions)
		{
			root.add(new DefaultMutableTreeNode( partyc.getNameByID( abo ) ));
		}
		
		tree_abos.setModel(new DefaultTreeModel(root));		
		
		// ORGINAL
//		JTree tree = new JTree();
//		tree.setModel(new DefaultTreeModel(
//			new DefaultMutableTreeNode("Meine Party Themes") {
//				{
//					DefaultMutableTreeNode node_1;
//					node_1 = new DefaultMutableTreeNode("colors");
//						node_1.add(new DefaultMutableTreeNode("blue"));
//						node_1.add(new DefaultMutableTreeNode("violet"));
//						node_1.add(new DefaultMutableTreeNode("red"));
//						node_1.add(new DefaultMutableTreeNode("yellow"));
//					add(node_1);
//					node_1 = new DefaultMutableTreeNode("sports");
//						node_1.add(new DefaultMutableTreeNode("basketball"));
//						node_1.add(new DefaultMutableTreeNode("soccer"));
//						node_1.add(new DefaultMutableTreeNode("football"));
//						node_1.add(new DefaultMutableTreeNode("hockey"));
//					add(node_1);
//					node_1 = new DefaultMutableTreeNode("food");
//						node_1.add(new DefaultMutableTreeNode("hot dogs"));
//						node_1.add(new DefaultMutableTreeNode("pizza"));
//						node_1.add(new DefaultMutableTreeNode("ravioli"));
//						node_1.add(new DefaultMutableTreeNode("bananas"));
//					add(node_1);
//				}
//			}
//		));
		
		GroupLayout gl_panel_abos = new GroupLayout(panel_abos);
		gl_panel_abos.setHorizontalGroup(
			gl_panel_abos.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_abos.createSequentialGroup()
					.addComponent(tree_abos, GroupLayout.PREFERRED_SIZE, 437, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_panel_abos.createParallelGroup(Alignment.LEADING)
						.addComponent(btnThemeAnsehen, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
						.addComponent(btnAbonnementKndigen, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
						.addComponent(btnAlleAbonnementsKndigen, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_abos.setVerticalGroup(
			gl_panel_abos.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_abos.createSequentialGroup()
					.addGap(29)
					.addComponent(btnThemeAnsehen)
					.addPreferredGap(ComponentPlacement.RELATED, 189, Short.MAX_VALUE)
					.addComponent(btnAbonnementKndigen)
					.addGap(18)
					.addComponent(btnAlleAbonnementsKndigen)
					.addGap(23))
				.addComponent(tree_abos, GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
		);
		panel_abos.setLayout(gl_panel_abos);
		
		/************************************ ACTIONLISTENER ****************************************************/
		
		btnThemeAnsehen.addActionListener(new ActionListener()
		{
			@Override
			/**
			 * Holt Alle Themes via Partyclient, wenn diese noch nicht im Auswahlfeld sind
			 */
			public void actionPerformed(ActionEvent e)
			{
				String selection = tree_abos.getSelectionPath().getLastPathComponent().toString();
				if ( tree_abos.isSelectionEmpty() || !( partyc.isTheme( selection ) ))
					InfoPopup.start("Bitte Theme auswählen.");
				else
					ThemeInfo.start( partyc.getIDByName( selection ) );			
			}
		});
		
		btnAbonnementKndigen.addActionListener(new ActionListener()
		{
			@Override
			/**
			 * Holt Abo Liste von Partyclient über IDByName
			 * kündigt Abo über XMPP client
			 * aktualisiert die Liste
			 */
			public void actionPerformed(ActionEvent e)
			{
				if ( tree_abos.isSelectionEmpty() )
					InfoPopup.start( "Bitte ein Element auswählen." );
				else
				{
					String abo = partyc.getIDByName( tree_abos.getSelectionPath().getLastPathComponent().toString() );
					partyc.xc.unsubscribe(abo);
					InfoPopup.start( abo + " wurde erfolgreich gekündigt." );
					updateAbos();
					updateBearbeitbar();
					updatePublish();
				}				
			}
		});
		
		btnAlleAbonnementsKndigen.addActionListener(new ActionListener()
		{	
			/**
			 * Prüft ob Aboliste vorhanden, wenn ja werden alle Abos
			 * via partyc.xc über XMPP-client
			 * AKtualisiert  anschließend die liste
			 */
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if ( mySubscriptions.isEmpty() )
					InfoPopup.start("Sie haben gar keine Abos...");
				else
				{
					partyc.xc.unsubscribeAll();
					InfoPopup.start("Alle Abonnements wurden gekündigt.");
					updateAbos();
					updateBearbeitbar();
					updatePublish();
				}
			}
		});
	}

	private void searchMenue(JPanel panel_search)
	{		
		panel_search.setLayout(new GridLayout(0, 3, 0, 0));
		JButton btnAbonnieren = new JButton("abonnieren");
		JButton btnAnsehen = new JButton("ansehen");
		
		/************************************ GENRES ******************************************/
		
		JPanel panel_genre = new JPanel();
		JLabel lblGenres = new JLabel("Genres");
		
		list_genre = new JList( partyc.getGenresTitles() );

		panel_search.add(panel_genre);
		
		GroupLayout gl_panel_genre = new GroupLayout(panel_genre);
		gl_panel_genre.setHorizontalGroup(
			gl_panel_genre.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_genre.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_genre.createParallelGroup(Alignment.LEADING)
						.addComponent(list_genre, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
						.addComponent(lblGenres))
					.addContainerGap())
		);
		gl_panel_genre.setVerticalGroup(
			gl_panel_genre.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_genre.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblGenres)
					.addGap(5)
					.addComponent(list_genre, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(50, Short.MAX_VALUE))
		);
		panel_genre.setLayout(gl_panel_genre);
		
		
		/************************************ Kategorien ******************************************/

		JPanel panel_kategorie = new JPanel();
		lblKategorien = new JLabel("Kategorien");
		list_kategorien = new JList();
		
		lblKategorien.setVisible(false);
		list_kategorien.setVisible(false);
		
		panel_search.add(panel_kategorie);	
		
		GroupLayout gl_panel_kategorie = new GroupLayout(panel_kategorie);
		gl_panel_kategorie.setHorizontalGroup(
			gl_panel_kategorie.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_kategorie.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_kategorie.createParallelGroup(Alignment.LEADING)
						.addComponent(list_kategorien, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
						.addComponent(lblKategorien))
					.addContainerGap())
		);
		gl_panel_kategorie.setVerticalGroup(
			gl_panel_kategorie.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_kategorie.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblKategorien)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(list_kategorien, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(49, Short.MAX_VALUE))
		);
		panel_kategorie.setLayout(gl_panel_kategorie);
		
		
		/************************************ Themes ******************************************/

		JPanel panel_themes = new JPanel();
		lblThemes = new JLabel("Themes");
		list_themes = new JList();
		scrollPane_theme = new JScrollPane(list_themes);
		
		scrollPane_theme.setVisible(false);
		lblThemes.setVisible(false);
		
		panel_search.add(panel_themes);
		
		GroupLayout gl_panel_themes = new GroupLayout(panel_themes);
		gl_panel_themes.setHorizontalGroup(
			gl_panel_themes.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_themes.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_themes.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_theme, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
						.addComponent(lblThemes)
						.addGroup(Alignment.TRAILING, gl_panel_themes.createSequentialGroup()
							.addComponent(btnAnsehen, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnAbonnieren)))
					.addContainerGap())
		);
		gl_panel_themes.setVerticalGroup(
			gl_panel_themes.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_themes.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblThemes)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_theme, GroupLayout.PREFERRED_SIZE, 247, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_themes.createParallelGroup(Alignment.LEADING)
						.addComponent(btnAnsehen)
						.addComponent(btnAbonnieren))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_themes.setLayout(gl_panel_themes);
		
		/****************************************** ACTIONLISTENER ****************************************/
		
		list_genre.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			/**
			 * SelectionListener wird erzeugt,um bei Auswahl eines Genre,
			 * die Liste zu aktualisieren, damit die Kategorien angezeigt werden
			 * Informationen werden über die Methode getIDByName vom Partyclient geholt
			 */
			public void valueChanged(ListSelectionEvent e)
			{
				 if ( !list_genre.isSelectionEmpty() )
				 {
					 String selection = partyc.getIDByName( list_genre.getSelectedValue().toString() );
					 
					 changeKategorienContent( selection );
					 
					 list_kategorien.setVisible(true);
		        	 lblKategorien.setVisible(true);
		        	 
				 } 
			}
		});
		/**
		 * SelectionListener wird erzeugt,um bei Auswahl einer Kategorie,
		 * die Liste zu aktualisieren, damit die Themes angezeigt werden
		 * Via getIDByName von partyclient
		 */
		list_kategorien.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent arg0)
			{
				if ( !list_kategorien.isSelectionEmpty() )
				{
					String selectionG =  partyc.getIDByName( list_genre.getSelectedValue().toString() );
					String selectionK =  partyc.getIDByName( list_kategorien.getSelectedValue().toString() );

					changeThemesContent( selectionG, selectionK );
					
					scrollPane_theme.setVisible(true);
					lblThemes.setVisible(true);
				}
			}
		});
		/**
		 * falls ein theme ausgewählt wird, wird ThemeInfo.java gestartet
		 */
		btnAnsehen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if ( list_themes.isSelectionEmpty() )
					InfoPopup.start( "Es muss ein Theme ausgewählt sein." );
				else
					ThemeInfo.start( partyc.getIDByName(list_themes.getSelectedValue().toString()) );
			}
		});
		/**
		 * In dieser Methode werden verschiedene Meldungen ausgeben,
		 * wenn bei den Überprüfungen, die verschiedenen Fälle zutreffen.
		 * z.B. wird in der ersten if Bedingung geguckt, ob die Listen von Genres,
		 * Kategorien und Themes leer sind, falls ja kommt ein Pop-up fenster,dass Topic noch
		 * ausgewählt werden muss.
		 *
		 *Anschließend werden die Listen aktualisiert.
		 */
		btnAbonnieren.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{		
				String selection;
				if ( list_genre.isSelectionEmpty() && list_kategorien.isSelectionEmpty() && list_themes.isSelectionEmpty() )
					InfoPopup.start("Es muss ein Topic ausgewählt sein.");
				
				else if ( !list_genre.isSelectionEmpty() && list_kategorien.isSelectionEmpty() && list_themes.isSelectionEmpty() )
				{
					selection = partyc.getIDByName( list_genre.getSelectedValue().toString() );
					if ( !partyc.xc.isSubscribed( selection ) )
					{
						partyc.xc.subscribe( selection );
						InfoPopup.start( "'" + list_genre.getSelectedValue() + "'  wurde erfolgreich abonniert." );
					}
					else
						InfoPopup.start( "Fehler! '" + list_genre.getSelectedValue() + "' wurde bereits abonniert.");
				}
				
				else if ( !list_genre.isSelectionEmpty() && !list_kategorien.isSelectionEmpty() && list_themes.isSelectionEmpty() )
				{
					selection = partyc.getIDByName( list_kategorien.getSelectedValue().toString() );
					if ( !partyc.xc.isSubscribed( selection ) )
					{
						partyc.xc.subscribe( selection );
						InfoPopup.start( selection + " wurde erfolgreich abonniert.");
					}
					else
						InfoPopup.start( "Fehler! " + selection + " wurde bereits abonniert.");
				}	
				
				else if ( !list_genre.isSelectionEmpty() && !list_kategorien.isSelectionEmpty() && !list_themes.isSelectionEmpty() )
				{
					selection = partyc.getIDByName( list_themes.getSelectedValue().toString() );
					if ( !partyc.xc.isSubscribed( selection ) )
					{
						partyc.xc.subscribe( selection );
						InfoPopup.start( selection + " wurde erfolgreich abonniert.");
						
						list_genre.clearSelection();
						list_kategorien.clearSelection();
						list_themes.clearSelection();
					}
					else
						InfoPopup.start( "Fehler! " + selection + " wurde bereits abonniert.");	
				}
				
				updateAbos();
				updateBearbeitbar();
				updatePublish();
			}
		});
	}

	private void themesMenue(JPanel panel_create)
	{
		/***************************************LINKS*************************************************/
		
		comboBox_auswaehlen = new JComboBox<String>();
		updateBearbeitbar();
		
		lblThemeTitel = new JLabel("Theme Titel");
		lblThemeTitel.setVisible(false);
		txtTitelHierEingeben = new JTextField("Titel eingeben");
		txtTitelHierEingeben.setVisible(false);
		
		lblGenre = new JLabel("Genre");
		lblGenre.setVisible(false);
		comboBox_genre = new JComboBox<String>();
		comboBox_genre.setVisible(false);
		comboBox_genre.setModel(new DefaultComboBoxModel<String>( partyc.getGenresTitles() ));
		
		lblKategorie = new JLabel("Kategorie");
		lblKategorie.setVisible(false);
		comboBox_kategorie = new JComboBox<String>();
		comboBox_kategorie.setModel(new DefaultComboBoxModel<String>());
		comboBox_kategorie.setVisible(false);
		
		JTabbedPane tabbedPane_module = new JTabbedPane(JTabbedPane.TOP);
		
		JButton btnClear = new JButton("zurücksetzten");
		JButton btnSpeichern = new JButton("speichern");
		
		/*****************************************RECHTS***********************************************/

		JPanel panel_deko = new JPanel();
		tabbedPane_module.addTab("Dekoration", null, panel_deko, null);
		final JEditorPane editorPane_deko = new JEditorPane();
		editorPane_deko.setContentType("text/plain");
		
		JPanel panel_loca = new JPanel();
		tabbedPane_module.addTab("Location", null, panel_loca, null);
		final JEditorPane editorPane_loca = new JEditorPane();
		editorPane_loca.setContentType("text/plain");
		
		JPanel panel_cate = new JPanel();
		tabbedPane_module.addTab("Catering", null, panel_cate, null);
		final JEditorPane editorPane_cate = new JEditorPane();
		editorPane_cate.setContentType("text/plain");
		
		JPanel panel_music = new JPanel();
		tabbedPane_module.addTab("Music", null, panel_music, null);
		final JEditorPane editorPane_music = new JEditorPane();
		editorPane_music.setContentType("text/plain");
		
		JPanel panel_outfit = new JPanel();
		tabbedPane_module.addTab("Outfits", null, panel_outfit, null);
		final JEditorPane editorPane_outfit = new JEditorPane();
		editorPane_outfit.setContentType("text/plain");
		
		/******************************************LAYOUT**********************************************/

		GroupLayout gl_panel_create = new GroupLayout(panel_create);
		gl_panel_create.setHorizontalGroup(
			gl_panel_create.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_create.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_create.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_create.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel_create.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_create.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel_create.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel_create.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_panel_create.createParallelGroup(Alignment.LEADING)
												.addGroup(Alignment.TRAILING, gl_panel_create.createSequentialGroup()
													.addGroup(gl_panel_create.createParallelGroup(Alignment.TRAILING)
														.addComponent(btnClear, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
														.addComponent(btnSpeichern, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
													.addPreferredGap(ComponentPlacement.RELATED))
												.addGroup(gl_panel_create.createSequentialGroup()
													.addComponent(lblKategorie)
													.addPreferredGap(ComponentPlacement.RELATED)))
											.addGroup(gl_panel_create.createSequentialGroup()
												.addComponent(comboBox_genre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)))
										.addGroup(gl_panel_create.createSequentialGroup()
											.addComponent(lblGenre)
											.addPreferredGap(ComponentPlacement.RELATED)))
									.addGroup(gl_panel_create.createSequentialGroup()
										.addComponent(comboBox_kategorie, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)))
								.addGroup(gl_panel_create.createSequentialGroup()
									.addComponent(txtTitelHierEingeben, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGroup(gl_panel_create.createSequentialGroup()
								.addComponent(lblThemeTitel)
								.addPreferredGap(ComponentPlacement.RELATED)))
						.addGroup(gl_panel_create.createSequentialGroup()
							.addComponent(comboBox_auswaehlen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addComponent(tabbedPane_module, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_create.setVerticalGroup(
			gl_panel_create.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_create.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_create.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_create.createParallelGroup(Alignment.BASELINE)
							.addComponent(tabbedPane_module, GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
							.addComponent(comboBox_auswaehlen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_panel_create.createSequentialGroup()
							.addComponent(lblThemeTitel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtTitelHierEingeben, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblGenre)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBox_genre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblKategorie)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBox_kategorie, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(41)
							.addComponent(btnClear)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSpeichern)))
					.addContainerGap())
		);
				
		GroupLayout gl_panel_deko = new GroupLayout(panel_deko);
		gl_panel_deko.setHorizontalGroup(
			gl_panel_deko.createParallelGroup(Alignment.LEADING)
				.addComponent(editorPane_deko, GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
		);
		gl_panel_deko.setVerticalGroup(
			gl_panel_deko.createParallelGroup(Alignment.LEADING)
				.addComponent(editorPane_deko, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
		);
		panel_deko.setLayout(gl_panel_deko);
			
		GroupLayout gl_panel_loca = new GroupLayout(panel_loca);
		gl_panel_loca.setHorizontalGroup(
			gl_panel_loca.createParallelGroup(Alignment.LEADING)
				.addComponent(editorPane_loca, GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
		);
		gl_panel_loca.setVerticalGroup(
			gl_panel_loca.createParallelGroup(Alignment.LEADING)
				.addComponent(editorPane_loca, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
		);
		panel_loca.setLayout(gl_panel_loca);
		
		GroupLayout gl_panel_cate = new GroupLayout(panel_cate);
		gl_panel_cate.setHorizontalGroup(
			gl_panel_cate.createParallelGroup(Alignment.LEADING)
				.addComponent(editorPane_cate, GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
		);
		gl_panel_cate.setVerticalGroup(
			gl_panel_cate.createParallelGroup(Alignment.LEADING)
				.addComponent(editorPane_cate, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
		);
		panel_cate.setLayout(gl_panel_cate);
		
		GroupLayout gl_panel_music = new GroupLayout(panel_music);
		gl_panel_music.setHorizontalGroup(
			gl_panel_music.createParallelGroup(Alignment.LEADING)
				.addComponent(editorPane_music, GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
		);
		gl_panel_music.setVerticalGroup(
			gl_panel_music.createParallelGroup(Alignment.LEADING)
				.addComponent(editorPane_music, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
		);
		panel_music.setLayout(gl_panel_music);
		
		GroupLayout gl_panel_outfit = new GroupLayout(panel_outfit);
		gl_panel_outfit.setHorizontalGroup(
			gl_panel_outfit.createParallelGroup(Alignment.LEADING)
				.addComponent(editorPane_outfit, GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
		);
		gl_panel_outfit.setVerticalGroup(
			gl_panel_outfit.createParallelGroup(Alignment.LEADING)
				.addComponent(editorPane_outfit, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
		);
		panel_outfit.setLayout(gl_panel_outfit);
		panel_create.setLayout(gl_panel_create);
	
		/****************************************** ACTIONLISTENER *****************************************/
		
		comboBox_auswaehlen.addActionListener( new ActionListener()
		{	
			@Override
			/**
			 * Prüft ob Auswahl = Theme erstellen ist, wenn ja werden entsprechende
			 * Felder sichtbar gemacht.
			 */
			public void actionPerformed(ActionEvent arg0)
			{
				String auswahl = comboBox_auswaehlen.getSelectedItem().toString();
				if ( auswahl.equals("Theme erstellen") )
				{
					lblThemeTitel.setVisible(true);
					txtTitelHierEingeben.setVisible(true);
					lblGenre.setVisible(true);
					comboBox_genre.setVisible(true);
					
					editorPane_deko.setText( "" );
					editorPane_cate.setText( "" );
					editorPane_loca.setText( "" );
					editorPane_music.setText( "" );
					editorPane_outfit.setText( "" );
				}
				else
				{
					auswahl = partyc.getIDByName( comboBox_auswaehlen.getSelectedItem().toString() );
					lblThemeTitel.setVisible(false);
					txtTitelHierEingeben.setVisible(false);
					lblGenre.setVisible(false);
					comboBox_genre.setVisible(false);
					
					// TODO: Inkosistenz - sollte nicht auf rc zugreifen
					editorPane_deko.setText( ThemeInfo.getDeko(partyc.rc.getTheme(auswahl)) );
					editorPane_loca.setText( ThemeInfo.getLoca(partyc.rc.getTheme(auswahl)) );
					editorPane_cate.setText( ThemeInfo.getCatering(partyc.rc.getTheme(auswahl)) );
					editorPane_music.setText( ThemeInfo.getMusic(partyc.rc.getTheme(auswahl)) );
					editorPane_outfit.setText( ThemeInfo.getOutfit(partyc.rc.getTheme(auswahl)) );
				}
			}
		});
		
		comboBox_genre.addActionListener(new ActionListener()
		{
			@Override
			/**
			 * Legt einen Vektor an in dem alle Kategoren gespeichert werden,
			 * die zuvor ausgewählt wurden
			 * Neue Kategorien Combobox wird erstellt und sichtbar gesetzt.
			 */
			public void actionPerformed(ActionEvent arg0)
			{
				String selection = partyc.getIDByName( comboBox_genre.getSelectedItem().toString() );
				Vector<String> newKats = partyc.getKategorienTitles( selection ); 
				comboBox_kategorie.setModel(new DefaultComboBoxModel<String>( newKats ));
				
				lblKategorie.setVisible(true);
				comboBox_kategorie.setVisible(true);
			}
			
		});
		
		btnClear.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				editorPane_deko.setText("");
				editorPane_cate.setText("");
				editorPane_music.setText("");
				editorPane_outfit.setText("");
				editorPane_loca.setText("");
			}
		});
		
		btnSpeichern.addActionListener(new ActionListener()
		{			
			@Override
			/**
			 * Beim Speichern des Themes, wird dem Theme eine neue ID aus, gID und kID
			 * hinzugefügt und über XMPP.client ein neues Topic mit entsprechneder ID
			 * erstellt und gepublished
			 */
			public void actionPerformed(ActionEvent arg0)
			{
				String t_id;
				String deko = editorPane_deko.getText();
				String cate = editorPane_cate.getText();
				String music = editorPane_music.getText();
				String outfit = editorPane_outfit.getText();
				String loca = editorPane_loca.getText();
				
				if ( comboBox_auswaehlen.getSelectedItem().equals( "Theme erstellen" ) )
				{
					if ( comboBox_genre.getSelectedItem() == null || comboBox_kategorie.getSelectedItem() == null)
						InfoPopup.start( "Genre und Kategorie muss ausgewählt sein!" );
					else 
					{
						String g_id = partyc.getIDByName( comboBox_genre.getSelectedItem().toString() );
						String k_id = partyc.getIDByName( comboBox_kategorie.getSelectedItem().toString() );
						String titel = txtTitelHierEingeben.getText();
						t_id = partyc.postTheme( g_id, k_id, titel, deko, cate, music, outfit, loca );
						InfoPopup.start( t_id + " wurde ererstellt.");
					}	
				}
				
				else if ( comboBox_auswaehlen.getSelectedItem() != null )
				{
					t_id = partyc.getIDByName( comboBox_auswaehlen.getSelectedItem().toString() );
					partyc.putTheme( t_id, deko, cate, music, outfit, loca );
					InfoPopup.start( "Änderungen für " + t_id + " wurden übernommen.");
				}
				
				else if ( comboBox_auswaehlen.getSelectedItem() == null )
				{
					InfoPopup.start( "Wählen Sie etwas aus dem Dropdown Menü aus!");
				}
			}
		});
	}

	private void publishMenue(JPanel panel_publish)
	{		
		checkBox_subscriptions = new JComboBox();
		JButton btnPublish = new JButton( "publish" );
		JLabel lblPublishTo = new JLabel( "publish to:" );
		final JEditorPane dtrpnPayload = new JEditorPane();
		dtrpnPayload.setText( "enter data here!" );
		
		updatePublish();
		updateNews();
		
		GroupLayout gl_panel_publish = new GroupLayout(panel_publish);
		gl_panel_publish.setHorizontalGroup(
			gl_panel_publish.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_publish.createSequentialGroup()
					.addGap(27)
					.addGroup(gl_panel_publish.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_publish.createSequentialGroup()
							.addComponent(lblPublishTo)
							.addGap(38)
							.addComponent(checkBox_subscriptions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(dtrpnPayload, GroupLayout.PREFERRED_SIZE, 381, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPublish))
					.addContainerGap(240, Short.MAX_VALUE))
		);
		gl_panel_publish.setVerticalGroup(
			gl_panel_publish.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_publish.createSequentialGroup()
					.addGap(31)
					.addGroup(gl_panel_publish.createParallelGroup(Alignment.BASELINE)
						.addComponent(checkBox_subscriptions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPublishTo))
					.addGap(18)
					.addComponent(dtrpnPayload, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btnPublish)
					.addGap(43))
		);
		panel_publish.setLayout(gl_panel_publish);
		
		/************************* ActionListener ******************************/
		/**
		 * Published ein Item mit ID und Payload über XMPP client
		 */
		btnPublish.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String id = partyc.getIDByName( checkBox_subscriptions.getSelectedItem().toString() );
				String payload = dtrpnPayload.getText();

				if ( partyc.xc.publish( id, payload ) ) 
					InfoPopup.start( "Publishing erfolgreich." );
				else
					InfoPopup.start( "FEHLER! Publishing  nicht erfolgreich!" );
				
				updateNews();
			}
		});
		
	}
	
	/***************************************************************************************** HILFSFUNKTIONEN *********************/
	
	/**
	 * Ändert den Inhalt der Auswahl
	 * @param selection
	 */
	private void changeKategorienContent(String selection)
	{
		list_kategorien.setListData( partyc.getKategorienTitles(selection) );
	}
	
	/**
	 * Ändert den Inhalt der Auswahl
	 * @param selectionG
	 * @param selectionK
	 */
	private void changeThemesContent(String selectionG, String selectionK)
	{
		list_themes.setListData( partyc.getThemesTitles(selectionG, selectionK) );
	}
	/**
	 * Die folgenden update Methoden aktualisieren die Oberfläche
	 * je nach Interaktion.
	 */
	
	/**
	 * Gibt Anzahl der neuen Nachrichten aus, falls welche vorhanden
	 */
	private void updateNews()
	{
		benachrichtigungen_v = partyc.getBenachrichtigungen();
		if (!benachrichtigungen_v.isEmpty())
			nachricht = "Sie haben " + benachrichtigungen_v.size() + " Benachrichtigung(en).";
		else
			nachricht = "Zur Zeit haben Sie keine Benachrichtigungen.";
		lblZurZeitHaben.setText(nachricht);

		list_benachrichtigungen.setListData(benachrichtigungen_v);
	}
	
	/**
	 * Aktualisiert die Abonnements
	 */
	private void updateAbos()
	{
		mySubscriptions = partyc.getMySubscriptions();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Meine Abos");

		for (String abo : mySubscriptions)
		{
			String name = "";
			if ( abo.substring(0,1).equals("g") )
			{
				name = abo + " " + partyc.getGenreTitel(abo);
				root.add(new DefaultMutableTreeNode( name ));
			}
			else if ( abo.substring(0,1).equals("k") )
			{
				name = abo + " " + partyc.getKategorieTitel( abo.substring(3, 5), abo.substring(0,5));
				root.add( new DefaultMutableTreeNode( name ));
			}
			else if ( abo.substring(0,1).equals("t") )
			{
				name = abo + " " + partyc.getThemeTitel(abo);
				root.add( new DefaultMutableTreeNode( name ));
			}
		}
		
		DefaultTreeModel aktuallisiert = new DefaultTreeModel(root);
		tree_abos.setModel(aktuallisiert);
	}
	
	/**
	 * Prüft, ob im Vektor Abonnements abgespeichert sind, wenn nicht wird ein neues
	 * erstellt.
	 */
	private void updatePublish()
	{
		Vector<String> topics = new Vector<String>();
		if ( mySubscriptions.isEmpty() )
		{
			mySubscriptions.add( "Zur Zeit keine Abonnements vorhanden." );
			topics = mySubscriptions;
		}
		else
		{
			for ( String item : mySubscriptions )
				topics.add( partyc.getTitle(item) );
		}
		checkBox_subscriptions.setModel( new DefaultComboBoxModel<String>( topics ) );
	}
	
	/**
	 * 
	 */
	private void updateBearbeitbar()
	{
		Vector<String> themes = new Vector<String>();
		for ( String item : mySubscriptions )
		{
			if ( partyc.isTheme( item ) )
				themes.add( partyc.getTitle(item) );
		}
		themes.add("Theme erstellen");
		comboBox_auswaehlen.setModel( new DefaultComboBoxModel(themes) );
	}

	private void close()
	{
		partyc.logout();
		this.dispose();
	}
}
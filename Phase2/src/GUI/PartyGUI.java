package GUI;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.jivesoftware.smack.XMPPException;

import client.PartyClient;

public class PartyGUI extends JFrame {

	private JFrame frame_login;
	private JPanel contentPane;
	private JPanel main_panel;
	private JTextField txtTitelHierEingeben;
	private Popup hilfePU;
	private Popup ansehenPU;
	private Popup bearbeitenPU;
	private Popup kuendigenPU;
	private Popup aboPU;
	private Popup speichernPU;
	private Popup fehlerPU;
	
	private Border b = new LineBorder(Color.black);
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
	Vector<String> subscriptions;
	
	Vector<String> genres_liste;
	JList list_genre;
	
	Vector<String> kategorien_list;
	JList list_kategorien;
	
	Vector<String> theme_topics;
	JList list_themes;
	JScrollPane scrollPane_theme;
	
	Vector<String> benachrichtigungen_v;
	String nachricht;
	JLabel lblZurZeitHaben;
	JScrollPane scrollPane_news;
	
	JTree tree_abos;
 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PartyGUI frame = new PartyGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws XMPPException 
	 */
	public PartyGUI() throws XMPPException {
		partyc = new PartyClient();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 679, 460);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		main_panel = new JPanel();
		contentPane.add(main_panel);
		main_panel.setLayout(null);
		
		fixedMenue();
		mainTabbedMenue();
		login();		
	}
	
	private void fixedMenue()
	{
		// Content
		btnAusloggen = new JButton("Logout");
		btnAusloggen.setBounds(554, 376, 89, 23);
		main_panel.add(btnAusloggen);
		
		btnHilfe = new JButton("Hilfe");
		btnHilfe.setBounds(455, 376, 89, 23);
		main_panel.add(btnHilfe);
		
		/*****************************************************/
		/**************** ACTIONLISTENER *********************/
		/*****************************************************/
		
		btnAusloggen.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				partyc.logout();
				close();
			}
		});
		
		btnHilfe.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				hilfePopup();
			}
		});
	}

	private void mainTabbedMenue()
	{
		mainTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		mainTabbedPane.setBounds(0, 0, 653, 356);
		main_panel.add(mainTabbedPane);
		
		panel_news = new JPanel();
		panel_news.setToolTipText("Benachrichtigungen");
		mainTabbedPane.addTab("Benachrichtigungen", null, panel_news, null);
		newsMenue(panel_news);
		
		panel_abos = new JPanel();
		panel_abos.setToolTipText("Abonnemnets");
		mainTabbedPane.addTab("Meine Abonnements", null, panel_abos, null);
		aboMenue(panel_abos);
		
		panel_search = new JPanel();
		panel_search.setToolTipText("Stöbern");
		mainTabbedPane.addTab("Stöbern", null, panel_search, null);
		searchMenue(panel_search);
		
		panel_create = new JPanel();
		panel_create.setToolTipText("Theme erstellen");
		mainTabbedPane.addTab("Theme erstellen", null, panel_create, null);
		createMenue(panel_create);
		
		JPanel panel_publish = new JPanel();
		panel_publish.setToolTipText("publish something");
		mainTabbedPane.addTab("Publishing", null, panel_publish, null);
		publishMenue(panel_publish);
	}
	
	private void newsMenue(JPanel panel_news)
	{
		// TODO: Feld soll sich immer aktuallisieren, sobald es Neuigkeiten gibt, nicht nur, wenn der Nutzer sich das erste mal einloggt.
		
		/**************************** BenachrichtigungenFeld **********************************/
		
		nachricht = "Zur Zeit haben Sie keine Benachrichtigungen.";
		benachrichtigungen_v = partyc.getBenachrichtigungen();
		
		if (!benachrichtigungen_v.isEmpty())
			nachricht = "Sie haben " + benachrichtigungen_v.size() + " Benachrichtigung(en).";
		
		lblZurZeitHaben = new JLabel(nachricht);;

		list_benachrichtigungen = new JList( benachrichtigungen_v );
		scrollPane_news = new JScrollPane( list_benachrichtigungen );
		
		
		/**************************** Löschen ************************************************/
		
		JButton btnAllesLoeschen = new JButton("Benachrichtigungen löschen");
		
		// LAYOUT
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

	private void aboMenue(JPanel panel_abos)
	{
		JButton btnThemeAnsehen = new JButton("Theme ansehen");
		JButton btnThemeBearbeiten = new JButton("Theme bearbeiten");
		JButton btnAbonnementKndigen = new JButton("Abonnement k\u00FCndigen");
		JButton btnAlleAbonnementsKndigen = new JButton("Alle Abonnements k\u00FCndigen");
		
		//TODO: Genres, Kategorien und Themes sollen hierarschisch angezeigt werden.
		//TODO: Liste soll sich aktuallisieren, sobald etwas neues abonniert wurde.
		
		subscriptions = partyc.getMySubscriptions();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Meine Party-Themes");
		
		for (String abo : subscriptions)
		{
			root.add(new DefaultMutableTreeNode(abo));
		}
		
		tree_abos = new JTree();
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
						.addComponent(btnThemeBearbeiten, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
						.addComponent(btnAbonnementKndigen, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
						.addComponent(btnAlleAbonnementsKndigen, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_abos.setVerticalGroup(
			gl_panel_abos.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_abos.createSequentialGroup()
					.addGap(29)
					.addComponent(btnThemeAnsehen)
					.addGap(18)
					.addComponent(btnThemeBearbeiten)
					.addPreferredGap(ComponentPlacement.RELATED, 148, Short.MAX_VALUE)
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
			public void actionPerformed(ActionEvent e)
			{
				if (tree_abos.isSelectionEmpty() || !(tree_abos.getSelectionPath().getLastPathComponent().toString().substring(0,1).equals("t")))
					fehlerPopup("Bitte Theme auswählen.");
				else
					themeAnsehenPopup(tree_abos.getSelectionPath().getLastPathComponent().toString());			
			}
		});
		
		btnThemeBearbeiten.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (tree_abos.isSelectionEmpty() || !(tree_abos.getSelectionPath().getLastPathComponent().toString().substring(0,1).equals("t")))
					fehlerPopup("Bitte Theme auswählen.");
				else
					themeBearbeitenPopup(tree_abos.getSelectionPath().getLastPathComponent().toString());
			}
		});
		
		btnAbonnementKndigen.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if ( tree_abos.isSelectionEmpty() )
					fehlerPopup("Bitte ein Element auswählen.");
				else
				{
					String abo = tree_abos.getSelectionPath().getLastPathComponent().toString();
					partyc.unsubscribe(abo);
					aboKuendigenPopup(abo);
				}				
			}
		});
		
		btnAlleAbonnementsKndigen.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				partyc.unsubscribeAll();
				aboKuendigenPopup("alle");
			}
		});
	}

	private void searchMenue(JPanel panel_search)
	{
		// TODO: Selection-Exceptions behandeln  bei Umwahl!
		
		panel_search.setLayout(new GridLayout(0, 3, 0, 0));
		JButton btnAbonnieren = new JButton("abonnieren");
		JButton btnAnsehen = new JButton("ansehen");
		
		/************************************ GENRES ******************************************/
		
		JPanel panel_genre = new JPanel();
		panel_search.add(panel_genre);
		JLabel lblGenres = new JLabel("Genres");
		
		genres_liste = partyc.getGenres();
		list_genre = new JList(genres_liste);
		list_genre.setBorder(b);

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
		panel_search.add(panel_kategorie);	
		final JLabel lblKategorien = new JLabel("Kategorien");
		
		list_kategorien = new JList();
		list_kategorien.setBorder(b);
		
		lblKategorien.setVisible(false);
		list_kategorien.setVisible(false);		
		
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
		panel_search.add(panel_themes);
		final JLabel lblThemes = new JLabel("Themes");
		
		list_themes = new JList();
		
		scrollPane_theme = new JScrollPane(list_themes);
		scrollPane_theme.setBorder(b);
		
		scrollPane_theme.setVisible(false);
		lblThemes.setVisible(false);
		
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
			public void valueChanged(ListSelectionEvent e)
			{
				 if ( !list_genre.isSelectionEmpty() )
				 {
					 String selection = list_genre.getSelectedValue().toString();
					 changeKategorienContent(selection);
					 
					 list_kategorien.setVisible(true);
		        	 lblKategorien.setVisible(true);
				 } 
			}
		});
		
		list_kategorien.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent arg0)
			{
				if ( !list_kategorien.isSelectionEmpty() )
				{
					String selectionG =  list_genre.getSelectedValue().toString();
					String selectionK =  list_kategorien.getSelectedValue().toString().substring(0, 2);
	
					changeThemesContent(selectionG, selectionK);
					
					scrollPane_theme.setVisible(true);
					lblThemes.setVisible(true);
				}
			}
		});
		
		btnAnsehen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if ( list_themes.isSelectionEmpty() )
					fehlerPopup("Es muss ein Theme ausgewählt sein.");
				else
					themeAnsehenPopup(list_themes.getSelectedValue().toString());
			}
		});
		
		btnAbonnieren.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if ( list_genre.isSelectionEmpty() && list_kategorien.isSelectionEmpty() && list_themes.isSelectionEmpty())
					fehlerPopup("Es muss ein Topic ausgewählt sein.");
				
				else if ( !list_genre.isSelectionEmpty() && list_kategorien.isSelectionEmpty() && list_themes.isSelectionEmpty() )
				{
					partyc.subscribe(list_genre.getSelectedValue().toString());
					aboPopup(list_genre.getSelectedValue().toString());
				}
				
				else if ( !list_genre.isSelectionEmpty() && !list_kategorien.isSelectionEmpty() && list_themes.isSelectionEmpty() )
				{
					partyc.subscribe(list_kategorien.getSelectedValue().toString());
					aboPopup(list_kategorien.getSelectedValue().toString());
				}	
				
				else if ( !list_genre.isSelectionEmpty() && !list_kategorien.isSelectionEmpty() && !list_themes.isSelectionEmpty() )
				{
					partyc.subscribe(list_themes.getSelectedValue().toString());
					aboPopup(list_themes.getSelectedValue().toString());
					
					list_genre.clearSelection();
					list_kategorien.clearSelection();
					list_themes.clearSelection();
				}
			}
		});
	}

	private void createMenue(JPanel panel_create)
	{
		/***************************************LINKS*************************************************/
		
		JLabel lblThemeTitel = new JLabel("Theme Titel");
		txtTitelHierEingeben = new JTextField();
		
		txtTitelHierEingeben.setText("Titel eingeben");
		txtTitelHierEingeben.setColumns(10);
		
		JLabel lblGenre = new JLabel("Genre");
		final JComboBox<String> comboBox_genre = new JComboBox<String>();
		comboBox_genre.setModel(new DefaultComboBoxModel<String>(genres_liste));
		
		final JLabel lblKategorie = new JLabel("Kategorie");
		final JComboBox<String> comboBox_kategorie = new JComboBox<String>();
		comboBox_kategorie.setModel(new DefaultComboBoxModel<String>());
		
		lblKategorie.setVisible(false);
		comboBox_kategorie.setVisible(false);
		
		JTabbedPane tabbedPane_module = new JTabbedPane(JTabbedPane.TOP);
		
		JButton btnClear = new JButton("zurücksetzten");
		JButton btnSpeichern = new JButton("speichern");
		
		/******************************************LAYOUT**********************************************/

		GroupLayout gl_panel_create = new GroupLayout(panel_create);
		gl_panel_create.setHorizontalGroup(
			gl_panel_create.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_create.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_create.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_create.createSequentialGroup()
							.addGroup(gl_panel_create.createParallelGroup(Alignment.LEADING)
								.addComponent(lblThemeTitel)
								.addComponent(txtTitelHierEingeben, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblGenre)
								.addComponent(comboBox_genre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblKategorie)
								.addComponent(comboBox_kategorie, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(78))
						.addGroup(gl_panel_create.createSequentialGroup()
							.addComponent(btnSpeichern, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_panel_create.createSequentialGroup()
							.addComponent(btnClear, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addComponent(tabbedPane_module, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_create.setVerticalGroup(
			gl_panel_create.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_create.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_create.createParallelGroup(Alignment.LEADING)
						.addComponent(tabbedPane_module, GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
						.addGroup(gl_panel_create.createSequentialGroup()
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
							.addPreferredGap(ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
							.addComponent(btnClear)
							.addGap(18)
							.addComponent(btnSpeichern)))
					.addContainerGap())
		);
		
		/*****************************************RECHTS***********************************************/

		JPanel panel = new JPanel();
		tabbedPane_module.addTab("Dekoration", null, panel, null);
		
		final JEditorPane editorPane = new JEditorPane();
		editorPane.setContentType("application/xml");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(editorPane, GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(editorPane, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
		);
		panel.setLayout(gl_panel);
		
		JPanel panel_1 = new JPanel();
		tabbedPane_module.addTab("Location", null, panel_1, null);
		
		JEditorPane editorPane_1 = new JEditorPane();
		editorPane_1.setContentType("application/xml");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addComponent(editorPane_1, GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addComponent(editorPane_1, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
		);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_2 = new JPanel();
		tabbedPane_module.addTab("Catering", null, panel_2, null);
		
		final JEditorPane editorPane_2 = new JEditorPane();
		editorPane_2.setContentType("application/xml");
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addComponent(editorPane_2, GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addComponent(editorPane_2, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
		);
		panel_2.setLayout(gl_panel_2);
		
		JPanel panel_3 = new JPanel();
		tabbedPane_module.addTab("Music", null, panel_3, null);
		
		final JEditorPane editorPane_3 = new JEditorPane();
		editorPane_3.setContentType("application/xml");
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addComponent(editorPane_3, GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addComponent(editorPane_3, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
		);
		panel_3.setLayout(gl_panel_3);
		
		JPanel panel_4 = new JPanel();
		tabbedPane_module.addTab("Outfits", null, panel_4, null);
		
		final JEditorPane editorPane_4 = new JEditorPane();
		editorPane_4.setContentType("application/XML");
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addComponent(editorPane_4, GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addComponent(editorPane_4, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
		);
		panel_4.setLayout(gl_panel_4);
		panel_create.setLayout(gl_panel_create);
	
		/****************************************** ACTIONLISTENER *****************************************/
		
		comboBox_genre.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				String selection = comboBox_genre.getSelectedItem().toString();
				Vector<String> newKats = partyc.getKategorien(selection); 
				comboBox_kategorie.setModel(new DefaultComboBoxModel<String>(newKats));
				
				lblKategorie.setVisible(true);
				comboBox_kategorie.setVisible(true);
			}
			
		});
		
		btnClear.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				editorPane.setText("");
				editorPane_2.setText("");
				editorPane_3.setText("");
				editorPane_4.setText("");
			}
		});
		
		btnSpeichern.addActionListener(new ActionListener()
		{
			// TODO: Theme-ID holen + für das PopUp übergeben
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				String t_id = "t" + String.valueOf( partyc.anz_t );
				String g_id = comboBox_genre.getItemAt( comboBox_genre.getSelectedIndex() );
				String k_id = comboBox_kategorie.getItemAt( comboBox_kategorie.getSelectedIndex() );
				String id = t_id + "_" + k_id;
								
				partyc.createTopic( partyc.createTID(g_id, k_id) );
				partyc.publish( id, "new Theme '" + id + "' created" );
				
				speichernPopup( id );
			}
		});
	}

	private void publishMenue(JPanel panel_publish)
	{
		final JComboBox checkBox_subscriptions = new JComboBox();
		Vector<String> subscriptions = partyc.getMySubscriptions();
		checkBox_subscriptions.setModel(new DefaultComboBoxModel<String>(subscriptions));
		
		final JEditorPane dtrpnPayload = new JEditorPane();
		dtrpnPayload.setText("payload");
		
		JButton btnPublish = new JButton("publish");
		
		JLabel lblPublishTo = new JLabel("publish to:");
		
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
		
		btnPublish.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String t_id = checkBox_subscriptions.getSelectedItem().toString();
				String payload = dtrpnPayload.getText();
				partyc.publish(t_id, payload);
				
				updateNews();
			}
		});
		
	}
	
	/*****************************************************/
	/*************** Pop-Ups *****************************/
	/*****************************************************/
	
	private void hilfePopup()
	{	
		JPanel panel_hilfe = new JPanel();
		
		JLabel hilfe_text = new JLabel("In WBA2 verdienen wir eine 1,0.");
		panel_hilfe.add(hilfe_text);
		
		JButton btnOk = new JButton("Auf jeden!");
		panel_hilfe.add(btnOk);
		
		panel_hilfe.setBorder(b);
				
		PopupFactory factory = PopupFactory.getSharedInstance();
		hilfePU = factory.getPopup(this, panel_hilfe, 400, 300);
		hilfePU.show();
		
		/*****************************************************/
		/**************** ACTIONLISTENER *********************/
		/*****************************************************/
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hilfePU.hide();
			}
		});
	}

	private void themeAnsehenPopup(String themeName)
	{
		// TODO: richtiger Theme Inhalt per Webservices!
		
		JPanel panel_ansehen = new JPanel();
		JLabel ansehen_text = new JLabel("Theme '"+ themeName +"': ");
		JButton btnOk = new JButton("Schliessen");
		JEditorPane content = new JEditorPane();
		
		content.setEditable(false);
		content.setText("Theme Inhalt");
		
		panel_ansehen.add(ansehen_text);
		panel_ansehen.add(content);
		panel_ansehen.add(btnOk);
		panel_ansehen.setBorder(b);
				
		PopupFactory factory = PopupFactory.getSharedInstance();
		ansehenPU = factory.getPopup(this, panel_ansehen, 400, 300);
		ansehenPU.show();
		
		/************************************* ACTIONLISTENER **************************************/
		
		btnOk.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ansehenPU.hide();
			}
		});
	}
	
	private void themeBearbeitenPopup(String theme)
	{
		// TODO: richtigen Theme Inhalt anzeigen
		// TODO: Oberfläche angleichen an "Theme erstellen"
		
		JPanel panel_bearbeiten = new JPanel();
		JLabel bearbeiten_text = new JLabel("Theme '"+ theme +"': ");
		JButton btnOk = new JButton("Speichern");
		JButton btnNotOk = new JButton("Abbrechen");
		JEditorPane content = new JEditorPane();
		
		content.setText("Theme Inhalt");
		
		panel_bearbeiten.add(btnOk);
		panel_bearbeiten.add(btnNotOk);
		panel_bearbeiten.add(bearbeiten_text);
		panel_bearbeiten.add(content);
		panel_bearbeiten.setBorder(b);
				
		PopupFactory factory = PopupFactory.getSharedInstance();
		bearbeitenPU = factory.getPopup(this, panel_bearbeiten, 400, 300);
		bearbeitenPU.show();
		
		/******************************************** ACTIONLISTENER ****************************************/
		
		btnOk.addActionListener(new ActionListener()
		{
			// TODO: Speichern und Publishen!
			public void actionPerformed(ActionEvent e)
			{
//				partyc.
				bearbeitenPU.hide();
			}
		});
		
		btnNotOk.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				bearbeitenPU.hide();
			}
		});
	}
	
	private void aboKuendigenPopup(String unsubbed)
	{
		String abo = unsubbed;
		JPanel panel_kuendigen = new JPanel();
		JLabel text_kuendigen = new JLabel("Ihr Abonemment für " + abo + " wurde gekündigt.");
		panel_kuendigen.add(text_kuendigen);
		
		JButton btnOk = new JButton("Gut");
		panel_kuendigen.add(btnOk);
		
		panel_kuendigen.setBorder(b);
				
		PopupFactory factory = PopupFactory.getSharedInstance();
		kuendigenPU = factory.getPopup(this, panel_kuendigen, 400, 300);
		kuendigenPU.show();
		
		/********************************** ACTIONLISTENER *******************************************/
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kuendigenPU.hide();
			}
		});
	}
	
	private void aboPopup(String abo_gewählt)
	{
		String abo = abo_gewählt;
		JPanel panel_abo = new JPanel();
		JLabel text_abo = new JLabel(abo + " wurde abonniert.");
		panel_abo.add(text_abo);
		
		JButton btnOk = new JButton("Gut");
		panel_abo.add(btnOk);
				
		panel_abo.setBorder(b);
		
		PopupFactory factory = PopupFactory.getSharedInstance();
		aboPU = factory.getPopup(this, panel_abo, 400, 300);
		aboPU.show();
		
		/*****************************************************/
		/**************** ACTIONLISTENER *********************/
		/*****************************************************/
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aboPU.hide();
			}
		});
	}
	
	private void speichernPopup(String theme)
	{
		JPanel panel_speichern = new JPanel();
		JLabel text = new JLabel(theme + " wurde gespeichert und veröffentlicht.");
		JButton btnOk = new JButton("Gut");
		
		panel_speichern.add(text);
		panel_speichern.add(btnOk);
		panel_speichern.setBorder(b);
				
		PopupFactory factory = PopupFactory.getSharedInstance();
		speichernPU = factory.getPopup(this, panel_speichern, 400, 300);
		speichernPU.show();
		
		/**************** ACTIONLISTENER *********************/
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				speichernPU.hide();
			}
		});
	}
	
	private void login()
	{
		//TODO: Werte eingeben und an client übergeben
		
//		JPanel panel_login = new JPanel(new GridBagLayout());
//        GridBagConstraints cs = new GridBagConstraints();
// 
//        cs.fill = GridBagConstraints.HORIZONTAL;
// 
//        JLabel lbUsername = new JLabel("Username: ");
//        cs.gridx = 0;
//        cs.gridy = 0;
//        cs.gridwidth = 1;
//        panel_login.add(lbUsername, cs);
// 
//        JTextField tfUsername = new JTextField(20);
//        cs.gridx = 1;
//        cs.gridy = 0;
//        cs.gridwidth = 2;
//        panel_login.add(tfUsername, cs);
// 
//        JLabel lbPassword = new JLabel("Password: ");
//        cs.gridx = 0;
//        cs.gridy = 1;
//        cs.gridwidth = 1;
//        panel_login.add(lbPassword, cs);
// 
//        JPasswordField pfPassword = new JPasswordField(20);
//        cs.gridx = 1;
//        cs.gridy = 1;
//        cs.gridwidth = 2;
//        panel_login.add(pfPassword, cs);
//        panel_login.setBorder(new LineBorder(Color.GRAY));
//        
//        frame_login.add(panel_login);
	}
	
	private void fehlerPopup(String meldung)
	{
		String theme =":)";
		JPanel panel_fehler = new JPanel();
		JLabel text = new JLabel(meldung);
		panel_fehler.add(text);
		
		JButton btnOk = new JButton("Nagut");
		panel_fehler.add(btnOk);
		
		panel_fehler.setBorder(b);
				
		PopupFactory factory = PopupFactory.getSharedInstance();
		fehlerPU = factory.getPopup(this, panel_fehler, 400, 300);
		fehlerPU.show();
		
		/**************** ACTIONLISTENER *********************/
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fehlerPU.hide();
			}
		});
	}
	
	/*****************************************************/
	/*************** HILFSFUNKTIONEN *********************/
	/*****************************************************/
	
	private void changeVis(Component c)
	{
		if (c.isVisible())
			c.setVisible(false);
		else
			c.setVisible(true);
	}
	
	private void changeKategorienContent(String selection)
	{
		Vector<String> neueKategorien = partyc.getKategorien(selection);
		list_kategorien.setListData(neueKategorien);
	}
	
	private void changeThemesContent(String selectionG, String selectionK)
	{
		Vector<String> neueThemes = partyc.getThemes(selectionG, selectionK);
		if ( neueThemes.isEmpty() )
			neueThemes.add("Keine Themes vorhanden");
		list_themes.setListData(neueThemes);
	}
	
	private void updateNews()
	{
//		nachricht = "Zur Zeit haben Sie keine Benachrichtigungen.";
		benachrichtigungen_v = partyc.getBenachrichtigungen();
//		System.out.println(benachrichtigungen_v.get(0));
		if (!benachrichtigungen_v.isEmpty())
			nachricht = "Sie haben " + benachrichtigungen_v.size() + " Benachrichtigung(en).";
		
		lblZurZeitHaben = new JLabel(nachricht);

		list_benachrichtigungen.setListData(benachrichtigungen_v);
	}
	
	private void updateAbos()
	{
		subscriptions = partyc.getMySubscriptions();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Meine Party-Themes");
		
		for (String abo : subscriptions)
		{
			root.add(new DefaultMutableTreeNode(abo));
		}
		
		DefaultTreeModel aktuallisiert = new DefaultTreeModel(root);
		
		tree_abos.setModel(aktuallisiert);
	}
	
	private void close()
	{
		this.dispose();
	}
}

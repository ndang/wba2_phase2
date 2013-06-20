package GUI;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.tools.JavaFileManager.Location;

import app.Beschreibung;
import app.Theme;
import app.Theme.Module.Musik.Song;
import client.PartyClient;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ThemeInfo extends JFrame {

	private JPanel contentPane;
	private static String theme_id;
	private Theme t;
	
	public static void start(String t_id)
	{
		theme_id = t_id;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ThemeInfo frame = new ThemeInfo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ThemeInfo()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblDekoration = new JLabel("Dekoration");
		JLabel lblLocation = new JLabel("Location");
		JLabel lblCatering = new JLabel("Catering");
		JLabel lblMusik = new JLabel("Musik");
		JLabel lblOutfits = new JLabel("Outfits");
		JLabel lblGenre = new JLabel("Genre");
		JLabel lblKategorie = new JLabel("Kategorie");
		
		// TODO: richtigen Daten
		
		t = PartyClient.rc.getTheme(theme_id);
		
		JLabel lblThemeTitel = new JLabel( t.getAllgemeines().getThemeTitel().toString() );
		JLabel lblBewertung = new JLabel("Bewertung");
		contentPane.add(lblThemeTitel, "2, 2");
		contentPane.add(lblBewertung, "2, 4");
		JLabel lblBewertungangabe = new JLabel ( String.valueOf(t.getAllgemeines().getBewertung()) ) ;	
		contentPane.add(lblBewertungangabe, "6, 4");
		contentPane.add(lblDekoration, "2, 8");
		JLabel lblKeineAngaben_deko = new JLabel( getDeko(t) );
		contentPane.add(lblKeineAngaben_deko, "6, 8");
		contentPane.add(lblCatering, "2, 10");
		JLabel lblKeineAngaben_cate = new JLabel( getCatering(t) );
		contentPane.add(lblKeineAngaben_cate, "6, 10");
		contentPane.add(lblMusik, "2, 12");	
		JLabel lblKeineAngaben_music = new JLabel( getMusic(t) );
		contentPane.add(lblKeineAngaben_music, "6, 12");
		contentPane.add(lblLocation, "2, 14");
		JLabel lblKeineAngaben_loca = new JLabel( getLoca(t) );
		contentPane.add(lblKeineAngaben_loca, "6, 14");
		contentPane.add(lblOutfits, "2, 16");	
		JLabel lblKeineAngaben_outfit = new JLabel( getOutfit(t) );
		contentPane.add(lblKeineAngaben_outfit, "6, 16");	
		contentPane.add(lblGenre, "2, 20");	
		JLabel lblGenrename = new JLabel( t.getAllgemeines().getGenres().getGenre().get(0).getValue() );
		contentPane.add(lblGenrename, "6, 20");
		contentPane.add(lblKategorie, "2, 22");
		JLabel lblKategorienamen = new JLabel( t.getAllgemeines().getKategorien().getKategorie().get(0).getValue());
		contentPane.add(lblKategorienamen, "6, 22");
		JButton btnOk = new JButton("OK");
		contentPane.add(btnOk, "6, 26");
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				close();
			}
		});
	}
	
	static protected String getCatering( Theme t )
	{
		String catering = "";
		for (app.Rezept item : t.getModule().getCatering().getGericht())
			catering += item.getRezeptname() + ", " + item.getRezeptLink() + "/ ";
		
		for (app.Rezept item : t.getModule().getCatering().getGetraenk())
			catering += item.getRezeptname() + ", " + item.getRezeptLink() + "/ ";
		return catering;
	}
	
	static protected String getMusic( Theme t )
	{
		String music = "";
		for (Song item : t.getModule().getMusik().getSong())
			music += item.getSongInterpret() + " - " + item.getSongTitel() + ": " + item.getSongTitel() + "   ";
		return music;
	}
	
	static protected String getLoca( Theme t )
	{
		String location = "";
		for (Beschreibung item : t.getModule().getLocations().getLocation())
			location += item.getTitel() + ", " + item.getBild() + ", " + item.getText() + "   ";
		return location;
	}
	
	static protected String getOutfit( Theme t )
	{
		String outfit = "";
		for (Beschreibung item : t.getModule().getOutfits().getOutfit())
			outfit += item.getTitel() + ", " + item.getBild() + ", " + item.getText() + "   ";
		return outfit;
	}
	
	static protected String getDeko( Theme t )
	{
		String deko = "";
		for(Beschreibung item : t.getModule().getDekoration().getDekorationElement())
			deko += item.getTitel() + ", " + item.getBild() + ", " + item.getText() + "   ";
		return deko;
	}
	
	private void close ()
	{
		this.dispose();
	}
}

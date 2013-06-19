package GUI;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import app.Theme;
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
		setBounds(100, 100, 450, 300);
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
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblBewertung = new JLabel("Bewertung");
		JLabel lblDekoration = new JLabel("Dekoration");
		JLabel lblLocation = new JLabel("Location");
		JLabel lblCatering = new JLabel("Catering");
		JLabel lblMusik = new JLabel("Musik");
		JLabel lblOutfits = new JLabel("Outfits");
		JLabel lblGenre = new JLabel("Genre");
		JLabel lblKategorie = new JLabel("Kategorie");
		JButton btnOk = new JButton("OK");
		
		// TODO: richtigen Daten
		
		t = PartyClient.rc.getTheme(theme_id);
		
		JLabel lblThemeTitel = new JLabel( t.getAllgemeines().getThemeTitel().toString() );
		JLabel lblBewertungangabe = new JLabel ( String.valueOf(t.getAllgemeines().getBewertung()) ) ;	
		JLabel lblKeineAngaben_cate = new JLabel( t.getModule().getCatering().toString() );
		JLabel lblKeineAngaben_music = new JLabel( t.getModule().getMusik().toString() );
		JLabel lblKeineAngaben_loca = new JLabel( t.getModule().getLocations().toString() );
		JLabel lblKeineAngaben_outfit = new JLabel( t.getModule().getOutfits().toString() );
		JLabel lblKeineAngaben_deko = new JLabel( t.getModule().getDekoration().getDekorationElement().get(0).getTitel().toString() );
		JLabel lblGenrename = new JLabel( t.getAllgemeines().getGenres().getGenre().get(0).getValue() );
		JLabel lblKategorienamen = new JLabel( t.getAllgemeines().getKategorien().getKategorie().get(0).getValue());
		
		lblBewertung.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(lblThemeTitel, "2, 2");
		
		
		contentPane.add(lblBewertung, "10, 2");
		contentPane.add(lblBewertungangabe, "12, 2");
		contentPane.add(lblDekoration, "2, 6");
		contentPane.add(lblKeineAngaben_deko, "6, 6");
		contentPane.add(lblCatering, "2, 8");
		contentPane.add(lblKeineAngaben_cate, "6, 8");
		contentPane.add(lblMusik, "2, 10");	
		contentPane.add(lblKeineAngaben_music, "6, 10");
		contentPane.add(lblLocation, "2, 12");
		contentPane.add(lblKeineAngaben_loca, "6, 12");
		contentPane.add(lblOutfits, "2, 14");	
		contentPane.add(lblKeineAngaben_outfit, "6, 14");	
		contentPane.add(lblGenre, "2, 18");	
		contentPane.add(lblGenrename, "6, 18");
		contentPane.add(lblKategorie, "2, 20");
		contentPane.add(lblKategorienamen, "6, 20");
		contentPane.add(btnOk, "12, 22");
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				close();
			}
		});
	}
	
	private void close ()
	{
		this.dispose();
	}
}

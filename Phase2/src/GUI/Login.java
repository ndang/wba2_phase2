package GUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

@SuppressWarnings("serial")
public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField_user;
	private JPasswordField textField_passw;
	private static String user;
	private static String passw;

	public static void start ()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login()
	{
		/************************************************************************ LAYOUT ************/
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
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
				FormFactory.DEFAULT_ROWSPEC,}));
		
		
		/************************************************************************ CONTENT ************/
		
		JLabel lblBitteLoggenSie = new JLabel("Bitte loggen Sie sich ein.");
		JLabel lblUser = new JLabel("Username:");
		JLabel lblPassw = new JLabel("Passwort:");
		textField_user = new JTextField();
		textField_user.setText("user1");
		textField_passw = new JPasswordField();
		textField_passw.setText("user1user1");
		JButton btnLogin = new JButton("Login");
		JButton btnClose = new JButton("close");
		
		contentPane.add(lblBitteLoggenSie, "6, 2");
		contentPane.add(lblUser, "4, 6, right, default");
		contentPane.add(textField_user, "6, 6, 3, 1, fill, default");
		contentPane.add(lblPassw, "4, 8, right, default");
		contentPane.add(textField_passw, "6, 8, 3, 1, fill, default");
		contentPane.add(btnLogin, "6, 14");
		contentPane.add(btnClose, "8, 14");
		
		/************************************************************************ ACTIONS ************/
		/**
		 * Actionlistener wird dem Button hinzugefügt
		 */
		btnClose.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0)
			{
				close();
			}
		});
		
		btnLogin.addActionListener(new ActionListener()
		{
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e)
			{
				user = textField_user.getText();
				passw = textField_passw.getText();
				
				PartyGUI.start();
				close();
			}
		});
	}	
	
	public static String getUser()
	{
		return user;
	}

	public static String getPassw()
	{
		return passw;
	}

	private void close()
	{
		this.dispose();
	}

}

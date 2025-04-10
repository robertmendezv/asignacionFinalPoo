package visual;


import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import logico.User;
import logico.SerieNacionaldeBasket;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;



public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField passwordFieldContrasena;
	private JPanel panel;



	//Create the frame
	 
	public Login() {
	    SerieNacionaldeBasket.getInstance().cargarDatos();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				FileOutputStream empresa2;
				ObjectOutputStream empresaWrite;
				try {
					empresa2 = new  FileOutputStream("empresa.dat");
					empresaWrite = new ObjectOutputStream(empresa2);
					empresaWrite.writeObject(SerieNacionaldeBasket.getInstance());
				} catch (FileNotFoundException e1) {		
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		try {
	        String projectPath = System.getProperty("user.dir");
	        String imagePath = projectPath + File.separator + "Imagenes" + File.separator + "login.png";
	        File imageFile = new File(imagePath);

	        if (!imageFile.exists()) {
	            JOptionPane.showMessageDialog(this, 
	                "No se encontr� la imagen en:\n" + imagePath, 
	                "Error", JOptionPane.ERROR_MESSAGE);
	        } else {
	            Image img = ImageIO.read(imageFile);
	            ImageIcon icon = new ImageIcon(img.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
	            JLabel lblImagen = new JLabel(icon);
	            lblImagen.setBounds(450, 20, 100, 100);
	            panel.add(lblImagen);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, 
	            "Error al cargar la imagen: " + e.getMessage(), 
	            "Error", JOptionPane.ERROR_MESSAGE);
	    }

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 583, 383);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Usuario:");
		lblNewLabel.setBounds(15, 40, 69, 20);
		panel.add(lblNewLabel);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(15, 64, 178, 26);
		panel.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Contrase\u00F1a:");
		lblNewLabel_1.setBounds(15, 115, 93, 20);
		panel.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String username = txtUsuario.getText().trim();
		        String password = new String(passwordFieldContrasena.getPassword()).trim();
		        
		        
		        if (username.isEmpty() || password.isEmpty()) {
		            JOptionPane.showMessageDialog(Login.this, 
		                "Por favor complete todos los campos", 
		                "Campos vacíos", 
		                JOptionPane.WARNING_MESSAGE);
		            return;
		        }
		        
		        
		        boolean loginExitoso = SerieNacionaldeBasket.getInstance().confirmLogin(username, password);
		        
		        if (loginExitoso) {
		            SerieNacionalBasketball frame = new SerieNacionalBasketball();
		            dispose();
		            frame.setVisible(true);
		        } else {
		            JOptionPane.showMessageDialog(Login.this, 
		                "Usuario o contraseña incorrectos", 
		                "Error de autenticación", 
		                JOptionPane.ERROR_MESSAGE);
		            passwordFieldContrasena.setText("");
		            txtUsuario.requestFocus();
		        }        
		    }
		});
		btnNewButton.setBounds(241, 92, 115, 29);
		panel.add(btnNewButton);
		
		passwordFieldContrasena = new JPasswordField();
		passwordFieldContrasena.setBounds(15, 137, 178, 26);
		panel.add(passwordFieldContrasena);
		
		JButton btnRegistrate = new JButton("\u00BFNo tienes cuenta? Registrate");
		btnRegistrate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirVentanaRegistro();
			}
		});
		btnRegistrate.setBounds(15, 218, 245, 29);
		panel.add(btnRegistrate);
	}
	
	

	
	private void abrirVentanaRegistro() {
	    this.setVisible(false); // ocultar login
	    RegUser regUser = new RegUser();
	    regUser.setModal(true); 
	    regUser.setVisible(true);
	    
	    // volver a abrir login
	    
	    regUser.addWindowListener(new WindowAdapter() {
	        @Override 
	        public void windowClosed(java.awt.event.WindowEvent e) { 
	            Login.this.setVisible(true); 
	        }
	    });
	}
	
}
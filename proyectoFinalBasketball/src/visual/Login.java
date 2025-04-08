

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

import com.sun.glass.events.WindowEvent;

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


public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField passwordFieldContrasena;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				FileInputStream empresa;
				FileOutputStream empresa2;
				ObjectInputStream empresaRead;
				ObjectOutputStream empresaWrite;
				try {
					empresa = new FileInputStream ("empresa.dat");
					empresaRead = new ObjectInputStream(empresa);
					SerieNacionaldeBasket temp = (SerieNacionaldeBasket)empresaRead.readObject();
					SerieNacionaldeBasket.setSerie(temp);
					empresa.close();
					empresaRead.close();
				} catch (FileNotFoundException e) {
					try {
						empresa2 = new  FileOutputStream("empresa.dat");
						empresaWrite = new ObjectOutputStream(empresa2);
						User aux = new User("Administrador", "Admin", "Admin");
						SerieNacionaldeBasket.getInstance().regUser(aux);
						empresaWrite.writeObject(SerieNacionaldeBasket.getInstance());
						empresa2.close();
						empresaWrite.close();
					} catch (FileNotFoundException e1) {
					} catch (IOException e1) {
						// TODO Auto-generated catch block
					}
				} catch (IOException e) {
					
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Create the frame.
	 
	public Login() {
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
				autenticarUser();
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
	        //@Override
	        public void windowClosed(WindowEvent e) {
	            Login.this.setVisible(true);
	        }
	    });
	}
	
	
	
	private void autenticarUser() {
	    String username = txtUsuario.getText().trim();
	    String password = new String(passwordFieldContrasena.getPassword());
	    
	    if (username.isEmpty() || password.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Por favor ingrese usuario y contraseña", 
	            "Campos vacíos", JOptionPane.WARNING_MESSAGE);
	        return;
	    }
	    
	    User user = SerieNacionaldeBasket.getInstance().buscarUsuarioPorNombre(username);
	    
	    if (user == null || !user.getPassw().equals(password)) {
	        JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", 
	            "Error de autenticación", JOptionPane.ERROR_MESSAGE);
	        passwordFieldContrasena.setText("");
	        txtUsuario.requestFocus();
	    } else {
	        // Login exitoso - Abrir ventana principal y cerrar login
	        JOptionPane.showMessageDialog(this, "Bienvenido " + user.getUserName(), 
	            "Login exitoso", JOptionPane.INFORMATION_MESSAGE);
	        
	        // Cargar datos (si es necesario)
	        cargarDatos();
	        
	        // Abrir ventana principal
	        EventQueue.invokeLater(() -> {
	            SerieNacionalBasketball mainWindow = new SerieNacionalBasketball();
	            mainWindow.setVisible(true);
	        });
	        
	        // Cerrar ventana de login
	        this.dispose();
	    }
	}

	private void cargarDatos() {
	    try (FileInputStream fileIn = new FileInputStream("serie_nacional.dat");
	         ObjectInputStream in = new ObjectInputStream(fileIn)) {
	        SerieNacionaldeBasket.setSerie((SerieNacionaldeBasket) in.readObject());
	    } catch (FileNotFoundException e) {
	        System.out.println("No se encontró archivo de datos, se creará uno nuevo al guardar");
	    } catch (IOException | ClassNotFoundException e) {
	        JOptionPane.showMessageDialog(this, 
	            "Error al cargar datos: " + e.getMessage(),
	            "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
}

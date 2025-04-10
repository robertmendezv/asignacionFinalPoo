package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import logico.SerieNacionaldeBasket;
import logico.User;

public class Login extends JFrame {

    private JPanel contentPane;
    private JTextField txtUsuario;
    private JPasswordField passwordFieldContrasena;
    private Image backgroundImage;

    public Login() {
        setTitle("Login");
        try {
            String imagePath = "../Imagenes/fondoLogin.jpg";
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                backgroundImage = new ImageIcon(imagePath).getImage();
            } else {
                imagePath = "Imagenes/fondoLogin.jpg";
                imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    backgroundImage = new ImageIcon(imagePath).getImage();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando imagen de fondo", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        SerieNacionaldeBasket.getInstance().cargarDatos();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                SerieNacionaldeBasket.getInstance().guardarDatos();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 583, 383);
        
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setBorder(null);
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(null);
        
        JPanel panelForm = new JPanel();
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(new LineBorder(new Color(0, 0, 0)));
        panelForm.setBounds(73, 40, 393, 238);
        panel.add(panelForm);
        panelForm.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Usuario:");
        lblNewLabel.setBounds(12, 13, 69, 20);
        panelForm.add(lblNewLabel);
        
        txtUsuario = new JTextField();
        txtUsuario.setBounds(12, 37, 178, 26);
        panelForm.add(txtUsuario);
        txtUsuario.setColumns(10);
        
        JLabel lblNewLabel_1 = new JLabel("Contrasena:");
        lblNewLabel_1.setBounds(12, 88, 93, 20);
        panelForm.add(lblNewLabel_1);
        
        passwordFieldContrasena = new JPasswordField();
        passwordFieldContrasena.setBounds(12, 110, 178, 26);
        panelForm.add(passwordFieldContrasena);
        
        JButton btnNewButton = new JButton("Login");
        btnNewButton.setBounds(238, 64, 115, 29);
        panelForm.add(btnNewButton);
        
        JButton btnRegistrate = new JButton("No tienes cuenta? Registrate");
        btnRegistrate.setBounds(83, 182, 245, 29);
        panelForm.add(btnRegistrate);

        btnRegistrate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirVentanaRegistro();
            }
        });
        
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtUsuario.getText().trim();
                String password = new String(passwordFieldContrasena.getPassword()).trim();
                
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(Login.this, 
                        "Por favor complete todos los campos", 
                        "Campos vacios", 
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
                        "Usuario o contrasena incorrectos", 
                        "Error de autenticacion", 
                        JOptionPane.ERROR_MESSAGE);
                    passwordFieldContrasena.setText("");
                    txtUsuario.requestFocus();
                }        
            }
        });
        
        setLocationRelativeTo(null);
    }
    
    private void abrirVentanaRegistro() {
        this.setVisible(false);
        RegUser regUser = new RegUser();
        regUser.setModal(true); 
        regUser.setVisible(true);
        
        regUser.addWindowListener(new WindowAdapter() {
            @Override 
            public void windowClosed(java.awt.event.WindowEvent e) { 
                Login.this.setVisible(true);
            }
        });
    }
}
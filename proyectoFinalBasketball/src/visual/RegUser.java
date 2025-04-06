package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import logico.SerieNacionaldeBasket;
import logico.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.awt.event.ActionEvent;

public class RegUser extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldConfirmacion;
	private JComboBox comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegUser dialog = new RegUser();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegUser() {
		setBounds(100, 100, 450, 228);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNombreUsuario = new JLabel("Nombre Usuario:");
		lblNombreUsuario.setBounds(20, 26, 97, 14);
		contentPanel.add(lblNombreUsuario);
		
		textField = new JTextField();
		textField.setBounds(20, 49, 127, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>", "Admin", "Anotador"}));
		comboBox.setBounds(20, 113, 127, 20);
		contentPanel.add(comboBox);
		
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(20, 88, 97, 14);
		contentPanel.add(lblTipo);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(190, 49, 147, 20);
		contentPanel.add(passwordField);
		passwordField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(189, 26, 97, 14);
		contentPanel.add(lblPassword);
		
		JLabel lblConfirmarPassword = new JLabel("Confirmar Password:");
		lblConfirmarPassword.setBounds(189, 88, 167, 14);
		contentPanel.add(lblConfirmarPassword);
		
		passwordFieldConfirmacion = new JPasswordField();
		passwordFieldConfirmacion.setColumns(10);
		passwordFieldConfirmacion.setBounds(190, 113, 147, 20);
		contentPanel.add(passwordFieldConfirmacion);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Aceptar");
				okButton.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				        // 1. Validar campos vacíos
				        if (textField.getText().isEmpty() || 
				            passwordField.getPassword().length == 0 || 
				            passwordFieldConfirmacion.getPassword().length == 0) {
				            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
				            return;
				        }

				        // 2. Validar contraseñas
				        String password = new String(passwordField.getPassword());
				        String confirmPassword = new String(passwordFieldConfirmacion.getPassword());
				        
				        if (!password.equals(confirmPassword)) {
				            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
				            return;
				        }

				        // 3. Validar tipo de usuario
				        if (comboBox.getSelectedItem().equals("<Seleccione>")) {
				            JOptionPane.showMessageDialog(null, "Seleccione un tipo de usuario.", "Error", JOptionPane.ERROR_MESSAGE);
				            return;
				        }

				        // 4. Crear y registrar usuario
				        try {
				            User user = new User(
				                comboBox.getSelectedItem().toString(),
				                textField.getText(),
				                password
				            );
				            
				            SerieNacionaldeBasket.getInstance().registrarUser(user);
				            
				            // 5. Guardar en empresa.dat
				            guardarUsuariosEnArchivo();
				            
				            JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
				            dispose(); // Cerrar ventana después de registrar
				        } catch (Exception ex) {
				            JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				        }
				    }
				});
				okButton.setActionCommand("Aceptar");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancelar");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private void guardarUsuariosEnArchivo() {
	    try (FileOutputStream fileOut = new FileOutputStream("empresa.dat");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
	        out.writeObject(SerieNacionaldeBasket.getInstance());
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(null, "Error al guardar usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
/*
    private void limpiarCampos() {
        txtNombre.setText("");
        spEdad.setValue(18);
        txtPosicion.setText("");
        spNumJugador.setValue(1);
        spEstatura.setValue(170);
        txtSangre.setText("");
        spPeso.setValue(70.0);
        txtFechaNac.setText("");
        txtMarca.setText("");
    }
    */
}

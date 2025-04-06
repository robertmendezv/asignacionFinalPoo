package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import logico.Equipo;
import logico.SerieNacionaldeBasket;

import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegistrarEquipo extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNameEquipo;
	private JTextField txtEntrenador;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegistrarEquipo dialog = new RegistrarEquipo(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegistrarEquipo(Equipo aux) {
		if(aux == null) {
			setTitle("Registrar Equipo");
		}else {
			setTitle("Modificar Equipo");
		}
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_10.setBackground(Color.WHITE);
		panel_10.setBounds(45, 34, 346, 148);
		contentPanel.add(panel_10);
		panel_10.setLayout(null);

		if(aux==null) {
			JLabel lblNewLabel = new JLabel("Registrar Equipo:");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
			lblNewLabel.setBounds(12, 13, 143, 19);
			panel_10.add(lblNewLabel);
		}else {
			JLabel lblNewLabel = new JLabel("Modificar Equipo:");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
			lblNewLabel.setBounds(12, 13, 143, 19);
			panel_10.add(lblNewLabel);
		}

		JLabel lblNewLabel_1 = new JLabel("Nombre del Equipo:");
		lblNewLabel_1.setBounds(12, 59, 123, 16);
		panel_10.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Nombre del Entrenador:");
		lblNewLabel_2.setBounds(12, 96, 143, 16);
		panel_10.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("---------------------------------");
		lblNewLabel_3.setBounds(12, 30, 175, 16);
		panel_10.add(lblNewLabel_3);

		txtNameEquipo = new JTextField();
		txtNameEquipo.setBounds(136, 56, 198, 22);
		panel_10.add(txtNameEquipo);
		txtNameEquipo.setColumns(10);

		txtEntrenador = new JTextField();
		txtEntrenador.setBounds(159, 93, 175, 22);
		panel_10.add(txtEntrenador);
		txtEntrenador.setColumns(10);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(224, 255, 255));
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel.setBounds(0, 0, 432, 10);
		contentPanel.add(panel);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_1.setBackground(new Color(224, 255, 255));
		panel_1.setBounds(0, 23, 432, 10);
		contentPanel.add(panel_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_2.setBackground(new Color(224, 255, 255));
		panel_2.setBounds(0, 46, 432, 10);
		contentPanel.add(panel_2);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_3.setBackground(new Color(224, 255, 255));
		panel_3.setBounds(0, 69, 432, 10);
		contentPanel.add(panel_3);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_4.setBackground(new Color(224, 255, 255));
		panel_4.setBounds(0, 92, 432, 10);
		contentPanel.add(panel_4);

		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_5.setBackground(new Color(224, 255, 255));
		panel_5.setBounds(0, 115, 432, 10);
		contentPanel.add(panel_5);

		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_6.setBackground(new Color(224, 255, 255));
		panel_6.setBounds(0, 138, 432, 10);
		contentPanel.add(panel_6);

		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_7.setBackground(new Color(224, 255, 255));
		panel_7.setBounds(0, 161, 432, 10);
		contentPanel.add(panel_7);

		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_8.setBackground(new Color(224, 255, 255));
		panel_8.setBounds(0, 184, 432, 10);
		contentPanel.add(panel_8);

		JPanel panel_9 = new JPanel();
		panel_9.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_9.setBackground(new Color(224, 255, 255));
		panel_9.setBounds(0, 207, 432, 10);
		contentPanel.add(panel_9);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Registrar");
				if(aux != null) {
					okButton.setText("Modificar");
				}
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(aux==null) {
							Equipo equipo = new Equipo(txtNameEquipo.getText(), txtEntrenador.getText());
							SerieNacionaldeBasket.getInstance().registrarEquipo(equipo);
							JOptionPane.showMessageDialog(null, "Operación Satisfactoria", "Registro", JOptionPane.INFORMATION_MESSAGE);
							clean();
						}else {
							aux.setNombreEquipo(txtNameEquipo.getText());
							aux.setNombreEntrenador(txtEntrenador.getText());
							dispose();
							ListadoEquipos.loadSupply();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		loadEquipo(aux);
	}
	private void loadEquipo(Equipo aux) {
		if(aux!=null) {
			txtNameEquipo.setText(aux.getNombreEquipo());
			txtEntrenador.setText(aux.getNombreEntrenador());
		}
		
	}
	
	private void clean() {
		txtNameEquipo.setText("");
		txtEntrenador.setText("");
	}
}

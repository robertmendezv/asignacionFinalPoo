package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import logico.Equipo;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ModificarEquipo extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtEquipo;
	private JTextField txtEntrenador;
	private ArrayList<Equipo> equipos;
	private JButton btnModificar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ArrayList<Equipo> equipos = new ArrayList<>();
			ModificarEquipo dialog = new ModificarEquipo(equipos);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ModificarEquipo(ArrayList<Equipo> equipos) {
		this.equipos = equipos;
		setTitle("Modificar Equipo");
		setBounds(100, 100, 532, 419);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(204, 255, 204));
		contentPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(41, 28, 425, 268);
		contentPanel.add(panel);
		panel.setLayout(null);
		{
			JLabel label = new JLabel("Modificar Equipo:");
			label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
			label.setBounds(12, 13, 191, 25);
			panel.add(label);
		}
		{
			JLabel label = new JLabel("---------------------------------------------");
			label.setBounds(12, 37, 191, 16);
			panel.add(label);
		}
		{
			JLabel label = new JLabel("Equipo a Modificar:");
			label.setBounds(12, 91, 119, 16);
			panel.add(label);
		}
		{
			JComboBox cmbEquipos = new JComboBox();
			cmbEquipos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int index = cmbEquipos.getSelectedIndex() - 1;
					if (index >= 0) {
						Equipo equipoSeleccionado = equipos.get(index);
						txtEquipo.setText(equipoSeleccionado.getNombreEquipo());
						txtEntrenador.setText(equipoSeleccionado.getNombreEntrenador());
						txtEquipo.setEnabled(true);
						txtEntrenador.setEnabled(true);
						btnModificar.setEnabled(true);
					} else {
						txtEquipo.setText("");
						txtEntrenador.setText("");
						txtEquipo.setEnabled(false);
						txtEntrenador.setEnabled(false);
						btnModificar.setEnabled(false);
					}
				}
			});
			cmbEquipos.setModel(new DefaultComboBoxModel(new String[] {"<Seleccionar>"}));
			for (Equipo equipo : equipos) {
				cmbEquipos.addItem(equipo.getNombreEquipo());
			}
			cmbEquipos.setBounds(129, 88, 209, 22);
			panel.add(cmbEquipos);
		}
		{
			JLabel lblNewLabel = new JLabel("Nombre del Equipo:");
			lblNewLabel.setBounds(12, 133, 119, 16);
			panel.add(lblNewLabel);
		}
		
		txtEquipo = new JTextField();
		txtEquipo.setEnabled(false);
		txtEquipo.setBounds(139, 130, 199, 22);
		panel.add(txtEquipo);
		txtEquipo.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre del Entrenador:");
		lblNewLabel_1.setBounds(12, 174, 146, 16);
		panel.add(lblNewLabel_1);
		
		txtEntrenador = new JTextField();
		txtEntrenador.setEnabled(false);
		txtEntrenador.setBounds(159, 171, 179, 22);
		panel.add(txtEntrenador);
		txtEntrenador.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnModificar = new JButton("Modificar");
				btnModificar.setEnabled(false);
				btnModificar.setActionCommand("OK");
				buttonPane.add(btnModificar);
				getRootPane().setDefaultButton(btnModificar);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}

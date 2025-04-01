package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class ListadoEstadisticasGeneral extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListadoEstadisticasGeneral dialog = new ListadoEstadisticasGeneral();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListadoEstadisticasGeneral() {
		setTitle("Listado de Estadisticas");
		setBounds(100, 100, 826, 456);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			{
				JLabel lblNewLabel = new JLabel("Elija el jugador:");
				lblNewLabel.setBounds(15, 16, 144, 20);
				panel.add(lblNewLabel);
			}
			
			JComboBox cbxJugador = new JComboBox();
			cbxJugador.setBounds(166, 13, 181, 26);
			panel.add(cbxJugador);
			{
				JLabel lblNewLabel_1 = new JLabel("Todos los jugadores");
				lblNewLabel_1.setBounds(419, 16, 163, 20);
				panel.add(lblNewLabel_1);
			}
			
			JCheckBox chkboxJugadores = new JCheckBox("");
			chkboxJugadores.setSelected(true);
			chkboxJugadores.setBounds(587, 12, 139, 29);
			panel.add(chkboxJugadores);
			
			table = new JTable();
			table.setBounds(35, 117, 680, 234);
			panel.add(table);
			
			JLabel lblNewLabel_2 = new JLabel("Nombre");
			lblNewLabel_2.setBounds(54, 95, 69, 20);
			panel.add(lblNewLabel_2);
			
			JLabel lblNewLabel_3 = new JLabel("Equipo");
			lblNewLabel_3.setBounds(138, 95, 69, 20);
			panel.add(lblNewLabel_3);
			
			JLabel lblNewLabel_4 = new JLabel("Promedio Puntos");
			lblNewLabel_4.setBounds(217, 95, 126, 20);
			panel.add(lblNewLabel_4);
			
			JLabel lblNewLabel_5 = new JLabel("Promedio Rebotes");
			lblNewLabel_5.setBounds(371, 95, 144, 20);
			panel.add(lblNewLabel_5);
			
			JLabel lblNewLabel_6 = new JLabel("Promedio Asistencias");
			lblNewLabel_6.setBounds(530, 95, 173, 20);
			panel.add(lblNewLabel_6);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}

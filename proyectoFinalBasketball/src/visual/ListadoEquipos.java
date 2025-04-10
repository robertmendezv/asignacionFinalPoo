package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Equipo;
import logico.SerieNacionaldeBasket;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListadoEquipos extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private static DefaultTableModel model;
	private static Object[] row;
	private Equipo miEquipo = null;
	private JButton btnEliminar;
	private JButton btnModificar;
	private JButton btnVerEquipo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListadoEquipos dialog = new ListadoEquipos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListadoEquipos() {
		setTitle("Listado de Equipos");
		setBounds(100, 100, 828, 381);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JScrollPane scrollPane = new JScrollPane();
				panel.add(scrollPane, BorderLayout.CENTER);
				{
					model = new DefaultTableModel();
					String[] headers = {"Nombre", "Entrenador", "Cantidad de Jugadores"};
					model.setColumnIdentifiers(headers);
					table = new JTable();
					table.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							int index = table.getSelectedRow();
							if(index!= -1) {
								btnEliminar.setEnabled(true);
								btnModificar.setEnabled(true);
								btnVerEquipo.setEnabled(true);
								miEquipo = SerieNacionaldeBasket.getInstance().buscarEquipoPorNombre(table.getValueAt(index, 0).toString());
							}
						}
					});
					table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					table.setModel(model);
					scrollPane.setViewportView(table);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnEliminar = new JButton("Eliminar");
				btnEliminar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int option = JOptionPane.showConfirmDialog(null, "Esta seguro que desea eliminar el equipo con nombre: " +miEquipo.getNombreEquipo()+ "?", "Eliminar", JOptionPane.WARNING_MESSAGE);
						if(JOptionPane.YES_OPTION == option) {
							SerieNacionaldeBasket.getInstance().eliminarEquipo(miEquipo.getNombreEquipo());
							loadSupply();
						}
					}
				});
				{
					btnModificar = new JButton("Modificar");
					btnModificar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							RegistrarEquipo update = new RegistrarEquipo(miEquipo);
							update.setModal(true);
							update.setVisible(true);
						}
					});
					{
						btnVerEquipo = new JButton("Ver Equipo");
						btnVerEquipo.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(miEquipo != null) {
									VerEquipo dialog = new VerEquipo(miEquipo);
									dialog.setModal(true);
									dialog.setVisible(true);
								}
							}
						});
						btnVerEquipo.setEnabled(false);
						buttonPane.add(btnVerEquipo);
					}
					btnModificar.setEnabled(false);
					buttonPane.add(btnModificar);
				}
				btnEliminar.setEnabled(false);
				btnEliminar.setActionCommand("OK");
				buttonPane.add(btnEliminar);
				getRootPane().setDefaultButton(btnEliminar);
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
		loadSupply();
	}
	public static void loadSupply() {
		model.setRowCount(0);
		row = new Object[model.getColumnCount()];
		ArrayList<Equipo> aux = SerieNacionaldeBasket.getInstance().getEquipos();
		for (Equipo equipo : aux) {
			row[0] = equipo.getNombreEquipo();
			row[1] = equipo.getNombreEntrenador();
			row[2] = equipo.getMisjugadores().size();
			model.addRow(row);
		}
	}

}

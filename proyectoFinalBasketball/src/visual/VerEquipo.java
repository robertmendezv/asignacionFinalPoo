package visual;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import logico.Equipo;
import logico.Jugador;
import logico.Lesion;
import logico.SerieNacionaldeBasket;

public class VerEquipo extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private DefaultTableModel model;
    private Equipo equipo;

    public VerEquipo(Equipo equipo) {
        this.equipo = equipo;
        setTitle("Detalles del Equipo: " + equipo.getNombreEquipo());
        setBounds(100, 100, 800, 500);
        getContentPane().setLayout(new BorderLayout());
        
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelSuperior.setLayout(new BorderLayout());
        
        JLabel lblEntrenador = new JLabel("Entrenador: " + equipo.getNombreEntrenador());
        lblEntrenador.setFont(new Font("Tahoma", Font.BOLD, 14));
        panelSuperior.add(lblEntrenador, BorderLayout.NORTH);
        
        JLabel lblJugadores = new JLabel("Jugadores: " + equipo.getMisjugadores().size());
        lblJugadores.setFont(new Font("Tahoma", Font.PLAIN, 12));
        panelSuperior.add(lblJugadores, BorderLayout.SOUTH);
        
        getContentPane().add(panelSuperior, BorderLayout.NORTH);
        
        model = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1) return Boolean.class;
                return Object.class;
            }
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2;
            }
        };
        
        model.setColumnIdentifiers(new String[]{"Jugador", "Lesionado", "Acción"});
        table = new JTable(model);
        
        table.getColumnModel().getColumn(1).setCellRenderer(new CheckBoxRenderer());
        table.getColumnModel().getColumn(1).setCellEditor(new CheckBoxEditor());
        table.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor());
        
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        loadJugadores();
        
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
        JButton cancelButton = new JButton("Cerrar");
        cancelButton.addActionListener(e -> dispose());
        buttonPane.add(cancelButton);
    }

    private void loadJugadores() {
        model.setRowCount(0);
        for (Jugador jugador : equipo.getMisjugadores()) {
            model.addRow(new Object[]{
                jugador.getNombreJugador(),
                SerieNacionaldeBasket.getInstance().tieneLesionActiva(jugador),
                "Ver Jugador"
            });
        }
    }

    class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {
        public CheckBoxRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Jugador jugador = equipo.getMisjugadores().get(row);
            boolean lesionado = SerieNacionaldeBasket.getInstance().tieneLesionActiva(jugador);
            setSelected(lesionado);
            setEnabled(!lesionado || haCumplidoDuracion(jugador));
            return this;
        }
        
        private boolean haCumplidoDuracion(Jugador jugador) {
            Lesion lesion = SerieNacionaldeBasket.getInstance().getLesionActiva(jugador);
            if (lesion != null) {
                long diasPasados = (new Date().getTime() - lesion.getFechaInicio().getTime()) 
                                  / (1000 * 60 * 60 * 24);
                return diasPasados >= lesion.getDuracion();
            }
            return true;
        }
    }

    class CheckBoxEditor extends AbstractCellEditor implements TableCellEditor {
        private JCheckBox checkBox;

        public CheckBoxEditor() {
            checkBox = new JCheckBox();
            checkBox.setHorizontalAlignment(JCheckBox.CENTER);
            checkBox.addActionListener(e -> {
                int row = table.getEditingRow();
                if (row != -1) {
                    Jugador jugador = equipo.getMisjugadores().get(row);
                    if (checkBox.isSelected()) {
                        LesionRegistro dialog = new LesionRegistro(jugador);
                        dialog.setModal(true);
                        dialog.setVisible(true);
                    } else {
                        Lesion lesion = SerieNacionaldeBasket.getInstance().getLesionActiva(jugador);
                        if (lesion != null && haCumplidoDuracion(jugador)) {
                            SerieNacionaldeBasket.getInstance().eliminarLesion(lesion);
                        }
                    }
                    loadJugadores();
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            Jugador jugador = equipo.getMisjugadores().get(row);
            boolean lesionado = SerieNacionaldeBasket.getInstance().tieneLesionActiva(jugador);
            checkBox.setSelected(lesionado);
            checkBox.setEnabled(!lesionado || haCumplidoDuracion(jugador));
            return checkBox;
        }

        public Object getCellEditorValue() {
            return checkBox.isSelected();
        }
        
        private boolean haCumplidoDuracion(Jugador jugador) {
            Lesion lesion = SerieNacionaldeBasket.getInstance().getLesionActiva(jugador);
            return lesion == null || (new Date().getTime() - lesion.getFechaInicio().getTime()) 
                                  / (1000 * 60 * 60 * 24) >= lesion.getDuracion();
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Ver Jugador");
            return this;
        }
    }

    class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private JButton button;
        private int currentRow;

        public ButtonEditor() {
            button = new JButton();
            button.addActionListener(this);
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            currentRow = row;
            button.setText("Ver Jugador");
            return button;
        }

        public Object getCellEditorValue() {
            return "Ver Jugador";
        }

        public void actionPerformed(ActionEvent e) {
            Jugador jugador = equipo.getMisjugadores().get(currentRow);
            VerJugador dialog = new VerJugador(jugador);
            dialog.setModal(true);
            dialog.setVisible(true);
            fireEditingStopped();
        }
    }
}
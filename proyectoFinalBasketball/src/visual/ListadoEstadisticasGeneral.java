package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import logico.SerieNacionaldeBasket;
import logico.Jugador;
import logico.Estadistica;
import javax.swing.JTable;


public class ListadoEstadisticasGeneral extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private DefaultTableModel model;
    private JTable table;
    private static final long serialVersionUID = 1L;

    public ListadoEstadisticasGeneral() {
        setTitle("Listado de Estadísticas de Jugadores");
        setBounds(100, 100, 800, 500);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        
       
        model = new DefaultTableModel(
        	    new Object[][] {}, 
        	    new String[] {"Jugador", "Puntos", "Rebotes", "Asistencias", "Acciones Defensivas"}
        	) {
        	    @Override
        	    public boolean isCellEditable(int row, int column) {
        	        return false;
        	    }
        	    
        	    @Override
        	    public Class<?> getColumnClass(int columnIndex) {
        	        return columnIndex == 0 ? String.class : Integer.class;
        	    }
        	};
        
        table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        
        JScrollPane scrollPane = new JScrollPane(table);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarDatos());
        buttonPane.add(btnActualizar);
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        buttonPane.add(btnCerrar);
        
        // Cargar datos iniciales
        cargarDatos();
    }
    
    private void cargarDatos() {
        model.setRowCount(0); 
        
        SerieNacionaldeBasket serie = SerieNacionaldeBasket.getInstance();
        if(serie != null) {
            ArrayList<Jugador> jugadores = serie.getTodosJugadores();
            
            for(Jugador jugador : jugadores) {
                Estadistica stats = jugador.getEstadistica();
                model.addRow(new Object[]{
                    jugador.getNombreJugador(),
                    stats != null ? stats.getPuntos() : 0,
                    stats != null ? stats.getRebotes() : 0,
                    stats != null ? stats.getAsistencias() : 0,
                    stats != null ? stats.getAccionesDefensivas() : 0
                });
            }
        }
    }
    
    public static void mostrarListado() {
        ListadoEstadisticasGeneral dialog = new ListadoEstadisticasGeneral();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}
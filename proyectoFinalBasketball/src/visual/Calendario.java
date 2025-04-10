package visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import logico.Partido;
import logico.SerieNacionaldeBasket;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import logico.Equipo;
import java.time.ZoneId;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Calendario extends JFrame {
    private JTable tablaPartidos;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> cbEquipoLocal, cbEquipoVisitante;
    private JSpinner spinnerHora;
    private JButton btnAgregar, btnEliminar, btnSimular;
    private JDateChooser dateChooser;
    private static final long serialVersionUID = 1L;

    public Calendario() {
        setTitle("Calendario de Partidos");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        cargarEquipos();
        cargarPartidos();
    }

    

    
    private void initComponents() {
        JPanel panelSuperior = new JPanel(new GridLayout(0, 2, 10, 10));
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Nuevo Partido"));
        
  
        panelSuperior.add(new JLabel("Fecha:"));
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        panelSuperior.add(dateChooser);
        
        panelSuperior.add(new JLabel("Hora:"));
        spinnerHora = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spinnerHora, "HH:mm");
        spinnerHora.setEditor(timeEditor);
        panelSuperior.add(spinnerHora);
        
        panelSuperior.add(new JLabel("Equipo Local:"));
        cbEquipoLocal = new JComboBox<>();
        panelSuperior.add(cbEquipoLocal);
        
        panelSuperior.add(new JLabel("Equipo Visitante:"));
        cbEquipoVisitante = new JComboBox<>();
        panelSuperior.add(cbEquipoVisitante);
        
        btnAgregar = new JButton("Agregar partido");
        btnAgregar.addActionListener(e -> agregarPartido());
        panelSuperior.add(btnAgregar);
        
        btnEliminar = new JButton("Eliminar seleccionado");
        btnEliminar.addActionListener(e -> eliminarPartido());
        panelSuperior.add(btnEliminar);
        
        btnSimular = new JButton("Simular Partido");
        btnSimular.addActionListener(e -> simularPartido());
        panelSuperior.add(btnSimular);

     
        modeloTabla = new DefaultTableModel(
            new Object[]{"Fecha", "Hora", "Local", "Visitante", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaPartidos = new JTable(modeloTabla);
        tablaPartidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tablaPartidos);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Partidos Programados"));
        
        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().add(panelSuperior, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarEquipos() {
        ArrayList<Equipo> equipos = SerieNacionaldeBasket.getInstance().getEquipos();
        cbEquipoLocal.removeAllItems();
        cbEquipoVisitante.removeAllItems();
        
        cbEquipoLocal.addItem("Seleccionar");
        cbEquipoVisitante.addItem("Seleccionar");
        
        for (Equipo equipo : equipos) {
            cbEquipoLocal.addItem(equipo.getNombreEquipo());
            cbEquipoVisitante.addItem(equipo.getNombreEquipo());
        }
    }

    private void cargarPartidos() {
        modeloTabla.setRowCount(0);
        ArrayList<Partido> partidos = SerieNacionaldeBasket.getInstance().getPartidos();
        
        DateTimeFormatter fechaFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        for (Partido partido : partidos) {
            modeloTabla.addRow(new Object[]{
                partido.getFecha().format(fechaFormatter),
                partido.getHora().format(horaFormatter),
                partido.getEquipoLocal().getNombreEquipo(),
                partido.getEquipoVisitante().getNombreEquipo(),
                partido.getEstado()
            });
        }
    }

    private void agregarPartido() {
        try {
          
            if (dateChooser.getDate() == null) {
                mostrarError("Seleccione una fecha valida");
                return;
            }

       
            if (cbEquipoLocal.getSelectedIndex() <= 0 || cbEquipoVisitante.getSelectedIndex() <= 0) {
                mostrarError("Seleccione ambos equipos");
                return;
            }

      
            if (cbEquipoLocal.getSelectedItem().equals(cbEquipoVisitante.getSelectedItem())) {
                mostrarError("Un equipo no puede jugar contra si mismo");
                return;
            }

        
            LocalDate fecha = dateChooser.getDate().toInstant()
                              .atZone(ZoneId.systemDefault())
                              .toLocalDate();
            
            LocalTime hora = ((Date) spinnerHora.getValue()).toInstant()
                             .atZone(ZoneId.systemDefault())
                             .toLocalTime();

           
            if (fecha.isBefore(LocalDate.now())) {
                mostrarError("No se pueden programar partidos en fechas pasadas");
                return;
            }

   
            String nombreLocal = (String) cbEquipoLocal.getSelectedItem();
            String nombreVisitante = (String) cbEquipoVisitante.getSelectedItem();
            
            Equipo local = SerieNacionaldeBasket.getInstance().buscarEquipoPorNombre(nombreLocal);
            Equipo visitante = SerieNacionaldeBasket.getInstance().buscarEquipoPorNombre(nombreVisitante);

            if (local == null || visitante == null) {
                mostrarError("No se encontro uno de los equipos");
                return;
            }

    
            Partido nuevoPartido = new Partido(local, visitante, fecha, hora);
            boolean agregado = SerieNacionaldeBasket.getInstance().agregarPartido(nuevoPartido);

            if (agregado) {
                cargarPartidos();
                JOptionPane.showMessageDialog(this, 
                    "Partido agregado correctamente", 
                    "�xito", JOptionPane.INFORMATION_MESSAGE);
                
        
                dateChooser.setDate(null);
                cbEquipoLocal.setSelectedIndex(0);
                cbEquipoVisitante.setSelectedIndex(0);
            } else {
                mostrarError("Ya existe un partido programado para esa fecha y hora");
            }

        } catch (Exception ex) {
            mostrarError("Error al agregar partido: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void eliminarPartido() {
        int filaSeleccionada = tablaPartidos.getSelectedRow();
        if (filaSeleccionada == -1) {
            mostrarAdvertencia("Seleccione un partido para eliminar");
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "Esta seguro de eliminar este partido?", "Confirmar eliminacion", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            ArrayList<Partido> partidos = SerieNacionaldeBasket.getInstance().getPartidos();
            if (filaSeleccionada < partidos.size()) {
                Partido partido = partidos.get(filaSeleccionada);
                
           
                if (partido.getEstado().equals("En juego") || partido.getEstado().equals("Finalizado")) {
                    mostrarError("No se pueden eliminar partidos en juego o finalizados");
                    return;
                }
                
                SerieNacionaldeBasket.getInstance().eliminarPartido(partido);
                cargarPartidos();
            }
        }
    }
    
    private void simularPartido() {
        int filaSeleccionada = tablaPartidos.getSelectedRow();
        if (filaSeleccionada == -1) {
            mostrarAdvertencia("Seleccione un partido para simular");
            return;
        }
        
        ArrayList<Partido> partidos = SerieNacionaldeBasket.getInstance().getPartidos();
        Partido partido = partidos.get(filaSeleccionada);
        
    
        if (!partido.getFecha().equals(LocalDate.now())) {
            mostrarError("Solo se pueden simular partidos programados para hoy");
            return;
        }
        
      
        if (partido.getEstado().equals("Finalizado")) {
            mostrarError("Este partido ya ha finalizado");
            return;
        }
        
        if (partido.getEstado().equals("En juego")) {
            int opcion = JOptionPane.showConfirmDialog(this, 
                "Este partido ya esta en juego. Desea continuar la simulacion?", 
                "Partido en curso", JOptionPane.YES_NO_OPTION);
            
            if (opcion != JOptionPane.YES_OPTION) {
                return;
            }
        }
        
  
        EventQueue.invokeLater(() -> {
            Simulacion simulacion = new Simulacion(partido);
            simulacion.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    cargarPartidos();
                }
            });
            simulacion.setVisible(true);
        });
        
    
        partido.setEstado("En juego");
        cargarPartidos();
    }
    
   
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Calendario frame = new Calendario();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
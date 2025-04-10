package visual;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import logico.Partido;
import logico.SerieNacionaldeBasket;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JCalendar;
import java.util.Date;
import com.toedter.calendar.IDateEditor;
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
    private JButton btnAgregar, btnEliminar;
    private JDateChooser dateChooser; 
    

    public Calendario() {
        setTitle("Calendario de Partidos");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        try {
            String projectPath = System.getProperty("user.dir");
            String imagePath = projectPath + File.separator + "Imagenes" + File.separator + "calendar.png";
            File imageFile = new File(imagePath);
            
            if (!imageFile.exists()) {
                JOptionPane.showMessageDialog(this, 
                    "No se encontró la imagen en:\n" + imagePath, 
                    "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Image img = ImageIO.read(imageFile);
                ImageIcon icon = new ImageIcon(img.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                JLabel lblImagen = new JLabel(icon);
                lblImagen.setBounds(10, 10, 100, 100);
                getContentPane().add(lblImagen, BorderLayout.WEST);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error al cargar la imagen: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        
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
        
        JLabel label = new JLabel("Equipo Local:");
        panelSuperior.add(label);
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
                JOptionPane.showMessageDialog(this, "Seleccione una fecha válida", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (cbEquipoLocal.getSelectedIndex() <= 0 || cbEquipoVisitante.getSelectedIndex() <= 0) {
                JOptionPane.showMessageDialog(this, "Seleccione ambos equipos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (cbEquipoLocal.getSelectedItem().equals(cbEquipoVisitante.getSelectedItem())) {
                JOptionPane.showMessageDialog(this, "Un equipo no puede jugar contra sí mismo", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
            LocalDate fecha = ((java.sql.Date) dateChooser.getDate()).toLocalDate();
            LocalTime hora = ((Date) spinnerHora.getValue()).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalTime();
            
            String nombreLocal = (String) cbEquipoLocal.getSelectedItem();
            String nombreVisitante = (String) cbEquipoVisitante.getSelectedItem();
            
            Equipo local = SerieNacionaldeBasket.getInstance().buscarEquipoPorNombre(nombreLocal);
            Equipo visitante = SerieNacionaldeBasket.getInstance().buscarEquipoPorNombre(nombreVisitante);
            
    
            Partido nuevoPartido = new Partido(local, visitante, fecha, hora);
            SerieNacionaldeBasket.getInstance().agregarPartido(nuevoPartido);
            
         
            cargarPartidos();
            
            JOptionPane.showMessageDialog(this, "Partido agregado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar partido: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void eliminarPartido() {
        int filaSeleccionada = tablaPartidos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un partido para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este partido?", "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            ArrayList<Partido> partidos = SerieNacionaldeBasket.getInstance().getPartidos();
            if (filaSeleccionada < partidos.size()) {
                SerieNacionaldeBasket.getInstance().eliminarPartido(partidos.get(filaSeleccionada));
                cargarPartidos();
            }
        }
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
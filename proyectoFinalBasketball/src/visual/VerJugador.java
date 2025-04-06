package visual;

import java.awt.BorderLayout;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import logico.Jugador;
import logico.Lesion;
import logico.SerieNacionaldeBasket;

public class VerJugador extends JDialog {
    public VerJugador(Jugador jugador) {
        setTitle("Detalles del Jugador: " + jugador.getNombreJugador());
        setBounds(100, 100, 400, 400);
        setLocationRelativeTo(null);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPanel.setLayout(new BorderLayout());
        
        JLabel lblInfo = new JLabel("Información del Jugador");
        lblInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
        contentPanel.add(lblInfo, BorderLayout.NORTH);
        
        JTextArea txtInfo = new JTextArea();
        txtInfo.setEditable(false);
        txtInfo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        StringBuilder info = new StringBuilder();
        info.append("Nombre: ").append(jugador.getNombreJugador()).append("\n");
        info.append("Edad: ").append(jugador.getEdad()).append(" años\n");
        info.append("Posición: ").append(jugador.getPosicionJugador()).append("\n");
        info.append("Número: ").append(jugador.getNumJugador()).append("\n");
        info.append("Estatura: ").append(jugador.getEstatura()).append(" cm\n");
        info.append("Peso: ").append(jugador.getPeso()).append(" kg\n");
        info.append("Fecha Nacimiento: ").append(jugador.getFechanacimiento()).append("\n");
        info.append("Tipo Sangre: ").append(jugador.getSangre()).append("\n");
        info.append("Marca Promotora: ").append(jugador.getMarcapromotora()).append("\n\n");
        
        // Información de lesiones
        Lesion lesion = SerieNacionaldeBasket.getInstance().getLesionActiva(jugador);
        
        if (lesion != null) {
            long diasRestantes = calcularDiasRestantes(lesion);
            info.append("--- ESTADO: LESIONADO ---\n");
            info.append("Tipo: ").append(lesion.getTipoLesion()).append("\n");
            info.append("Duración total: ").append(lesion.getDuracion()).append(" días\n");
            info.append("Días restantes: ").append(diasRestantes).append("\n");
            info.append("Fecha recuperación: ").append(calcularFechaRecuperacion(lesion)).append("\n");
        } else {
            info.append("--- ESTADO: DISPONIBLE ---\n");
        }
        
        txtInfo.setText(info.toString());
        
        JScrollPane scrollPane = new JScrollPane(txtInfo);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        getContentPane().add(contentPanel, BorderLayout.CENTER);
    }
    
    private long calcularDiasRestantes(Lesion lesion) {
        long diasPasados = (new Date().getTime() - lesion.getFechaInicio().getTime()) 
                         / (1000 * 60 * 60 * 24);
        long diasRestantes = lesion.getDuracion() - diasPasados;
        return diasRestantes > 0 ? diasRestantes : 0;
    }
    
    private String calcularFechaRecuperacion(Lesion lesion) {
        long fechaRecupMillis = lesion.getFechaInicio().getTime() + 
                               (lesion.getDuracion() * 24L * 60 * 60 * 1000);
        return new Date(fechaRecupMillis).toString();
    }
}
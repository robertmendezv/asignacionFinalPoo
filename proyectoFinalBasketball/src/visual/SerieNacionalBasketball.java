package visual;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

public class SerieNacionalBasketball {

	private JFrame frmSerieNacionalDe;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SerieNacionalBasketball window = new SerieNacionalBasketball();
					window.frmSerieNacionalDe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SerieNacionalBasketball() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSerieNacionalDe = new JFrame();
		frmSerieNacionalDe.setTitle("Serie Nacional de Basketball");
		frmSerieNacionalDe.setBounds(100, 100, 743, 460);
		frmSerieNacionalDe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSerieNacionalDe.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frmSerieNacionalDe.getContentPane().add(panel, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		frmSerieNacionalDe.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Equipos");
		menuBar.add(mnNewMenu);
		
		JMenu mnNewMenu_1 = new JMenu("Jugadores");
		menuBar.add(mnNewMenu_1);
		
		JMenu mnNewMenu_2 = new JMenu("Estadisticas");
		menuBar.add(mnNewMenu_2);
		
		JMenu mnNewMenu_3 = new JMenu("Lesiones");
		menuBar.add(mnNewMenu_3);
		
		JMenu mnNewMenu_4 = new JMenu("Partido");
		menuBar.add(mnNewMenu_4);
	}

}

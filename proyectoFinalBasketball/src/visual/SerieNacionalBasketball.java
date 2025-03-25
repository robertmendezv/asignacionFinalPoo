package visual;

import java.awt.EventQueue;

import javax.swing.JFrame;

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
		frmSerieNacionalDe.setBounds(100, 100, 450, 300);
		frmSerieNacionalDe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

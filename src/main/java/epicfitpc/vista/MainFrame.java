package epicfitpc.vista;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import epicfitpc.utils.Estilos;
import epicfitpc.vista.paneles.PanelLogin;
import epicfitpc.vista.paneles.PanelRegistro;

/**
 * JFrame que contiene el panel del menú.
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = -7633771846060976450L;
	private static MainFrame instance;

	public MainFrame() {
		initialize();
	}

	/**
	 * Inicializa el JFrame.
	 */
	private void initialize() {
		setTitle("EpicFit");
		setBounds(100, 100, 1200, 750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		setBackground(Estilos.DARK_BACKGROUND);

		PanelLogin panelLogin = new PanelLogin(this);
		getContentPane().add(panelLogin);
		
		/*PanelRegistro panelRegistro = new PanelRegistro(this);
		 * getContentPane().add(panelRegistro);
		 */
		revalidate();
		repaint();
	}

	public static synchronized MainFrame getInstance() {
		if (instance == null) {
			instance = new MainFrame();
		}
		return instance;
	}
}

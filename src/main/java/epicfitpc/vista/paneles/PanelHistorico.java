package epicfitpc.vista.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.google.cloud.firestore.Firestore;

import epicfitpc.bbdd.GestorDeHistoricos;
import epicfitpc.modelo.Historico;
import epicfitpc.modelo.Usuario;
import epicfitpc.utils.Conexion;
import epicfitpc.utils.Estilos;
import epicfitpc.utils.UsuarioLogueado;
import epicfitpc.vista.componentes.HistoricoItemPanel;

public class PanelHistorico extends JPanel {

	private static final long serialVersionUID = 2651779404513169891L;
	private JPanel panelHInterior;
	private ArrayList<Historico> historicos = null;
	private Usuario usuario = null;
	private static final int PANELES_NECESARIOS = 3;

	/**
	 * Constructor que inicializa el panel y recibe el listado de workouts.
	 * 
	 * @param workouts ArrayList de Workouts
	 */
	public PanelHistorico() {
		this.usuario =  UsuarioLogueado.getInstance().getUsuario();
		this.historicos = obtenerHistoricos();
		initialize();
	}

	/**
	 * Inicializa los componentes del panel.
	 */
	private void initialize() {
		setLayout(new BorderLayout(0,0));

		panelHInterior = new JPanel();
		panelHInterior.setBackground(Color.WHITE);
		panelHInterior.setLayout(new GridLayout(0, 1, 10, 10));
		panelHInterior.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelHInterior.setBackground(Estilos.DARK_BACKGROUND);

		JScrollPane scrollPane = new JScrollPane(panelHInterior);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane);

		agregarPanelesHistoricos();
	}

	/**
	 * Añade los paneles de cada workout al grid. Se añaden paneles vacíos en caso
	 * de que no haya suficientes para mantener las proporciones.
	 * 
	 * @param nivel Número entero que es el nivel por el cual se filtra. -1 si no
	 *              hay filtro.
	 */
	private void agregarPanelesHistoricos() {
		int numeroDePaneles = 0;
		int panelesNecesarios = PANELES_NECESARIOS;

		// Items reales para los workouts
		if (historicos != null) {
			for (Historico historico : historicos) {
				numeroDePaneles++;
				HistoricoItemPanel itemPanel = new HistoricoItemPanel(historico);
				panelHInterior.add(itemPanel);
			}
		}

		generarPanelesVacios(numeroDePaneles, panelesNecesarios, panelHInterior);

	}

	/**
	 * Genera paneles vacíos.
	 * 
	 * @param numeroDePaneles   Paneles generados hasta el momento.
	 * @param panelesNecesarios Paneles necesarios en total.
	 * @param panel             Panel padre al que se añaden el resto de paneles.
	 */
	private void generarPanelesVacios(int numeroDePaneles, int panelesNecesarios, JPanel panel) {
		// Paneles vacíos para que se ajusten bien los tamaños
		for (int i = numeroDePaneles; i < panelesNecesarios; i++) {
			JPanel emptyPanel = new JPanel();
			emptyPanel.setBackground(Estilos.DARK_BACKGROUND);
			panel.add(emptyPanel);
		}
	}
	
	private ArrayList<Historico> obtenerHistoricos() {
		ArrayList<Historico> historicos = null;
		Firestore db;
		try {
			db = Conexion.getInstance().getConexion();
			GestorDeHistoricos gdh = new GestorDeHistoricos(db);
			historicos = gdh.obtenerTodosLosHistoricosPorUsuario(usuario);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return historicos;

	}

}

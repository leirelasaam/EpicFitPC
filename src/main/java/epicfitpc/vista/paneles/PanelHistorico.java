package epicfitpc.vista.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.google.cloud.firestore.Firestore;

import epicfitpc.modelo.bbdd.GestorDeHistoricos;
import epicfitpc.modelo.pojos.Historico;
import epicfitpc.modelo.pojos.Usuario;
import epicfitpc.utils.Conexion;
import epicfitpc.utils.UsuarioLogueado;
import epicfitpc.vista.componentes.HistoricoItemPanel;

public class PanelHistorico extends JPanel {

	private static final long serialVersionUID = 2651779404513169891L;
	private JPanel panelHInterior;
	private ArrayList<Historico> historicos = null;
	private Usuario usuario = null;
	private static final int PANELES_NECESARIOS = 4;

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
		panelHInterior.setLayout(new GridLayout(0, 1, 5, 5));

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
			emptyPanel.setBackground(Color.WHITE);
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

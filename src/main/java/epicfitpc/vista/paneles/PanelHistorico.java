package epicfitpc.vista.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import epicfitpc.modelo.Historico;
import epicfitpc.utils.Estilos;
import epicfitpc.vista.componentes.HistoricoItemPanel;

/**
 * Panel que muestra el listado de históricos del usuario logueado.
 */
public class PanelHistorico extends JPanel {

	private static final long serialVersionUID = 2651779404513169891L;
	private JPanel panelHInterior;
	private ArrayList<Historico> historicos = null;
	private static final int PANELES_NECESARIOS = 4;

	/**
	 * Constructor que inicializa el panel y recibe el listado de workouts.
	 * 
	 * @param workouts ArrayList de Workouts
	 */
	public PanelHistorico(ArrayList<Historico> historicos) {
		this.historicos = historicos;
		initialize();
	}

	/**
	 * Inicializa los componentes del panel.
	 */
	private void initialize() {
		setLayout(new BorderLayout(0, 0));

		panelHInterior = new JPanel();
		panelHInterior.setBackground(Color.WHITE);
		panelHInterior.setLayout(new GridLayout(0, 1, 10, 10));
		panelHInterior.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelHInterior.setBackground(Estilos.BACKGROUND);

		JScrollPane scrollPane = new JScrollPane(panelHInterior);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane);

		agregarPanelesHistoricos();
	}

	/**
	 * Añade los paneles de cada histórico al grid. Se añaden paneles vacíos en caso
	 * de que no haya suficientes para mantener las proporciones.
	 * 
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

			generarPanelesVacios(numeroDePaneles, panelesNecesarios, panelHInterior);
		} else {
			JLabel lblNoHistoricos = new JLabel(
					"<html><p><b>No hay registros.</b></p><p style=\"color: black;\">Empieza a realizar workouts para añadir históricos.</p></html>");
			lblNoHistoricos.setHorizontalAlignment(SwingConstants.CENTER);
			lblNoHistoricos.setFont(new Font("Noto Sans", Font.PLAIN, 20));
			lblNoHistoricos.setForeground(Estilos.PRIMARY_DARK);
			panelHInterior.add(lblNoHistoricos);
		}

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
			emptyPanel.setBackground(Estilos.BACKGROUND);
			panel.add(emptyPanel);
		}
	}

}

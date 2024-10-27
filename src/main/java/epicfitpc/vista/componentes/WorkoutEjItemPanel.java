package epicfitpc.vista.componentes;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import epicfitpc.modelo.Ejercicio;
import epicfitpc.utils.DateUtils;
import epicfitpc.utils.Estilos;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;

public class WorkoutEjItemPanel extends JPanel {

	private static final long serialVersionUID = 8329219010128277587L;
	private Ejercicio ejercicio = null;

	public WorkoutEjItemPanel(Ejercicio ejercicio) {
		this.ejercicio = ejercicio;
		initialize();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Establecer propiedades para dibujar
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Color de fondo
		g2d.setColor(getBackground());
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Fondo redondeado
	}

	private void initialize() {
		setLayout(new GridLayout(4, 1, 0, 0));
		setBorder(new EmptyBorder(20, 20, 20, 20));
		setOpaque(false);
		setBackground(Estilos.CARD_BACKGROUND);

		JLabelTitle lblNombre = new JLabelTitle(ejercicio.getNombre());
		JLabelText lblSeries = new JLabelText("Series: " + ejercicio.getSeries());

		// Obtener el tiempo en segundos
		int tiempoEnSegundos = ejercicio.getTiempoSerie();
;
		String tiempoFormateado = DateUtils.formatearTiempo(tiempoEnSegundos);
		JLabelText lblTiempo = new JLabelText("Tiempo por serie: " + tiempoFormateado);

		JLabelText lblTiempoDescanso = new JLabelText("Tiempo descanso: " + ejercicio.getDescanso() + "s");

		add(lblNombre);
		add(lblSeries);
		add(lblTiempo);
		add(lblTiempoDescanso);
	}
}

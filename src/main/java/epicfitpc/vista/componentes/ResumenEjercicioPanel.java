package epicfitpc.vista.componentes;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import epicfitpc.modelo.TiempoEjercicio;
import epicfitpc.utils.DateUtils;
import epicfitpc.utils.Estilos;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;

public class ResumenEjercicioPanel extends JPanel {

	private static final long serialVersionUID = 8329219010128277587L;
	private TiempoEjercicio tiempoEjercicio = null;

	public ResumenEjercicioPanel(TiempoEjercicio tiempoEjercicio) {
		this.tiempoEjercicio = tiempoEjercicio;
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
		setLayout(new GridLayout(0, 1, 0, 0));
		setBorder(new EmptyBorder(20, 20, 20, 20));
		setOpaque(false);
		setBackground(Estilos.CARD_BACKGROUND);

		JLabelTitle lblNombre = new JLabelTitle(tiempoEjercicio.getEjercicio().getOrden() + ". " + tiempoEjercicio.getEjercicio().getNombre());
		JLabel lblSeries = new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Series</b>: " + tiempoEjercicio.getEjercicio().getSeries() + "</html>");
		JLabel lblRepeticiones = new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Repeticiones</b>: " + tiempoEjercicio.getEjercicio().getRepeticiones() + "</html>");

		// Obtener el tiempo en segundos
		int tiempoEnSegundos = tiempoEjercicio.getEjercicio().getTiempoSerie();

		String tiempoFormateado = DateUtils.formatearTiempo(tiempoEnSegundos);
		JLabel lblTiempo = new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Tiempo por serie</b>: " + tiempoFormateado + "</html>");

		tiempoEnSegundos = tiempoEjercicio.getTiempo();
		tiempoFormateado = DateUtils.formatearTiempo(tiempoEnSegundos);
		
		JLabel lblTiempoDescanso = new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Tiempo descanso</b>: " + tiempoEjercicio.getEjercicio().getDescanso() + " s" + "</html>");
		JLabel lblTiempoReal = new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Tu tiempo total</b>: " + tiempoFormateado + "</html>");
		
		
		add(lblNombre);
		add(lblSeries);
		add(lblRepeticiones);
		add(lblTiempo);
		add(lblTiempoDescanso);
		add(lblTiempoReal);
	}
}

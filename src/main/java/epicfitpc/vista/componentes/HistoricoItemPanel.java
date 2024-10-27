package epicfitpc.vista.componentes;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.cloud.Timestamp;

import epicfitpc.modelo.Historico;
import epicfitpc.utils.DateUtils;
import epicfitpc.utils.Estilos;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;

public class HistoricoItemPanel extends JPanel {

	private static final long serialVersionUID = 8329219010128277587L;
	private Historico historico = null;

	public HistoricoItemPanel(Historico historico) {
		this.historico = historico;
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
		setLayout(new GridLayout(6, 1, 0, 0));
		setBorder(new EmptyBorder(20, 20, 20, 20));
		setOpaque(false);
		setBackground(Estilos.CARD_BACKGROUND);

		JLabelTitle lblNombre = new JLabelTitle(historico.getWorkoutObj().getNombre());
		JLabelText lblNivel = new JLabelText("Nivel: " + historico.getWorkoutObj().getNivel());
		
		int tiempoPrevEnSegundos = historico.getWorkoutObj().getTiempo();
		String tiempoPrevFormateado = DateUtils.formatearTiempo(tiempoPrevEnSegundos);
		JLabelText lblTiempo = new JLabelText("Tiempo previsto: " + tiempoPrevFormateado);
		
		int tiempoEnSegundos = historico.getTiempo();
		String tiempoFormateado = DateUtils.formatearTiempo(tiempoEnSegundos);
		JLabelText lblTiempoReal = new JLabelText("Tiempo real: " + tiempoFormateado);

		Timestamp fecha = historico.getFecha();
		String fechaFormateada = DateUtils.formatearTimestamp(fecha);
		JLabelText lblFecha = new JLabelText(fechaFormateada);
		
		JLabelText lblPorcentaje = new JLabelText(historico.getPorcentaje() + "%");
		
		add(lblNombre);
		add(lblNivel);
		add(lblTiempo);
		add(lblTiempoReal);
		add(lblFecha);		
		add(lblPorcentaje);
	}
}

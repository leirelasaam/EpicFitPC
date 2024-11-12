package epicfitpc.vista.componentes;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.cloud.Timestamp;

import epicfitpc.modelo.Historico;
import epicfitpc.utils.DateUtils;
import epicfitpc.utils.Estilos;
import epicfitpc.utils.ImageUtils;
import epicfitpc.utils.WindowUtils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;

/**
 * Panel para cada histórico, donde se muestra la información relativa.
 */
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
		setLayout(new GridBagLayout());
		setBorder(new EmptyBorder(20, 20, 20, 20));
		setOpaque(false);
		setBackground(Estilos.CARD_BACKGROUND);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(2, 2, 2, 2);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 6;
		gbc.weightx = 0.25;
		gbc.weighty = 1.0;

		String tipo = historico.getWorkoutObj().getTipo();
		String ruta = ImageUtils.obtenerRutaImagen(tipo);
		ImageIcon img = WindowUtils.cargarImagen(ruta, 80, 80);
		JLabel labelImg = new JLabel(img);
		add(labelImg, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.weightx = 0.75;

		JLabelTitle lblNombre = new JLabelTitle(historico.getWorkoutObj().getNombre());
		JLabel lblNivel = new JLabel("<html><b>Nivel</b>: " + historico.getWorkoutObj().getNivel() + "</html>");

		int tiempoPrevEnSegundos = historico.getWorkoutObj().getTiempo();
		String tiempoPrevFormateado = DateUtils.formatearTiempo(tiempoPrevEnSegundos);
		JLabel lblTiempo = new JLabel("<html><b>Tiempo previsto</b>: " + tiempoPrevFormateado + "</html>");

		int tiempoEnSegundos = historico.getTiempo();
		String tiempoFormateado = DateUtils.formatearTiempo(tiempoEnSegundos);
		JLabel lblTiempoReal = new JLabel("<html><b>Tiempo real</b>: " + tiempoFormateado + "</html>");

		Timestamp fecha = historico.getFecha();
		String fechaFormateada = DateUtils.formatearTimestamp(fecha);
		JLabel lblFecha = new JLabel("<html><b>Fecha</b>: " + fechaFormateada + "</html>");

		JLabel lblPorcentaje = new JLabel(
				"<html><b>Porcentaje de completado</b>: " + historico.getPorcentaje() + "%</html>");

		add(lblNombre, gbc);
		gbc.gridy++;
		add(lblNivel, gbc);
		gbc.gridy++;
		add(lblTiempoReal, gbc);
		gbc.gridx = 2;
		add(lblTiempo, gbc);
		gbc.gridx = 1;
		gbc.gridy++;
		add(lblFecha, gbc);
		gbc.gridx = 2;
		add(lblPorcentaje, gbc);
	}
}

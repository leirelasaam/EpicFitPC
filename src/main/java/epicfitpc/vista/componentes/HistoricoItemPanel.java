package epicfitpc.vista.componentes;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.cloud.Timestamp;

import epicfitpc.modelo.Historico;
import epicfitpc.utils.DateUtils;

import java.awt.Color;
import java.awt.GridLayout;

public class HistoricoItemPanel extends JPanel {

	private static final long serialVersionUID = 8329219010128277587L;
	private Historico historico = null;

	public HistoricoItemPanel(Historico historico) {
		this.historico = historico;
		initialize();
	}

	private void initialize() {
		setLayout(new GridLayout(6, 1, 0, 0));
		setBorder(new EmptyBorder(20, 20, 20, 20));
		setBackground(Color.LIGHT_GRAY);

		JLabel lblNombre = new JLabel("Nombre: " + historico.getWorkoutObj().getNombre());
		JLabel lblNivel = new JLabel("Nivel: " + historico.getWorkoutObj().getNivel());
		
		int tiempoPrevEnSegundos = historico.getWorkoutObj().getTiempo();
		String tiempoPrevFormateado = DateUtils.formatearTiempo(tiempoPrevEnSegundos);
		JLabel lblTiempo = new JLabel("Tiempo previsto: " + tiempoPrevFormateado);
		
		int tiempoEnSegundos = historico.getTiempo();
		String tiempoFormateado = DateUtils.formatearTiempo(tiempoEnSegundos);
		JLabel lblTiempoReal = new JLabel("Tiempo real: " + tiempoFormateado);

		Timestamp fecha = historico.getFecha();
		String fechaFormateada = DateUtils.formatearTimestamp(fecha);
		JLabel lblFecha = new JLabel("Fecha: " + fechaFormateada);
		
		JLabel lblPorcentaje = new JLabel("Porcentaje: " + historico.getPorcentaje());
		
		add(lblNombre);
		add(lblNivel);
		add(lblTiempo);
		add(lblTiempoReal);
		add(lblFecha);		
		add(lblPorcentaje);
	}
}

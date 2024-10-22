package epicfitpc.vista.componentes;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import epicfitpc.modelo.pojos.Ejercicio;
import epicfitpc.modelo.pojos.Workout;

import java.awt.Color;
import java.awt.GridLayout;

public class WorkoutEjItemPanel extends JPanel {

	private static final long serialVersionUID = 8329219010128277587L;
	private Ejercicio ejercicio = null;
	
	public WorkoutEjItemPanel(Ejercicio ejercicio) {
		this.ejercicio = ejercicio;
		initialize();
	}

	private void initialize() {
		setLayout(new GridLayout(4, 1, 0, 0));
		setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.LIGHT_GRAY);

		JLabel lblNombre = new JLabel("Ejercicio: " + ejercicio.getNombre());
		JLabel lblSeries = new JLabel("Series: " + ejercicio.getSeries());
		
		// Obtener el tiempo en segundos
		int tiempoEnSegundos = ejercicio.getTiempoSerie();

		// Calcular horas, minutos y segundos
		int horas = tiempoEnSegundos / 3600;
		int minutos = (tiempoEnSegundos % 3600) / 60;
		int segundos = tiempoEnSegundos % 60;
		
		String tiempoFormateado = "";
		if (horas > 0) {
			tiempoFormateado += horas + "h ";
		} 
		tiempoFormateado += minutos + "min " + segundos + "s";
		JLabel lblTiempo = new JLabel("Tiempo por serie: " + tiempoFormateado);
		
		JLabel lblTiempoDescanso = new JLabel("Tiempo descanso: " + ejercicio.getDescanso() + "s");

		add(lblNombre);
		add(lblSeries);
		add(lblTiempo);
		add(lblTiempoDescanso);
	}
}

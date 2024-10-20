package epicfitpc.vista.componentes;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import epicfitpc.modelo.pojos.Workout;

import java.awt.Color;
import java.awt.GridLayout;

public class ItemPanel extends JPanel {

	private static final long serialVersionUID = 8329219010128277587L;
	private Workout workout = null;
	
	public ItemPanel(Workout workout) {
		this.workout = workout;
		initialize();
	}

	private void initialize() {
		setLayout(new GridLayout(4, 1, 0, 0));
		setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.ORANGE);

		JLabel lblNombre = new JLabel("Nombre: " + workout.getNombre());
		JLabel lblNivel = new JLabel("Nivel: " + workout.getNivel());
		JLabel lblEjercicios = new JLabel("Ejercicios: " + workout.getEjercicios().size());
		
		// Obtener el tiempo en segundos
		int tiempoEnSegundos = workout.getTiempo();

		// Calcular horas, minutos y segundos
		int horas = tiempoEnSegundos / 3600;
		int minutos = (tiempoEnSegundos % 3600) / 60;
		int segundos = tiempoEnSegundos % 60;
		
		String tiempoFormateado = "";
		if (horas > 0) {
			tiempoFormateado += horas + "h ";
		} 
		tiempoFormateado += minutos + "min " + segundos + "s";
		JLabel lblTiempo = new JLabel("Tiempo: " + tiempoFormateado);
		
		add(lblNombre);
		add(lblTiempo);
		add(lblNivel);
		add(lblEjercicios);
	}
}

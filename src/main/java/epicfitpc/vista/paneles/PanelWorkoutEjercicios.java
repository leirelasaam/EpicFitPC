package epicfitpc.vista.paneles;


import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import epicfitpc.modelo.pojos.Ejercicio;
import epicfitpc.modelo.pojos.Workout;

public class PanelWorkoutEjercicios extends JPanel {

	private static final long serialVersionUID = 2651779404513169891L;
	private Workout workout = null;

	/**
	 * Constructor que inicializa el panel y recibe el listado de workouts.
	 * 
	 * @param workouts ArrayList de Workouts
	 */
	public PanelWorkoutEjercicios(Workout workout, ArrayList<Ejercicio> ejercicios) {
		this.workout = workout;
		initialize();
	}

	/**
	 * Inicializa los componentes del panel.
	 */
	private void initialize() {
		setLayout(new GridLayout(4, 1, 0, 0));
		setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.LIGHT_GRAY);

		JLabel lblNombre = new JLabel("Nombre: " + workout.getNombre());
		JLabel lblNivel = new JLabel("Nivel: " + workout.getNivel());
		
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
		JLabel lblEjercicios = new JLabel("Ejercicios: " + workout.getEjercicios().size());
		
		add(lblNombre);
		add(lblTiempo);
		add(lblNivel);
		add(lblEjercicios);
	}
}

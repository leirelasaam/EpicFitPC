package epicfitpc.vista.paneles;

import javax.swing.JLabel;
import javax.swing.JPanel;

import epicfitpc.modelo.pojos.Workout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

public class ItemPanel extends JPanel {

	private static final long serialVersionUID = 8329219010128277587L;
	private Workout workout = null;
	
	public ItemPanel(Workout workout) {
		this.workout = workout;
		initialize();
	}

	private void initialize() {
		setLayout(new GridLayout(3, 1, 5, 5));

		JLabel lblNombre = new JLabel("Nombre: " + workout.getNombre());
		JLabel lblNivel = new JLabel("Nivel: " + workout.getNivel());
		JLabel lblTiempo = new JLabel("Tiempo: " + (workout.getTiempo() / 60) + " min");

		add(lblNombre);
		add(lblNivel);
		add(lblTiempo);
		
        setBackground(Color.ORANGE);
	}
}

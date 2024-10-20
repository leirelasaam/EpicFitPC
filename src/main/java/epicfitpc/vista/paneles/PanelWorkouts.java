package epicfitpc.vista.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import epicfitpc.modelo.pojos.Workout;
import epicfitpc.vista.componentes.ItemPanel;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class PanelWorkouts extends JPanel {

	private static final long serialVersionUID = 2651779404513169891L;
	private JPanel panelWInterior;
	private JComboBox<String> comboBox;
	private ArrayList<Workout> workouts = null;
	private static final String NIVELES_ALL = "-- Filtrar por nivel --";
	private static final String NIVELES_NONE = "-- No hay workouts disponibles --";

	public PanelWorkouts(ArrayList<Workout> workouts) {
		this.workouts = workouts;
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		comboBox = new JComboBox<String>();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				filtrarPorNivel();
			}
		});
		add(comboBox, BorderLayout.NORTH);

		panelWInterior = new JPanel();
		panelWInterior.setBackground(Color.BLACK);
		panelWInterior.setLayout(new GridLayout(0, 3, 5, 5));
		add(panelWInterior);

		JScrollPane scrollPane = new JScrollPane(panelWInterior);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane);

		agregarPanelesWorkouts(-1);
		cargarCombo();
	}

	private void agregarPanelesWorkouts(int nivel) {
		int numeroDeTarjetas = 0;
		// Columnas * Filas
		int espaciosNecesarios = 3 * 4;

		// Items reales para los workouts
		if (workouts != null) {
			numeroDeTarjetas = workouts.size();
			for (Workout workout : workouts) {
				if (nivel == -1) {
					ItemPanel card = new ItemPanel(workout);
					panelWInterior.add(card);
				} else {
					if (workout.getNivel() == nivel) {
						ItemPanel card = new ItemPanel(workout);
						panelWInterior.add(card);
					}
				}
			}
		}

		// Paneles vacíos para que se ajusten bien los tamaños
		for (int i = numeroDeTarjetas; i < espaciosNecesarios; i++) {
			JPanel emptyPanel = new JPanel();
			emptyPanel.setBackground(Color.BLACK);
			panelWInterior.add(emptyPanel);
		}
	}

	private void cargarCombo() {
		comboBox.removeAllItems();
		// Set se utiliza para evitar duplicados
		Set<String> niveles = new HashSet<>();

		if (workouts != null) {
			for (Workout workout : workouts) {
				niveles.add("" + workout.getNivel());
			}

			comboBox.addItem(NIVELES_ALL);

			for (String nivel : niveles) {
				comboBox.addItem(nivel);
			}
		} else {
			comboBox.addItem(NIVELES_NONE);
		}
	}

	private void filtrarPorNivel() {
		if (workouts != null) {
			String nivelSeleccionado = comboBox.getSelectedItem().toString();
			panelWInterior.removeAll();
			if (nivelSeleccionado.equals(NIVELES_ALL)) {
				agregarPanelesWorkouts(-1);
			} else {
				int nivel = Integer.parseInt(nivelSeleccionado);
				agregarPanelesWorkouts(nivel);
			}
			panelWInterior.repaint();
		}
	}

}

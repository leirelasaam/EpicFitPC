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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemEvent;

public class PanelWorkouts extends JPanel {

	private static final long serialVersionUID = 2651779404513169891L;
	private JPanel panelWInterior;
	private JComboBox<String> comboBox;
	private ArrayList<Workout> workouts = null;
	private static final String NIVELES_ALL = "-- Filtrar por nivel --";
	private static final String NIVELES_NONE = "-- No hay workouts disponibles --";

	/**
	 * Constructor que inicializa el panel y recibe el listado de workouts.
	 * 
	 * @param workouts ArrayList de Workouts
	 */
	public PanelWorkouts(ArrayList<Workout> workouts) {
		this.workouts = workouts;
		initialize();
	}

	/**
	 * Inicializa los componentes del panel.
	 */
	private void initialize() {
		setLayout(new GridLayout(0, 2));
		
		JPanel panelContenidoWorkout = new JPanel();
		panelContenidoWorkout.setLayout(new BorderLayout(0, 0));
		add(panelContenidoWorkout);
		
		comboBox = new JComboBox<String>();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				filtrarPorNivel();
			}
		});
		panelContenidoWorkout.add(comboBox, BorderLayout.NORTH);

		panelWInterior = new JPanel();
		panelWInterior.setBackground(Color.WHITE);
		panelWInterior.setLayout(new GridLayout(0, 1, 5, 5));
		panelContenidoWorkout.add(panelWInterior);
		
		JPanel panelEj= new JPanel();
		panelEj.setBackground(Color.WHITE);
		add(panelEj);

		JScrollPane scrollPane = new JScrollPane(panelWInterior);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelContenidoWorkout.add(scrollPane);

		agregarPanelesWorkouts(-1);
		cargarCombo();
	}

	/**
	 * Añade los paneles de cada workout al grid. Se añaden paneles vacíos en caso
	 * de que no haya suficientes para mantener las proporciones.
	 * 
	 * @param nivel Número entero que es el nivel por el cual se filtra. -1 si no
	 *              hay filtro.
	 */
	private void agregarPanelesWorkouts(int nivel) {
		int numeroDeTarjetas = 0;
		int espaciosNecesarios = 5;

		// Items reales para los workouts
		if (workouts != null) {
			for (Workout workout : workouts) {
				if (nivel == -1 || workout.getNivel() == nivel) {
					numeroDeTarjetas++;
					ItemPanel itemPanel = new ItemPanel(workout);
					panelWInterior.add(itemPanel);
					itemPanel.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							
						}
					});
				}
			}
		}

		// Paneles vacíos para que se ajusten bien los tamaños
		for (int i = numeroDeTarjetas; i < espaciosNecesarios; i++) {
			JPanel emptyPanel = new JPanel();
			emptyPanel.setBackground(Color.WHITE);
			panelWInterior.add(emptyPanel);
		}
	}

	/**
	 * Añade los posibles niveles al combo, comprobando los que hay en la base de
	 * datos. Antes de añadir nada, resetea el listado cargado, eliminando los
	 * items.
	 */
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

	/**
	 * Función que se activa cuando se cambia el valor seleccionado del combo.
	 * Elimina todos los elementos y vuelve a cargarlos.
	 */
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

			panelWInterior.revalidate();
			panelWInterior.repaint();
		}
	}

}

package epicfitpc.vista.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.google.cloud.firestore.Firestore;

import epicfitpc.bbdd.GestorDeWorkouts;
import epicfitpc.modelo.Ejercicio;
import epicfitpc.modelo.Usuario;
import epicfitpc.modelo.Workout;
import epicfitpc.utils.Conexion;
import epicfitpc.utils.UsuarioLogueado;
import epicfitpc.vista.componentes.WorkoutEjItemPanel;
import epicfitpc.vista.componentes.WorkoutItemPanel;

import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemEvent;

public class PanelWorkouts extends JPanel {

	private static final long serialVersionUID = 2651779404513169891L;
	private JPanel panelWInterior;
	private JPanel panelEj;
	private JLabel labelWorkout;
	private JComboBox<String> comboBox;
	private ArrayList<Workout> workouts = null;
	private Usuario usuario = null;
	
	private static final String NIVELES_ALL = "-- Filtrar por nivel --";
	private static final String NIVELES_NONE = "-- No hay workouts disponibles --";
	private static final String SELECCIONA_WORKOUT = "Selecciona un workout";
	private static final int PANELES_NECESARIOS = 5;

	/**
	 * Constructor que inicializa el panel y recibe el listado de workouts.
	 * 
	 * @param workouts ArrayList de Workouts
	 */
	public PanelWorkouts() {
		this.usuario =  UsuarioLogueado.getInstance().getUsuario();
		this.workouts = obtenerWorkouts();
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
		comboBox.setPreferredSize(new Dimension(comboBox.getPreferredSize().width, 30));
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				filtrarPorNivel();
			}
		});
		panelContenidoWorkout.add(comboBox, BorderLayout.NORTH);

		panelWInterior = new JPanel();
		panelWInterior.setBackground(Color.WHITE);
		panelWInterior.setLayout(new GridLayout(0, 1, 5, 5));

		JPanel panelEjerciciosW = new JPanel();
		panelEjerciciosW.setLayout(new BorderLayout(0, 0));
		panelEjerciciosW.setBackground(Color.WHITE);
		add(panelEjerciciosW);

		labelWorkout = new JLabel(SELECCIONA_WORKOUT);
		labelWorkout.setPreferredSize(new Dimension(labelWorkout.getPreferredSize().width, 30));
		labelWorkout.setHorizontalAlignment(JLabel.CENTER);
		panelEjerciciosW.add(labelWorkout, BorderLayout.NORTH);

		panelEj = new JPanel();
		panelEj.setLayout(new GridLayout(0, 1, 5, 5));
		panelEj.setBackground(Color.WHITE);

		JScrollPane scrollPane = new JScrollPane(panelWInterior);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelContenidoWorkout.add(scrollPane);

		JScrollPane scrollPaneEj = new JScrollPane(panelEj);
		scrollPaneEj.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelEjerciciosW.add(scrollPaneEj);

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
		int numeroDePaneles = 0;
		int panelesNecesarios = PANELES_NECESARIOS;

		// Items reales para los workouts
		if (workouts != null) {
			for (Workout workout : workouts) {
				if (nivel == -1 || workout.getNivel() == nivel) {
					numeroDePaneles++;
					WorkoutItemPanel itemPanel = new WorkoutItemPanel(workout);
					panelWInterior.add(itemPanel);
					itemPanel.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							agregarInfoEjercicios(workout);
						}
					});
				}
			}
		}

		generarPanelesVacios(numeroDePaneles, panelesNecesarios, panelWInterior);

	}

	/**
	 * Genera paneles vacíos.
	 * 
	 * @param numeroDePaneles   Paneles generados hasta el momento.
	 * @param panelesNecesarios Paneles necesarios en total.
	 * @param panel             Panel padre al que se añaden el resto de paneles.
	 */
	private void generarPanelesVacios(int numeroDePaneles, int panelesNecesarios, JPanel panel) {
		// Paneles vacíos para que se ajusten bien los tamaños
		for (int i = numeroDePaneles; i < panelesNecesarios; i++) {
			JPanel emptyPanel = new JPanel();
			emptyPanel.setBackground(Color.WHITE);
			panel.add(emptyPanel);
		}
	}

	/**
	 * Genera el contenido en el panel que muestra la información de los ejercicios
	 * que componen el workout.
	 * 
	 * @param workout Workout seleccionado.
	 */
	private void agregarInfoEjercicios(Workout workout) {
		int numeroDePaneles = 0;
		int panelesNecesarios = PANELES_NECESARIOS;

		labelWorkout.setText(workout.getNombre());

		panelEj.removeAll();

		ArrayList<Ejercicio> ejercicios = workout.getEjerciciosArray();

		if (null != ejercicios) {
			for (Ejercicio ejercicio : ejercicios) {
				numeroDePaneles++;
				WorkoutEjItemPanel itemPanel = new WorkoutEjItemPanel(ejercicio);
				panelEj.add(itemPanel);
			}
		}

		generarPanelesVacios(numeroDePaneles, panelesNecesarios, panelEj);

		panelEj.revalidate();
		panelEj.repaint();
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
	
	private ArrayList<Workout> obtenerWorkouts() {
		ArrayList<Workout> workouts = null;
		Firestore db;
		try {
			db = Conexion.getInstance().getConexion();
			GestorDeWorkouts gdw = new GestorDeWorkouts(db);
			workouts = gdw.obtenerWorkoutsPorNivelUsuario(usuario.getNivel());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return workouts;

	}

}

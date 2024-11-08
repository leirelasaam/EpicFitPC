package epicfitpc.vista.paneles;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import com.google.cloud.firestore.Firestore;

import epicfitpc.bbdd.GestorDeWorkouts;
import epicfitpc.ficheros.GestorDeFicherosBinarios;
import epicfitpc.modelo.Ejercicio;
import epicfitpc.modelo.Usuario;
import epicfitpc.modelo.Workout;
import epicfitpc.utils.Estilos;
import epicfitpc.utils.GestorDeConexiones;
import epicfitpc.utils.Rutas;
import epicfitpc.utils.Conexion;
import epicfitpc.utils.UsuarioLogueado;
import epicfitpc.utils.WindowUtils;
import epicfitpc.vista.componentes.JButtonPrimary;
import epicfitpc.vista.componentes.WorkoutEjItemPanel;
import epicfitpc.vista.componentes.WorkoutItemPanel;

import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelWorkouts extends JPanel {

	private static final long serialVersionUID = 2651779404513169891L;
	private JPanel panelWInterior;
	private JPanel panelEj;
	private JLabel labelWorkout;
	private JComboBox<String> comboBox;
	private ArrayList<Workout> workouts = null;
	private Usuario usuario = null;
	private JButtonPrimary playButton;
	private PanelMenu panelMenu = null;
	private Workout workoutSeleccionado = null;

	private static final String NIVELES_ALL = "-- Filtrar por nivel --";
	private static final String NIVELES_NONE = "-- No hay workouts disponibles --";
	private static final String SELECCIONA_WORKOUT = "Selecciona un workout";
	private static final int PANELES_NECESARIOS = 4;
	private static final String FICHERO_WORKOUTS = Rutas.BACKUP_WORKOUTS;

	/**
	 * Constructor que inicializa el panel y recibe el listado de workouts.
	 * 
	 * @param workouts ArrayList de Workouts
	 */
	public PanelWorkouts(PanelMenu panelMenu) {
		this.usuario = UsuarioLogueado.getInstance().getUsuario();
		this.workouts = obtenerWorkouts();
		this.panelMenu = panelMenu;
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
		comboBox.setBackground(Estilos.PRIMARY);
		comboBox.setForeground(Estilos.WHITE);
		comboBox.setPreferredSize(new Dimension(comboBox.getPreferredSize().width, 30));
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				filtrarPorNivel();
			}
		});
		panelContenidoWorkout.add(comboBox, BorderLayout.NORTH);
		comboBox.setRenderer(new ComboBoxRenderer());

		panelWInterior = new JPanel();
		panelWInterior.setBackground(Estilos.DARK_BACKGROUND);
		panelWInterior.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelWInterior.setLayout(new GridLayout(0, 1, 10, 10));

		JPanel panelEjerciciosW = new JPanel();
		panelEjerciciosW.setLayout(new BorderLayout(0, 0));
		panelEjerciciosW.setBackground(Estilos.DARK_BACKGROUND);
		add(panelEjerciciosW);

		labelWorkout = new JLabel(SELECCIONA_WORKOUT);
		labelWorkout.setFont(Estilos.FONT_SMALL_BOLD);
		labelWorkout.setBackground(Estilos.WHITE);
		labelWorkout.setOpaque(true);
		labelWorkout.setPreferredSize(new Dimension(labelWorkout.getPreferredSize().width, 30));
		labelWorkout.setHorizontalAlignment(JLabel.CENTER);
		panelEjerciciosW.add(labelWorkout, BorderLayout.NORTH);

		panelEj = new JPanel();
		panelEj.setLayout(new GridLayout(0, 1, 10, 10));
		panelEj.setBackground(Estilos.DARK_BACKGROUND);
		panelEj.setBorder(new EmptyBorder(10, 10, 10, 10));

		playButton = new JButtonPrimary("Play");
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciarWorkout();
			}
		});
		panelEjerciciosW.add(playButton, BorderLayout.SOUTH);
		playButton.setVisible(false);

		JScrollPane scrollPane = new JScrollPane(panelWInterior);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelContenidoWorkout.add(scrollPane);

		JScrollPane scrollPaneEj = new JScrollPane(panelEj);
		scrollPaneEj.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelEjerciciosW.add(scrollPaneEj, BorderLayout.CENTER);

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
							workoutSeleccionado = workout;
							agregarInfoEjercicios();
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
			emptyPanel.setBackground(Estilos.DARK_BACKGROUND);
			panel.add(emptyPanel);
		}
	}

	/**
	 * Genera el contenido en el panel que muestra la información de los ejercicios
	 * que componen el workout.
	 * 
	 * @param workout Workout seleccionado.
	 */
	private void agregarInfoEjercicios() {
		if (workoutSeleccionado == null)
			return;

		int numeroDePaneles = 0;
		int panelesNecesarios = PANELES_NECESARIOS - 1;

		labelWorkout.setText(workoutSeleccionado.getNombre());
		playButton.setVisible(true);

		panelEj.removeAll();

		ArrayList<Ejercicio> ejercicios = workoutSeleccionado.getEjercicios();

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
		System.out.println("Obteniendo workouts en Workout");
		ArrayList<Workout> workouts = null;
		Firestore db;
		boolean hayConexion = GestorDeConexiones.getInstance().hayConexion();
		
		if (hayConexion) {
			try {
				db = Conexion.getInstance().getConexion();
				GestorDeWorkouts gdw = new GestorDeWorkouts(db);
				workouts = gdw.obtenerWorkoutsPorNivelUsuario(usuario.getNivel());
			} catch (Exception e) {
				WindowUtils.errorPane("Error en la carga desde la base de datos.", "Error en la base de datos");
			}
		} else {
			GestorDeFicherosBinarios<Workout> gdfb = new GestorDeFicherosBinarios<Workout>(FICHERO_WORKOUTS);
			try {
				workouts = gdfb.leer();
			} catch (FileNotFoundException e) {
				WindowUtils.errorPane("No se ha encontrado el fichero de carga para los workouts.", "Error en la carga");
			} catch (ClassNotFoundException e) {
				WindowUtils.errorPane("No se ha encontrado la clase Workout.", "Error en la clase");
			} catch (IOException e) {
				WindowUtils.errorPane("No se ha podido realizar la lectura del fichero de carga.", "Error en la lectura");
			}
		}

		return workouts;

	}

	private void iniciarWorkout() {
		PanelEjercicio panelEjercicio = new PanelEjercicio(workoutSeleccionado, panelMenu);
		panelMenu.agregarNuevoTab("Ejercicio", panelEjercicio);
	}

	private class ComboBoxRenderer extends JLabel implements ListCellRenderer<String> {
		private static final long serialVersionUID = -3995483244577557908L;

		public ComboBoxRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
				boolean isSelected, boolean cellHasFocus) {
			setText(value);
			if (isSelected) {
				setBackground(Estilos.PRIMARY_DARK);
				setForeground(Estilos.WHITE);
			} else {
				setBackground(Estilos.PRIMARY);
				setForeground(Estilos.WHITE);
			}
			return this;
		}
	}

}

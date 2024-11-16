package epicfitpc.vista.paneles;

import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.google.cloud.firestore.Firestore;

import epicfitpc.bbdd.GestorDeHistoricos;
import epicfitpc.bbdd.GestorDeWorkouts;
import epicfitpc.ficheros.GestorDeFicherosBinarios;
import epicfitpc.modelo.Historico;
import epicfitpc.modelo.Usuario;
import epicfitpc.modelo.Workout;
import epicfitpc.utils.Conexion;
import epicfitpc.utils.Estilos;
import epicfitpc.utils.GestorDeConexiones;
import epicfitpc.utils.Rutas;
import epicfitpc.utils.UsuarioLogueado;
import epicfitpc.utils.WindowUtils;

/**
 * Panel del menú principal que contiene pestañas para acceder a los diferentes
 * apartados de la aplicación, tras el inicio de sesión.
 */
public class PanelMenu extends JPanel {

	private static final long serialVersionUID = 6067181926807089944L;
	private JTabbedPane tabbedPane;
	private Usuario usuario = null;
	private ArrayList<Workout> workouts = null;
	private ArrayList<Historico> historicos = null;
	private static final String FICHERO_WORKOUTS = Rutas.BACKUP_WORKOUTS;

	public PanelMenu() {
		this.usuario = UsuarioLogueado.getInstance().getUsuario();
		this.workouts = obtenerWorkouts();
		this.historicos = obtenerHistoricos();
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout());
		setBounds(100, 100, 1200, 750);
		setBackground(Estilos.WHITE);

		tabbedPane = new JTabbedPane();

		PanelWorkouts panelWorkouts = new PanelWorkouts(this, workouts);
		PanelHistorico panelHistorico = new PanelHistorico(historicos);
		PanelPerfil panelPerfil = new PanelPerfil(usuario);

		tabbedPane.addTab("Workouts", panelWorkouts);
		tabbedPane.addTab("Histórico", panelHistorico);
		tabbedPane.addTab("Perfil", panelPerfil);

		add(tabbedPane);
	}

	public void agregarNuevoTab(String titulo, JPanel nuevoPanel) {
		tabbedPane.addTab(titulo, nuevoPanel);
		tabbedPane.setSelectedComponent(nuevoPanel);
	}

	public void seleccionarPrimerTab() {
		tabbedPane.setSelectedIndex(0);
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
				WindowUtils.errorPane("No se ha encontrado el fichero de carga para los workouts.",
						"Error en la carga");
			} catch (ClassNotFoundException e) {
				WindowUtils.errorPane("No se ha encontrado la clase Workout.", "Error en la clase");
			} catch (IOException e) {
				WindowUtils.errorPane("No se ha podido realizar la lectura del fichero de carga.",
						"Error en la lectura");
			}
		}

		return workouts;

	}
	
	private ArrayList<Historico> obtenerHistoricos() {
		System.out.println("Obteniendo históricos en Histórico");
		ArrayList<Historico> historicos = null;
		Firestore db;
		boolean hayConexion = GestorDeConexiones.getInstance().hayConexion();

		if (hayConexion) {
			try {
				db = Conexion.getInstance().getConexion();
				GestorDeHistoricos gdh = new GestorDeHistoricos(db);
				historicos = gdh.obtenerTodosLosHistoricosPorUsuario(usuario, workouts);
			} catch (Exception e) {
				WindowUtils.errorPane("Error en la carga desde la base de datos.", "Error en la base de datos");
			}
		}else {
			historicos = usuario.getHistoricos();
		}

		return historicos;

	}
}

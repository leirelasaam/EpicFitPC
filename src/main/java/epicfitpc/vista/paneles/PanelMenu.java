package epicfitpc.vista.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.google.cloud.firestore.Firestore;

import epicfitpc.modelo.bbdd.GestorDeWorkouts;
import epicfitpc.modelo.pojos.Usuario;
import epicfitpc.modelo.pojos.Workout;
import epicfitpc.utils.Conexion;
import epicfitpc.utils.UsuarioLogueado;

public class PanelMenu extends JPanel {

	private static final long serialVersionUID = 6067181926807089944L;
	private Usuario usuario = null;
	private ArrayList<Workout> workouts = null;

	public PanelMenu() {
		this.usuario =  UsuarioLogueado.getInstance().getUsuario();
		obtenerWorkouts();
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout());
		setBounds(100, 100, 1200, 750);
		setBackground(Color.WHITE);

		JTabbedPane tabbedPane = new JTabbedPane();

		PanelWorkouts panelWorkouts = new PanelWorkouts(workouts);
		JPanel panelHistorico = new JPanel();
		JPanel panelPerfil = new JPanel();

		tabbedPane.addTab("Workouts", panelWorkouts);
		tabbedPane.addTab("Histórico", panelHistorico);
		tabbedPane.addTab("Perfil", panelPerfil);

		add(tabbedPane);
	}


	private void obtenerWorkouts() {
		Firestore db;
		try {
			db = Conexion.getInstance().getConexion();
			GestorDeWorkouts gdw = new GestorDeWorkouts(db);
			workouts = gdw.obtenerWorkoutsPorNivelUsuario(usuario.getNivel());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

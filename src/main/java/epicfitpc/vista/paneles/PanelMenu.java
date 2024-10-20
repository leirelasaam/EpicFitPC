package epicfitpc.vista.paneles;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import com.google.cloud.firestore.Firestore;

import epicfitpc.modelo.bbdd.GestorDeWorkouts;
import epicfitpc.modelo.pojos.Usuario;
import epicfitpc.modelo.pojos.Workout;
import epicfitpc.utils.Conexion;
import epicfitpc.vista.MainFrame;

public class PanelMenu extends JPanel {

	private static final long serialVersionUID = 6067181926807089944L;
	private MainFrame frame = null;
	private Usuario usuario = null;
	private JPanel panelWInterior = null;
	private ArrayList<Workout> workouts = null;

	public PanelMenu(MainFrame frame, Usuario usuario) {
		this.frame = frame;
		this.usuario = usuario;
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout());
		setBounds(100, 100, 1200, 750);

		JTabbedPane tabbedPane = new JTabbedPane();

		JPanel panelWorkouts = new JPanel();
		JPanel panelHistorico = new JPanel();
		JPanel panelPerfil = new JPanel();

		tabbedPane.addTab("Workouts", panelWorkouts);
		panelWorkouts.setLayout(new BorderLayout(0, 0));

		panelWInterior = new JPanel();
		panelWInterior.setBackground(Color.BLACK);
		panelWInterior.setLayout(new GridLayout(0, 3, 5, 5));
		panelWorkouts.add(panelWInterior);

		JScrollPane scrollPane = new JScrollPane(panelWInterior);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelWorkouts.add(scrollPane);

		tabbedPane.addTab("Histórico", panelHistorico);
		tabbedPane.addTab("Perfil", panelPerfil);

		if (usuario.isEsEntrenador()) {
			JPanel panelEntrenador = new JPanel();
			tabbedPane.addTab("Entrenador", panelEntrenador);
		}

		add(tabbedPane);
		
		crearItemsWorkout();
	}

	private void crearItemsWorkout() {
		Firestore db;
		try {
			db = Conexion.getConexion();
			GestorDeWorkouts gdw = new GestorDeWorkouts(db);
			workouts = gdw.obtenerTodosLosWorkouts();
			agregarTarjetas(3, 4);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
    private void agregarTarjetas(int columnas, int filasMinimas) {
        int numeroDeTarjetas = workouts.size();
        int espaciosNecesarios = columnas * filasMinimas;

        // Items reales para los workouts
        for (Workout workout : workouts) {
            ItemPanel card = new ItemPanel(workout);
            panelWInterior.add(card);
        }

        // Paneles vacíos para que se ajusten bien los tamaños
        for (int i = numeroDeTarjetas; i < espaciosNecesarios; i++) {
            JPanel emptyPanel = new JPanel();
            emptyPanel.setBackground(Color.BLACK);
            panelWInterior.add(emptyPanel);
        }
    }

}

package epicfitpc.vista.paneles;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

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
		panelWInterior.setLayout(new GridBagLayout());
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

		verWorkouts();
	}

	private void verWorkouts() {
		Firestore db;
		try {
			db = Conexion.getConexion();
			GestorDeWorkouts gdw = new GestorDeWorkouts(db);
			ArrayList<Workout> workouts = gdw.obtenerTodosLosWorkouts();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.NORTHWEST;

			gbc.weightx = 0;
			gbc.weighty = 0;

			for (int i = 0; i < workouts.size(); i++) {
				Workout workout = workouts.get(i);
				ItemPanel itemPanel = new ItemPanel(workout);

				gbc.gridx = i % 3;
				gbc.gridy = i / 3;
				gbc.insets = new Insets(10, 10, 10, 10);

				panelWInterior.add(itemPanel, gbc);
			}
			
			for (int i = 0; i < workouts.size(); i++) {
				Workout workout = workouts.get(i);
				ItemPanel itemPanel = new ItemPanel(workout);

				gbc.gridx = (i+2) % 3;
				gbc.gridy = (i+2) / 3;
				gbc.insets = new Insets(10, 10, 10, 10);

				panelWInterior.add(itemPanel, gbc);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

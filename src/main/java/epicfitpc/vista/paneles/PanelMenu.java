package epicfitpc.vista.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class PanelMenu extends JPanel {

	private static final long serialVersionUID = 6067181926807089944L;

	public PanelMenu() {
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout());
		setBounds(100, 100, 1200, 750);
		setBackground(Color.WHITE);

		JTabbedPane tabbedPane = new JTabbedPane();

		PanelWorkouts panelWorkouts = new PanelWorkouts();
		JPanel panelHistorico = new PanelHistorico();
		JPanel panelPerfil = new PanelPerfil();

		tabbedPane.addTab("Workouts", panelWorkouts);
		tabbedPane.addTab("Histórico", panelHistorico);
		tabbedPane.addTab("Perfil", panelPerfil);

		add(tabbedPane);
	}

}

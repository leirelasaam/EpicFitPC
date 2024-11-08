package epicfitpc.vista.paneles;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import epicfitpc.modelo.Usuario;
import epicfitpc.utils.Estilos;

public class PanelMenu extends JPanel {

	private static final long serialVersionUID = 6067181926807089944L;
	private JTabbedPane tabbedPane;

	public PanelMenu(Usuario usuario) {
		initialize(usuario);
	}

	private void initialize(Usuario usuario) {
		setLayout(new BorderLayout());
		setBounds(100, 100, 1200, 750);
		setBackground(Estilos.WHITE);

		tabbedPane = new JTabbedPane();

		PanelWorkouts panelWorkouts = new PanelWorkouts(this);
		PanelHistorico panelHistorico = new PanelHistorico();
		PanelPerfil panelPerfil = new PanelPerfil(this, usuario);

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
}

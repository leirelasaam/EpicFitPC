package epicfitpc.vista.paneles;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import epicfitpc.modelo.pojos.Usuario;
import epicfitpc.vista.MainFrame;

public class PanelMenu extends JPanel {
	
	private static final long serialVersionUID = 6067181926807089944L;
	private MainFrame frame = null;
	private Usuario usuario = null;

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
        tabbedPane.addTab("Histórico", panelHistorico);
        tabbedPane.addTab("Perfil", panelPerfil);
        
        if (usuario.isEsEntrenador()) {
        	JPanel panelEntrenador = new JPanel();
        	tabbedPane.addTab("Entrenador", panelEntrenador);
        }
        
        this.add(tabbedPane);
	}
}

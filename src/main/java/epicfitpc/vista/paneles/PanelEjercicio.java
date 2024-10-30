package epicfitpc.vista.paneles;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import epicfitpc.hilos.CronometroGeneral;
import epicfitpc.modelo.Workout;
import epicfitpc.vista.componentes.JButtonPrimary;

public class PanelEjercicio extends JPanel {
	private static final long serialVersionUID = -8810446678745477313L;
	private Workout workout = null;
	private CronometroGeneral cronGeneral = null;
	private JButtonPrimary btnPausar;

	public PanelEjercicio(Workout workout) {
		this.workout = workout;
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JButtonPrimary btnCerrar = new JButtonPrimary("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		cerrarPanel();
        	}
        });
		
		btnPausar = new JButtonPrimary("Pausar");
        btnPausar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		pausarWorkout();
        	}
        });

        panelSuperior.add(btnPausar);
		panelSuperior.add(btnCerrar); 
		add(panelSuperior, BorderLayout.NORTH); 

        JPanel panelGrid = new JPanel(new GridLayout(1, 3));
        JLabel labelNombreWorkout = new JLabel("Workout: " + (workout != null ? workout.getNombre() : "No seleccionado"));
        JLabel labelCronometroGeneral = new JLabel("00:00");
        JLabel labelEjercicioActual = new JLabel("Ejercicio actual: " + (workout != null ? workout.getEjerciciosArray().get(0).getNombre() : "No seleccionado"));

        panelGrid.add(labelNombreWorkout);
        panelGrid.add(labelCronometroGeneral);
        panelGrid.add(labelEjercicioActual);

        add(panelGrid, BorderLayout.CENTER);
        
        cronGeneral = new CronometroGeneral(labelCronometroGeneral);
        cronGeneral.start();
	}

	// Método para cerrar el panel
	private void cerrarPanel() {
        if (cronGeneral != null) {
            cronGeneral.terminar();
        }
		this.setVisible(false);
		this.getParent().remove(this);
		this.revalidate();
		this.repaint();
	}
	
	private void pausarWorkout() {
		if (cronGeneral != null) {
            if (btnPausar.getText().equals("Pausar")) {
                cronGeneral.pausar();
                btnPausar.setText("Reanudar");
            } else {
                cronGeneral.reanudar();
                btnPausar.setText("Pausar");
            }
        }
	}
}

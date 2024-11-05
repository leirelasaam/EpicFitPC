package epicfitpc.vista.paneles;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.Firestore;

import epicfitpc.bbdd.GestorDeHistoricos;
import epicfitpc.bbdd.GestorDeUsuarios;
import epicfitpc.hilos.ControladorCronometros;
import epicfitpc.hilos.CronometroProgresivo;
import epicfitpc.hilos.CronometroRegresivo;
import epicfitpc.hilos.CronometroSeries;
import epicfitpc.modelo.Ejercicio;
import epicfitpc.modelo.Historico;
import epicfitpc.modelo.Usuario;
import epicfitpc.modelo.Workout;
import epicfitpc.utils.Conexion;
import epicfitpc.utils.Estilos;
import epicfitpc.utils.UsuarioLogueado;
import epicfitpc.utils.WindowUtils;
import epicfitpc.vista.MainFrame;
import epicfitpc.vista.componentes.JButtonPrimary;

public class PanelEjercicio extends JPanel {
	private static final long serialVersionUID = -8810446678745477313L;
	private Workout workout;
	private Usuario usuario;

	private ControladorCronometros controladorCron = null;
	private CronometroProgresivo cronGeneral;
	private CronometroProgresivo cronEjercicio;

	private JButtonPrimary btnPausar;
	private JButtonPrimary btnIniciar;
	private JButtonPrimary btnSiguiente;
	private JButtonPrimary btnSalir;
	private JButtonPrimary btnAvanzar;

	private JLabel labelTiempoSerie;
	private JLabel labelTiempoDescanso;
	private JLabel labelNumeroSerie;
	private JLabel labelCronometroGeneral;
	private JLabel labelEjercicioActual;
	private JLabel lblCronEjercicio;

	private boolean cronometroIniciado = false;
	private int ejercicioActualIndex = 0;
	private int serieActual = 1;

	public PanelEjercicio(Workout workout) {
		this.usuario = UsuarioLogueado.getInstance().getUsuario();
		this.workout = workout;
		Collections.sort(workout.getEjercicios(), Comparator.comparingInt(Ejercicio::getOrden));
		this.controladorCron = new ControladorCronometros();
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		setBackground(Estilos.DARK_BACKGROUND);

		// Panel Superior
		JPanel panelSuperior = new JPanel(new GridLayout(2, 3));
		labelCronometroGeneral = new JLabel("00:00");
		JLabel labelNombreWorkout = new JLabel(
				"Workout: " + (workout != null ? workout.getNombre() : "No seleccionado"));
		panelSuperior.add(labelNombreWorkout);

		panelSuperior.add(labelCronometroGeneral);

		add(panelSuperior, BorderLayout.NORTH);

		cronGeneral = new CronometroProgresivo("Cronómetro del workout", labelCronometroGeneral, controladorCron);
		labelEjercicioActual = new JLabel("Ejercicio: " + (workout != null ? workout.getEjercicios().get(ejercicioActualIndex).getNombre() : "No seleccionado"));
		panelSuperior.add(labelEjercicioActual);

		lblCronEjercicio = new JLabel("00:00");
		panelSuperior.add(lblCronEjercicio);

		// Panel Central
		JPanel panelCentral = new JPanel(new GridLayout(1, 3));
		labelNumeroSerie = new JLabel("Serie 1 de " + (workout != null ? workout.getEjercicios().get(ejercicioActualIndex).getSeries() : "X"));
		labelTiempoSerie = new JLabel("00:00");
		labelTiempoDescanso = new JLabel("");
		labelTiempoDescanso.setVisible(true);

		panelCentral.add(labelTiempoSerie);
		panelCentral.add(labelTiempoDescanso);
		panelCentral.add(labelNumeroSerie);

		add(panelCentral, BorderLayout.CENTER);

		// Panel Inferior con botones
		btnSalir = new JButtonPrimary("SALIR");
		btnIniciar = new JButtonPrimary("INICIAR");
		btnAvanzar = new JButtonPrimary("AVANZAR");
		btnSiguiente = new JButtonPrimary("SIGUIENTE");
		btnPausar = new JButtonPrimary("PAUSAR");

		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciarCronometros();
			}
		});

		btnPausar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pausarCronometros();
			}
		});
		
		btnSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				siguienteEjercicio();
			}
		});
		
		btnAvanzar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				avanzarSerie();
			}
		});

		JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelInferior.add(btnSiguiente);
		panelInferior.add(btnIniciar);
		panelInferior.add(btnPausar);
		panelInferior.add(btnAvanzar);
		panelInferior.add(btnSalir);
		add(panelInferior, BorderLayout.SOUTH);

		btnAvanzar.setVisible(false);
		btnSiguiente.setVisible(false);
		btnPausar.setVisible(false);
	}

	private void iniciarCronometros() {
		if (!cronometroIniciado) {
			cronometroIniciado = true;
			cronGeneral.start();
			iniciarEjercicio();
		}

		btnIniciar.setVisible(false);
		btnPausar.setVisible(true);
	}

	private void pausarCronometros() {
		if (btnPausar.getText().equalsIgnoreCase("PAUSAR")) {
			controladorCron.pausarTodos();
			btnPausar.setText("REANUDAR");
		} else {
			controladorCron.reanudarTodos();
			btnPausar.setText("PAUSAR");
		}
	}

	private void iniciarEjercicio() {
		if (ejercicioActualIndex < workout.getEjercicios().size()) {
            Ejercicio ejercicioActual = workout.getEjercicios().get(ejercicioActualIndex);
            labelEjercicioActual.setText(ejercicioActual.getNombre());
            cronEjercicio = new CronometroProgresivo("Ejercicio - " + ejercicioActual.getOrden(), lblCronEjercicio, controladorCron);
            cronEjercicio.start();

            System.out.println("Iniciando ejercicio: " + ejercicioActual.getNombre());
            iniciarSerie();
        }
	}
	
	private void iniciarSerie() {
			Ejercicio ejercicioActual = workout.getEjercicios().get(ejercicioActualIndex);
            CronometroSeries cronSeries = new CronometroSeries(ejercicioActual, labelNumeroSerie, labelTiempoSerie, labelTiempoDescanso, controladorCron, cronEjercicio, serieActual, btnAvanzar, btnSiguiente);
            cronSeries.start();
	}
	
    private void avanzarSerie() {
    	btnAvanzar.setVisible(false);
    	Ejercicio ejercicioActual = workout.getEjercicios().get(ejercicioActualIndex);
        
        if (serieActual < ejercicioActual.getSeries()) {
            serieActual++;
            iniciarSerie();
        }
    }
	
    private void siguienteEjercicio() {
        if (ejercicioActualIndex < workout.getEjercicios().size() - 1) {
            ejercicioActualIndex++;
            serieActual = 1;
            btnSiguiente.setVisible(false);
            iniciarEjercicio();
        } else {
        	if(cronGeneral.isAlive())
        		cronGeneral.terminar();
        	WindowUtils.confirmationPane("Workout finalizado", "Fin");
        }
    }
}

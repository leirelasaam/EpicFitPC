package epicfitpc.vista.paneles;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.collection.LLRBNode.Color;

import epicfitpc.bbdd.GestorDeHistoricos;
import epicfitpc.hilos.CronometroGeneral;
import epicfitpc.modelo.Historico;
import epicfitpc.modelo.Usuario;
import epicfitpc.modelo.Workout;
import epicfitpc.utils.Conexion;
import epicfitpc.utils.Estilos;
import epicfitpc.utils.UsuarioLogueado;
import epicfitpc.vista.MainFrame;
import epicfitpc.vista.componentes.JButtonPrimary;

public class PanelEjercicio extends JPanel {
	private static final long serialVersionUID = -8810446678745477313L;
	private Workout workout = null;
	private CronometroGeneral cronGeneral = null;
	private JButtonPrimary btnPausar;
	private JButtonPrimary btnIniciar;
	private JButtonPrimary btnSiguiente;
	int ejercicioActual = 0;
	private Usuario usuario = null;
	protected Historico historico;

	public PanelEjercicio(Workout workout) {
		this.usuario = UsuarioLogueado.getInstance().getUsuario();
		this.workout = workout;
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		setBackground(Estilos.DARK_BACKGROUND);

		// ***PANEL SUPERIOR***
		JPanel panelSuperior = new JPanel(new GridLayout(1, 3));
		JLabel labelNombreWorkout = new JLabel(
				"Workout: " + (workout != null ? workout.getNombre() : "No seleccionado"));
		JLabel labelCronometroGeneral = new JLabel("00:00");
		JLabel labelEjercicioActual = new JLabel("Ejercicio actual: "
				+ (workout != null ? workout.getEjercicios().get(0).getNombre() : "No seleccionado"));

		panelSuperior.add(labelCronometroGeneral);
		panelSuperior.add(labelEjercicioActual);
		panelSuperior.add(labelNombreWorkout);

		add(panelSuperior, BorderLayout.NORTH);

		cronGeneral = new CronometroGeneral(labelCronometroGeneral);

		// Hay que obtener el primer ejercicio del workout

		// Cuando se termine el primer ejercicio, hay que obtener el siguiente

		// ***PANEL CENTRAL***
		JPanel panelCentral = new JPanel(new GridLayout(1, 3));
		JLabel labelTiempoSerie = new JLabel("Tiempo ejercicio "
				+ (workout != null ? workout.getEjercicios().get(0).getTiempoSerie() : "No seleccionado"));

		JLabel labelTiempoDescanso = new JLabel("Tiempo descanso 00:00"
				+ (workout != null ? workout.getEjercicios().get(0).getDescanso() : "No seleccionado"));

		JLabel labelNumeroSerie = new JLabel("Serie numero 1");

		panelCentral.add(labelTiempoSerie);
		panelCentral.add(labelTiempoDescanso);
		panelCentral.add(labelNumeroSerie);

		add(panelCentral, BorderLayout.CENTER);

		// cronEjercicio = new CronometroGeneral(labelCronometroGeneral);
		// cronEjercicio.start();

		// ***PANEL INFERIOR***
		// Al pulsar este botón nos aparece un resumen, tiempos y un mensaje
		// motivacional
		JButtonPrimary btnSalir = new JButtonPrimary("SALIR");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cronGeneral.terminar();

				// Cuando terminamos o salimos creamos un Historico
				try {
					Firestore db = Conexion.getInstance().getConexion();

					historico = new Historico();

					// Guardamos los 4 parametros que necesitamos del historico
					historico.setTiempo(cronGeneral.getTiempoTotal());
					historico.setFecha(Timestamp.now());
					// historico.setPorcentaje(porcentajeCompletado);
					historico.setWorkout(db.document("Workouts/" + workout.getId()));

					GestorDeHistoricos gdh = new GestorDeHistoricos(db);
					gdh.guardarHistorico(usuario, historico);

				} catch (InterruptedException | ExecutionException e1) {
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				cerrarPanel();

			}
		});

		// Botón Iniciar
		btnIniciar = new JButtonPrimary("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Inicia el cronómetro
				cronGeneral.start();

				// Oculta el botón Iniciar y muestra el botón Pausar
				btnIniciar.setVisible(false);
				btnPausar.setVisible(true);
			}
		});

		// Botón Siguiente - este boton se muestra al terminar un ejercicio
		btnSiguiente = new JButtonPrimary("Siguiente");
		btnSiguiente.setVisible(false); // Oculto al inicio
		btnSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Carga el ejercicio siguiente
				ejercicioActual += 1;

				btnIniciar.setVisible(false);
			}
		});

		// Botón Pausar (inicialmente oculto)
		btnPausar = new JButtonPrimary("Pausar");
		btnPausar.setVisible(false); // Oculto al inicio
		btnPausar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Alterna entre Pausar y Reanudar
				if (btnPausar.getText().equalsIgnoreCase("Pausar")) {
					cronGeneral.pausar();
					btnPausar.setText("Reanudar");
					btnPausar.setBackgroundColor(Estilos.BLACK); // Cambia el fondo a negro cuando el texto es
																	// "Reanudar"
				} else {
					cronGeneral.reanudar();
					btnPausar.setText("Pausar");
					btnPausar.setBackgroundColor(Estilos.PRIMARY);
				}
			}
		});

		// Panel Inferior con botones
		JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelInferior.add(btnSiguiente);
		panelInferior.add(btnIniciar);
		panelInferior.add(btnPausar);
		panelInferior.add(btnSalir);
		add(panelInferior, BorderLayout.SOUTH);
	}

	// Método para cerrar el panel
	private void cerrarPanel() {
		if (cronGeneral != null) {
			cronGeneral.terminar();
		}

		MainFrame.getInstance().getContentPane().removeAll();
		JPanel panelResumen = new PanelResumen(historico);
		MainFrame.getInstance().getContentPane().add(panelResumen);
		MainFrame.getInstance().revalidate();
		MainFrame.getInstance().repaint();
	}

	private void pausarWorkout() {
		if (cronGeneral != null) {
			if (btnPausar.getText().equalsIgnoreCase("PAUSAR")) {
				cronGeneral.pausar();
				btnPausar.setText("REANUDAR");
			} else {
				cronGeneral.reanudar();
				btnPausar.setText("PAUSAR");
			}
		}
	}
}

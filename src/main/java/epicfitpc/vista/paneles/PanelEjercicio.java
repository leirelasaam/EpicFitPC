package epicfitpc.vista.paneles;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.Firestore;

import epicfitpc.bbdd.GestorDeHistoricos;
import epicfitpc.bbdd.GestorDeUsuarios;
import epicfitpc.hilos.CronometroCuentaAtras;
import epicfitpc.hilos.CronometroGeneral;
import epicfitpc.hilos.CronometroListener;
import epicfitpc.modelo.Historico;
import epicfitpc.modelo.Usuario;
import epicfitpc.modelo.Workout;
import epicfitpc.utils.Conexion;
import epicfitpc.utils.DBUtils;
import epicfitpc.utils.Estilos;
import epicfitpc.utils.UsuarioLogueado;
import epicfitpc.vista.MainFrame;
import epicfitpc.vista.componentes.JButtonPrimary;

public class PanelEjercicio extends JPanel{
	private static final long serialVersionUID = -8810446678745477313L;
	private Workout workout;
	private CronometroGeneral cronGeneral;
	private CronometroCuentaAtras cronSerie;
	private CronometroCuentaAtras cronDescanso;
	private JButtonPrimary btnPausar;
	private JButtonPrimary btnIniciar;
	private JButtonPrimary btnSiguiente;
	private JButtonPrimary btnSalir;
	private int ejercicioActual = 0;
	private int serieActual = 0;
	private Usuario usuario;
	private Historico historico;
	private JLabel labelTiempoSerie;
	private JLabel labelTiempoDescanso;
	private JLabel labelNumeroSerie;
	private JLabel labelCronometroGeneral;
	private JLabel labelEjercicioActual;
	private boolean cronometroIniciado = false;
	int ejerciciosCompletados = 0;

	
	public void tiempoFinalizado() {
		System.out.println("El tiempo ha finalizado.");
	}

	public PanelEjercicio(Workout workout) {
		this.usuario = UsuarioLogueado.getInstance().getUsuario();
		this.workout = workout;
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		setBackground(Estilos.DARK_BACKGROUND);

		// Panel Superior
		JPanel panelSuperior = new JPanel(new GridLayout(1, 3));
		labelCronometroGeneral = new JLabel("00:00");
		JLabel labelNombreWorkout = new JLabel(
				"Workout: " + (workout != null ? workout.getNombre() : "No seleccionado"));
		labelEjercicioActual = new JLabel("Ejercicio actual: "
				+ (workout != null ? workout.getEjercicios().get(ejercicioActual).getNombre() : "No seleccionado"));

		panelSuperior.add(labelCronometroGeneral);
		panelSuperior.add(labelEjercicioActual);
		panelSuperior.add(labelNombreWorkout);

		add(panelSuperior, BorderLayout.NORTH);

		cronGeneral = new CronometroGeneral(labelCronometroGeneral);

		// Panel Central
		JPanel panelCentral = new JPanel(new GridLayout(1, 3));
		labelNumeroSerie = new JLabel("Serie: 1");
		labelTiempoSerie = new JLabel("Tiempo serie: 00:00");
		labelTiempoDescanso = new JLabel("Tiempo descanso: 00:00");
		labelTiempoDescanso.setVisible(false);

		panelCentral.add(labelTiempoSerie);
		panelCentral.add(labelTiempoDescanso);
		panelCentral.add(labelNumeroSerie);

		add(panelCentral, BorderLayout.CENTER);

		// Panel Inferior con botones
		btnSalir = new JButtonPrimary("SALIR");
		btnIniciar = new JButtonPrimary("Iniciar");
		btnSiguiente = new JButtonPrimary("Siguiente");
		btnPausar = new JButtonPrimary("Pausar");

		JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelInferior.add(btnSiguiente);
		panelInferior.add(btnIniciar);
		panelInferior.add(btnPausar);
		panelInferior.add(btnSalir);
		add(panelInferior, BorderLayout.SOUTH);

		btnSiguiente.setVisible(false);
		btnPausar.setVisible(false);

		configurarBotones();
	}

	private void configurarBotones() {
	    // Botón Iniciar
	    btnIniciar.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            if (!cronometroIniciado) { // solo se inicia una vez el cronómetro general
	                cronGeneral.start();
	                cronometroIniciado = true;
	            }
	            iniciarSerie();
	            btnIniciar.setVisible(false);
	        }
	    });

	    // Botón Pausar/Reanudar
	    btnPausar.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            if (btnPausar.getText().equalsIgnoreCase("Pausar")) {
	                cronGeneral.pausar();
	                cronSerie.pausar();
	                cronDescanso.pausar();
	                btnPausar.setText("Reanudar");
	            } else {
	                cronGeneral.reanudar();
	                cronSerie.reanudar();
	                cronDescanso.reanudar();
	                btnPausar.setText("Pausar");
	            }
	        }
	    });

	    // Botón Siguiente - Cambia al siguiente ejercicio
	    btnSiguiente.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            cambiarEjercicio();
	        }
	    });

	    // Botón Salir
	    btnSalir.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            salir();
	        }
	    });
	}


	private void iniciarSerie() {
	    int tiempoSerie = workout.getEjercicios().get(ejercicioActual).getTiempoSerie();
	    labelNumeroSerie.setText(
	            "Serie: " + (serieActual + 1) + " de " + workout.getEjercicios().get(ejercicioActual).getSeries());
	    labelTiempoSerie.setVisible(true);
	    labelTiempoDescanso.setVisible(false);
	    btnPausar.setVisible(true);

	    cronSerie = new CronometroCuentaAtras("Tiempo serie", labelTiempoSerie, tiempoSerie) {
	        @Override
	        public void run() {
	            super.run();  // Llama al método run() original de CronometroCuentaAtras

	            // Al finalizar el cronómetro, se inicia el descanso
	            if (tiempo <= 0) {
	                iniciarDescanso();
	            }
	        }
	    };
	    cronSerie.start();
	}


	private void iniciarDescanso() {
	    int tiempoDescanso = workout.getEjercicios().get(ejercicioActual).getDescanso();
	    labelTiempoSerie.setVisible(false);
	    labelTiempoDescanso.setVisible(true);
	    btnPausar.setVisible(false);
	    
	    cronDescanso = new CronometroCuentaAtras("Tiempo descanso", labelTiempoDescanso, tiempoDescanso);
	    
	    // Iniciar el cronómetro de descanso
	    cronDescanso.start();

	    // Mientras el cronómetro está corriendo, se puede verificar su estado
	    new Thread(() -> {
	        try {
	            cronDescanso.join(); // Espera a que termine el cronómetro
	            // Lógica que se ejecuta al finalizar el tiempo de descanso
	            serieActual++;
	            btnIniciar.setVisible(true);
	            if (serieActual < workout.getEjercicios().get(ejercicioActual).getSeries()) {
	                labelNumeroSerie.setText("Serie: " + (serieActual + 1) + " de " + workout.getEjercicios().get(ejercicioActual).getSeries());
	            } else {
	                serieActual = 0; // Reiniciar serie para el siguiente ejercicio
	                btnIniciar.setVisible(false);
	                btnSiguiente.setVisible(true);
	                ejerciciosCompletados++;
	            }
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt(); // Manejar la interrupción
	        }
	    }).start();
	}


	private void cambiarEjercicio() {
		if (ejercicioActual < workout.getEjercicios().size() - 1) {
			ejercicioActual++;
			serieActual = 0;
			labelEjercicioActual
					.setText("Ejercicio actual: " + workout.getEjercicios().get(ejercicioActual).getNombre());
			btnSiguiente.setVisible(false);
			btnIniciar.setVisible(true);
		} else {
			salir(); // Finalizar si es el último ejercicio
		}
	}

	private void salir() {
		cronGeneral.terminar();
		// Guardar el historico en Firestore
		try {
			Firestore db = Conexion.getInstance().getConexion();
			historico = new Historico();
			historico.setTiempo(cronGeneral.getTiempoTotal());
			historico.setFecha(Timestamp.now());
			historico.setWorkout(db.document(DBUtils.WORKOUTS + "/" + workout.getId()));
			historico.setPorcentaje(calcularPorcentaje());

			GestorDeHistoricos gdh = new GestorDeHistoricos(db);
			gdh.guardarHistorico(usuario, historico);

			GestorDeUsuarios gdu = new GestorDeUsuarios(db);

			int nivelUsuario = usuario.getNivel();
			String idUsuario = usuario.getId();

			// Se actualiza el nivel del usuario cuando nivel ej y usurio = y porcentaje es
			// 100
			if (nivelUsuario == workout.getNivel() && historico.getPorcentaje() == 100) {
				int nuevoNivel = nivelUsuario+1;
				
				usuario.setNivel(nuevoNivel);
				gdu.actualizarNivelUsuario(idUsuario, nuevoNivel);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();

		} catch (ExecutionException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

		cerrarPanel();
	}

	private int calcularPorcentaje() {
		return (ejerciciosCompletados * 100) / (workout.getEjercicios().size());
	}

	private void cerrarPanel() {
		MainFrame.getInstance().getContentPane().removeAll();
		JPanel panelResumen = new PanelResumen(historico);
		MainFrame.getInstance().getContentPane().add(panelResumen);
		MainFrame.getInstance().revalidate();
		MainFrame.getInstance().repaint();
	}
}

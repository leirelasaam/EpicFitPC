package epicfitpc.vista.paneles;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.Firestore;

import epicfitpc.bbdd.GestorDeHistoricos;
import epicfitpc.bbdd.GestorDeUsuarios;
import epicfitpc.hilos.ControladorCronometros;
import epicfitpc.hilos.CronometroProgresivo;
import epicfitpc.hilos.CronometroSeries;
import epicfitpc.modelo.Ejercicio;
import epicfitpc.modelo.Historico;
import epicfitpc.modelo.TiempoEjercicio;
import epicfitpc.modelo.Usuario;
import epicfitpc.modelo.Workout;
import epicfitpc.utils.Conexion;
import epicfitpc.utils.DBUtils;
import epicfitpc.utils.Estilos;
import epicfitpc.utils.ImageUtils;
import epicfitpc.utils.UsuarioLogueado;
import epicfitpc.utils.WindowUtils;
import epicfitpc.vista.MainFrame;
import epicfitpc.vista.componentes.JButtonPrimary;
import epicfitpc.vista.componentes.RoundedPanel;

public class PanelEjercicio extends JPanel {
	private static final long serialVersionUID = -8810446678745477313L;
	private Workout workout;
	private Usuario usuario;
	private Historico historico;
	private ArrayList<TiempoEjercicio> tiempoEjercicios = null;

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
	private JLabel labelCuentaAtras;

	private int ejercicioActualIndex = 0;
	private int serieActual = 1;
	int ejerciciosCompletados = 0;

	public PanelEjercicio(Workout workout) {
		this.usuario = UsuarioLogueado.getInstance().getUsuario();
		this.workout = workout;
		Collections.sort(workout.getEjercicios(), Comparator.comparingInt(Ejercicio::getOrden));
		this.controladorCron = new ControladorCronometros();
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		setBorder(new EmptyBorder(20, 40, 20, 40));
		setBackground(Estilos.DARK_BACKGROUND);

		// Panel Superior
		JPanel panelSuperior = new JPanel(new GridLayout(1, 0, 20, 10));
		panelSuperior.setBorder(new EmptyBorder(0, 0, 20, 0));
		panelSuperior.setMinimumSize(new Dimension(panelSuperior.getWidth(), 80));
		add(panelSuperior, BorderLayout.NORTH);

		// Panel Workout: contiene el cronómetro general y el nombre del workout
		RoundedPanel panelWorkout = new RoundedPanel(new GridLayout(1, 0));
		panelWorkout.setBackground(Estilos.CARD_BACKGROUND);
		panelSuperior.add(panelWorkout);
		
		JPanel panelImagen = new JPanel(new BorderLayout(0, 0));
		panelImagen.setOpaque(false);
		panelWorkout.add(panelImagen);

		String tipo = workout.getTipo();
		String ruta = ImageUtils.obtenerRutaImagen(tipo);
		ImageIcon img = WindowUtils.cargarImagen(ruta, 100, 100);
		JLabel labelImg = new JLabel(img);
		panelImagen.add(labelImg, BorderLayout.CENTER);
		
		JPanel panelInfoWorkout = new JPanel(new GridLayout(0, 1));
		panelInfoWorkout.setOpaque(false);
		panelWorkout.add(panelInfoWorkout);

		JLabel labelWorkout = new JLabel("WORKOUT");
		labelWorkout.setHorizontalAlignment(SwingConstants.CENTER);
		labelWorkout.setFont(new Font("Noto Sans", Font.BOLD, 14));
		labelWorkout.setForeground(Estilos.PRIMARY_DARK);
		panelInfoWorkout.add(labelWorkout);

		JLabel labelNombreWorkout = new JLabel(workout != null ? workout.getNombre() : "No seleccionado");
		labelNombreWorkout.setHorizontalAlignment(SwingConstants.CENTER);
		labelNombreWorkout.setFont(new Font("Noto Sans", Font.BOLD, 18));
		panelInfoWorkout.add(labelNombreWorkout);

		labelCronometroGeneral = new JLabel("00:00");
		labelCronometroGeneral.setHorizontalAlignment(SwingConstants.CENTER);
		labelCronometroGeneral.setFont(new Font("Noto Sans", Font.PLAIN, 25));
		panelInfoWorkout.add(labelCronometroGeneral);

		// Panel Ejercicio: contiene el cronómetro del ejercicio y el nombre
		RoundedPanel panelEjercicio = new RoundedPanel(new GridLayout(0, 1));
		panelEjercicio.setBackground(Estilos.CARD_BACKGROUND);
		panelSuperior.add(panelEjercicio);

		JLabel labelEjercicio = new JLabel("EJERCICIO");
		labelEjercicio.setHorizontalAlignment(SwingConstants.CENTER);
		labelEjercicio.setFont(new Font("Noto Sans", Font.BOLD, 14));
		labelEjercicio.setForeground(Estilos.PRIMARY_DARK);
		panelEjercicio.add(labelEjercicio);

		labelEjercicioActual = new JLabel(
				workout != null ? workout.getEjercicios().get(ejercicioActualIndex).getNombre() : "No seleccionado");
		labelEjercicioActual.setHorizontalAlignment(SwingConstants.CENTER);
		labelEjercicioActual.setFont(new Font("Noto Sans", Font.BOLD, 18));
		panelEjercicio.add(labelEjercicioActual);

		lblCronEjercicio = new JLabel("00:00");
		lblCronEjercicio.setHorizontalAlignment(SwingConstants.CENTER);
		lblCronEjercicio.setFont(new Font("Noto Sans", Font.PLAIN, 25));
		panelEjercicio.add(lblCronEjercicio);

		// Panel Central
		JPanel panelContenedor = new JPanel(new BorderLayout(0,0));
		panelContenedor.setBorder(new EmptyBorder(0, 0, 20, 0));
		add(panelContenedor, BorderLayout.CENTER);
		
		RoundedPanel panelCentral = new RoundedPanel(new GridLayout(0, 1));
		panelContenedor.add(panelCentral, BorderLayout.CENTER);

		labelCuentaAtras = new JLabel("");
		labelCuentaAtras.setPreferredSize(new Dimension(200, labelCuentaAtras.getHeight()));
		labelCuentaAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelCuentaAtras.setFont(new Font("Noto Sans", Font.BOLD, 75));
		labelCuentaAtras.setForeground(Estilos.PRIMARY_DARK);
		panelCentral.add(labelCuentaAtras);

		labelNumeroSerie = new JLabel("Serie: 1 de "
				+ (workout != null ? workout.getEjercicios().get(ejercicioActualIndex).getSeries() : "X"));
		labelNumeroSerie.setHorizontalAlignment(SwingConstants.CENTER);
		labelNumeroSerie.setFont(new Font("Noto Sans", Font.BOLD, 18));
		panelCentral.add(labelNumeroSerie);

		labelTiempoSerie = new JLabel("00:00");
		labelTiempoSerie.setHorizontalAlignment(SwingConstants.CENTER);
		labelTiempoSerie.setFont(new Font("Noto Sans", Font.PLAIN, 25));
		panelCentral.add(labelTiempoSerie);

		JLabel labelDescansoEstipulado = new JLabel("Descanso de "
				+ (workout != null ? workout.getEjercicios().get(ejercicioActualIndex).getDescanso() : "0") + " s");
		labelDescansoEstipulado.setHorizontalAlignment(SwingConstants.CENTER);
		labelDescansoEstipulado.setFont(new Font("Noto Sans", Font.BOLD, 18));
		panelCentral.add(labelDescansoEstipulado);

		labelTiempoDescanso = new JLabel("00:00");
		labelTiempoDescanso.setHorizontalAlignment(SwingConstants.CENTER);
		labelTiempoDescanso.setFont(new Font("Noto Sans", Font.PLAIN, 25));
		labelTiempoDescanso.setVisible(true);
		panelCentral.add(labelTiempoDescanso);

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
		
	    btnSalir.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            salir();
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

		cronGeneral = new CronometroProgresivo("Cronómetro del workout", labelCronometroGeneral, controladorCron);
	}

	private void iniciarCronometros() {
		if (!cronGeneral.isAlive()) {
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
			cronEjercicio = new CronometroProgresivo("Ejercicio - " + ejercicioActual.getOrden(), lblCronEjercicio,
					controladorCron);
			cronEjercicio.start();

			System.out.println("Iniciando ejercicio: " + ejercicioActual.getNombre());
			iniciarSerie();
		}
	}

	private void iniciarSerie() {
		Ejercicio ejercicioActual = workout.getEjercicios().get(ejercicioActualIndex);
		CronometroSeries cronSeries = new CronometroSeries(ejercicioActual, labelNumeroSerie, labelTiempoSerie,
				labelTiempoDescanso, labelCuentaAtras, controladorCron, cronEjercicio, serieActual, btnAvanzar, btnSiguiente);
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
		if (tiempoEjercicios == null)
			tiempoEjercicios = new ArrayList<TiempoEjercicio>();
		Ejercicio ejercicioActual = workout.getEjercicios().get(ejercicioActualIndex);
		
		TiempoEjercicio tiempoEjercicio = new TiempoEjercicio();
		tiempoEjercicio.setEjercicio(ejercicioActual);
		tiempoEjercicio.setTiempo(cronEjercicio.getTiempo());
		tiempoEjercicios.add(tiempoEjercicio);
		System.out.println("Tiempos: " + tiempoEjercicios.toString());
		ejerciciosCompletados++;
		
		if (ejercicioActualIndex < workout.getEjercicios().size() - 1) {
			ejercicioActualIndex++;
			serieActual = 1;
			btnSiguiente.setVisible(false);
			iniciarEjercicio();
		} else {
			if (cronGeneral.isAlive())
				cronGeneral.terminar();
			salir();
		}
	}
	
	private void salir() {
		if (cronGeneral.isAlive())
			cronGeneral.terminar();
		if (cronGeneral.getTiempo() == 0 || ejerciciosCompletados == 0) {
			this.setVisible(false);
			this.getParent().remove(this);
			this.revalidate();
			this.repaint();
			return;
		}
			
		try {
			Firestore db = Conexion.getInstance().getConexion();
			historico = new Historico();
			historico.setTiempo(cronGeneral.getTiempo());
			historico.setFecha(Timestamp.now());
			historico.setWorkout(db.document(DBUtils.WORKOUTS + "/" + workout.getId()));
			historico.setPorcentaje(calcularPorcentaje());

			GestorDeHistoricos gdh = new GestorDeHistoricos(db);
			gdh.guardarHistorico(usuario, historico);

			GestorDeUsuarios gdu = new GestorDeUsuarios(db);

			int nivelUsuario = usuario.getNivel();
			String idUsuario = usuario.getId();

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

package epicfitpc.vista.paneles;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.Firestore;

import epicfitpc.bbdd.GestorDeHistoricos;
import epicfitpc.bbdd.GestorDeUsuarios;
import epicfitpc.hilos.Cronometro;
import epicfitpc.hilos.CronometroRegresivo;
import epicfitpc.modelo.Ejercicio;
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
    private Workout workout;
    private Cronometro cronGeneral;
    private Cronometro cronEjercicio;
    private CronometroRegresivo cronSerie;
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
    private JLabel lblCronEjercicio;
    private Ejercicio ejercicio;
    
    public PanelEjercicio(Workout workout) {
        this.usuario = UsuarioLogueado.getInstance().getUsuario();
        this.workout = workout;
        this.ejercicio = workout.getEjercicios().getFirst();
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout(0, 0));
        setBackground(Estilos.DARK_BACKGROUND);

        // Panel Superior
        JPanel panelSuperior = new JPanel(new GridLayout(2, 3));
        labelCronometroGeneral = new JLabel("00:00");
        JLabel labelNombreWorkout = new JLabel("Workout: " + (workout != null ? workout.getNombre() : "No seleccionado"));
        panelSuperior.add(labelNombreWorkout);

        panelSuperior.add(labelCronometroGeneral);

        add(panelSuperior, BorderLayout.NORTH);

        cronGeneral = new Cronometro(labelCronometroGeneral);
        labelEjercicioActual = new JLabel("Ejercicio actual: " + (workout != null ? workout.getEjercicios().get(ejercicioActual).getNombre() : "No seleccionado"));
        panelSuperior.add(labelEjercicioActual);
        
        lblCronEjercicio = new JLabel("00:00");
        panelSuperior.add(lblCronEjercicio);
        cronEjercicio = new Cronometro(lblCronEjercicio);

        // Panel Central
        JPanel panelCentral = new JPanel(new GridLayout(1, 3));
        labelNumeroSerie = new JLabel("Serie: " + ejercicio.getOrden());
        labelTiempoSerie = new JLabel("00:00");
        labelTiempoDescanso = new JLabel("00:00");
        labelTiempoDescanso.setVisible(false);
        
        cronSerie = new CronometroRegresivo(labelTiempoSerie, ejercicio.getTiempoSerie());

        panelCentral.add(labelTiempoSerie);
        panelCentral.add(labelTiempoDescanso);
        panelCentral.add(labelNumeroSerie);

        add(panelCentral, BorderLayout.CENTER);

        // Panel Inferior con botones
        btnSalir = new JButtonPrimary("SALIR");
        btnIniciar = new JButtonPrimary("INICIAR");
        btnSiguiente = new JButtonPrimary("SIGUIENTE");
        btnPausar = new JButtonPrimary("PAUSAR");

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
    	btnIniciar.addActionListener(e -> {
    	    if (!cronometroIniciado) {
    	        cronGeneral.start();
    	        cronEjercicio.start();
    	        cronSerie.start();
    	        cronometroIniciado = true;
    	    }
    	    iniciarSerie();
    	    btnIniciar.setVisible(false);
    	    
    	});


        // Botón Pausar/Reanudar
        btnPausar.addActionListener(e -> {
            if (btnPausar.getText().equalsIgnoreCase("PAUSAR")) {
                cronGeneral.pausar();
                cronEjercicio.pausar();
                cronSerie.pausar();
                btnPausar.setText("REANUDAR");
            } else {
                cronGeneral.reanudar();
                cronEjercicio.reanudar();
                cronSerie.reanudar();
                btnPausar.setText("PAUSAR");
            }
        });

        // Botón Siguiente - Cambia al siguiente ejercicio
        btnSiguiente.addActionListener(e -> cambiarEjercicio());

        // Botón Salir
        btnSalir.addActionListener(e -> salir());
    }

    private void iniciarSerie() {
        int tiempoSerie = workout.getEjercicios().get(ejercicioActual).getTiempoSerie();
        labelNumeroSerie.setText("Serie: " + (serieActual + 1) + " de " + workout.getEjercicios().get(ejercicioActual).getSeries());
        labelTiempoSerie.setVisible(true);
        labelTiempoDescanso.setVisible(false);
        btnPausar.setVisible(true);
    }

    private void cambiarEjercicio() {
        if (ejercicioActual < workout.getEjercicios().size() - 1) {
            ejercicioActual++;
            serieActual = 0;
            labelEjercicioActual.setText("Ejercicio actual: " + workout.getEjercicios().get(ejercicioActual).getNombre());
            btnSiguiente.setVisible(false);
            btnIniciar.setVisible(true);
        } else {
            salir();
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
            historico.setWorkout(db.document("Workouts/" + workout.getId()));
            historico.setPorcentaje(calcularPorcentaje());
            
            GestorDeHistoricos gdh = new GestorDeHistoricos(db);
            gdh.guardarHistorico(usuario, historico);

            GestorDeUsuarios gdu = new GestorDeUsuarios(db);
            
            int nivelUsuario = usuario.getNivel();
            String idUsuario = usuario.getId();
            
            //Se actualiza el nivel del usuario cuando nivel ej y usurio = y porcentaje es 100
            if(nivelUsuario == workout.getNivel() && historico.getPorcentaje()==100) {
            	gdu.actualizarNivelUsuario(idUsuario, nivelUsuario++);
            	
            }
            
        } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }

        cerrarPanel();
    }

    private int calcularPorcentaje() {
		return (ejerciciosCompletados*100)/(workout.getEjercicios().size());
	}

	private void cerrarPanel() {
        MainFrame.getInstance().getContentPane().removeAll();
        JPanel panelResumen = new PanelResumen(historico);
        MainFrame.getInstance().getContentPane().add(panelResumen);
        MainFrame.getInstance().revalidate();
        MainFrame.getInstance().repaint();
    }
}

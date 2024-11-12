package epicfitpc.hilos;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;

import epicfitpc.modelo.Ejercicio;
import epicfitpc.modelo.TiempoEjercicio;
import epicfitpc.vista.paneles.PanelEjercicio;

public class CronometroSeries extends Thread {

	private Ejercicio ejercicio;
	private JLabel lblSerie;
	private JLabel lblTiempoSerie;
	private JLabel labelCuentaAtras;
	private ControladorCronometros controladorCron;
	private CronometroProgresivo cronEjercicio;
	private CronometroProgresivo cronDescanso;
	private int serie;
	private JButton btnAvanzar;
	private JButton btnSiguiente;
	private CronometroRegresivo cronSerie;
	private CronometroRegresivo cronRegresivo;
	private PanelEjercicio panelEjercicio;

	public CronometroSeries(Ejercicio ejercicio, JLabel lblSerie, JLabel lblTiempoSerie, JLabel labelCuentaAtras,
			ControladorCronometros controladorCron, CronometroProgresivo cronEjercicio,
			CronometroProgresivo cronDescanso, int serie, JButton btnAvanzar, JButton btnSiguiente,
			PanelEjercicio panelEjercicio) {
		this.ejercicio = ejercicio;
		this.lblSerie = lblSerie;
		this.lblTiempoSerie = lblTiempoSerie;
		this.controladorCron = controladorCron;
		this.cronEjercicio = cronEjercicio;
		this.cronDescanso = cronDescanso;
		this.labelCuentaAtras = labelCuentaAtras;
		this.serie = serie;
		this.btnAvanzar = btnAvanzar;
		this.btnSiguiente = btnSiguiente;
		this.panelEjercicio = panelEjercicio;
	}

	public void run() {
		try {
			lblSerie.setText("Serie: " + serie + " de " + ejercicio.getSeries());
			System.out.println("  Serie " + serie + " de " + ejercicio.getSeries());
			cronSerie = new CronometroRegresivo("Serie " + serie, lblTiempoSerie, ejercicio.getTiempoSerie(), false,
					controladorCron);
			cronRegresivo = new CronometroRegresivo("Cuenta atrás", labelCuentaAtras, 5, true, controladorCron);
			cronRegresivo.start();

			cronRegresivo.join();

			labelCuentaAtras.setText("GO");
			cronSerie.start();

			cronSerie.join();

			cronDescanso.start();

			if (serie == ejercicio.getSeries()) {
				labelCuentaAtras.setText("FINISH");
				cronEjercicio.terminar();

				// Gestionar guardado del tiempo del ejercicio completado
				panelEjercicio.setEjerciciosCompletados(panelEjercicio.getEjerciciosCompletados() + 1);
				ArrayList<TiempoEjercicio> tiempoEjercicios = panelEjercicio.getTiempoEjercicios();
				if (tiempoEjercicios == null) {
					panelEjercicio.setTiempoEjercicio(new ArrayList<TiempoEjercicio>());
				}

				TiempoEjercicio tiempoEjercicio = new TiempoEjercicio();
				tiempoEjercicio.setEjercicio(ejercicio);
				tiempoEjercicio.setTiempo(cronEjercicio.getTiempo());

				panelEjercicio.addTiempoEjercicio(tiempoEjercicio);

				btnSiguiente.setVisible(true);
			} else {
				labelCuentaAtras.setText("CONTINUE");
				btnAvanzar.setVisible(true);
			}
		} catch (InterruptedException e) {

		}
	}

	public void terminar() {
		if (cronSerie.isAlive())
			cronSerie.terminar();
		if (cronRegresivo.isAlive())
			cronRegresivo.terminar();
		if (cronDescanso.isAlive())
			cronDescanso.terminar();

		this.interrupt();
	}

}

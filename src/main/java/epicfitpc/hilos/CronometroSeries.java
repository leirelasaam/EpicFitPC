package epicfitpc.hilos;

import javax.swing.JButton;
import javax.swing.JLabel;

import epicfitpc.modelo.Ejercicio;
import epicfitpc.vista.paneles.PanelEjercicio;

public class CronometroSeries extends Thread {

	private Ejercicio ejercicio;
	private JLabel lblSerie;
	private JLabel lblTiempoSerie;
	private JLabel labelTiempoDescanso;
	private JLabel labelCuentaAtras;
	private ControladorCronometros controladorCron;
	private CronometroProgresivo cronEjercicio;
	private int serie;
	private JButton btnAvanzar;
	private JButton btnSiguiente;
	private CronometroRegresivo cronSerie;
	private CronometroRegresivo cronRegresivo;
	private PanelEjercicio panelEjercicio;

	public CronometroSeries(Ejercicio ejercicio, JLabel lblSerie, JLabel lblTiempoSerie, JLabel labelTiempoDescanso,
			JLabel labelCuentaAtras, ControladorCronometros controladorCron, CronometroProgresivo cronEjercicio,
			int serie, JButton btnAvanzar, JButton btnSiguiente, PanelEjercicio panelEjercicio) {
		this.ejercicio = ejercicio;
		this.lblSerie = lblSerie;
		this.lblTiempoSerie = lblTiempoSerie;
		this.controladorCron = controladorCron;
		this.cronEjercicio = cronEjercicio;
		this.labelTiempoDescanso = labelTiempoDescanso;
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
			labelCuentaAtras.setText("PULSA AVANZAR");

			if (serie == ejercicio.getSeries()) {
				cronEjercicio.terminar();
				panelEjercicio.setEjerciciosCompletados(panelEjercicio.getEjerciciosCompletados() + 1);
				btnSiguiente.setVisible(true);
			} else {
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

		this.interrupt();
	}

}

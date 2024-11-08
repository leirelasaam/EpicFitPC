package epicfitpc.hilos;

import javax.swing.JButton;
import javax.swing.JLabel;

import epicfitpc.modelo.Ejercicio;

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

	public CronometroSeries(Ejercicio ejercicio, JLabel lblSerie, JLabel lblTiempoSerie, JLabel labelTiempoDescanso,
			JLabel labelCuentaAtras, ControladorCronometros controladorCron, CronometroProgresivo cronEjercicio,
			int serie, JButton btnAvanzar, JButton btnSiguiente) {
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

			if (serie == ejercicio.getSeries()) {
				cronEjercicio.terminar();
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

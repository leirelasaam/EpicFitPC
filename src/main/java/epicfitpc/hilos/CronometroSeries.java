package epicfitpc.hilos;

import javax.swing.JButton;
import javax.swing.JLabel;

import epicfitpc.modelo.Ejercicio;

public class CronometroSeries extends Thread {

	private Ejercicio ejercicio;
	private JLabel lblSerie;
	private JLabel lblTiempoSerie;
	private JLabel labelTiempoDescanso;
	private ControladorCronometros controladorCron;
	private CronometroProgresivo cronEjercicio;
	private int serie;
	private JButton btnAvanzar;
	private JButton btnSiguiente;

	public CronometroSeries(Ejercicio ejercicio, JLabel lblSerie, JLabel lblTiempoSerie, JLabel labelTiempoDescanso,
			ControladorCronometros controladorCron, CronometroProgresivo cronEjercicio, int serie, JButton btnAvanzar, JButton btnSiguiente) {
		this.ejercicio = ejercicio;
		this.lblSerie = lblSerie;
		this.lblTiempoSerie = lblTiempoSerie;
		this.controladorCron = controladorCron;
		this.cronEjercicio = cronEjercicio;
		this.labelTiempoDescanso = labelTiempoDescanso;
		this.serie = serie;
		this.btnAvanzar = btnAvanzar;
		this.btnSiguiente = btnSiguiente;
	}

	public void run() {
		lblSerie.setText("Serie: " + serie + " de " + ejercicio.getSeries());
		System.out.println("  Serie " + serie + " de " + ejercicio.getSeries());
		CronometroRegresivo cronSerie = new CronometroRegresivo("Serie " + serie, lblTiempoSerie,
				ejercicio.getTiempoSerie(), false, controladorCron);
		CronometroRegresivo cronRegresivo = new CronometroRegresivo("Cuenta atrás", labelTiempoDescanso, 5, true,
				controladorCron);
		cronRegresivo.start();
		try {
			cronRegresivo.join();
		} catch (InterruptedException e) {

		}

		labelTiempoDescanso.setText("");
		cronSerie.start();

		try {
			cronSerie.join();
		} catch (InterruptedException e) {

		}

		if (serie == ejercicio.getSeries()) {
			cronEjercicio.terminar();
			btnSiguiente.setVisible(true);
		} else {
            btnAvanzar.setVisible(true);
		}
	}

}

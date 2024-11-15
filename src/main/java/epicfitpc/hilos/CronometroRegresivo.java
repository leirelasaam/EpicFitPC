package epicfitpc.hilos;

import javax.swing.JLabel;

import epicfitpc.utils.DateUtils;

/**
 * Cronómetro que avanza de forma regresiva.
 */
public class CronometroRegresivo extends Thread {
	private JLabel label;
	private int tiempoMax = 0;
	private ControladorCronometros controlador = null;
	private boolean ejecutando = true;
	private boolean soloSegundos;

	public CronometroRegresivo(String nombre, JLabel label, int tiempoMax, boolean soloSegundos, ControladorCronometros controlador) {
		super(nombre);
		this.label = label;
		this.controlador = controlador;
		this.tiempoMax = tiempoMax;
		this.soloSegundos = soloSegundos;
	}

	public void run() {
		while (ejecutando && tiempoMax >= 0) {
			synchronized (controlador) {
				while (controlador.isPausado()) {
					try {
						controlador.wait();
					} catch (InterruptedException ignore) {

					}
				}
			}

			if (!soloSegundos)
				label.setText(DateUtils.formatearTiempoCronometro(tiempoMax));
			else
				label.setText("" + tiempoMax);
		
			System.out.println(getName() + " - Cuenta regresiva: " + tiempoMax);
			
			tiempoMax--;

			try {
				sleep(1000);
			} catch (InterruptedException ignore) {
				
			}
		}
	}

	public void terminar() {
		ejecutando = false;
		this.interrupt();
	}

}
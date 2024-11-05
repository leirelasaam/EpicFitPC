package epicfitpc.hilos;

import javax.swing.JLabel;

import epicfitpc.utils.DateUtils;

public class CronometroProgresivo extends Thread {
	private JLabel label;
	private int tiempo = 0;
	private ControladorCronometros controlador = null;
	private boolean ejecutando = true;

	public CronometroProgresivo(String nombre, JLabel label, ControladorCronometros controlador) {
		super(nombre);
		this.label = label;
		this.controlador = controlador;
	}

	public void run() {
		while (ejecutando) {
			synchronized (controlador) {
				while (controlador.isPausado()) {
					try {
						controlador.wait();
					} catch (InterruptedException ignore) {

					}
				}
			}
			tiempo++;
			label.setText(DateUtils.formatearTiempoCronometro(tiempo));
			System.out.println(getName() + " - Cuenta progresiva " + tiempo);
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

	public int getTiempo() {
		return this.tiempo;
	}

}
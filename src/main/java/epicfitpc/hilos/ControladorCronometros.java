package epicfitpc.hilos;

/**
 * Controla y sincroniza los cronómetros, de forma que se pueden pausar y
 * reanudar todos a la vez.
 */
public class ControladorCronometros {
	private boolean pausado = false;

	public synchronized void pausarTodos() {
		setPausado(true);
	}

	public synchronized void reanudarTodos() {
		setPausado(false);
		notifyAll();
	}

	public synchronized boolean isPausado() {
		return pausado;
	}

	public synchronized void setPausado(boolean pausado) {
		this.pausado = pausado;
	}
}

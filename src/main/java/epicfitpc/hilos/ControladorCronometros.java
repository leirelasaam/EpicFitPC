package epicfitpc.hilos;

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

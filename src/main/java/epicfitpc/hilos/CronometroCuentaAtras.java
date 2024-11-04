package epicfitpc.hilos;
import javax.swing.JLabel;

public class CronometroCuentaAtras extends Thread {
	private JLabel label;
	private boolean enPausa = false;
	private boolean ejecutando = true;
	private int tiempo;

	public CronometroCuentaAtras(JLabel label, int tiempo) {
		this.label = label;
		this.tiempo = tiempo;
	}

	//Cronometro cuenta atrás hasta 0 y devuelve un true porque ha finalizado
	public void run() {
		while (ejecutando && tiempo > 0) {
			if (!enPausa) {
				tiempo--;
				label.setText(formatearTiempo(tiempo));
			}
			try {
				sleep(1000);
			} catch (InterruptedException ignore) {
				break;
			}
		}
	}

	public void pausar() {
		enPausa = true;
	}

	public void reanudar() {
		enPausa = false;
	}

	private String formatearTiempo(int tiempo) {
		int minutos = tiempo / 60;
		int segundos = tiempo % 60;
		return String.format("%02d:%02d", minutos, segundos);
	}

	public void terminar() {
		this.ejecutando = false;
		this.interrupt();
	}

}
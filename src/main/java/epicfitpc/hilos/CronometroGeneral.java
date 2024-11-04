package epicfitpc.hilos;

import javax.swing.JLabel;

public class CronometroGeneral extends Thread {
	private JLabel label;
	private boolean enPausa = false;
	private int tiempoAcumulado = 0;
	private boolean ejecutando = true;

	public CronometroGeneral(JLabel label) {
		this.label = label;
	}

	public void run() {
		while (ejecutando) {
			if (!enPausa) {
				tiempoAcumulado++;
				label.setText(formatearTiempo(tiempoAcumulado));
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
	
	public int getTiempoTotal() {
		return tiempoAcumulado;
	}

}
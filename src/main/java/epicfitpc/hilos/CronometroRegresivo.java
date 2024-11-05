package epicfitpc.hilos;

import javax.swing.JLabel;

public class CronometroRegresivo extends Thread {
	private JLabel label;
	private boolean enPausa = false;
	private int tiempo = 0;
	private boolean ejecutando = true;

	public CronometroRegresivo(JLabel label, int tiempo) {
		this.label = label;
		this.tiempo = tiempo;
	}

	public void run() {
		while (ejecutando) {
			if (!enPausa) {
				tiempo--;
				System.out.println("Tiempo regresivo: " + tiempo);
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
	
	public int getTiempoTotal() {
		return tiempo;
	}

}
package epicfitpc.hilos;

import javax.swing.JLabel;

public class CronometroCuentaAtras extends Thread {
    private JLabel label;
    private boolean enPausa = false;
    private boolean ejecutando = true;
    protected int tiempo;
    private String mensaje;

    public CronometroCuentaAtras(String mensaje, JLabel label, int tiempo) {
        this.mensaje = mensaje;
        this.label = label;
        this.tiempo = tiempo;
    }

    // Cronómetro cuenta atrás hasta 0
    public void run() {
        while (ejecutando && tiempo > 0) {
            if (!enPausa) {
                tiempo--;
                label.setText(mensaje + formatearTiempo(tiempo));
            }
            try {
                sleep(1000);
            } catch (InterruptedException ignore) {
                break;
            }
        }
        // Al finalizar, actualiza la etiqueta de tiempo o realiza cualquier acción necesaria
        if (tiempo == 0) {
            label.setText(mensaje + " ¡Tiempo finalizado!");
            // Aquí puedes llamar a otro método o realizar alguna acción adicional
            // Por ejemplo, iniciar descanso o cambiar ejercicio
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

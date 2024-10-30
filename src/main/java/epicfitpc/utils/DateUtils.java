package epicfitpc.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.cloud.Timestamp;

public class DateUtils {

    /**
     * Formatea un tiempo en segundos a un String en formato "Xh Ymin Zs".
     * 
     * @param tiempoEnSegundos El tiempo en segundos a formatear.
     * @return Un String con el tiempo formateado.
     */
    public static String formatearTiempo(int tiempoEnSegundos) {
        int horas = tiempoEnSegundos / 3600;
        int minutos = (tiempoEnSegundos % 3600) / 60;
        int segundos = tiempoEnSegundos % 60;

        StringBuilder tiempoFormateado = new StringBuilder();
        if (horas > 0) {
            tiempoFormateado.append(horas).append("h ");
        }
        tiempoFormateado.append(minutos).append("min ").append(segundos).append("s");

        return tiempoFormateado.toString();
    }
    
    /**
     * Formatea un Timestamp de Firestore a un String en formato "dd/MM/yyyy hh:mm:ss".
     * 
     * @param timestamp El Timestamp a formatear.
     * @return Un String con la fecha y hora formateadas.
     */
    public static String formatearTimestamp(Timestamp timestamp) {
        if (timestamp == null) {
            return "Fecha no disponible";
        }
        
        Date date = timestamp.toDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        return sdf.format(date);
    }
}


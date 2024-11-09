package epicfitpc.utils;

import java.text.ParseException;
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
            tiempoFormateado.append(horas).append(" h ");
        }
        if (minutos > 0) {
            tiempoFormateado.append(minutos).append(" min ");
        }
        tiempoFormateado.append(segundos).append(" s");

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
    
    public static String formatearTiempoCronometro(int tiempo) {
		int minutos = tiempo / 60;
		int segundos = tiempo % 60;
		return String.format("%02d:%02d", minutos, segundos);
	}
    
	public static String parsearTimestampAString(java.sql.Timestamp timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(timestamp);
	}
	
	public static Timestamp convertirStringToTimestamp(String texto) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date parsedDate = null;
		try {
			parsedDate = dateFormat.parse(texto);
		} catch (ParseException e) {
			throw e;
		}
		return Timestamp.of(parsedDate);
	}
}


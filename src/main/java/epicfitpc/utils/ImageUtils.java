package epicfitpc.utils;

/**
 * Clase que gestiona la imagen a mostrar por el tipo del workout.
 */
public class ImageUtils {

	private ImageUtils() {

	}

	public static String obtenerRutaImagen(String tipo) {
		String ruta;

		switch (tipo) {
		case "biceps":
			ruta = Rutas.IMG_BICEPS;
			break;
		case "triceps":
			ruta = Rutas.IMG_TRICEPS;
			break;
		case "abdominales":
			ruta = Rutas.IMG_ABS;
			break;
		case "espalda":
			ruta = Rutas.IMG_ESPALDA;
			break;
		case "gluteos":
			ruta = Rutas.IMG_GLUTEOS;
			break;
		case "pecho":
			ruta = Rutas.IMG_PECHO;
			break;
		case "piernas":
			ruta = Rutas.IMG_PIERNAS;
			break;
		default:
			ruta = Rutas.IMG_EJERCICIO;
			break;
		}

		return ruta;
	}
}

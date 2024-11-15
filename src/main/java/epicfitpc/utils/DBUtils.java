package epicfitpc.utils;

/**
 * Clase que contiene los nombres de las colecciones y subcolecciones de la base
 * de datos de Firestore.
 */
public class DBUtils {

	private DBUtils() {

	}

	// Colecciones
	public static final String USUARIOS = "Usuarios";
	public static final String WORKOUTS = "Workouts";

	// Subcolecciones
	public static final String EJERCICIOS = "Ejercicios";
	public static final String HISTORICOS = "Historicos";
}

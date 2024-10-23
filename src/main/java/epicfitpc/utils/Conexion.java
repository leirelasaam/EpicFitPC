package epicfitpc.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

/**
 * Clase para establecer la conexión con la base de datos de Firestore.
 */
public class Conexion {

	private static final String RUTA_CREDENCIALES = "epicfit_key_firestore.json";
	private static final String PROJECT_ID = "epicfit-54195";

	private static Firestore db; // Almacenará la instancia de Firestore

	// Evitar la instanciación de la clase
	private Conexion() {
	}

	public static Firestore getConexion() throws FileNotFoundException, IOException {
		if (db == null) { // Si no se ha inicializado, crear la conexión
			synchronized (Conexion.class) { // Asegurar que solo un hilo inicialice la conexión
				if (db == null) {
					FileInputStream serviceAccount = new FileInputStream(RUTA_CREDENCIALES);

					FirestoreOptions fsOptions = FirestoreOptions.getDefaultInstance().toBuilder()
							.setProjectId(PROJECT_ID).setCredentials(GoogleCredentials.fromStream(serviceAccount))
							.build();

					db = fsOptions.getService(); // Almacena la instancia
				}
			}
		}
		return db; // Devuelve la conexión a Firestore
	}
}

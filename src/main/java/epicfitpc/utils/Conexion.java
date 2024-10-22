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

	private static Conexion conn = null;

	private static final String RUTA_CREDENCIALES = "epicfit_key_firestore.json";
	private static final String PROJECT_ID = "epicfit-54195";

	private FileInputStream serviceAccount = null;
	private Firestore db = null;

	public static Conexion getInstance() {
		if (null == conn)
			conn = new Conexion();
		return conn;
	}

	private Conexion() {
		try {
			serviceAccount = new FileInputStream(RUTA_CREDENCIALES);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Firestore getConexion() throws FileNotFoundException, IOException {

		if (null == db) {
			FirestoreOptions fsOptions = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId(PROJECT_ID)
					.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

			db = fsOptions.getService();
		}
		return db;
	}

}

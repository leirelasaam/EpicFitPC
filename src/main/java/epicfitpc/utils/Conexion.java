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
    
    private Conexion() {
    	// Evitar que se instancie
    }
	
	public static Firestore getConexion() throws FileNotFoundException, IOException {
		FileInputStream serviceAccount = new FileInputStream(RUTA_CREDENCIALES);

		FirestoreOptions fsOptions = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId(PROJECT_ID)
				.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

		Firestore db = fsOptions.getService();
		
		return db;
	}

}

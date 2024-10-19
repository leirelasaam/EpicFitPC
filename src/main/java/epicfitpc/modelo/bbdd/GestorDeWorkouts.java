package epicfitpc.modelo.bbdd;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import epicfitpc.modelo.pojos.Workout;

public class GestorDeWorkouts {

	private Firestore db = null;
	private static final String COLLECTION = "Workouts";

	public GestorDeWorkouts(Firestore db) {
		this.db = db;
	}

	public ArrayList<Workout> obtenerTodosLosWorkouts() throws InterruptedException, ExecutionException {
		ArrayList<Workout> workouts = null;

		CollectionReference workoutsDb = db.collection(COLLECTION);
		ApiFuture<QuerySnapshot> futureQuery = workoutsDb.get();
		QuerySnapshot querySnapshot = null;

		try {
			querySnapshot = futureQuery.get();
		} catch (InterruptedException e) {
			throw e;
		} catch (ExecutionException e) {
			throw e;
		}

		List<QueryDocumentSnapshot> documentos = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot documento : documentos) {
			String id = documento.getId();
			String nombre = documento.getString("nombre");
			double nivel = documento.getDouble("nivel");
			double tiempo = documento.getDouble("tiempo");
			String video = documento.getString("video");
			@SuppressWarnings("unchecked")
			ArrayList<String> ejercicios = (ArrayList<String>) documento.get("ejercicios");
			

			Workout workout = new Workout(id, nombre, nivel, tiempo, video, ejercicios);
			
			if (null == workouts)
				workouts = new ArrayList<Workout>();
			
			workouts.add(workout);
		}

		return workouts;
	}

}


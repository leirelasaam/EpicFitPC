package epicfitpc.modelo.bbdd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import epicfitpc.modelo.pojos.Ejercicio;
import epicfitpc.modelo.pojos.Workout;

public class GestorDeEjercicios {

	private Firestore db = null;
	private static final String COLLECTION_EJERCICIOS = "Ejercicios";
	private static final String COLLECTION_WORKOUTS = "Workouts";

	public GestorDeEjercicios(Firestore db) {
		this.db = db;
	}

	public ArrayList<Ejercicio> obtenerEjerciciosPorWorkout(Workout workout)
			throws InterruptedException, ExecutionException {
		ArrayList<Ejercicio> ejercicios = null;
		String idWorkout = workout.getId();
		System.out.println("ID: " + idWorkout);

		DocumentReference workoutsDb = db.collection(COLLECTION_WORKOUTS).document(idWorkout);
		ApiFuture<DocumentSnapshot> futureQuery = workoutsDb.get();
		DocumentSnapshot docSnapshot = null;

		try {
			docSnapshot = futureQuery.get();
		} catch (InterruptedException e) {
			throw e;
		} catch (ExecutionException e) {
			throw e;
		}

		@SuppressWarnings("unchecked")
		List<DocumentReference> ejs = (List<DocumentReference>) docSnapshot.get("ejercicios");
		for (DocumentReference ej : ejs) {
			System.out.println("Ref: " + ej.getId());
			DocumentReference ejerciciosDb = db.collection(COLLECTION_EJERCICIOS).document(ej.getId());
			futureQuery = ejerciciosDb.get();
			DocumentSnapshot docSnapshotEj = null;

			try {
				docSnapshotEj = futureQuery.get();
			} catch (InterruptedException e) {
				throw e;
			} catch (ExecutionException e) {
				throw e;
			}
			System.out.println(docSnapshotEj.toString());
			Map<String, Object> datos = docSnapshotEj.getData();

			for (Map.Entry<String, Object> entry : datos.entrySet()) {
			    System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue());
			    if (entry.getKey().equals("id")) {
			    	
			    }
			}
			/*
			String id = docSnapshot.getId();
			String nombre = docSnapshot.getString("nombre");
			double repeticiones = docSnapshot.getDouble("repeticiones");
			double tiempo = docSnapshot.getDouble("tiempoSerie");
			double descanso = docSnapshot.getDouble("descanso");
			double series = docSnapshot.getDouble("series");
			
/*
			Ejercicio ejercicio = new Ejercicio();

			if (null == ejercicios)
				ejercicios = new ArrayList<Ejercicio>();

			ejercicios.add(ejercicio);
			*/
		}

		return ejercicios;
	}

}

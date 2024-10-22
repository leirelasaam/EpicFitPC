package epicfitpc.modelo.bbdd;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;

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

			String id = docSnapshotEj.getId();
			String nombre = docSnapshotEj.getString("nombre");
			double repeticiones = docSnapshotEj.getDouble("repeticiones");
			double tiempoSerie = docSnapshotEj.getDouble("tiempoSerie");
			double descanso = docSnapshotEj.getDouble("descanso");
			double series = docSnapshotEj.getDouble("series");

			Ejercicio ejercicio = new Ejercicio(id, nombre, repeticiones, tiempoSerie, descanso, series);
			if (null == ejercicios)
				ejercicios = new ArrayList<Ejercicio>();
			ejercicios.add(ejercicio);
		}

		return ejercicios;
	}

}

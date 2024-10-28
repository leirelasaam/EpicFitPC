package epicfitpc.bbdd;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import epicfitpc.modelo.Ejercicio;
import epicfitpc.modelo.Workout;

public class GestorDeEjercicios {

	private Firestore db = null;
	private static final String COLLECTION_EJERCICIOS = "Ejercicios";
	private static final String COLLECTION_WORKOUTS = "Workouts";

	public GestorDeEjercicios(Firestore db) {
		this.db = db;
	}

	public ArrayList<Ejercicio> obtenerTodosLosEjercicios() throws InterruptedException, ExecutionException {
		ArrayList<Ejercicio> ejercicios = null;

		CollectionReference ejerciciosDb = db.collection(COLLECTION_EJERCICIOS);
		ApiFuture<QuerySnapshot> futureQuery = ejerciciosDb.get();
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
			Ejercicio ejercicio = documento.toObject(Ejercicio.class);
			ejercicio.setId(documento.getId());
			
			if (null == ejercicios)
				ejercicios = new ArrayList<Ejercicio>();
			
			ejercicios.add(ejercicio);
		}

		return ejercicios;
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

			Ejercicio ejercicio = docSnapshotEj.toObject(Ejercicio.class);
			ejercicio.setId(docSnapshotEj.getId());

			if (null == ejercicios)
				ejercicios = new ArrayList<Ejercicio>();
			ejercicios.add(ejercicio);
		}

		return ejercicios;
	}

}
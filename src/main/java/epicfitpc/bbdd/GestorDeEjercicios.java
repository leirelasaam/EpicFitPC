package epicfitpc.bbdd;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import epicfitpc.modelo.Ejercicio;
import epicfitpc.modelo.Workout;
import epicfitpc.utils.DBUtils;

public class GestorDeEjercicios {

	private Firestore db = null;

	public GestorDeEjercicios(Firestore db) {
		this.db = db;
	}

	public ArrayList<Ejercicio> obtenerEjerciciosPorWorkout(Workout workout)
			throws InterruptedException, ExecutionException {
		ArrayList<Ejercicio> ejercicios = null;
		String idWorkout = workout.getId();

		CollectionReference ejerciciosDb = db.collection(DBUtils.WORKOUTS).document(idWorkout).collection(DBUtils.EJERCICIOS);
		ApiFuture<QuerySnapshot> futureQuery = ejerciciosDb.orderBy("orden", Query.Direction.ASCENDING).get();
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
	
	

}

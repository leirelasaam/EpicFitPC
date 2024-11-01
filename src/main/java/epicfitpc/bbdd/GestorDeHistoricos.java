package epicfitpc.bbdd;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import epicfitpc.modelo.Historico;
import epicfitpc.modelo.Usuario;
import epicfitpc.modelo.Workout;

import com.google.cloud.firestore.Query;

public class GestorDeHistoricos {
	private Firestore db = null;
	private static final String COLLECTION_HISTORICOS = "Historicos";
	private static final String COLLECTION_USUARIOS = "Usuarios";

	public GestorDeHistoricos(Firestore db) {
		this.db = db;
	}

	public ArrayList<Historico> obtenerTodosLosHistoricosPorUsuario(Usuario usuario)
			throws InterruptedException, ExecutionException {
		ArrayList<Historico> historicos = null;
		
		String idUsuario = usuario.getId();
		CollectionReference historicosDb = db.collection(COLLECTION_USUARIOS).document(idUsuario).collection(COLLECTION_HISTORICOS);
		ApiFuture<QuerySnapshot> futureQuery = historicosDb.orderBy("fecha", Query.Direction.DESCENDING).get();
		QuerySnapshot querySnapshot = null;

		try {
			querySnapshot = futureQuery.get();
		} catch (InterruptedException | ExecutionException e) {
			throw e;
		}

		GestorDeWorkouts gestorDeWorkouts = new GestorDeWorkouts(db);
		List<QueryDocumentSnapshot> documentos = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot documento : documentos) {
			Historico historico = documento.toObject(Historico.class);
			historico.setId(documento.getId());

			DocumentReference workoutRef = historico.getWorkout();
			if (workoutRef != null) {
				Workout workout = obtenerWorkout(workoutRef, gestorDeWorkouts);
				historico.setWorkoutObj(workout);
			}

			if (historicos == null)
				historicos = new ArrayList<Historico>();

			historicos.add(historico);
		}

		return historicos;
	}

	private Workout obtenerWorkout(DocumentReference workoutRef, GestorDeWorkouts gestorDeWorkouts)
			throws InterruptedException, ExecutionException {
		Workout w = null;
		try {
			ArrayList<Workout> workouts = gestorDeWorkouts.obtenerTodosLosWorkouts();
			for (Workout workout : workouts) {
				if (workoutRef.getId().equals(workout.getId())) {
					w = workout;
				}
			}
		} catch (InterruptedException e) {
			throw e;
		} catch (ExecutionException e) {
			throw e;
		}

		return w;
	}
}

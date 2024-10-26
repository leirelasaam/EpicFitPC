package epicfitpc.modelo.bbdd;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.Query;

import epicfitpc.modelo.pojos.Historico;
import epicfitpc.modelo.pojos.Usuario;
import epicfitpc.modelo.pojos.Workout;

public class GestorDeHistoricos {
	private Firestore db = null;
	private static final String COLLECTION = "Historicos";

	public GestorDeHistoricos(Firestore db) {
		this.db = db;
	}

	public ArrayList<Historico> obtenerTodosLosHistoricosPorUsuario(Usuario usuario)
			throws InterruptedException, ExecutionException {
		ArrayList<Historico> historicos = null;

		CollectionReference historicosDb = db.collection(COLLECTION);

		// Crear la referencia del usuario usando su ID
		DocumentReference usuarioRef = db.collection("Usuarios").document(usuario.getId());

		// Consulta filtrando por la referencia del usuario
		ApiFuture<QuerySnapshot> futureQuery = historicosDb.whereEqualTo("usuario", usuarioRef)
				.orderBy("fecha", Query.Direction.DESCENDING).get();
		QuerySnapshot querySnapshot;

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
			historico.setUsuarioObj(usuario);

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

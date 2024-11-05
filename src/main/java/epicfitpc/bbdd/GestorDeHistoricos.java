package epicfitpc.bbdd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import epicfitpc.utils.DBUtils;

import com.google.cloud.firestore.Query;

public class GestorDeHistoricos {
	private Firestore db = null;

	public GestorDeHistoricos(Firestore db) {
		this.db = db;
	}

	public ArrayList<Historico> obtenerTodosLosHistoricosPorUsuario(Usuario usuario)
			throws InterruptedException, ExecutionException {
		ArrayList<Historico> historicos = null;

		String idUsuario = usuario.getId();
		CollectionReference historicosDb = db.collection(DBUtils.USUARIOS).document(idUsuario)
				.collection(DBUtils.HISTORICOS);
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

	public boolean guardarHistorico(Usuario usuario, Historico historico)
			throws InterruptedException, ExecutionException {
		// Obtener la referencia de la colección 'historicos' para el usuario
		String idUsuario = usuario.getId();
		CollectionReference historicosDb = db.collection(DBUtils.USUARIOS).document(idUsuario)
				.collection(DBUtils.HISTORICOS);

		// Añadir el objeto Historico a la colección
		Map<String, Object> historicoMap = new HashMap<>();
		historicoMap.put("tiempo", historico.getTiempo());
		historicoMap.put("fecha", historico.getFecha());
		historicoMap.put("workout", historico.getWorkout());
		historicoMap.put("porcentaje", historico.getPorcentaje());

		DocumentReference devolver = historicosDb.add(historicoMap).get();
		return devolver.getId() != null;
	}
}

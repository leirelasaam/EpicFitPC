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

public class GestorDeWorkouts {

	private Firestore db = null;

	public GestorDeWorkouts(Firestore db) {
		this.db = db;
	}

	public ArrayList<Workout> obtenerTodosLosWorkouts() throws InterruptedException, ExecutionException {
		System.out.println("BBDD: obtenerTodosLosWorkouts");
		ArrayList<Workout> workouts = null;

		CollectionReference workoutsDb = db.collection(DBUtils.WORKOUTS);
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
			Workout workout = documento.toObject(Workout.class);
			workout.setId(documento.getId());
			

			ArrayList<Ejercicio> ejercicios = obtenerEjercicios(workout);
			workout.setEjercicios(ejercicios);

			
			int tiempoTotal = agregarTiempoEstimadoWorkout(ejercicios);
			workout.setTiempo(tiempoTotal);
			
			if (null == workouts)
				workouts = new ArrayList<Workout>();
			
			workouts.add(workout);
		}

		return workouts;
	}
	
	private ArrayList<Ejercicio> obtenerEjercicios(Workout workout) throws InterruptedException, ExecutionException{
		ArrayList<Ejercicio> ejercicios = null;
		
		GestorDeEjercicios gde = new GestorDeEjercicios(db);
		try {
			ejercicios = gde.obtenerEjerciciosPorWorkout(workout);
		} catch (InterruptedException e) {
			throw e;
		} catch (ExecutionException e) {
			throw e;
		}
		
		return ejercicios;
	}
	
	private int agregarTiempoEstimadoWorkout(ArrayList<Ejercicio> ejercicios) {
		int tiempoTotal = 0;
		if (ejercicios != null) {
			for (Ejercicio ejercicio : ejercicios) {
				int tiempo = ejercicio.getTiempoSerie();
				int descanso = ejercicio.getDescanso();
				int cuentaRegresiva = 5;
				int series = ejercicio.getSeries();
				
				tiempoTotal += (tiempo * series) + (descanso * (series - 1) + (cuentaRegresiva * series));
			}
		}
		return tiempoTotal;
	}
	
	public ArrayList<Workout> obtenerWorkoutsPorNivelUsuario(int nivelUsuario) throws InterruptedException, ExecutionException {
		System.out.println("BBDD: obtenerWorkoutsPorNivelUsuario");
		ArrayList<Workout> workouts = null;

		CollectionReference workoutsDb = db.collection(DBUtils.WORKOUTS);
		ApiFuture<QuerySnapshot> futureQuery = workoutsDb.whereLessThanOrEqualTo("nivel", nivelUsuario).orderBy("nivel", Query.Direction.DESCENDING).get();
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
			Workout workout = documento.toObject(Workout.class);
			workout.setId(documento.getId());
			
			ArrayList<Ejercicio> ejercicios = obtenerEjercicios(workout);
			workout.setEjercicios(ejercicios);
			
			int tiempoTotal = agregarTiempoEstimadoWorkout(ejercicios);
			workout.setTiempo(tiempoTotal);
			
			if (null == workouts)
				workouts = new ArrayList<Workout>();
			
			workouts.add(workout);
		}

		return workouts;
	}

}


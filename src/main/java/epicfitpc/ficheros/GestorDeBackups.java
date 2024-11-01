package epicfitpc.ficheros;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.Firestore;
import epicfitpc.bbdd.GestorDeUsuarios;
import epicfitpc.bbdd.GestorDeWorkouts;
import epicfitpc.modelo.Usuario;
import epicfitpc.modelo.Workout;

public class GestorDeBackups {
	private Firestore db = null;
	private static final String CARPETA_BACKUP = "src\\main\\java\\epicfitpc\\ficheros\\backup\\";
	private static final String FICHERO_USUARIOS = CARPETA_BACKUP + "usuarios.dat";
	private static final String FICHERO_WORKOUTS = CARPETA_BACKUP + "workouts.dat";

	public GestorDeBackups(Firestore db) {
		this.db = db;
	}

	public void realizarBackup() throws FileNotFoundException, InterruptedException, ExecutionException, IOException {
		escribirUsuarios();
		escribirWorkouts();
	}

	public void cargarBackup() throws FileNotFoundException, InterruptedException, ExecutionException, IOException,
			ClassNotFoundException {
		ArrayList<Usuario> usuarios = leerUsuarios();
		ArrayList<Workout> workouts = leerWorkouts();;

		System.out.println("CARGADO Usuarios: ");
		for (Usuario usuario : usuarios) {
			System.out.println("\t" + usuario.toString());
		}

		System.out.println("CARGADO Workouts: ");
		for (Workout workout : workouts) {
			System.out.println("\t" + workout.toString());
		}
	}

	public void escribirUsuarios() throws InterruptedException, ExecutionException, FileNotFoundException, IOException {
		GestorDeFicherosBinarios<Usuario> gdfb = new GestorDeFicherosBinarios<Usuario>(FICHERO_USUARIOS);
		GestorDeUsuarios gdu = new GestorDeUsuarios(db);
		ArrayList<Usuario> usuarios = gdu.obtenerUsuariosConHistoricos();

		System.out.println("ESCRIBIENDO Usuarios...");
		if (null != usuarios) {
			gdfb.escribir(usuarios);
		}
	}

	public ArrayList<Usuario> leerUsuarios() throws InterruptedException, ExecutionException, FileNotFoundException,
			IOException, ClassNotFoundException {
		ArrayList<Usuario> usuarios = null;
		GestorDeFicherosBinarios<Usuario> gdfb = new GestorDeFicherosBinarios<Usuario>(FICHERO_USUARIOS);
		usuarios = gdfb.leer();

		return usuarios;
	}

	public void escribirWorkouts() throws InterruptedException, ExecutionException, FileNotFoundException, IOException {
		GestorDeFicherosBinarios<Workout> gdfb = new GestorDeFicherosBinarios<Workout>(FICHERO_WORKOUTS);
		GestorDeWorkouts gdw = new GestorDeWorkouts(db);
		ArrayList<Workout> workouts = gdw.obtenerTodosLosWorkouts();

		System.out.println("ESCRIBIENDO Workouts...");
		if (null != workouts) {
			gdfb.escribir(workouts);
		}
	}

	public ArrayList<Workout> leerWorkouts() throws InterruptedException, ExecutionException, FileNotFoundException,
			IOException, ClassNotFoundException {
		ArrayList<Workout> workouts = null;
		GestorDeFicherosBinarios<Workout> gdfb = new GestorDeFicherosBinarios<Workout>(FICHERO_WORKOUTS);
		workouts = gdfb.leer();

		return workouts;
	}
}

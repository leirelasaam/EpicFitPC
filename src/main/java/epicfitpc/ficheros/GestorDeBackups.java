package epicfitpc.ficheros;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.Firestore;

import epicfitpc.bbdd.GestorDeEjercicios;
import epicfitpc.bbdd.GestorDeHistoricos;
import epicfitpc.bbdd.GestorDeUsuarios;
import epicfitpc.bbdd.GestorDeWorkouts;
import epicfitpc.modelo.Ejercicio;
import epicfitpc.modelo.Historico;
import epicfitpc.modelo.Usuario;
import epicfitpc.modelo.Workout;

public class GestorDeBackups {
	private Firestore db = null;
	private static final String CARPETA_BACKUP = "src\\main\\java\\epicfitpc\\ficheros\\backup\\";
	private static final String FICHERO_USUARIOS = CARPETA_BACKUP + "usuarios.dat";
	private static final String FICHERO_HISTORICOS = CARPETA_BACKUP + "historicos.dat";
	private static final String FICHERO_WORKOUTS = CARPETA_BACKUP + "workouts.dat";
	private static final String FICHERO_EJERCICIOS = CARPETA_BACKUP + "ejercicios.dat";

	public GestorDeBackups(Firestore db) {
		this.db = db;
	}

	public boolean hayConexion() {
		boolean conexion = true;

		try {

			@SuppressWarnings("deprecation")
			URL url = new URL("https://www.google.com");
			HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
			urlConnect.setConnectTimeout(1000);
			urlConnect.connect();
			conexion = (urlConnect.getResponseCode() == 200);
		} catch (IOException e) {
			conexion = false;
		}

		return conexion;
	}

	public void realizarBackup() throws FileNotFoundException, InterruptedException, ExecutionException, IOException {
		escribirUsuarios();
		escribirHistoricos();
		escribirWorkouts();
		escribirEjercicios();
	}

	public void cargarBackup() throws FileNotFoundException, InterruptedException, ExecutionException, IOException,
			ClassNotFoundException {
		ArrayList<Usuario> usuarios = leerUsuarios();
		ArrayList<Historico> historicos = leerHistoricos();
		ArrayList<Workout> workouts = leerWorkouts();
		ArrayList<Ejercicio> ejercicios = leerEjercicios();

		System.out.println("CARGADO Usuarios: ");
		for (Usuario usuario : usuarios) {
			System.out.println("\t" + usuario.toString());
		}

		System.out.println("CARGADO Historicos: ");
		for (Historico historico : historicos) {
			System.out.println("\t" + historico.toString());
		}

		System.out.println("CARGADO Workouts: ");
		for (Workout workout : workouts) {
			System.out.println("\t" + workout.toString());
		}

		System.out.println("CARGADO Ejercicios: ");
		for (Ejercicio ejercicio : ejercicios) {
			System.out.println("\t" + ejercicio.toString());
		}
	}

	public void escribirUsuarios() throws InterruptedException, ExecutionException, FileNotFoundException, IOException {
		GestorDeFicherosBinarios<Usuario> gdfb = new GestorDeFicherosBinarios<Usuario>(FICHERO_USUARIOS);
		GestorDeUsuarios gdu = new GestorDeUsuarios(db);
		ArrayList<Usuario> usuarios = gdu.obtenerTodosLosUsuarios();

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

	public void escribirHistoricos()
			throws InterruptedException, ExecutionException, FileNotFoundException, IOException {
		GestorDeFicherosBinarios<Historico> gdfb = new GestorDeFicherosBinarios<Historico>(FICHERO_HISTORICOS);
		GestorDeHistoricos gdh = new GestorDeHistoricos(db);
		ArrayList<Historico> historicos = gdh.obtenerTodosLosHistoricos();

		System.out.println("ESCRIBIENDO Historicos...");
		if (null != historicos) {
			gdfb.escribir(historicos);
		}
	}

	public ArrayList<Historico> leerHistoricos() throws InterruptedException, ExecutionException, FileNotFoundException,
			IOException, ClassNotFoundException {
		ArrayList<Historico> historicos = null;
		GestorDeFicherosBinarios<Historico> gdfb = new GestorDeFicherosBinarios<Historico>(FICHERO_HISTORICOS);
		historicos = gdfb.leer();

		return historicos;
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

	public void escribirEjercicios()
			throws InterruptedException, ExecutionException, FileNotFoundException, IOException {
		GestorDeFicherosBinarios<Ejercicio> gdfb = new GestorDeFicherosBinarios<Ejercicio>(FICHERO_EJERCICIOS);
		GestorDeEjercicios gde = new GestorDeEjercicios(db);
		ArrayList<Ejercicio> ejercicios = gde.obtenerTodosLosEjercicios();

		System.out.println("ESCRIBIENDO Ejercicios...");
		if (null != ejercicios) {
			gdfb.escribir(ejercicios);
		}
	}

	public ArrayList<Ejercicio> leerEjercicios() throws InterruptedException, ExecutionException, FileNotFoundException,
			IOException, ClassNotFoundException {
		ArrayList<Ejercicio> ejercicios = null;
		GestorDeFicherosBinarios<Ejercicio> gdfb = new GestorDeFicherosBinarios<Ejercicio>(FICHERO_EJERCICIOS);
		ejercicios = gdfb.leer();

		return ejercicios;
	}
}

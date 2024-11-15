package epicfitpc.procesos;

import java.io.IOException;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.Firestore;

import epicfitpc.bbdd.GestorDeHistoricos;
import epicfitpc.ficheros.GestorDeBackups;
import epicfitpc.ficheros.GestorDeFicherosXML;
import epicfitpc.modelo.Historico;
import epicfitpc.modelo.Usuario;
import epicfitpc.utils.Conexion;
import epicfitpc.utils.GestorDeConexiones;
import epicfitpc.utils.Rutas;
import epicfitpc.utils.UsuarioLogueado;

public class Procesos implements Runnable {

	@Override
	public void run() {
		if (GestorDeConexiones.getInstance().hayConexion()) {
			Firestore db = conexionFirebase();
			procesoFicheroXML(db);
			procesoFicheroBinario(db);
		} else {
			System.out.println("No se ha podido realizar los backups porque no hay conexión a la bbdd");
		}

	}

	/**
	 * @return
	 */
	public void procesoFicheroXML(Firestore db) {

		System.out.println("Iniciando el proceso de obtención de históricos en segundo plano...");

		Usuario usuario = UsuarioLogueado.getInstance().getUsuario();

		GestorDeHistoricos gestorDeHistoricos = new GestorDeHistoricos(db);
		List<Historico> historicos = null;
		try {
			historicos = gestorDeHistoricos.obtenerTodosLosHistoricosPorUsuario(usuario);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		if(historicos != null) {
			GestorDeFicherosXML gestorDeFicherosXML = new GestorDeFicherosXML();
			gestorDeFicherosXML.crearBackupXML(usuario, historicos, Rutas.BACKUP_HISTORICOS);
		}


		System.out.println("Proceso terminado de la creación de fichero XML de históricos...");
	}

	/**
	 * @param db
	 */
	public void procesoFicheroBinario(Firestore db) {
		System.out.println("Iniciando el proceso de obtención del backup binario...");
		GestorDeBackups gestorDeBackups = new GestorDeBackups(db);
		try {
			gestorDeBackups.realizarBackup();
			System.out.println("Proceso de obtención del backup binario realizado con éxito ...");

		} catch (InterruptedException | ExecutionException | IOException e) {

			System.out.println("¡Error! No ha sido posible obtener el backup binario...");
		}
	}

	/**
	 * @return
	 */
	public Firestore conexionFirebase() {
		// Conectar a Firebase y obtener los datos
		Firestore db = null;
		try {
			db = Conexion.getInstance().getConexion();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return db;
	}

}

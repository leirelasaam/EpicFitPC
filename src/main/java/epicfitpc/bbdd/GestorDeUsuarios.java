package epicfitpc.bbdd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

import epicfitpc.modelo.Historico;
import epicfitpc.modelo.Usuario;
import epicfitpc.utils.DBUtils;

public class GestorDeUsuarios {

	private Firestore db = null;

	public GestorDeUsuarios(Firestore db) {
		this.db = db;
	}

	public ArrayList<Usuario> obtenerTodosLosUsuarios() throws InterruptedException, ExecutionException {
		System.out.println("BBDD: obtenerTodosLosUsuarios");
		ArrayList<Usuario> usuarios = null;
		CollectionReference usuariosDb = db.collection(DBUtils.USUARIOS);
		ApiFuture<QuerySnapshot> futureQuery = usuariosDb.get();
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
			Usuario usuario = documento.toObject(Usuario.class);
			usuario.setId(documento.getId());
			
			if (null == usuarios)
				usuarios = new ArrayList<Usuario>();
			usuarios.add(usuario);
		}
		return usuarios;
	}

	// Para el backup
	public ArrayList<Usuario> obtenerUsuariosConHistoricos() throws InterruptedException, ExecutionException {
		System.out.println("BBDD: obtenerUsuariosConHistoricos");
		ArrayList<Usuario> usuarios = null;
		CollectionReference usuariosDb = db.collection(DBUtils.USUARIOS);
		ApiFuture<QuerySnapshot> futureQuery = usuariosDb.get();
		QuerySnapshot querySnapshot = null;

		try {
			querySnapshot = futureQuery.get();
		} catch (InterruptedException e) {
			throw e;
		} catch (ExecutionException e) {
			throw e;
		}

		GestorDeHistoricos gdh = new GestorDeHistoricos(db);
		List<QueryDocumentSnapshot> documentos = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot documento : documentos) {
			Usuario usuario = documento.toObject(Usuario.class);
			usuario.setId(documento.getId());

			ArrayList<Historico> historicos = gdh.obtenerTodosLosHistoricosPorUsuario(usuario);
			usuario.setHistoricos(historicos);

			if (null == usuarios)
				usuarios = new ArrayList<Usuario>();
			usuarios.add(usuario);
		}
		return usuarios;
	}

	public Usuario obtenerUsuarioPorNombreUsuario(String nombreUsuario)
			throws InterruptedException, ExecutionException {

		Usuario usuario = new Usuario();

		CollectionReference colRef = db.collection("Usuarios");

		// Se realiza la consulta asíncrona
		Query query = colRef.whereEqualTo("usuario", nombreUsuario);

		ApiFuture<QuerySnapshot> future = query.get();
		QuerySnapshot querySnapshot = future.get();

		for (DocumentSnapshot documento : querySnapshot.getDocuments()) {
			if (documento.exists()) {
				usuario = documento.toObject(Usuario.class);
				usuario.setId(documento.getId());
			} else {
				// Devuelve null si no se encuentra el usuario
				System.out.println("No se encontró un usuario con el nombre de usuario especificado: " + nombreUsuario);
			}

		}
		return usuario;
	}

	public boolean guardarUsuarios(Usuario usuario)
			throws InterruptedException, ExecutionException, FileNotFoundException, IOException {
		System.out.println("BBDD: guardarUsuarios");

		Map<String, Object> user = new HashMap<String, Object>();
		user.put("usuario", usuario.getUsuario());
		user.put("pass", usuario.getPass());
		user.put("nombre", usuario.getNombre());
		user.put("apellido", usuario.getApellido());
		user.put("correo", usuario.getCorreo());
		user.put("fechaNac", usuario.getFechaNac());
		user.put("fechaAlt", usuario.getFechaAlt());
		user.put("esEntrenador", usuario.isEsEntrenador());
		user.put("nivel", usuario.getNivel());

		CollectionReference usuarios = db.collection(DBUtils.USUARIOS);
		DocumentReference devolver = usuarios.add(user).get();
		if (devolver.getId() != null)
			return true;
		return false;
	}

	public boolean actualizarNivelUsuario(String usuarioId, int nuevoNivel)
			throws InterruptedException, ExecutionException {
		//Así lo propone firebase
	    DocumentReference docRef = db.collection(DBUtils.USUARIOS).document(usuarioId);
	    ApiFuture<WriteResult> future = docRef.update("nivel", nuevoNivel);
	    WriteResult result = future.get();
	    return result != null;
	}

	public Usuario comprobarUsuario(String usuarioIntroducido, String contraseniaIntroducida) throws Exception {
		ArrayList<Usuario> usuarios = obtenerTodosLosUsuarios();

		// Recorremos los usuarios para buscar el usuario introducido
		for (Usuario usuario : usuarios) {

			// Verificar que userName no sea null antes de comparar
			if (usuario.getUsuario() != null && usuario.getUsuario().equalsIgnoreCase(usuarioIntroducido)) {
				// Usuario encontrado, ahora verificamos la contraseña
				if (usuario.getPass() != null && usuario.getPass().equals(contraseniaIntroducida)) {
					// Usuario y contraseña correctos
					JOptionPane.showMessageDialog(null, "Acceso concedido.");
					return usuario; // Devuelve el usuario si ambas condiciones son correctas
				} else {
					// Si la contraseña no es correcta, lanzamos excepción genérica
					JOptionPane.showMessageDialog(null, "Datos introducidos incorrectos.");
					throw new Exception("Datos incorrectos.");
				}
			}
		}

		// Si no se encuentra el usuario en la base de datos, lanzamos otra excepción
		// genérica
		JOptionPane.showMessageDialog(null, "Datos introducidos incorrectos.");
		throw new Exception("Datos incorrectos.");
	}

	public boolean comprobarSiExisteNombreUsuario(String usuarioIntroducido) throws Exception {
		ArrayList<Usuario> usuarios = obtenerTodosLosUsuarios();

		for (Usuario usuario : usuarios) {

			if (usuario.getUsuario() != null && usuario.getUsuario().equals(usuarioIntroducido)) {
				return true;
			}
		}
		return false;
	}

	public boolean modificarUsuario(Usuario usuarioActualizado, String nombreUsuarioOriginal) {

		Usuario usuario = null;
		try {
			usuario = obtenerUsuarioPorNombreUsuario(nombreUsuarioOriginal);

		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		CollectionReference usuarioDocRef = db.collection("Usuarios");

		Query query = usuarioDocRef.whereEqualTo("usuario", nombreUsuarioOriginal);
		ApiFuture<QuerySnapshot> querySnapshotFuture = query.get();

		try {

			QuerySnapshot querySnapshot = querySnapshotFuture.get();

			if (!querySnapshot.isEmpty()) {
				// Obtener el primer documento coincidente
				DocumentSnapshot document = querySnapshot.getDocuments().get(0);

				// Mapear los campos del usuario para actualizar
				Map<String, Object> updates = new HashMap<>();
				updates.put("nombre", usuarioActualizado.getNombre());
				updates.put("apellido", usuarioActualizado.getApellido());
				updates.put("correo", usuarioActualizado.getCorreo());
				updates.put("usuario", usuarioActualizado.getUsuario());
				updates.put("pass", usuario.getPass());
				updates.put("fechaNac", usuarioActualizado.getFechaNac());
				updates.put("fechaAlt", usuario.getFechaAlt());
				updates.put("esEntrenador", usuario.isEsEntrenador());
				updates.put("nivel", usuario.getNivel());

				// Actualizar el documento en Firestore

				ApiFuture<WriteResult> writeResult = document.getReference().update(updates);
				writeResult.get(); // Esperar a que se complete la actualización

				// Comprobar si la actualización fue exitosa
				writeResult.get(); // Se bloquea hasta que la actualización se complete
				System.out.println("Datos de usuario actualizados correctamente.");
				return true;

			} else {

				System.out.println("No se encontró el nombre de usuario especificado.");
				return false;
			}

		} catch (InterruptedException | ExecutionException e) {
			System.err.println("Error al actualizar el usuario: " + e.getMessage());
			return false;
		}
	}

}

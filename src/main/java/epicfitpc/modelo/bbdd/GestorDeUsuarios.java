package epicfitpc.modelo.bbdd;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import epicfitpc.modelo.pojos.Usuario;

public class GestorDeUsuarios {

	private Firestore db = null;

	public GestorDeUsuarios(Firestore db) {
		this.db = db;
	}

	public ArrayList<Usuario> obtenerTodosLosUsuarios() throws InterruptedException, ExecutionException {
		ArrayList<Usuario> usuarios = null;

		CollectionReference usuariosDb = db.collection("Usuarios");
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
			String id = documento.getId();
			String nombre = documento.getString("nombre");
			String apellido = documento.getString("apellido");
			String correo = documento.getString("correo");
			String pass = documento.getString("pass");
			double nivel = documento.getDouble("nivel");
			Timestamp fechaNac = documento.getTimestamp("fechaNac");
			Timestamp fechaAlt = documento.getTimestamp("fechaAlt");
			boolean esEntrenador = documento.getBoolean("esEntrenador");

			Usuario usuario = new Usuario(id, nombre, apellido, correo, pass, nivel, fechaNac, fechaAlt, esEntrenador);
			
			if (null == usuarios)
				usuarios = new ArrayList<Usuario>();
			
			usuarios.add(usuario);
		}

		return usuarios;
	}
	public boolean comprobarUsuario(String usuarioIntroducido, String contraseniaIntroducida) throws Exception {
		boolean ret = false;
		if (comprobarUsuario(usuarioIntroducido) && comprobarContrasenia(usuarioIntroducido, contraseniaIntroducida)) 
			ret = true;
		return ret;
	}
	
	private boolean comprobarUsuario(String login) throws Exception {
		UserManager userManager = new UserManager();
		boolean ret = false;
		if (login.equalsIgnoreCase(userManager.getLogin(login)))
			ret = true;
		return ret;
	}

	private boolean comprobarContrasenia(String login, String password) throws Exception {
		UserManager userManager = new UserManager();
		boolean ret = false;
		if (password.equals(userManager.getPassword(login)))
			ret = true;
		return ret;
	}

	 {
		// TODO Auto-generated method stub
		return false;
	}

}

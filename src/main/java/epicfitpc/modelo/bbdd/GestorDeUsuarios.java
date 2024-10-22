package epicfitpc.modelo.bbdd;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import epicfitpc.modelo.pojos.Usuario;
import epicfitpc.utils.Conexion;

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
			double nivel = documento.getDouble("nivel");
			Timestamp fechaNac = documento.getTimestamp("fechaNac");
			Timestamp fechaAlt = documento.getTimestamp("fechaAlt");
			boolean esEntrenador = documento.getBoolean("esEntrenador");

			String user = documento.getString("usuario");
			String pass = documento.getString("pass");

			Usuario usuario = new Usuario(id, nombre, apellido, correo, pass, nivel, fechaNac, fechaAlt, esEntrenador);

			if (null == usuarios)
				usuarios = new ArrayList<Usuario>();

			usuarios.add(usuario);
		}

		return usuarios;
	}

	public Usuario comprobarUsuario(String usuarioIntroducido, String contraseniaIntroducida) throws Exception {
	    Firestore db = Conexion.getConexion();
	    ArrayList<Usuario> usuarios = obtenerTodosLosUsuarios();
	    boolean usuarioEncontrado = false;

	    for (Usuario usuario : usuarios) {
	        // Comprobamos si el usuario introducido existe
	        if (usuario.user.toString().equals(usuarioIntroducido)) {
	            usuarioEncontrado = true;
	            // Si el usuario es correcto, verificamos la contraseña
	            if (usuario.getPass().equals(contraseniaIntroducida)) {
	                JOptionPane.showMessageDialog(null, "Usuario y contraseña correctos.");
	                return usuario; // Retorna el usuario si ambas condiciones son correctas
	            } else {
	                // Si la contraseña no es correcta, lanza una excepción
	                throw new Exception("Contraseña incorrecta.");
	            }
	        }
	    }

	    // Si no encuentra el usuario, lanza una excepción y muestra mensaje
	    if (!usuarioEncontrado) {
	        JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
	        throw new Exception("El usuario no existe.");
	    }

	    return null;
	}


}

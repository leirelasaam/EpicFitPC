package epicfitpc.bbdd;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import epicfitpc.modelo.Usuario;

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
			Usuario usuario = documento.toObject(Usuario.class);
			usuario.setId(documento.getId());

			if (null == usuarios)
				usuarios = new ArrayList<Usuario>();
			usuarios.add(usuario);
		}
		return usuarios;
	}

	public Usuario comprobarUsuario(String usuarioIntroducido, String contraseniaIntroducida) throws Exception {
	    ArrayList<Usuario> usuarios = obtenerTodosLosUsuarios();
	    
	    // Recorremos los usuarios para buscar el usuario introducido
	    for (Usuario usuario : usuarios) {

	    	// Verificar que userName no sea null antes de comparar
	        if (usuario.getUsuario() != null && usuario.getUsuario().equals(usuarioIntroducido)) {
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
	    
	    // Si no se encuentra el usuario en la base de datos, lanzamos otra excepción genérica
	    JOptionPane.showMessageDialog(null, "Datos introducidos incorrectos.");
	    throw new Exception("Datos incorrectos.");
	}

	
	//Prueba para comprobar que se conecta a la firebase e imprime todos los usuarios -> ID: zoVUUYKznIh8KDXOxjUc, Nombre: Leire, Usuario: 1234
	public void imprimirTodosLosUsuarios() throws Exception {
	    // Obtenemos todos los usuarios
	    ArrayList<Usuario> usuarios = obtenerTodosLosUsuarios();

	    // Si hay usuarios en la lista, los imprime
	    if (usuarios != null && !usuarios.isEmpty()) {
	        System.out.println("Usuarios encontrados:");
	        for (Usuario usuario : usuarios) {
	            System.out.println("ID: " + usuario.getId() + ", Nombre: " + usuario.getNombre() + ", Usuario: " + usuario.getUsuario());
	        }
	    } else {
	        System.out.println("No se encontraron usuarios.");
	    }
	}
	
/*
	Firestore db;
	try {
		db = Conexion.getInstance().getConexion();
		GestorDeWorkouts gdw = new GestorDeWorkouts(db);
		workouts = gdw.obtenerTodosLosWorkouts();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
*/	
}

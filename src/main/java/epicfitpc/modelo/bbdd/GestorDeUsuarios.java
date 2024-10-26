package epicfitpc.modelo.bbdd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
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

			Usuario usuario = new Usuario(id, nombre, apellido, correo, user, pass, nivel, fechaNac, fechaAlt,
					esEntrenador);

			if (null == usuarios)
				usuarios = new ArrayList<Usuario>();
			usuarios.add(usuario);
		}
		return usuarios;
	}

	public boolean guardarUsuarios(Usuario usuario)
			throws InterruptedException, ExecutionException, FileNotFoundException, IOException {

		Map<String, Object> user = new HashMap<String, Object>();
		user.put("usuario", usuario.getUser());
		user.put("pass", usuario.getPass());
		user.put("nombre", usuario.getNombre());
		user.put("apellido", usuario.getApellido());
		user.put("correo", usuario.getCorreo());
		user.put("fechaNac", java.sql.Timestamp.valueOf(usuario.getFechaNac().atStartOfDay()));
		user.put("fechaAlt", java.sql.Timestamp.valueOf(usuario.getFechaAlt().atStartOfDay()));
		user.put("esEntrenador", usuario.getIsEsEntrenador());
		user.put("nivel", usuario.getNivel());
		
	
		CollectionReference usuarios = db.collection("Usuarios");
		DocumentReference devolver = usuarios.add(user).get();
		if(devolver.getId() != null)
			return true;
		return false;
	}

	public Usuario comprobarUsuario(String usuarioIntroducido, String contraseniaIntroducida) throws Exception {
		Firestore db = Conexion.getInstance().getConexion();
		ArrayList<Usuario> usuarios = obtenerTodosLosUsuarios();

		// Recorremos los usuarios para buscar el usuario introducido
		for (Usuario usuario : usuarios) {

			// Verificar que userName no sea null antes de comparar
			if (usuario.getUser() != null && usuario.getUser().equals(usuarioIntroducido)) {
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
//
//	public Usuario comprobarRegistro(Usuario usuario ) throws FileNotFoundException, IOException {
//		Firestore db = Conexion.getInstance().getConexion();
//		return usuario;
//		
//		
//	}

    public boolean validarNombre(String nombre) {
        return nombre != null && !nombre.isEmpty() && nombre.length() <= 50;
    }

    public boolean validarApellido(String apellido) {
        boolean validar = apellido != null && !apellido.isEmpty() && apellido.length() <= 50;
    	return validar;
    }

    public boolean validarCorreo(String correo) {
        String regexCorreo = "^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,6}$";
        return correo != null && Pattern.matches(regexCorreo, correo);
    }

    public boolean validarUsername(String user) {
        String regexUser = "^(?=.*[A-Z])(?=.*[a-z]){2,15}$";
        return user != null && Pattern.matches(regexUser, user);
    }

    public boolean validarPassword(String pass) {
        String regexPass = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!_]).{8,20}$";
        return pass != null && Pattern.matches(regexPass, pass);
    }

    
    public boolean validarFechaNacimiento(LocalDate fechaNac) {
        LocalDate fechaMinima = LocalDate.now().minusYears(14);
        return fechaNac != null && fechaNac.isBefore(fechaMinima);
    }

	// Prueba para comprobar que se conecta a la firebase e imprime todos los
	// usuarios -> ID: zoVUUYKznIh8KDXOxjUc, Nombre: Leire, Usuario: 1234
	public void imprimirTodosLosUsuarios() throws Exception {
		// Conexión a firestore
		Firestore db = Conexion.getInstance().getConexion();

		// Obtenemos todos los usuarios
		ArrayList<Usuario> usuarios = obtenerTodosLosUsuarios();

		// Si hay usuarios en la lista, los imprime
		if (usuarios != null && !usuarios.isEmpty()) {
			System.out.println("Usuarios encontrados:");
			for (Usuario usuario : usuarios) {
				System.out.println("ID: " + usuario.getId() + ", Nombre: " + usuario.getNombre() + ", Usuario: "
						+ usuario.getUser());
			}
		} else {
			System.out.println("No se encontraron usuarios.");
		}
	}
}
/*
 * Firestore db; try { db = Conexion.getInstance().getConexion();
 * GestorDeWorkouts gdw = new GestorDeWorkouts(db); workouts =
 * gdw.obtenerTodosLosWorkouts(); } catch (Exception e) { // TODO Auto-generated
 * catch block e.printStackTrace(); }
 */

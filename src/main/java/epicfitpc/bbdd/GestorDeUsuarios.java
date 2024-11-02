package epicfitpc.bbdd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import epicfitpc.modelo.Historico;
import epicfitpc.modelo.Usuario;
import epicfitpc.utils.DBUtils;

public class GestorDeUsuarios {
	
	private Firestore db = null;

	public GestorDeUsuarios(Firestore db) {
		this.db = db;
	}

	public ArrayList<Usuario> obtenerTodosLosUsuarios() throws InterruptedException, ExecutionException {
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
	
	// Para el backup
	public ArrayList<Usuario> obtenerUsuariosConHistoricos() throws InterruptedException, ExecutionException {
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

	public boolean guardarUsuarios(Usuario usuario)
			throws InterruptedException, ExecutionException, FileNotFoundException, IOException {

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
	

	public boolean comprobarSiExisteNombreUsuario(String usuarioIntroducido) throws Exception {
		ArrayList<Usuario> usuarios = obtenerTodosLosUsuarios();

		for (Usuario usuario : usuarios) {

			if (usuario.getUsuario() != null && usuario.getUsuario().equals(usuarioIntroducido)) {
				return true;
			}
		}
		return false;
	}
	

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
		String regexUser = "^(?=.*[a-z])(?=.*[A-Z]).{2,15}$";
		return user != null && Pattern.matches(regexUser, user);
	}

	public boolean validarPassword(String pass) {
		String regexPass = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!_]).{8,20}$";
		return pass != null && Pattern.matches(regexPass, pass);
	}

	public boolean validarFechaNacimiento(Timestamp fechaNac) {
		LocalDate fechaMinima = LocalDate.now().minusYears(14);
		LocalDate fechaNacLocal = fechaNac.toDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
		return fechaNac != null && fechaNacLocal.isBefore(fechaMinima);
	}
}

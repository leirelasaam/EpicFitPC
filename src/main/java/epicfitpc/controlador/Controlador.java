package epicfitpc.controlador;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.regex.Pattern;

import com.google.cloud.Timestamp;

import epicfitpc.modelo.Usuario;

public class Controlador {
	public Controlador() {

	}

	public Usuario comprobarUsuario(ArrayList<Usuario> usuarios, String usuarioIntroducido,
			String contraseniaIntroducida) throws Exception {
		// Recorremos los usuarios para buscar el usuario introducido
		for (Usuario usuario : usuarios) {

			// Verificar que userName no sea null antes de comparar
			if (usuario.getUsuario() != null && usuario.getUsuario().equals(usuarioIntroducido)) {
				// Usuario encontrado, ahora verificamos la contraseña
				if (usuario.getPass() != null && usuario.getPass().equals(contraseniaIntroducida)) {
					// Usuario y contraseña correctos
					// JOptionPane.showMessageDialog(null, "Acceso concedido.");
					return usuario; // Devuelve el usuario si ambas condiciones son correctas
				} else {
					// Si la contraseña no es correcta, lanzamos excepción genérica
					// JOptionPane.showMessageDialog(null, "Datos introducidos incorrectos.");
					throw new Exception("Datos incorrectos.");
				}
			}
		}
		// Si no se encuentra el usuario en la base de datos, lanzamos otra excepción
		// genérica
		// JOptionPane.showMessageDialog(null, "Datos introducidos incorrectos.");
		throw new Exception("Datos incorrectos.");
	}
	
	public boolean validarNombre(String nombre) {
		return nombre != null && !nombre.isEmpty() && nombre.length() <= 25;
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
		LocalDate fechaNacLocal = fechaNac.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return fechaNac != null && fechaNacLocal.isBefore(fechaMinima);
	}
}

package epicfitpc.validaciones;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.regex.Pattern;

import com.google.cloud.Timestamp;

/**
 * Clase que contiene funciones para llevar a cabo diferentes validaciones.
 */
public class GestorDeValidaciones {
	public GestorDeValidaciones() {

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

package epicfitpc.controlador;

import java.util.ArrayList;

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
}

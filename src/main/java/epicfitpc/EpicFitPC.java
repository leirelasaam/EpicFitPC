package epicfitpc;

import java.awt.EventQueue;
/*
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.Firestore;

import epicfitpc.modelo.bbdd.GestorDeUsuarios;
import epicfitpc.modelo.pojos.Usuario;
import epicfitpc.utils.Conexion;
*/
import epicfitpc.vista.MainFrame;

// Esta clase debe lanzar la aplicación
public class EpicFitPC {

	public static void main(String[] args) {
		/*
		// Probando, esto no debería ir aquí, solamente es una prueba
		Firestore db;
		GestorDeUsuarios gdu;
		try {
			db = Conexion.getConexion();
			gdu = new GestorDeUsuarios(db);
			ArrayList<Usuario> usuarios = gdu.obtenerTodosLosUsuarios();
			for (Usuario usuario : usuarios) {
				System.out.println(usuario.toString());
			}
		} catch (IOException | InterruptedException | ExecutionException e) {
			System.out.println(e);
		}
		*/
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
				}
			}
		});

	}

}

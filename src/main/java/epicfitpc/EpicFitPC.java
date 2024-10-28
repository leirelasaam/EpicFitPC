package epicfitpc;

import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.swing.UIManager;

import epicfitpc.ficheros.GestorDeBackups;
import epicfitpc.utils.Conexion;
import epicfitpc.utils.Estilos;
import epicfitpc.utils.GestorDeConexiones;
import epicfitpc.vista.MainFrame;
import com.google.cloud.firestore.Firestore;

// Esta clase debe lanzar la aplicación
public class EpicFitPC {

	public static void main(String[] args) {
		Firestore db;
		GestorDeBackups gdb = null;
		try {
			db = Conexion.getInstance().getConexion();
			gdb = new GestorDeBackups(db);
			
			boolean conectado = GestorDeConexiones.getInstance().hayConexion();
			if (conectado) {
				System.out.println("Hay conexión a Internet");
				gdb.realizarBackup();
				gdb.cargarBackup();
			} else {
				gdb.cargarBackup();
				System.out.println("No hay conexión a Internet");
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Colores comboBox
		UIManager.put("ComboBox.selectionBackground", Estilos.PRIMARY_DARK);
		UIManager.put("ComboBox.selectionForeground", Estilos.WHITE);
		
        // Configuración de colores para el JTabbedPane
        UIManager.put("TabbedPane.selectedBackground", Estilos.DARK_BACKGROUND);
        UIManager.put("TabbedPane.selectedForeground", Estilos.WHITE);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = MainFrame.getInstance();
                    frame.setVisible(true);
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
				}
			}
		});

	}

}

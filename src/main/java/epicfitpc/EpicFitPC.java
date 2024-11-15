package epicfitpc;

import java.awt.EventQueue;
import javax.swing.UIManager;
import epicfitpc.utils.Estilos;
import epicfitpc.vista.MainFrame;

/*
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import com.google.cloud.firestore.Firestore;
import epicfitpc.ficheros.GestorDeBackups;
import epicfitpc.utils.Conexion;
import epicfitpc.utils.GestorDeConexiones;
*/

/**
 * Clase que lanza la aplicación.
 */
public class EpicFitPC {

	public static void main(String[] args) {
		try {
			// Establecer tema según sistema operativo
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// Colores comboBox
			UIManager.put("ComboBox.selectionBackground", Estilos.PRIMARY_DARK);
			UIManager.put("ComboBox.selectionForeground", Estilos.WHITE);
			UIManager.put("ComboBox.font", Estilos.FONT_SMALL_BOLD);

			// Configuración de colores para el JTabbedPane
			UIManager.put("TabbedPane.selectedBackground", Estilos.BACKGROUND);
			UIManager.put("TabbedPane.selectedForeground", Estilos.PRIMARY);
			UIManager.put("TabbedPane.font", Estilos.FONT_SMALL_BOLD);

			// Configurar fuente
			UIManager.put("Label.font", Estilos.FONT_SMALL);
			UIManager.put("Button.font", Estilos.FONT_SMALL_BOLD);
			UIManager.put("TextField.font", Estilos.FONT_SMALL);
		} catch (Exception e) {
			e.printStackTrace();
		}

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

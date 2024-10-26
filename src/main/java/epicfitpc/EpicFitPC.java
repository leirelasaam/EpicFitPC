package epicfitpc;

import java.awt.EventQueue;
import epicfitpc.vista.MainFrame;

// Esta clase debe lanzar la aplicación
public class EpicFitPC {

	public static void main(String[] args) {
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

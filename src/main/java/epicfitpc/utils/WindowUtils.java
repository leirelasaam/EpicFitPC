package epicfitpc.utils;


import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class WindowUtils {

	/**
	 * Genera un JOptionPane para indicar un error, añadiendo un icono personalizado.
	 * 
	 * @param message mensaje de error
	 * @param title   título
	 */
	public static void errorPane(String message, String title) {
		UIManager.put("OptionPane.background", Estilos.BACKGROUND);
		UIManager.put("OptionPane.messagebackground", Estilos.BACKGROUND);
		UIManager.put("Panel.background", Estilos.BACKGROUND);
		UIManager.put("Button.background", Color.WHITE);
		UIManager.put("Button.foreground", Color.BLACK);

		ImageIcon icon = new ImageIcon(Rutas.ICONO_CROSS);
		Image img = icon.getImage();
		Image resizedImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		icon.setImage(resizedImg);

		JOptionPane.showMessageDialog(null, "<html>" + message + "</html>", title,
				JOptionPane.INFORMATION_MESSAGE, icon);
	}

	/**
	 * Genera un JOptionPane para indicar una confirmación, añadiendo un icono personalizado.
	 * 
	 * @param message mensaje
	 * @param title   título
	 */
	public static void confirmationPane(String message, String title) {
		UIManager.put("OptionPane.background", Estilos.BACKGROUND);
		UIManager.put("OptionPane.messagebackground", Estilos.BACKGROUND);
		UIManager.put("Panel.background", Estilos.BACKGROUND);
		UIManager.put("Button.background", Color.WHITE);
		UIManager.put("Button.foreground", Color.BLACK);

		ImageIcon icon = new ImageIcon(Rutas.ICONO_TICK);
		Image img = icon.getImage();
		Image resizedImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		icon.setImage(resizedImg);

		JOptionPane.showMessageDialog(null, "<html>" + message + "</html>", title,
				JOptionPane.INFORMATION_MESSAGE, icon);
	}

	/**
	 * Crea una imagen usando la ruta indicada.
	 * 
	 * @param rutaImagen
	 * @param ancho ancho de la imagen
	 * @param alto alto de la imagen
	 * @return ImageIcon con la imagen y redimensionada
	 */
    public static ImageIcon cargarImagen(String rutaImagen, int ancho, int alto) {
        ImageIcon iconoOriginal = new ImageIcon(rutaImagen);
        Image imagenOriginal = iconoOriginal.getImage();
        Image imagenRedimensionada = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    }

}

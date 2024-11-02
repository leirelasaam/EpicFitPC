package epicfitpc.utils;


import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * Defines a class to create a specified JOptionPane with a custom icon.
 */
public class WindowUtils {

	/**
	 * Creates a message JOptionPane with a custom icon.
	 * 
	 * @param message custom message that appears in the JOptionPane
	 * @param title   title of the JOptionPane
	 * @param path    path of the image file location
	 */
	public static void messagePaneWithIcon(String message, String title, String path) {
		UIManager.put("OptionPane.background", Color.BLACK);
		UIManager.put("OptionPane.messagebackground", Color.BLACK);
		UIManager.put("Panel.background", Color.BLACK);
		UIManager.put("Button.background", Color.WHITE);
		UIManager.put("Button.foreground", Color.BLACK);

		ImageIcon icon = new ImageIcon(path);
		Image img = icon.getImage();
		Image resizedImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		icon.setImage(resizedImg);

		JOptionPane.showMessageDialog(null, "<html><font color='white'>" + message + "</font></html>", title,
				JOptionPane.INFORMATION_MESSAGE, icon);
	}

	/**
	 * Creates an error message JOptionPane.
	 * 
	 * @param message error message that appears in the JOptionPane
	 * @param title   title of the JOptionPane
	 */
	public static void errorPane(String message, String title) {
		UIManager.put("OptionPane.background", Color.BLACK);
		UIManager.put("OptionPane.messagebackground", Color.BLACK);
		UIManager.put("Panel.background", Color.BLACK);
		UIManager.put("Button.background", Color.WHITE);
		UIManager.put("Button.foreground", Color.BLACK);

		ImageIcon icon = new ImageIcon("img/icon/cross.png");
		Image img = icon.getImage();
		Image resizedImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		icon.setImage(resizedImg);

		JOptionPane.showMessageDialog(null, "<html><font color='white'>" + message + "</font></html>", title,
				JOptionPane.INFORMATION_MESSAGE, icon);
	}

	/**
	 * Creates a confirmation message JOptionPane.
	 * 
	 * @param message confirmation message that appears in the JOptionPane
	 * @param title   title of the JOptionPane
	 */
	public static void confirmationPane(String message, String title) {
		UIManager.put("OptionPane.background", Color.BLACK);
		UIManager.put("OptionPane.messagebackground", Color.BLACK);
		UIManager.put("Panel.background", Color.BLACK);
		UIManager.put("Button.background", Color.WHITE);
		UIManager.put("Button.foreground", Color.BLACK);

		ImageIcon icon = new ImageIcon("img/icon/tick.png");
		Image img = icon.getImage();
		Image resizedImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		icon.setImage(resizedImg);

		JOptionPane.showMessageDialog(null, "<html><font color='white'>" + message + "</font></html>", title,
				JOptionPane.INFORMATION_MESSAGE, icon);
	}

	/**
	 * Creates a yes or no answer JOptionPane.
	 * 
	 * @param message custom message that appears in the JOptionPane
	 * @param title   title of the JOptionPane
	 * @param path    path of the image file location
	 * @return 0 if answer is no or cancel and 1 if it's yes.
	 */
	public static int yesOrNoPaneWithIcon(String message, String title, String path) {
		UIManager.put("OptionPane.background", Color.BLACK);
		UIManager.put("OptionPane.messagebackground", Color.BLACK);
		UIManager.put("Panel.background", Color.BLACK);
		UIManager.put("Button.background", Color.WHITE);
		UIManager.put("Button.foreground", Color.BLACK);

		JFrame frame = new JFrame();
		String[] options = new String[2];
		options[0] = "Sí";
		options[1] = "No";

		ImageIcon icon = new ImageIcon(path);
		Image img = icon.getImage();
		Image resizedImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		icon.setImage(resizedImg);

		int ret = JOptionPane.showOptionDialog(frame.getContentPane(),
				"<html><font color='white'>" + message + "</font></html>", title, JOptionPane.YES_NO_OPTION,
				JOptionPane.YES_NO_OPTION, icon, options, null);

		return ret;
	}

	/**
	 * Creates an input type JOptionPane.
	 * 
	 * @param message custom message that appears in the JOptionPane
	 * @param title   title of the JOptionPane
	 * @param path    path of the image file location
	 * @return string with the answer
	 */
	public static String inputPaneWithIcon(String message, String title, String path) {
		UIManager.put("OptionPane.background", Color.BLACK);
		UIManager.put("OptionPane.messagebackground", Color.BLACK);
		UIManager.put("Panel.background", Color.BLACK);
		UIManager.put("Button.background", Color.WHITE);
		UIManager.put("Button.foreground", Color.BLACK);

		ImageIcon icon = new ImageIcon(path);
		Image img = icon.getImage();
		Image resizedImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		icon.setImage(resizedImg);

		JTextField textField = new JTextField();

		Object[] components = { "<html><font color='white'>" + message + "</font></html>", textField, };
		JOptionPane.showMessageDialog(null, components, title, JOptionPane.PLAIN_MESSAGE, icon);

		return textField.getText();
	}

	/**
	 * Adds a resized image to a label placed inside a panel.
	 * 
	 * @param panel panel that contains the label
	 * @param label label where the image is printed
	 * @param path  path of the image file location
	 */
	public static void addImageResized(JPanel panel, JLabel label, String path) {
		ImageIcon icon = new ImageIcon(path);
		Image img = icon.getImage();
		Image resizedImg = img.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_SMOOTH);
		icon.setImage(resizedImg);
		label.setIcon(icon);
	}
	
    public static ImageIcon cargarImagen(String rutaImagen, int ancho, int alto) {
        ImageIcon iconoOriginal = new ImageIcon(rutaImagen);
        Image imagenOriginal = iconoOriginal.getImage();
        Image imagenRedimensionada = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    }

	/**
	 * Adds a non resized .gif file to a label.
	 * 
	 * @param label label where the image is printed
	 * @param path  path of the image file location
	 */
	public static void addGif(JLabel label, String path) {
		ImageIcon icon = new ImageIcon(path);
		label.setIcon(icon);
	}

}

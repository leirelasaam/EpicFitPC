package epicfitpc.utils;

import java.awt.Color;
import java.awt.Font;

/**
 * Clase que contiene constantes utilizadas para aplicar estilos a los elementos
 * de la interfaz.
 */
public class Estilos {

	// Definición de colores como constantes
	public static final Color WHITE = Color.WHITE;
	public static final Color BLACK = Color.BLACK;
	public static final Color PRIMARY = Color.decode("#FD8F00");
	public static final Color PRIMARY_DARK = Color.decode("#DA790A");
	public static final Color CARD_BACKGROUND = Color.decode("#FFFFFF");
	public static final Color BACKGROUND = Color.decode("#F0F0F0");

	// Definición de fuentes
	public static final Font FONT_SMALL = new Font("Noto Sans", Font.PLAIN, 14);
	public static final Font FONT_SMALL_BOLD = new Font("Noto Sans", Font.BOLD, 14);
	public static final Font FONT_MEDIUM = new Font("Noto Sans", Font.BOLD, 18);

	// Constructor privado para evitar instanciación
	private Estilos() {

	}
}

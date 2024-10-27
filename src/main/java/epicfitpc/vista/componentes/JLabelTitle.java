package epicfitpc.vista.componentes;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import epicfitpc.utils.Estilos;

public class JLabelTitle extends JLabel {
	
	private static final long serialVersionUID = 1058696627733465065L;
	Font font = Estilos.FONT_MEDIUM;
	Color fg = Estilos.PRIMARY_DARK;
	
	public JLabelTitle() {
		
	}
	
	public JLabelTitle(String s) {
		this.setText(s);
		this.setFont(font);
		this.setForeground(fg);
	}
	
	public JLabelTitle(String s, Color color) {
		this.setText(s);
		this.setFont(font);
		this.setForeground(color);
	}
	
	public JLabelTitle(String s, Color color, Font f) {
		this.setText(s);
		this.setFont(f);
		this.setForeground(color);
	}

}


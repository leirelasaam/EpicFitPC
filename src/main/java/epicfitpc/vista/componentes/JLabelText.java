package epicfitpc.vista.componentes;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import epicfitpc.utils.Estilos;

public class JLabelText extends JLabel {
	
	private static final long serialVersionUID = -8720439129431049992L;
	Font font = Estilos.FONT_SMALL;
	Color fg = Estilos.BLACK;
	
	public JLabelText() {
		
	}
	
	public JLabelText(String s) {
		this.setText(s);
		this.setFont(font);
		this.setForeground(fg);
	}
	
	public JLabelText(String s, Color color) {
		this.setText(s);
		this.setFont(font);
		this.setForeground(color);
	}
	
	public JLabelText(String s, Color color, Font f) {
		this.setText(s);
		this.setFont(f);
		this.setForeground(color);
	}

}

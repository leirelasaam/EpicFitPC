package epicfitpc.vista.componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

import epicfitpc.utils.Estilos;

public class JButtonPrimary extends JButton implements MouseListener{

	private static final long serialVersionUID = 7471587497181334610L;
	Font defaultFont = Estilos.FONT_SMALL_BOLD;
    Color textColor = Color.decode("#ffffff");
    Color backgroundColor = Estilos.PRIMARY;
    Color hoverColor = Estilos.PRIMARY_DARK;
    Dimension buttonSize = new Dimension(100, 30);
    
    public JButtonPrimary(String s) {
        s = s.toUpperCase();
        this.setFocusPainted(false);
        this.setText(s);
        this.setBorder(null);
        this.setForeground(textColor);
        this.setBackground(backgroundColor);
        this.setFont(defaultFont);
        this.setOpaque(true);
        setMinimumSize(buttonSize);
        setPreferredSize(buttonSize);
        setMaximumSize(buttonSize);
        addMouseListener(this);
    }
    public JButtonPrimary(String s, Color backgroundColor, Color hoverColor) {
        s = s.toUpperCase();
        this.setFocusPainted(false);
        this.setText(s);
        this.setBorder(null);
        this.setHoverColor(hoverColor);
        this.setBackground(backgroundColor);
        this.setFont(defaultFont);
        this.setOpaque(true);
        setMinimumSize(buttonSize);
        setPreferredSize(buttonSize);
        setMaximumSize(buttonSize);
        addMouseListener(this);
    }
    
    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        this.setBackground(color);
    }
    public void setHoverColor(Color color) {
        this.hoverColor = color; 
    }

    @Override public void mouseClicked(MouseEvent me) {}
    @Override public void mouseReleased(MouseEvent me) {}
    @Override public void mousePressed(MouseEvent me) {}
    
    @Override
    public void mouseEntered(MouseEvent e) { 
        if (e.getSource()==this) {  
            this.setBackground(this.hoverColor); 
        }
    }
    @Override
    public void mouseExited(MouseEvent e) { 
        if (e.getSource()==this) { 
            this.setBackground(this.backgroundColor); 
        }
    }
}

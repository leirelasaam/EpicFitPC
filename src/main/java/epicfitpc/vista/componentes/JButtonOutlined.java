package epicfitpc.vista.componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import epicfitpc.utils.Estilos;

public class JButtonOutlined extends JButton implements MouseListener {

    private static final long serialVersionUID = 1L;
    Font defaultFont = Estilos.FONT_SMALL;
    Color textColor = Estilos.PRIMARY;
    Color borderColor = Estilos.PRIMARY;
    Color hoverColor = Color.white;
    Color backgroundColor = Color.decode("#ffffff");
    Dimension buttonSize = new Dimension(100, 30);

    public JButtonOutlined(String s) {
        s = s.toUpperCase();
        this.setFocusPainted(false);
        this.setText(s);
        this.setBorder(BorderFactory.createLineBorder(borderColor, 2));
        this.setForeground(borderColor);
        this.setBackground(backgroundColor);
        this.setFont(defaultFont);
        this.setOpaque(false);
        addMouseListener(this);
    }

    public JButtonOutlined(String s, Color borderColor, Color hoverColor) {
        s = s.toUpperCase();
        this.setFocusPainted(false);
        this.setText(s);
        this.setBorder(BorderFactory.createLineBorder(borderColor, 2));
        this.setHoverColor(hoverColor);
        this.setBorderColor(borderColor);
        this.setFont(defaultFont);
        this.setOpaque(false);
        setMinimumSize(buttonSize);
        setPreferredSize(buttonSize);
        setMaximumSize(buttonSize);
        addMouseListener(this);
    }

    public void setBorderColor(Color color) {
        borderColor = color;
        this.setBorder(BorderFactory.createLineBorder(borderColor, 2));
        this.setForeground(borderColor);
    }

    public void setHoverColor(Color color) {
        hoverColor = color;
    }

    @Override public void mouseClicked(MouseEvent me) {}
    @Override public void mouseReleased(MouseEvent me) {}
    @Override public void mousePressed(MouseEvent me) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == this) {
            this.setBackground(hoverColor);
            this.setOpaque(true);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == this) {
            this.setBackground(backgroundColor);
            this.setOpaque(false);
        }
    }
}


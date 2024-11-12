package epicfitpc.vista.componentes;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import epicfitpc.utils.Estilos;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;

/**
 * Panel con forma redondeada.
 */
public class RoundedPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private int cornerRadius = 20;

    public RoundedPanel(LayoutManager layout) {
    	super(layout);
    	setBorder(new EmptyBorder(10, 10, 10, 10));
        setOpaque(false);
        setBackground(Estilos.CARD_BACKGROUND);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
    }
}


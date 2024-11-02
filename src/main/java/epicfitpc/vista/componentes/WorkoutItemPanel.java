package epicfitpc.vista.componentes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import epicfitpc.modelo.Workout;
import epicfitpc.utils.Estilos;
import epicfitpc.utils.Rutas;
import epicfitpc.utils.WindowUtils;
import epicfitpc.utils.DateUtils;
import java.awt.*;

public class WorkoutItemPanel extends JPanel {

    private static final long serialVersionUID = 8329219010128277587L;
    private Workout workout;

    public WorkoutItemPanel(Workout workout) {
        this.workout = workout;
        initialize();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
    }

    private void initialize() {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setOpaque(false);
        setBackground(Estilos.CARD_BACKGROUND);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(2, 2, 2, 2);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 4;
        gbc.weightx = 0.25;
        gbc.weighty = 1.0;
        
        String tipo = workout.getTipo();
        String ruta = null;
        switch (tipo){
        	case "brazo":
        		ruta = Rutas.IMG_BRAZO;
        		break;
        	case "pecho":
        		ruta = Rutas.IMG_PECHO;
        		break;
        	case "default":
        		ruta = Rutas.IMG_EJERCICIO;
        		break;
        }
        
        ImageIcon img = WindowUtils.cargarImagen(ruta, 80, 80);
        JLabel labelImg = new JLabel(img);
        add(labelImg, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 0.75;

        JLabelTitle lblNombre = new JLabelTitle(workout.getNombre());
        JLabel lblNivel = new JLabel("<html><b>Nivel</b>: " + workout.getNivel() + "</html>");
        JLabel lblEjercicios = new JLabel("<html><b>Ejercicios</b>: " + workout.getEjercicios().size() + "</html>");

        int tiempoEnSegundos = workout.getTiempo();
        String tiempoFormateado = DateUtils.formatearTiempo(tiempoEnSegundos);
        JLabel lblTiempo = new JLabel("<html><b>Tiempo</b>: " + tiempoFormateado + "</html>");

        add(lblNombre, gbc);
        gbc.gridy++;
        add(lblTiempo, gbc);
        gbc.gridy++;
        add(lblNivel, gbc);
        gbc.gridy++;
        add(lblEjercicios, gbc);
    }
}

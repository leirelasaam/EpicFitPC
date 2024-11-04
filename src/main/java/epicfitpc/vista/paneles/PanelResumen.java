package epicfitpc.vista.paneles;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import epicfitpc.modelo.Historico;
import epicfitpc.modelo.Workout;
import epicfitpc.utils.Estilos;
import epicfitpc.utils.UsuarioLogueado;
import epicfitpc.vista.MainFrame;
import epicfitpc.vista.componentes.JButtonPrimary;
import epicfitpc.vista.componentes.JLabelTitle;

public class PanelResumen extends JPanel {
	private Historico historico = null;

	public PanelResumen(Historico historico) {
		this.historico = historico;
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		setBackground(Estilos.DARK_BACKGROUND);

		// PARTE NORTE DEL PANEL
		// Titulo
		JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabelTitle labelNombrePantalla = new JLabelTitle("Resumen del Workout");
		panelTitulo.add(labelNombrePantalla);
		panelTitulo.setBackground(Estilos.DARK_BACKGROUND);
		add(panelTitulo, BorderLayout.NORTH);
		JPanel panelContenido = new JPanel(new GridLayout(1, 1));
		add(panelContenido, BorderLayout.CENTER);

		// PARTE CENTRAL DEL PANEL
		JPanel panelResumen = new JPanel(new GridLayout(1, 3));

		// Tiempo total invertido
		JLabel labelTiempoTotal = new JLabel("Tiempo total: " + formatearTiempo(historico.getTiempo()));

		labelTiempoTotal.setHorizontalAlignment(JLabel.CENTER);
		// Ejercicios realizados
		JLabel labelEjerciciosRealizados = new JLabel("Ejercicios realizados");
		labelEjerciciosRealizados.setHorizontalAlignment(JLabel.CENTER);

		// Porcentaje de ejercicios completados
		JLabel labelPorcentajeEjercicios = new JLabel("Porcentaje completado: " + historico.getPorcentaje() + "%");
		labelPorcentajeEjercicios.setHorizontalAlignment(JLabel.CENTER);

		add(panelResumen, BorderLayout.CENTER, FlowLayout.CENTER);
		panelResumen.add(labelTiempoTotal);
		panelResumen.add(labelEjerciciosRealizados);
		panelResumen.add(labelPorcentajeEjercicios);

		panelResumen.setBackground(Estilos.DARK_BACKGROUND);

		// PARTE SUR DEL PANEL
		// Mensaje motivacional y el botón para confirmar
		JPanel panelInferior = new JPanel();
		panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS)); // Se ordena verticalmente
		panelInferior.setBackground(Estilos.DARK_BACKGROUND);

		// Mensaje motivacional
		JPanel panelMotivacional = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel labelMotivacional = new JLabel("Ánimo, ¡tú puedes!"); // Quizas mostrar mensajes aleatorios guardados
		panelMotivacional.add(labelMotivacional);
		panelMotivacional.setBackground(Estilos.DARK_BACKGROUND);
		panelInferior.add(panelMotivacional);

		// Botón para confirmar - Salir / Nos devuelve a workouts
		JButtonPrimary btnConfirmar = new JButtonPrimary("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Al pulsarlo nos lleva de vuelta a workouts
				cerrarPanel();
			}
		});

		JPanel panelConfirmar = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelConfirmar.add(btnConfirmar);
		panelConfirmar.setBackground(Estilos.DARK_BACKGROUND);
		panelInferior.add(panelConfirmar);
		add(panelInferior, BorderLayout.SOUTH);
	}

	private void cerrarPanel() {
				MainFrame.getInstance().getContentPane().removeAll();
		MainFrame.getInstance().getContentPane().add(new PanelMenu());
		MainFrame.getInstance().revalidate();
		MainFrame.getInstance().repaint();

	}
	
	private String formatearTiempo(int tiempo) {
		int minutos = tiempo / 60;
		int segundos = tiempo % 60;
		return String.format("%02d:%02d", minutos, segundos);
	}
}

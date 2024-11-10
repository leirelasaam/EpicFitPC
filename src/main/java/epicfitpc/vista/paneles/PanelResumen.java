package epicfitpc.vista.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import epicfitpc.modelo.Historico;
import epicfitpc.modelo.TiempoEjercicio;
import epicfitpc.utils.Estilos;
import epicfitpc.utils.UsuarioLogueado;
import epicfitpc.vista.MainFrame;
import epicfitpc.vista.componentes.JButtonPrimary;
import epicfitpc.vista.componentes.JLabelTitle;
import epicfitpc.vista.componentes.ResumenEjercicioPanel;

public class PanelResumen extends JPanel {
	private static final long serialVersionUID = -5043545200247246122L;
	private Historico historico = null;
	private ArrayList<TiempoEjercicio> tiempoEjercicios;
	private String[] mensajesPositivos = {"¡Felicidades!", "¡Increíble!"};
	private String[] mensajesAnimo = {"Ánimo, ¡tú puedes!", "¡A la próxima va la vencida!"};
	private String mensaje = null;
	private JPanel panelEjercicios;
	private static final int PANELES_NECESARIOS = 2;

	public PanelResumen(Historico historico, ArrayList<TiempoEjercicio> tiempoEjercicios) {
		this.historico = historico;
		this.tiempoEjercicios = tiempoEjercicios;
		seleccionarMensaje();
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		setBackground(Estilos.BACKGROUND);

		// PARTE NORTE DEL PANEL
		// Titulo
		JPanel panelTitulo = new JPanel(new BorderLayout(0,0));
		panelTitulo.setPreferredSize(new Dimension(panelTitulo.getWidth(), 80));
		add(panelTitulo, BorderLayout.NORTH);
		
		JLabelTitle labelNombrePantalla = new JLabelTitle("Resumen del Workout");
		labelNombrePantalla.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitulo.add(labelNombrePantalla, BorderLayout.CENTER);

		// PARTE CENTRAL DEL PANEL
		JPanel panelResumen = new JPanel(new GridLayout(1, 0));
		panelResumen.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(panelResumen, BorderLayout.CENTER);
		
		JPanel panelInfo = new JPanel(new GridLayout(0,1));
		panelInfo.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelResumen.add(panelInfo);

		// Tiempo total invertido
		JLabel labelTiempoTotal = new JLabel("<html><b>Tu tiempo total</b>: " + formatearTiempo(historico.getTiempo()) + "</html>");
		labelTiempoTotal.setFont(new Font("Noto Sans", Font.PLAIN, 16));
		panelInfo.add(labelTiempoTotal);
		
		// Porcentaje de ejercicios completados
		JLabel labelPorcentajeEjercicios = new JLabel("<html><b>Porcentaje completado</b>: " + historico.getPorcentaje() + "%</html>");
		labelPorcentajeEjercicios.setFont(new Font("Noto Sans", Font.PLAIN, 16));
		panelInfo.add(labelPorcentajeEjercicios);
		
		panelEjercicios = new JPanel(new GridLayout(0, 1, 10, 10));
		panelEjercicios.setBackground(Color.WHITE);
		panelEjercicios.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelEjercicios.setBackground(Estilos.BACKGROUND);

		JScrollPane scrollPane = new JScrollPane(panelEjercicios);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelResumen.add(scrollPane);

		// PARTE SUR DEL PANEL
		// Mensaje motivacional y el botón para confirmar
		JPanel panelInferior = new JPanel(new GridLayout(0, 1, 0, 10));
		panelInferior.setBorder(new EmptyBorder(20, 0, 20, 0));
		add(panelInferior, BorderLayout.SOUTH);

		// Mensaje motivacional
		JPanel panelMotivacional = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel labelMotivacional = new JLabel(mensaje);
		panelMotivacional.add(labelMotivacional);
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
		panelInferior.add(panelConfirmar);
		
		agregarPaneles();
	}
	
	private void agregarPaneles() {
		int numeroDePaneles = 0;
		int panelesNecesarios = PANELES_NECESARIOS;
		if (tiempoEjercicios != null) {
			for (TiempoEjercicio tiempoEjercicio : tiempoEjercicios) {
				numeroDePaneles++;
				panelEjercicios.add(new ResumenEjercicioPanel(tiempoEjercicio));
			}
		}
		generarPanelesVacios(numeroDePaneles, panelesNecesarios, panelEjercicios);
	}
	
	private void generarPanelesVacios(int numeroDePaneles, int panelesNecesarios, JPanel panel) {
		// Paneles vacíos para que se ajusten bien los tamaños
		for (int i = numeroDePaneles; i < panelesNecesarios; i++) {
			JPanel emptyPanel = new JPanel();
			emptyPanel.setBackground(Estilos.BACKGROUND);
			panel.add(emptyPanel);
		}
	}
	
	private void seleccionarMensaje() {
		String mensaje = null;
		int num = 0;
		if (historico != null) {
			if (historico.getPorcentaje() == 100) {
				num = getRandom(0, mensajesPositivos.length -1);
				mensaje = mensajesPositivos[num];
			}
			else {
				num = getRandom(0, mensajesAnimo.length -1);
				mensaje = mensajesAnimo[num];
			}
		}
		
		this.mensaje = mensaje;
	}

	private void cerrarPanel() {
		MainFrame.getInstance().getContentPane().removeAll();
		MainFrame.getInstance().getContentPane().add(new PanelMenu(UsuarioLogueado.getInstance().getUsuario()));
		MainFrame.getInstance().revalidate();
		MainFrame.getInstance().repaint();
	}
	
	private String formatearTiempo(int tiempo) {
		int minutos = tiempo / 60;
		int segundos = tiempo % 60;
		return String.format("%02d:%02d", minutos, segundos);
	}
	
	public int getRandom(int min, int max) {
		int range = (max - min) + 1;
     	int random = (int) ((range * Math.random()) + min);
		return random;
	}
}


package epicfitpc.vista.paneles;

import javax.swing.JPanel;
import javax.swing.JPasswordField;

import epicfitpc.bbdd.GestorDeUsuarios;
import epicfitpc.ficheros.GestorDeFicherosBinarios;
import epicfitpc.modelo.Usuario;
import epicfitpc.procesos.Procesos;
import epicfitpc.utils.Conexion;
import epicfitpc.utils.Estilos;
import epicfitpc.utils.GestorDeConexiones;
import epicfitpc.utils.Rutas;
import epicfitpc.utils.UsuarioLogueado;
import epicfitpc.utils.WindowUtils;
import epicfitpc.vista.MainFrame;
import epicfitpc.vista.componentes.JButtonPrimary;
import epicfitpc.vista.componentes.JLabelTitle;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

/**
 * Panel para el inicio de sesión.
 */
public class PanelLogin extends JPanel {
	private static final long serialVersionUID = 3044079574914466193L;
	private JTextField txtIntroduceTuCorreo;
	private JTextField txtIntroduceTuPass;
	private static final String FICHERO_USUARIOS = Rutas.BACKUP_USUARIOS;
	private boolean hayConexion = GestorDeConexiones.getInstance().hayConexion();

	public PanelLogin() {
		initialize();
	}

	private void initialize() {
		setLayout(new GridLayout(1, 2));
		setBounds(100, 100, 1200, 750);
		setBackground(Estilos.BACKGROUND);

		JPanel panelIzquierda = new JPanel();
		panelIzquierda.setBackground(Estilos.PRIMARY);
		panelIzquierda.setLayout(new BorderLayout());
		add(panelIzquierda);

		JPanel panelDerecha = new JPanel();
		panelDerecha.setLayout(null);
		add(panelDerecha);

		JLabelTitle lblTitulo = new JLabelTitle("¡Bienvenid@ a EpicFit!");
		lblTitulo.setBounds(180, 111, 300, 31);
		panelDerecha.add(lblTitulo);

		JButtonPrimary btnIniciarSesion = new JButtonPrimary("Iniciar sesión");
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciarSesion();
			}
		});
		btnIniciarSesion.setBounds(180, 378, 241, 31);
		panelDerecha.add(btnIniciarSesion);

		txtIntroduceTuCorreo = new JTextField();
		txtIntroduceTuCorreo.setText("");
		txtIntroduceTuCorreo.setBounds(180, 235, 241, 26);
		panelDerecha.add(txtIntroduceTuCorreo);
		txtIntroduceTuCorreo.setColumns(10);

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(180, 211, 100, 14);
		panelDerecha.add(lblUsuario);

		txtIntroduceTuPass = new JPasswordField();
		txtIntroduceTuPass.setBounds(180, 307, 241, 31);
		panelDerecha.add(txtIntroduceTuPass);
		txtIntroduceTuPass.setColumns(10);

		// Habilitar inicio mediante tecla ENTER
		txtIntroduceTuPass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				iniciarSesion();
			}
		});

		JLabel lblPass = new JLabel("Contraseña");
		lblPass.setBounds(180, 282, 100, 14);
		panelDerecha.add(lblPass);

		JButtonPrimary btnRegistrarme = new JButtonPrimary("Registrarme");
		btnRegistrarme.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				irRegistro();
			}
		});
		btnRegistrarme.setBounds(180, 535, 241, 31);
		panelDerecha.add(btnRegistrarme);

		JLabel lblTienesCuenta = new JLabel("¿Todavia no tienes cuenta?");
		lblTienesCuenta.setBounds(180, 510, 300, 20);
		panelDerecha.add(lblTienesCuenta);

		JButtonPrimary btnSalir = new JButtonPrimary("Salir");
		btnSalir.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				MainFrame.getInstance()
						.dispatchEvent(new WindowEvent(MainFrame.getInstance(), WindowEvent.WINDOW_CLOSING));
			}
		});
		btnSalir.setBounds(180, 584, 241, 31);
		panelDerecha.add(btnSalir);
		btnSalir.setBackgroundColor(Estilos.BLACK);
		btnSalir.setHoverColor(Color.DARK_GRAY);

		if (!hayConexion) {
			JLabelTitle lblSinConexion = new JLabelTitle("Modo sin conexión");
			lblSinConexion.setFont(new Font("Noto Sans", Font.ITALIC, 18));
			lblSinConexion.setForeground(Color.darkGray);
			lblSinConexion.setBounds(180, 50, 300, 31);
			panelDerecha.add(lblSinConexion);
		}

		// logo de la compania
		ImageIcon img = WindowUtils.cargarImagen(Rutas.LOGO_EF, 500, 500);
		JLabel lblImagen = new JLabel(img);
		panelIzquierda.add(lblImagen);

		if (!hayConexion) {
			btnRegistrarme.setVisible(false);
			lblTienesCuenta.setVisible(false);
		}
	}

	private void iniciarSesion() {
		// Obtener los datos introducidos
		String usuarioIntroducido = txtIntroduceTuCorreo.getText().toLowerCase();
		String passIntroducido = txtIntroduceTuPass.getText();

		ArrayList<Usuario> usuarios = null;
		Usuario usuario = null;

		// CARGAR DEPENDIENDO DE CONEXIÓN
		if (hayConexion) {
			try {
				GestorDeUsuarios gestorDeUsuarios = new GestorDeUsuarios(Conexion.getInstance().getConexion());
				usuarios = gestorDeUsuarios.obtenerTodosLosUsuarios();
			} catch (Exception e1) {
				WindowUtils.errorPane("Error al obtener usuarios.", "Error");
			}
		} else {
			GestorDeFicherosBinarios<Usuario> gdfb = new GestorDeFicherosBinarios<Usuario>(FICHERO_USUARIOS);
			try {
				usuarios = gdfb.leer();
			} catch (Exception e2) {
				// Si no hay conexión ni existen los ficheros de backup, se cierra la app.
				WindowUtils.errorPane(
						"<p>No existen ficheros de backup y no hay conexión.</p><p>No es posible utilizar la aplicación, se cerrará a continuación.</p>",
						"Error global");
				MainFrame.getInstance()
						.dispatchEvent(new WindowEvent(MainFrame.getInstance(), WindowEvent.WINDOW_CLOSING));
			}
		}

		// Devolverá el usuario si los datos introducidos son correctos
		try {
			usuario = comprobarUsuario(usuarios, usuarioIntroducido, passIntroducido);
		} catch (Exception e3) {
			WindowUtils.errorPane("Error en el acceso.", "Error");
		}
		if (usuario != null) {
			// si usuario y login es correcto
			WindowUtils.confirmationPane("Hola, " + usuario.getNombre() + ", ¡Bienvenid@ a EpicFit!",
					"Acceso concedido");
			UsuarioLogueado.getInstance().setUsuario(usuario);
			System.out.println("Usuario: " + usuario.toString());

			/* Ejecutando prceso de copia de historicos en cuanto nos logueamos */

			ExecutorService executor = Executors.newSingleThreadExecutor();
			executor.execute(new Procesos());
			executor.shutdown();
			irWorkouts();
		} else {
			WindowUtils.errorPane("No se ha podido completar el inicio de sesión.", "Acceso denegado");
		}
	}

	private void irWorkouts() {
		MainFrame.getInstance().getContentPane().removeAll();
		MainFrame.getInstance().getContentPane().add(new PanelMenu(UsuarioLogueado.getInstance().getUsuario()));
		MainFrame.getInstance().revalidate();
		MainFrame.getInstance().repaint();
	}

	private void irRegistro() {
		MainFrame.getInstance().getContentPane().removeAll();
		MainFrame.getInstance().getContentPane().add(new PanelRegistro());
		MainFrame.getInstance().revalidate();
		MainFrame.getInstance().repaint();
	}

	public Usuario comprobarUsuario(ArrayList<Usuario> usuarios, String usuarioIntroducido,
			String contraseniaIntroducida) throws Exception {
		// Recorremos los usuarios para buscar el usuario introducido
		for (Usuario usuario : usuarios) {

			// Verificar que userName no sea null antes de comparar
			if (usuario.getUsuario() != null && usuario.getUsuario().equals(usuarioIntroducido)) {
				// Usuario encontrado, ahora verificamos la contraseña
				if (usuario.getPass() != null && usuario.getPass().equals(contraseniaIntroducida)) {
					// Usuario y contraseña correctos
					// JOptionPane.showMessageDialog(null, "Acceso concedido.");
					return usuario; // Devuelve el usuario si ambas condiciones son correctas
				} else {
					// Si la contraseña no es correcta, lanzamos excepción genérica
					// JOptionPane.showMessageDialog(null, "Datos introducidos incorrectos.");
					throw new Exception("Datos incorrectos.");
				}
			}
		}
		// Si no se encuentra el usuario en la base de datos, lanzamos otra excepción
		// genérica
		// JOptionPane.showMessageDialog(null, "Datos introducidos incorrectos.");
		throw new Exception("Datos incorrectos.");
	}
}

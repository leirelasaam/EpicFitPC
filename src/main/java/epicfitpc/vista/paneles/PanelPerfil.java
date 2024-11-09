package epicfitpc.vista.paneles;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.google.cloud.Timestamp;

import epicfitpc.bbdd.GestorDeUsuarios;
import epicfitpc.modelo.Usuario;
import epicfitpc.utils.Conexion;
import epicfitpc.utils.DateUtils;
import epicfitpc.utils.Estilos;
import epicfitpc.utils.GestorDeConexiones;
import epicfitpc.utils.UsuarioLogueado;
import epicfitpc.utils.WindowUtils;
import epicfitpc.vista.MainFrame;
import epicfitpc.vista.componentes.JButtonPrimary;
import epicfitpc.vista.componentes.JLabelTitle;
import epicfitpc.vista.componentes.RoundedPanel;

import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.awt.event.ActionEvent;

public class PanelPerfil extends JPanel {

	private static final long serialVersionUID = -809466126072228019L;
	private JTextField textNombre;
	private JTextField textApellidos;
	private JTextField textFechaNac;
	private JTextField textCorreo;
	private JTextField textUsuario;
	private JTextField textNivel;
	private Usuario usuario;
	private GestorDeUsuarios gdu;

	public PanelPerfil(Usuario usuario) {
		this.usuario = usuario;
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		setBorder(new EmptyBorder(50, 50, 50, 50));
		setBounds(100, 100, 1200, 750);

		JPanel panelContenido = new JPanel(new GridLayout(1, 0, 100, 0));
		panelContenido.setBorder(new EmptyBorder(50, 50, 100, 50));
		add(panelContenido, BorderLayout.CENTER);

		// PANEL IZQUIERDA - Datos de usuario
		RoundedPanel panelDatosUsuario = new RoundedPanel(new GridLayout(0, 1, 0, 0));
		panelDatosUsuario.setBorder(new EmptyBorder(50, 50, 100, 50));
		panelContenido.add(panelDatosUsuario);

		JLabelTitle lblDatosDeUsuario = new JLabelTitle("Datos de usuario");
		panelDatosUsuario.add(lblDatosDeUsuario);

		JLabel lblNombre = new JLabel("Nombre");
		panelDatosUsuario.add(lblNombre);

		textNombre = new JTextField();
		textNombre.setText(usuario.getNombre());
		textNombre.setColumns(10);
		panelDatosUsuario.add(textNombre);

		JLabel lblApellidos = new JLabel("Apellidos");
		panelDatosUsuario.add(lblApellidos);

		textApellidos = new JTextField();
		textApellidos.setColumns(10);
		textApellidos.setText(usuario.getApellido());
		panelDatosUsuario.add(textApellidos);

		JLabel lblFechaNac = new JLabel("Fecha de nacimiento");
		panelDatosUsuario.add(lblFechaNac);

		textFechaNac = new JTextField();
		textFechaNac.setColumns(10);
		textFechaNac.setText(DateUtils.parsearTimestampAString(usuario.getFechaNac().toSqlTimestamp()));
		panelDatosUsuario.add(textFechaNac);

		RoundedPanel panelDatosCuenta = new RoundedPanel(new GridLayout(0, 1, 0, 0));
		panelDatosCuenta.setBorder(new EmptyBorder(50, 50, 100, 50));
		panelContenido.add(panelDatosCuenta);

		// PANEL DERECHA - Datos de cuenta
		JLabelTitle lblDatosCuenta = new JLabelTitle("Datos de tu cuenta");
		panelDatosCuenta.add(lblDatosCuenta);

		JLabel lblLogin = new JLabel("Usuario");
		panelDatosCuenta.add(lblLogin);

		textUsuario = new JTextField();
		textUsuario.setColumns(10);
		textUsuario.setText(usuario.getUsuario());
		panelDatosCuenta.add(textUsuario);

		JLabel lblCorreo = new JLabel("Correo electrónico ");
		panelDatosCuenta.add(lblCorreo);

		textCorreo = new JTextField();
		textCorreo.setColumns(10);
		textCorreo.setText(usuario.getCorreo());
		panelDatosCuenta.add(textCorreo);

		JLabel lblNivel = new JLabel("Nivel");
		panelDatosCuenta.add(lblNivel);

		textNivel = new JTextField();
		textNivel.setColumns(10);
		textNivel.setText(String.valueOf(usuario.getNivel()));
		textNivel.setEditable(false);
		panelDatosCuenta.add(textNivel);

		// PANEL INFERIOR - Botones
		JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelInferior.setBorder(new EmptyBorder(0, 50, 0, 50));
		add(panelInferior, BorderLayout.SOUTH);

		JButtonPrimary btnGuardarDatos = new JButtonPrimary("GUARDAR DATOS");
		btnGuardarDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarDatosActualizados();
			}
		});
		btnGuardarDatos.setPreferredSize(new Dimension(200, 30));
		panelInferior.add(btnGuardarDatos);

		boolean hayConexion = GestorDeConexiones.getInstance().hayConexion();
		if (!hayConexion)
			btnGuardarDatos.setVisible(false);

		JButtonPrimary btnCerrarSesion = new JButtonPrimary("Cerrar sesión");
		btnCerrarSesion.setPreferredSize(new Dimension(200, 30));
		btnCerrarSesion.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				cerrarSesion();
			}
		});
		panelInferior.add(btnCerrarSesion);
		btnCerrarSesion.setBackgroundColor(Estilos.BLACK);
		btnCerrarSesion.setHoverColor(Color.DARK_GRAY);
	}

	private void cerrarSesion() {
		UsuarioLogueado.getInstance().setUsuario(null);
		MainFrame.getInstance().getContentPane().removeAll();
		MainFrame.getInstance().getContentPane().add(new PanelLogin());
		MainFrame.getInstance().revalidate();
		MainFrame.getInstance().repaint();
	}

	private void guardarDatosActualizados() {
		Usuario usuarioModificado;
		boolean guardadoCorrectamente = false;
		boolean validar = false;
		try {
			if (gdu == null)
				gdu = new GestorDeUsuarios(Conexion.getInstance().getConexion());
			usuarioModificado = crearObjetoUsuario();
			validar = validacionesCamposCorrectos(usuarioModificado, usuario);
			if (validar) {
				guardadoCorrectamente = gdu.modificarUsuario(usuarioModificado, usuario.getUsuario());

				if (guardadoCorrectamente) {
					WindowUtils.confirmationPane("Se han guardado correctamente las modificaciones",
							"Guardado correco");
				} else {
					WindowUtils.errorPane("No se han podido guardar las modificaciones. Pruebe mas tarde",
							"Error en guardado");
				}
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * @param frame
	 * @param usuario
	 * @param gestorDeUsuarios
	 * @throws Exception
	 */
	public boolean validacionesCamposCorrectos(Usuario usuarioModificado, Usuario usuario) throws Exception {
		boolean validar = true;

		if (gdu == null)
			gdu = new GestorDeUsuarios(Conexion.getInstance().getConexion());

		if (!gdu.validarApellido(usuarioModificado.getApellido())) {
			WindowUtils.errorPane("El apellido esta vacio o es mayor de 50 carácteres", "Datos incorrectos");
			validar = false;
		} else if (!gdu.validarCorreo(usuarioModificado.getCorreo())) {
			WindowUtils.errorPane("Correo incorrecto, vuelva a insertarlo.", "Datos incorrectos");
			validar = false;
		} else if (!gdu.validarFechaNacimiento(usuarioModificado.getFechaNac())) {
			WindowUtils.errorPane("Fecha de nacimiento incorrecta. El usuario tiene que ser mayor de 14 años.",
					"Datos incorrectos");
			validar = false;
		} else if (!gdu.validarNombre(usuarioModificado.getNombre())) {
			WindowUtils.errorPane("Nombre incorrecto, esta vacio o es mayor de 50 carácteres.", "Datos incorrectos");
			validar = false;
		} else if (gdu.comprobarSiExisteNombreUsuario(usuarioModificado.getUsuario())
				&& !usuario.getUsuario().equals(usuarioModificado.getUsuario())) {
			WindowUtils.errorPane("El nombre de usuario ya existe.", "Datos incorrectos");
			validar = false;
		}

		return validar;
	}

	public Usuario crearObjetoUsuario() {
		Usuario usuario = new Usuario();
		usuario.setApellido(textApellidos.getText());
		usuario.setUsuario(textUsuario.getText());
		usuario.setCorreo(textCorreo.getText());

		Timestamp localDate = null;
		try {
			localDate = DateUtils.convertirStringToTimestamp(textFechaNac.getText());
		} catch (ParseException e) {
			WindowUtils.errorPane("Error en conversión de fecha", "Error");
		}
		usuario.setFechaNac(localDate);

		usuario.setNombre(textNombre.getText());

		return usuario;
	}
}

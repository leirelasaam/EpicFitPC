package epicfitpc.vista.paneles;

import javax.swing.JPanel;
import javax.swing.JTextField;

import com.google.cloud.Timestamp;

import epicfitpc.bbdd.GestorDeUsuarios;
import epicfitpc.modelo.Usuario;
import epicfitpc.utils.Conexion;
import epicfitpc.utils.Estilos;
import epicfitpc.utils.GestorDeConexiones;
import epicfitpc.utils.UsuarioLogueado;
import epicfitpc.vista.MainFrame;
import epicfitpc.vista.componentes.JButtonPrimary;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

public class PanelPerfil extends JPanel {

	private static final long serialVersionUID = -809466126072228019L;
	private JTextField textNombre;
	private JTextField textApellidos;
	private JTextField textFechaNac;
	private JTextField textCorreo;
	private JTextField textUsuario;
	private JTextField textNivel;

	public PanelPerfil(PanelMenu panelMenu, Usuario usuario) {

		GestorDeUsuarios gestorDeUsuarios = inicializarGestorDeUsuarios();

		setLayout(null);
		setBounds(100, 100, 1200, 750);

		textNombre = new JTextField();
		textNombre.setBounds(219, 139, 135, 20);
		textNombre.setText(usuario.getNombre());
		add(textNombre);
		textNombre.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(219, 114, 71, 14);
		add(lblNombre);

		JLabel lblApellidos = new JLabel("Apellidos");
		lblApellidos.setBounds(219, 192, 71, 14);
		add(lblApellidos);

		textApellidos = new JTextField();
		textApellidos.setColumns(10);
		textApellidos.setBounds(219, 217, 135, 20);
		textApellidos.setText(usuario.getApellido());
		add(textApellidos);

		JLabel lblFechaNac = new JLabel("Fecha de nacimiento");
		lblFechaNac.setBounds(219, 269, 135, 14);
		add(lblFechaNac);

		textFechaNac = new JTextField();
		textFechaNac.setColumns(10);
		textFechaNac.setBounds(219, 294, 135, 20);
		textFechaNac.setText(parsearTimestampAString(usuario.getFechaNac().toSqlTimestamp()));
		add(textFechaNac);

		JLabel lblDatosCuenta = new JLabel("Datos de tu cuenta");
		lblDatosCuenta.setForeground(new Color(255, 140, 0));
		lblDatosCuenta.setBounds(476, 75, 135, 14);
		add(lblDatosCuenta);

		JLabel lblDatosDeUsuario = new JLabel("Datos de usuario");
		lblDatosDeUsuario.setForeground(new Color(255, 140, 0));
		lblDatosDeUsuario.setBounds(219, 75, 135, 14);
		add(lblDatosDeUsuario);

		JLabel lblLogin = new JLabel("Usuario");
		lblLogin.setBounds(476, 114, 52, 14);
		add(lblLogin);

		JLabel lblCorreo = new JLabel("Correo electrónico ");
		lblCorreo.setBounds(476, 192, 135, 14);
		add(lblCorreo);

		textCorreo = new JTextField();
		textCorreo.setColumns(10);
		textCorreo.setBounds(476, 217, 135, 20);
		textCorreo.setText(usuario.getCorreo());
		add(textCorreo);

		JButtonPrimary btnguardarDatos = new JButtonPrimary("GUARDAR DATOS");
		btnguardarDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuario usuarioModificado;
				boolean guardadoCorrectamente = false;
				boolean validar = false;
				try {
					usuarioModificado = crearObjetoUsuario();
					validar = validacionesCamposCorrectos(panelMenu, usuarioModificado, usuario, gestorDeUsuarios);
					if (validar) {
						guardadoCorrectamente = gestorDeUsuarios.modificarUsuario(usuarioModificado, usuario.getUsuario());

						if (guardadoCorrectamente) {
							JOptionPane.showMessageDialog(MainFrame.getInstance(),
									"Se han guardado correctamente las modificaciones");
						} else {
							JOptionPane.showMessageDialog(MainFrame.getInstance(),
									"No se han podido guardar las modificaciones. Pruebe mas tarde");
						}
					}

				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		});

		btnguardarDatos.setBackground(new Color(0, 0, 0));
		btnguardarDatos.setBounds(598, 471, 138, 20);
		add(btnguardarDatos);
		
		boolean hayConexion = GestorDeConexiones.getInstance().hayConexion();
		if (!hayConexion)
			btnguardarDatos.setVisible(false);

		textUsuario = new JTextField();
		textUsuario.setColumns(10);
		textUsuario.setBounds(476, 139, 135, 20);
		textUsuario.setText(usuario.getUsuario());
		add(textUsuario);

		JLabel lblNivel = new JLabel("Nivel");
		lblNivel.setBounds(476, 269, 71, 14);
		add(lblNivel);

		textNivel = new JTextField();
		textNivel.setColumns(10);
		textNivel.setBounds(476, 294, 135, 20);
		textNivel.setText(String.valueOf(usuario.getNivel()));
		textNivel.setEditable(false);
		add(textNivel);
		
		JButtonPrimary btnSalir = new JButtonPrimary("Cerrar sesión");
		btnSalir.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				UsuarioLogueado.getInstance().setUsuario(null);
				MainFrame.getInstance().getContentPane().removeAll();
				MainFrame.getInstance().getContentPane().add(new PanelLogin());
				MainFrame.getInstance().revalidate();
				MainFrame.getInstance().repaint();
			}
		});
		btnSalir.setBounds(180, 624, 241, 31);
		add(btnSalir);
		btnSalir.setBackgroundColor(Estilos.BLACK);
		btnSalir.setHoverColor(Color.DARK_GRAY);
	}

	public static String parsearTimestampAString(java.sql.Timestamp timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		return sdf.format(timestamp);
	}

	/**
	 * @return
	 */
	public GestorDeUsuarios inicializarGestorDeUsuarios() {
		GestorDeUsuarios gestorDeUsuarios = null;
		try {
			gestorDeUsuarios = new GestorDeUsuarios(Conexion.getInstance().getConexion());
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return gestorDeUsuarios;
	}

	/**
	 * @param frame
	 * @param usuario
	 * @param gestorDeUsuarios
	 * @throws Exception
	 */
	public boolean validacionesCamposCorrectos(PanelMenu panelmenu, Usuario usuarioModificado, Usuario usuario,
			GestorDeUsuarios gestorDeUsuarios) throws Exception {
		boolean validar = true;

		if (!gestorDeUsuarios.validarApellido(usuarioModificado.getApellido())) {
			JOptionPane.showMessageDialog(panelmenu, "El apellido esta vacio o es mayor de 50 carácteres");
			validar = false;
		} else if (!gestorDeUsuarios.validarCorreo(usuarioModificado.getCorreo())) {
			JOptionPane.showMessageDialog(panelmenu, "Correo incorrecto, vuelva a insertarlo.");
			validar = false;
		} else if (!gestorDeUsuarios.validarFechaNacimiento(usuarioModificado.getFechaNac())) {
			JOptionPane.showMessageDialog(panelmenu,
					"Fecha de nacimiento incorrecta. El usuario tiene que ser mayor de 14 años.");
			validar = false;
		} else if (!gestorDeUsuarios.validarNombre(usuarioModificado.getNombre())) {
			JOptionPane.showMessageDialog(panelmenu, "Nombre incorrecto, esta vacio o es mayor de 50 carácteres.");
			validar = false;
		} else if (gestorDeUsuarios.comprobarSiExisteNombreUsuario(usuarioModificado.getUsuario())
				&& !usuario.getUsuario().equals(usuarioModificado.getUsuario())) {
			JOptionPane.showMessageDialog(panelmenu, "El nombre de usuario ya existe.");
			validar = false;
		}

		return validar;
	}

	public Usuario crearObjetoUsuario() {
		Usuario usuario = new Usuario();
		usuario.setApellido(textApellidos.getText());
		usuario.setUsuario(textUsuario.getText());
		usuario.setCorreo(textCorreo.getText());

		Timestamp localDate = convertirStringToTimestamp();
		usuario.setFechaNac(localDate);

		usuario.setNombre(textNombre.getText());

		return usuario;
	}

	/**
	 * @return
	 */
	public Timestamp convertirStringToTimestamp() {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date parsedDate = null;
		try {
			parsedDate = dateFormat.parse(textFechaNac.getText());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Timestamp.of(parsedDate);
	}

}

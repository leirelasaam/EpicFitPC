package epicfitpc.vista.paneles;

import javax.swing.JPanel;
import javax.swing.JTextField;

import com.google.cloud.Timestamp;

import epicfitpc.bbdd.GestorDeUsuarios;
import epicfitpc.modelo.Usuario;
import epicfitpc.utils.Conexion;
import epicfitpc.vista.MainFrame;
import epicfitpc.vista.componentes.JButtonOutlined;
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
import java.awt.GridLayout;

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
		setLayout(new GridLayout(0, 3, 0, 0));

		JLabel lblDatosDeUsuario = new JLabel("Datos de usuario");
		lblDatosDeUsuario.setForeground(new Color(255, 140, 0));
		add(lblDatosDeUsuario);

		JLabel label = new JLabel("");
		add(label);

		JLabel lblDatosCuenta = new JLabel("Datos de tu cuenta");
		lblDatosCuenta.setForeground(new Color(255, 140, 0));
		add(lblDatosCuenta);

		JLabel lblNombre = new JLabel("Nombre");
		add(lblNombre);

		JLabel label_1 = new JLabel("");
		add(label_1);

		JLabel lblLogin = new JLabel("Usuario");
		add(lblLogin);

		textNombre = new JTextField();
		textNombre.setText(usuario.getNombre());
		add(textNombre);
		textNombre.setColumns(10);

		JLabel label_2 = new JLabel("");
		add(label_2);

		textUsuario = new JTextField();
		textUsuario.setColumns(10);
		textUsuario.setText(usuario.getUsuario());
		add(textUsuario);

		JLabel lblApellidos = new JLabel("Apellidos");
		add(lblApellidos);

		JLabel label_3 = new JLabel("");
		add(label_3);

		JLabel lblCorreo = new JLabel("Correo electrónico ");
		add(lblCorreo);

		textApellidos = new JTextField();
		textApellidos.setColumns(10);
		textApellidos.setText(usuario.getApellido());
		add(textApellidos);

		JLabel label_4 = new JLabel("");
		add(label_4);

		textCorreo = new JTextField();
		textCorreo.setColumns(10);
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
						guardadoCorrectamente = gestorDeUsuarios.modificarUsuario(usuarioModificado, "");

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

		JLabel lblFechaNac = new JLabel("Fecha de nacimiento");
		add(lblFechaNac);

		JLabel label_5 = new JLabel("");
		add(label_5);

		JLabel lblNivel = new JLabel("Nivel");
		add(lblNivel);

		textFechaNac = new JTextField();
		textFechaNac.setColumns(10);
		textFechaNac.setText(parsearTimestampAString(usuario.getFechaNac().toSqlTimestamp()));
		add(textFechaNac);

		JLabel label_6 = new JLabel("");
		add(label_6);

		textNivel = new JTextField();
		textNivel.setColumns(10);
		textNivel.setText(String.valueOf(usuario.getNivel()));
		textNivel.setEditable(false);
		add(textNivel);

		JLabel label_7 = new JLabel("");
		add(label_7);

		btnguardarDatos.setBackground(new Color(0, 0, 0));
		add(btnguardarDatos);

		JLabel label_8 = new JLabel("");
		add(label_8);

		/* BORRAR BOTÓN, NO ES NECESARIO */
		/*
		 * JButtonOutlined btnVolver = new JButtonOutlined("VOLVER");
		 * btnVolver.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { panelMenu.getRootPane().removeAll();
		 * //panelMenu.getRootPane.add(new PanelWorkouts(panelMenu));
		 * panelMenu.revalidate(); panelMenu.repaint(); } });
		 * btnVolver.setForeground(new Color(0, 0, 0)); btnVolver.setBackground(new
		 * Color(0, 0, 0)); btnVolver.setBounds(46, 471, 138, 20); add(btnVolver);
		 */
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
		String usuariomod = usuarioModificado.getUsuario();
		String alias2 = usuario.getUsuario();

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
		} else if (!gestorDeUsuarios.validarUsername(usuarioModificado.getUsuario())) {
			JOptionPane.showMessageDialog(panelmenu,
					"Usuario incorrecta, vuelva a intentarlo incluyendo al menos una letra minúscula "
							+ "y una mayúscula.");
			validar = false;
		} else if (gestorDeUsuarios.comprobarSiExisteNombreUsuario(usuarioModificado.getUsuario())
				&& !usuario.getUsuario().equals(usuarioModificado.getUsuario())) {
			JOptionPane.showMessageDialog(panelmenu, "El nombre de usuario ya existe.");
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

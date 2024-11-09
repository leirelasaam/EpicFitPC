package epicfitpc.vista.paneles;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.swing.JSpinner;

import epicfitpc.bbdd.GestorDeUsuarios;
import epicfitpc.modelo.Usuario;
import epicfitpc.utils.Conexion;
import epicfitpc.utils.Estilos;
import epicfitpc.utils.WindowUtils;
import epicfitpc.vista.MainFrame;
import epicfitpc.vista.componentes.JButtonPrimary;
import epicfitpc.vista.componentes.JLabelTitle;
import epicfitpc.vista.componentes.RoundedPanel;

import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.border.EmptyBorder;

import com.google.cloud.Timestamp;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class PanelRegistro extends JPanel {

	private static final long serialVersionUID = -7631458094715795013L;
	private JTextField textUsuario;
	private JPasswordField passwordField;
	private JPasswordField passwordField_2;
	private JTextField textFechaNac;
	private JTextField textNombre;
	private JTextField textApellido;
	private JTextField textEmail;
	private JSpinner spinnerTipoUsuario;
	private GestorDeUsuarios gdu;

	public PanelRegistro() {
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
		textNombre.setColumns(10);
		panelDatosUsuario.add(textNombre);

		JLabel lblApellido = new JLabel("Apellido");
		panelDatosUsuario.add(lblApellido);

		textApellido = new JTextField();
		textApellido.setColumns(10);
		panelDatosUsuario.add(textApellido);

		JLabel lblFechaNac = new JLabel("<html>Fecha de nacimiento (<i>dd/mm/aaaa</i>)</html>");
		panelDatosUsuario.add(lblFechaNac);

		textFechaNac = new JTextField();
		textFechaNac.setColumns(10);
		panelDatosUsuario.add(textFechaNac);

		JLabel lblTipoUsuario = new JLabel("Elija el tipo de usuario");
		panelDatosUsuario.add(lblTipoUsuario);

		spinnerTipoUsuario = new JSpinner();
		SpinnerModel spinnerModel = new SpinnerListModel(new String[] { "Cliente", "Entrenador" });
		spinnerTipoUsuario.setModel(spinnerModel);
		panelDatosUsuario.add(spinnerTipoUsuario);

		// PANEL DERECHA - Datos de cuenta
		RoundedPanel panelDatosCuenta = new RoundedPanel(new GridLayout(0, 1, 0, 0));
		panelDatosCuenta.setBorder(new EmptyBorder(50, 50, 100, 50));
		panelContenido.add(panelDatosCuenta);

		JLabelTitle lblDatosCuenta = new JLabelTitle("Datos de tu cuenta");
		panelDatosCuenta.add(lblDatosCuenta);

		JLabel lblUsuario = new JLabel("Usuario");
		panelDatosCuenta.add(lblUsuario);

		textUsuario = new JTextField();
		textUsuario.setColumns(10);
		panelDatosCuenta.add(textUsuario);

		JLabel lblEmail = new JLabel("Correo electrónico");
		panelDatosCuenta.add(lblEmail);

		textEmail = new JTextField();
		textEmail.setColumns(10);
		panelDatosCuenta.add(textEmail);

		JLabel lblPass = new JLabel("Contraseña");
		panelDatosCuenta.add(lblPass);

		passwordField = new JPasswordField();
		panelDatosCuenta.add(passwordField);

		JLabel lblPass2 = new JLabel("Repita la contraseña:");
		panelDatosCuenta.add(lblPass2);

		passwordField_2 = new JPasswordField();
		panelDatosCuenta.add(passwordField_2);

//		UtilDateModel model = new UtilDateModel();
//		JDatePanelImpl datePanel = new JDatePanelImpl(model, null);
//		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, null);
//		datePicker.setBounds(303, 108, 130, 20);
//		add(datePicker);

		// PANEL INFERIOR
		JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelInferior.setBorder(new EmptyBorder(0, 50, 0, 50));
		add(panelInferior, BorderLayout.SOUTH);

		JButtonPrimary btnRegistro = new JButtonPrimary("Registrarme");
		btnRegistro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				registrarUsuario();
			}
		});
		btnRegistro.setPreferredSize(new Dimension(200, 30));
		panelInferior.add(btnRegistro);

		JButtonPrimary btnVolver = new JButtonPrimary("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volverLogin();
			}
		});
		btnVolver.setBackgroundColor(Estilos.BLACK);
		btnVolver.setHoverColor(Color.DARK_GRAY);
		btnVolver.setPreferredSize(new Dimension(200, 30));
		panelInferior.add(btnVolver);

	}

	private void volverLogin() {
		MainFrame.getInstance().getContentPane().removeAll();
		MainFrame.getInstance().getContentPane().add(new PanelLogin());
		MainFrame.getInstance().revalidate();
		MainFrame.getInstance().repaint();
	}

	private void registrarUsuario() {
		Usuario usuario = crearObjetoUsuario();

		boolean guardadoCorrectamente = false;

		boolean validar = false;
		try {
			if (gdu == null)
				gdu = new GestorDeUsuarios(Conexion.getInstance().getConexion());
			validar = validacionesCamposCorrectos(usuario);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (validar) {
			guardarUsuario(usuario, guardadoCorrectamente);
		}
	}

	/**
	 * @param spinnerTipoUsuario
	 * @return
	 */
	private Usuario crearObjetoUsuario() {
		Usuario usuario = new Usuario();
		usuario.setApellido(textApellido.getText());
		usuario.setUsuario(textUsuario.getText());
		usuario.setCorreo(textEmail.getText());
		if (spinnerTipoUsuario.getValue() == "Entrenador") {
			usuario.setEsEntrenador(true);
		} else {
			usuario.setEsEntrenador(false);
		}

		if (textFechaNac.getText() != null) {
			Timestamp localDate = convertirStringToTimestamp();
			usuario.setFechaNac(localDate);
		} else {
			JOptionPane.showMessageDialog(MainFrame.getInstance(), "La fecha de nacimiento no puede estar vacía.");
		}

		usuario.setFechaAlt(Timestamp.now());
		usuario.setNombre(textNombre.getText());
		usuario.setPass(new String(passwordField.getPassword()));
		return usuario;
	}

	/**
	 * @return
	 */
	private Timestamp convertirStringToTimestamp() {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date parsedDate = null;
		try {
			parsedDate = dateFormat.parse(textFechaNac.getText());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Timestamp.of(parsedDate);
	}

	/**
	 * @param frame
	 * @param usuario
	 * @param guardadoCorrectamente
	 * @param gestorDeUsuarios
	 */
	private void guardarUsuario(Usuario usuario, boolean guardadoCorrectamente) {
		try {
			if (gdu == null)
				gdu = new GestorDeUsuarios(Conexion.getInstance().getConexion());
			guardadoCorrectamente = gdu.guardarUsuarios(usuario);

		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			e1.printStackTrace();
		}
		if (!guardadoCorrectamente) {
			WindowUtils.errorPane("No se ha podido crear correctamente, vuelva a intentarlo más tarde.",
					"Error en el registro");
		} else {
			WindowUtils.confirmationPane("Usuario creado correctamente", "Registro completado");
			volverLogin();
		}
	}

	/**
	 * @param frame
	 * @param usuario
	 * @param gestorDeUsuarios
	 * @throws Exception
	 */
	private boolean validacionesCamposCorrectos(Usuario usuario) throws Exception {
		boolean validar = true;
		if (gdu == null)
			gdu = new GestorDeUsuarios(Conexion.getInstance().getConexion());

		String pass1 = new String(passwordField.getPassword());
		String pass2 = new String(passwordField_2.getPassword());

		if (!gdu.validarApellido(usuario.getApellido())) {
			WindowUtils.errorPane("El apellido esta vacio o es mayor de 50 carácteres", "Datos incorrectos");
			validar = false;
		} else if (!gdu.validarCorreo(usuario.getCorreo())) {
			WindowUtils.errorPane("Correo incorrecto, vuelva a insertarlo.", "Datos incorrectos");
			validar = false;
		} else if (!gdu.validarFechaNacimiento(usuario.getFechaNac())) {
			WindowUtils.errorPane("Fecha de nacimiento incorrecta. El usuario tiene que ser mayor de 14 años.",
					"Datos incorrectos");
			validar = false;
		} else if (!gdu.validarNombre(usuario.getNombre())) {
			WindowUtils.errorPane("Nombre incorrecto, esta vacio o es mayor de 50 carácteres.", "Datos incorrectos");
			validar = false;
		} else if (!gdu.validarPassword(usuario.getPass())) {
			WindowUtils.errorPane(
					"Contraseña incorrecta, debe tener entre 8 y 20 caracteres, incluir al menos una letra minúscula, una mayúscula, un número y un carácter especial.",
					"Datos incorrectos");
			validar = false;
		} else if (!pass1.equals(pass2)) {
			WindowUtils.errorPane("Contraseñas distintas, vuelva a intentarlo.", "Datos incorrectos");
			validar = false;
		} else if (gdu.comprobarSiExisteNombreUsuario(usuario.getUsuario())) {
			WindowUtils.errorPane("El nombre de usuario ya existe.", "Datos incorrectos");
			validar = false;
		}

		return validar;
	}

}
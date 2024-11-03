package epicfitpc.vista.paneles;

import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JSpinner;

import epicfitpc.bbdd.GestorDeUsuarios;
import epicfitpc.modelo.Usuario;
import epicfitpc.utils.Conexion;
import epicfitpc.vista.MainFrame;

import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;

import com.google.cloud.Timestamp;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class PanelRegistro extends JPanel {

	private static final long serialVersionUID = -7631458094715795013L;
	private JTextField textUsuario;
	private JPasswordField passwordField;
	private JPasswordField passwordField_2;
	private JTextField textFechaNac;
	private JTextField textNombre;
	private JTextField textApellido;
	private JTextField textEmail;

	public PanelRegistro(MainFrame frame) {
		setLayout(null);

		textUsuario = new JTextField();
		textUsuario.setBounds(94, 108, 130, 20);
		add(textUsuario);
		textUsuario.setColumns(10);

		JLabel lblPass = new JLabel("Contraseña:");
		lblPass.setBounds(94, 193, 102, 14);
		add(lblPass);

		JLabel lblUsuario = new JLabel("Nombre de Usuario:");
		lblUsuario.setBounds(94, 83, 130, 14);
		add(lblUsuario);

		JLabel lblPass2 = new JLabel("Repita la contraseña:");
		lblPass2.setBounds(94, 296, 130, 14);
		add(lblPass2);

		passwordField = new JPasswordField();
		passwordField.setBounds(94, 218, 130, 20);
		add(passwordField);

		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(94, 314, 130, 20);
		add(passwordField_2);

		JLabel lblFechaNac = new JLabel("Fecha de nacimiento:");
		lblFechaNac.setBounds(303, 83, 130, 14);
		add(lblFechaNac);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(303, 193, 110, 14);
		add(lblNombre);

		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(303, 296, 130, 14);
		add(lblApellido);

		textFechaNac = new JTextField();
		textFechaNac.setBounds(303, 108, 130, 20);
		add(textFechaNac);
		textFechaNac.setColumns(10);
//		UtilDateModel model = new UtilDateModel();
//		JDatePanelImpl datePanel = new JDatePanelImpl(model, null);
//		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, null);
//		datePicker.setBounds(303, 108, 130, 20);
//		add(datePicker);

		textNombre = new JTextField();
		textNombre.setBounds(303, 218, 130, 20);
		add(textNombre);
		textNombre.setColumns(10);

		textApellido = new JTextField();
		textApellido.setBounds(303, 314, 130, 20);
		add(textApellido);
		textApellido.setColumns(10);

		JLabel lblCorreo = new JLabel("Correo electrónico: ");
		lblCorreo.setBounds(518, 83, 130, 14);
		add(lblCorreo);

		JLabel lblTipoUsuario = new JLabel("Elija el tipo de usuario: ");
		lblTipoUsuario.setBounds(518, 193, 130, 14);
		add(lblTipoUsuario);

		textEmail = new JTextField();
		textEmail.setBounds(518, 108, 130, 20);
		add(textEmail);
		textEmail.setColumns(10);

		JSpinner spinnerTipoUsuario = new JSpinner();
		List<String> tipoUsuario = new ArrayList<String>();
		tipoUsuario.add("Cliente");
		tipoUsuario.add("Entrenador");
		SpinnerModel spinnerModel = new SpinnerListModel(tipoUsuario);
		spinnerTipoUsuario.setModel(spinnerModel);
		spinnerTipoUsuario.setBounds(518, 218, 130, 20);
		add(spinnerTipoUsuario);

		JButton btnRegistro = new JButton("Registrarme");
		btnRegistro.setBounds(303, 417, 179, 23);
		btnRegistro.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Usuario usuario = crearObjetoUsuario(spinnerTipoUsuario);

				boolean guardadoCorrectamente = false;
				GestorDeUsuarios gestorDeUsuarios = inicializarGestorDeUsuarios();

				boolean validar = false;
				try {
					validar = validacionesCamposCorrectos(frame, usuario, gestorDeUsuarios);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				if (validar) {
					
					guardarUsuario(frame, usuario, guardadoCorrectamente, gestorDeUsuarios);
				}
				 
			}

			/**
			 * @param spinnerTipoUsuario
			 * @return
			 */
			public Usuario crearObjetoUsuario(JSpinner spinnerTipoUsuario) {
				Usuario usuario = new Usuario();
				usuario.setApellido(textApellido.getText());
				usuario.setUsuario(textUsuario.getText());
				usuario.setCorreo(textEmail.getText());
				if (spinnerTipoUsuario.getValue() == "Entrenador") {
					usuario.setEsEntrenador(true);
				} else {
					usuario.setEsEntrenador(false);
				}

				Timestamp localDate = convertirStringToTimestamp();
				usuario.setFechaNac(localDate);

				usuario.setFechaAlt(Timestamp.now());
				usuario.setNombre(textNombre.getText());
				usuario.setPass(new String(passwordField.getPassword()));
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

			/**
			 * @param frame
			 * @param usuario
			 * @param guardadoCorrectamente
			 * @param gestorDeUsuarios
			 */
			public void guardarUsuario(MainFrame frame, Usuario usuario, boolean guardadoCorrectamente,
					GestorDeUsuarios gestorDeUsuarios) {
				try {
					guardadoCorrectamente = gestorDeUsuarios.guardarUsuarios(usuario);

				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} catch (ExecutionException e1) {
					e1.printStackTrace();
				}
				if (guardadoCorrectamente == false) {
					JOptionPane.showMessageDialog(frame,
							"No se ha podido crear correctamente, vuelva a intentarlo más tarde.");
				} else {
					JOptionPane.showMessageDialog(frame, "Usuario creado correctamente");
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new PanelLogin(frame));
					frame.revalidate();
					frame.repaint();

				}
			}

			/**
			 * @param frame
			 * @param usuario
			 * @param gestorDeUsuarios
			 * @throws Exception 
			 */
			public boolean validacionesCamposCorrectos(MainFrame frame, Usuario usuario,
					GestorDeUsuarios gestorDeUsuarios) throws Exception {
				boolean validar = true;
				String pass1 = new String(passwordField.getPassword());
				String pass2 =  new String(passwordField_2.getPassword());
				
				if (!gestorDeUsuarios.validarApellido(usuario.getApellido())) {
					JOptionPane.showMessageDialog(frame, "El apellido esta vacio o es mayor de 50 carácteres");
					validar = false;
				} else if (!gestorDeUsuarios.validarCorreo(usuario.getCorreo())) {
					JOptionPane.showMessageDialog(frame, "Correo incorrecto, vuelva a insertarlo.");
					validar = false;
				} else if (!gestorDeUsuarios.validarFechaNacimiento(usuario.getFechaNac())) {
					JOptionPane.showMessageDialog(frame,
							"Fecha de nacimiento incorrecta. El usuario tiene que ser mayor de 14 años.");
					validar = false;
				} else if (!gestorDeUsuarios.validarNombre(usuario.getNombre())) {
					JOptionPane.showMessageDialog(frame, "Nombre incorrecto, esta vacio o es mayor de 50 carácteres.");
					validar = false;
				} else if (!gestorDeUsuarios.validarPassword(usuario.getPass())) {
					JOptionPane.showMessageDialog(frame, "Contraseña incorrecta, debe tener entre 8 y 20 caracteres, "
							+ "incluir al menos una letra minúscula, una mayúscula, un número y un carácter especial.");
					validar = false;
				} else if (!pass1.equals(pass2)) {
					JOptionPane.showMessageDialog(frame, "Contraseñas distintas, vuelva a intentarlo.");
					validar = false;
				} else if (!gestorDeUsuarios.validarUsername(usuario.getUsuario())) {
					JOptionPane.showMessageDialog(frame, "Usuario incorrecta, vuelva a intentarlo incluyendo al menos una letra minúscula "
							+ "y una mayúscula.");
					validar = false;
				}else if (gestorDeUsuarios.comprobarSiExisteNombreUsuario(usuario.getUsuario())) {
					JOptionPane.showMessageDialog(frame, "El nombre de usuario ya existe.");
				}

				return validar;
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

		});
		add(btnRegistro);

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelLogin(frame));
				frame.revalidate();
				frame.repaint();
			}
		});
		btnVolver.setBounds(10, 531, 89, 23);
		add(btnVolver);

	}

	// logo de la compania
//	JLabel lblNewLabel_3 = new JLabel("New label");
//	lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\in2dm3-v\\Downloads\\Logo.PNG"));
//	lblNewLabel_3.setBounds(0, -1, 602, 751);
//	add(lblNewLabel_3);
}
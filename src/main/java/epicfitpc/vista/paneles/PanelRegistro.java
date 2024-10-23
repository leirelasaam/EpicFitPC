package epicfitpc.vista.paneles;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JSpinner;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;

import epicfitpc.modelo.bbdd.GestorDeUsuarios;
import epicfitpc.modelo.pojos.Usuario;
import epicfitpc.utils.Conexion;
import epicfitpc.vista.MainFrame;

import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class PanelRegistro extends JPanel {

	private static final long serialVersionUID = -7631458094715795013L;
	private static final ActionListener ActionListener = null;
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

		JLabel lblNewLabel = new JLabel("Contraseña:");
		lblNewLabel.setBounds(94, 193, 102, 14);
		add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nombre de Usuario:");
		lblNewLabel_1.setBounds(94, 83, 130, 14);
		add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Repita la contraseña:");
		lblNewLabel_2.setBounds(94, 296, 130, 14);
		add(lblNewLabel_2);

		passwordField = new JPasswordField();
		passwordField.setBounds(94, 218, 130, 20);
		add(passwordField);

		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(94, 314, 130, 20);
		add(passwordField_2);

		JLabel lblNewLabel_3 = new JLabel("Fecha de nacimiento:");
		lblNewLabel_3.setBounds(303, 83, 130, 14);
		add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Nombre:");
		lblNewLabel_4.setBounds(303, 193, 110, 14);
		add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Apellido:");
		lblNewLabel_5.setBounds(303, 296, 130, 14);
		add(lblNewLabel_5);

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

		JLabel lblNewLabel_6 = new JLabel("Correo electrónico: ");
		lblNewLabel_6.setBounds(518, 83, 130, 14);
		add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("Elija el tipo de usuario: ");
		lblNewLabel_7.setBounds(518, 193, 130, 14);
		add(lblNewLabel_7);

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

		JButton btnNewButton = new JButton("Registrarme");
		btnNewButton.setBounds(303, 417, 179, 23);
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Usuario usuario = new Usuario();
				usuario.setApellido(textApellido.getText());
				usuario.setUser(textUsuario.getText());
				usuario.setCorreo(textEmail.getText());
				if (spinnerTipoUsuario.getValue() == "Entrenador") {
					usuario.setEsEntrenador(true);
				} else {
					usuario.setEsEntrenador(false);
				}
				usuario.setFechaAlt(LocalDate.now());
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String date = textFechaNac.getText();
				// convert String to LocalDate
				LocalDate localDate = LocalDate.parse(date, formatter);

				usuario.setFechaNac(localDate);
				usuario.setNombre(textNombre.getName());
				usuario.setPass(passwordField.getPassword().toString());

				/*CODIGO REUTILIZADDO LUCIAN POR SI DA ERROR,hacerlo parecido, verificando si el usuario existe y el correo no est*/
				
				try {
					GestorDeUsuarios gestorDeUsuarios = new GestorDeUsuarios(Conexion.getInstance().getConexion());

					// Obtener los datos introducidos
					String usuarioIntroducido = textUsuario.getText();
					String passIntroducido = passwordField.getPassword().toString();

					// Devolverá el usuario si los datos introducidos son correctos
					Usuario user = gestorDeUsuarios.comprobarUsuario(usuarioIntroducido, passIntroducido);
					if (user != null) { // si usuario == null significa que los datos introducidos son incorrectos
						// si usuario y login es correcto
						// JOptionPane.showMessageDialog(frame, "Bienvenido a EpicFit");
						frame.getContentPane().removeAll();
						frame.getContentPane().add(new PanelMenu(frame, user));
						frame.revalidate();
						frame.repaint();
					} else {
						// si usuario y login es correcto
						JOptionPane.showMessageDialog(frame, "El login y el password es incorrecto");
						
					}
				
					
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
		add(btnNewButton);

		
	}
	/*BOTON ACTION PARA VOLVER MODIFICARLO*/
	/*btnNewButton_1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			frame.getContentPane().add(new PanelRegistro(frame));
			frame.revalidate();
			frame.repaint();*/
		
	
	// logo de la compania
//	JLabel lblNewLabel_3 = new JLabel("New label");
//	lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\in2dm3-v\\Downloads\\Logo.PNG"));
//	lblNewLabel_3.setBounds(0, -1, 602, 751);
//	add(lblNewLabel_3);
	
	private void initialize() {
	}
}

package epicfitpc.vista.paneles;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class PanelRegistro extends JPanel {
	
	private static final long serialVersionUID = -7631458094715795013L;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	public PanelRegistro() {
		setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(94, 108, 130, 20);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Contraseña:");
		lblNewLabel.setBounds(94, 193, 102, 14);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre de Usuario:");
		lblNewLabel_1.setBounds(94, 83, 102, 14);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Repita la contraseña:");
		lblNewLabel_2.setBounds(94, 296, 130, 14);
		add(lblNewLabel_2);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(94, 218, 130, 20);
		add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(94, 314, 130, 20);
		add(passwordField_1);
		
		JLabel lblNewLabel_3 = new JLabel("Fecha de nacimiento:");
		lblNewLabel_3.setBounds(303, 83, 130, 14);
		add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Nombre:");
		lblNewLabel_4.setBounds(303, 193, 46, 14);
		add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Apellido:");
		lblNewLabel_5.setBounds(303, 296, 46, 14);
		add(lblNewLabel_5);
		
		textField_1 = new JTextField();
		textField_1.setBounds(303, 108, 130, 20);
		add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(303, 218, 130, 20);
		add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(303, 314, 130, 20);
		add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Correo electrónico: ");
		lblNewLabel_6.setBounds(518, 83, 102, 14);
		add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Eliga el tipo de usuario: ");
		lblNewLabel_7.setBounds(518, 193, 130, 14);
		add(lblNewLabel_7);
		
		textField_4 = new JTextField();
		textField_4.setBounds(518, 108, 130, 20);
		add(textField_4);
		textField_4.setColumns(10);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(518, 218, 130, 20);
		add(spinner);
		
		JButton btnNewButton = new JButton("Registrate: ");
		btnNewButton.setBounds(330, 417, 89, 23);
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Arrow!");
		btnNewButton_1.setBounds(10, 519, 89, 23);
		add(btnNewButton_1);
		initialize();
	}
	
	private void initialize() {
	}
}

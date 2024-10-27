package epicfitpc.vista.paneles;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JButton;
import java.awt.Color;

public class PanelRegistro extends JPanel {
	
	private static final long serialVersionUID = -7631458094715795013L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;


	public PanelRegistro() {
		setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(59, 152, 149, 20);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("REGISTRO");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 27));
		lblNewLabel.setBounds(247, 30, 149, 41);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre de usuario:");
		lblNewLabel_1.setBounds(59, 127, 121, 20);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Contraseña:");
		lblNewLabel_2.setBounds(59, 199, 104, 20);
		add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Repita la contraseña:");
		lblNewLabel_3.setBounds(59, 273, 135, 14);
		add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Fecha Nacimiento:");
		lblNewLabel_4.setBounds(268, 130, 104, 14);
		add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Nombre:");
		lblNewLabel_5.setBounds(268, 202, 82, 14);
		add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Apellidos:");
		lblNewLabel_6.setBounds(268, 273, 82, 14);
		add(lblNewLabel_6);
		
		textField_3 = new JTextField();
		textField_3.setBounds(268, 152, 148, 20);
		add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Correo electrónico:");
		lblNewLabel_7.setBounds(469, 130, 104, 14);
		add(lblNewLabel_7);
		
		textField_4 = new JTextField();
		textField_4.setBounds(469, 152, 149, 20);
		add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setBounds(268, 229, 148, 20);
		add(textField_5);
		textField_5.setColumns(10);
		
		textField_6 = new JTextField();
		textField_6.setBounds(268, 298, 148, 20);
		add(textField_6);
		textField_6.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("Escoga tipo de usuario: ");
		lblNewLabel_8.setBounds(469, 202, 149, 17);
		add(lblNewLabel_8);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(59, 230, 149, 19);
		add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(59, 298, 149, 20);
		add(passwordField_1);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(469, 229, 149, 20);
		add(spinner);
		
		JButton btnNewButton = new JButton("Resgistrate");
		btnNewButton.setBackground(new Color(0, 0, 0));
		btnNewButton.setBounds(469, 462, 149, 20);
		add(btnNewButton);

		initialize();
	}
	
	private void initialize() {
	}
}

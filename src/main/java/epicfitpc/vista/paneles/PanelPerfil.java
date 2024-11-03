package epicfitpc.vista.paneles;

import javax.swing.JPanel;
import javax.swing.JTextField;

import epicfitpc.vista.MainFrame;
import epicfitpc.vista.componentes.JButtonOutlined;
import epicfitpc.vista.componentes.JButtonPrimary;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelPerfil extends JPanel {
	public PanelPerfil(PanelMenu panelMenu) {
		setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(219, 139, 135, 20);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(219, 114, 71, 14);
		add(lblNombre);
		
		JLabel lblApellidos = new JLabel("Apellidos");
		lblApellidos.setBounds(219, 192, 71, 14);
		add(lblApellidos);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(219, 217, 135, 20);
		add(textField_1);
		
		JLabel lblFechaNac = new JLabel("Fecha de nacimiento");
		lblFechaNac.setBounds(219, 269, 135, 14);
		add(lblFechaNac);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(219, 294, 135, 20);
		add(textField_2);
		
		JLabel lblDatosCuenta= new JLabel("Datos de tu cuenta");
		lblDatosCuenta.setForeground(new Color(255, 140, 0));
		lblDatosCuenta.setBounds(476, 75, 135, 14);
		add(lblDatosCuenta);
		
		JLabel lblDatosDeUsuario = new JLabel("Datos de usuario");
		lblDatosDeUsuario.setForeground(new Color(255, 140, 0));
		lblDatosDeUsuario.setBounds(219, 75, 135, 14);
		add(lblDatosDeUsuario);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(476, 114, 71, 14);
		add(lblLogin);
		
		JLabel lblCorreo = new JLabel("Correo electrónico ");
		lblCorreo.setBounds(476, 192, 135, 14);
		add(lblCorreo);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(476, 217, 135, 20);
		add(textField_3);
		
		JButtonPrimary btnguardarDatos = new JButtonPrimary("GUARDAR DATOS");
		btnguardarDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnguardarDatos.setBackground(new Color(0, 0, 0));
		btnguardarDatos.setBounds(598, 471, 138, 20);
		add(btnguardarDatos);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(476, 139, 135, 20);
		add(textField_5);
		
		JButtonOutlined btnVolver = new JButtonOutlined("VOLVER");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnVolver.setForeground(new Color(0, 0, 0));
		btnVolver.setBackground(new Color(0, 0, 0));
		btnVolver.setBounds(46, 471, 138, 20);
		add(btnVolver);
		
		JLabel lblNivel = new JLabel("Nivel");
		lblNivel.setBounds(476, 269, 71, 14);
		add(lblNivel);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(476, 294, 135, 20);
		add(textField_4);
	}
	private static final long serialVersionUID = -809466126072228019L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_5;
	private JTextField textField_4;
}

package epicfitpc.vista.paneles;

import javax.swing.JPanel;
import javax.swing.JTextField;

import epicfitpc.bbdd.GestorDeUsuarios;
import epicfitpc.modelo.Usuario;
import epicfitpc.utils.Conexion;
import epicfitpc.vista.componentes.JButtonOutlined;
import epicfitpc.vista.componentes.JButtonPrimary;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.concurrent.ExecutionException;
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
		
		GestorDeUsuarios gestorDeUsuarios;
		Usuario usuarioPrueba = null;
		try {
			gestorDeUsuarios = new GestorDeUsuarios(Conexion.getInstance().getConexion());
			usuarioPrueba = gestorDeUsuarios.obtenerUsuarioPorNombreUsuario("Vio23");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			e1.printStackTrace();
		}
		
		
		
		setLayout(null);
		
		textNombre = new JTextField();
		textNombre.setBounds(219, 139, 135, 20);
		textNombre.setText(usuarioPrueba.getNombre());
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
		textApellidos.setText(usuarioPrueba.getApellido());
		add(textApellidos);
		
		JLabel lblFechaNac = new JLabel("Fecha de nacimiento");
		lblFechaNac.setBounds(219, 269, 135, 14);
		add(lblFechaNac);
		
		textFechaNac = new JTextField();
		textFechaNac.setColumns(10);
		textFechaNac.setBounds(219, 294, 135, 20);
		textFechaNac.setText(parsearTimestampAString(usuarioPrueba.getFechaNac().toSqlTimestamp()));
		add(textFechaNac);
		
		JLabel lblDatosCuenta= new JLabel("Datos de tu cuenta");
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
		textCorreo.setText(usuarioPrueba.getCorreo());
		add(textCorreo);
		
		JButtonPrimary btnguardarDatos = new JButtonPrimary("GUARDAR DATOS");
		btnguardarDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnguardarDatos.setBackground(new Color(0, 0, 0));
		btnguardarDatos.setBounds(598, 471, 138, 20);
		add(btnguardarDatos);
		
		textUsuario = new JTextField();
		textUsuario.setColumns(10);
		textUsuario.setBounds(476, 139, 135, 20);
		textUsuario.setText(usuarioPrueba.getUsuario());
		add(textUsuario);
		
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
		
	
		textNivel = new JTextField();
		textNivel.setColumns(10);
		textNivel.setBounds(476, 294, 135, 20);
		textNivel.setText(String.valueOf(usuarioPrueba.getNivel()));
		add(textNivel);
	}

	public static String parsearTimestampAString(java.sql.Timestamp timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		return sdf.format(timestamp);
	}
}

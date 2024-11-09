package epicfitpc.vista.paneles;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.google.cloud.Timestamp;

import epicfitpc.bbdd.GestorDeUsuarios;
import epicfitpc.modelo.Usuario;
import epicfitpc.utils.Conexion;
import epicfitpc.utils.Estilos;
import epicfitpc.utils.GestorDeConexiones;
import epicfitpc.utils.UsuarioLogueado;
import epicfitpc.vista.MainFrame;
import epicfitpc.vista.componentes.JButtonPrimary;
import epicfitpc.vista.componentes.JLabelTitle;
import epicfitpc.vista.componentes.RoundedPanel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
		setLayout(new BorderLayout(0,0));
		setBorder(new EmptyBorder(50, 50, 50, 50));
		setBounds(100, 100, 1200, 750);
		
		JPanel panelContenido = new JPanel(new GridLayout(1, 0, 100, 0));
		panelContenido.setBorder(new EmptyBorder(50, 50, 100, 50));
		add(panelContenido, BorderLayout.CENTER);
		
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
		textFechaNac.setText(parsearTimestampAString(usuario.getFechaNac().toSqlTimestamp()));
		panelDatosUsuario.add(textFechaNac);
		
		RoundedPanel panelDatosCuenta = new RoundedPanel(new GridLayout(0, 1, 0, 0));
		panelDatosCuenta.setBorder(new EmptyBorder(50, 50, 100, 50));
		panelContenido.add(panelDatosCuenta);

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
		
		JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelInferior.setBorder(new EmptyBorder(0, 50, 0, 50));
		add(panelInferior, BorderLayout.SOUTH);
		

		JButtonPrimary btnGuardarDatos = new JButtonPrimary("GUARDAR DATOS");
		btnGuardarDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuario usuarioModificado;
				boolean guardadoCorrectamente = false;
				boolean validar = false;
				try {
					GestorDeUsuarios gestorDeUsuarios = new GestorDeUsuarios(Conexion.getInstance().getConexion());
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
		btnGuardarDatos.setPreferredSize(new Dimension(200, 30));
		panelInferior.add(btnGuardarDatos);
		
		boolean hayConexion = GestorDeConexiones.getInstance().hayConexion();
		if (!hayConexion)
			btnGuardarDatos.setVisible(false);
		
		JButtonPrimary btnCerrarSesion = new JButtonPrimary("Cerrar sesión");
		btnCerrarSesion.setPreferredSize(new Dimension(200, 30));
		btnCerrarSesion.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				UsuarioLogueado.getInstance().setUsuario(null);
				MainFrame.getInstance().getContentPane().removeAll();
				MainFrame.getInstance().getContentPane().add(new PanelLogin());
				MainFrame.getInstance().revalidate();
				MainFrame.getInstance().repaint();
			}
		});
		panelInferior.add(btnCerrarSesion);
		btnCerrarSesion.setBackgroundColor(Estilos.BLACK);
		btnCerrarSesion.setHoverColor(Color.DARK_GRAY);
	}

	public static String parsearTimestampAString(java.sql.Timestamp timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		return sdf.format(timestamp);
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

package epicfitpc.vista.paneles;

import javax.swing.JPanel;

import epicfitpc.bbdd.GestorDeUsuarios;
import epicfitpc.controlador.Controlador;
import epicfitpc.ficheros.GestorDeFicherosBinarios;
import epicfitpc.modelo.Usuario;
import epicfitpc.utils.Conexion;
import epicfitpc.utils.Estilos;
import epicfitpc.utils.GestorDeConexiones;
import epicfitpc.utils.Rutas;
import epicfitpc.utils.UsuarioLogueado;
import epicfitpc.utils.WindowUtils;
import epicfitpc.vista.MainFrame;
import epicfitpc.vista.componentes.JButtonPrimary;
import epicfitpc.vista.componentes.JLabelTitle;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

import java.awt.BorderLayout;
import java.awt.GridLayout;

public class PanelLogin extends JPanel {
	private static final long serialVersionUID = 3044079574914466193L;
	private JTextField txtIntroduceTuCorreo;
	private JTextField txtIntroduceTuPass;
	private static final String FICHERO_USUARIOS = Rutas.BACKUP_USUARIOS;

	public PanelLogin(MainFrame frame) {
		initialize(frame);
	}

	private void initialize(MainFrame frame) {
		setLayout(new GridLayout(1, 2));
		setBounds(100, 100, 1200, 750);
		setBackground(Estilos.DARK_BACKGROUND);
		
		JPanel panelIzquierda = new JPanel();
		panelIzquierda.setBackground(Estilos.PRIMARY);
		panelIzquierda.setLayout(new BorderLayout());
		add(panelIzquierda);
		
		JPanel panelDerecha = new JPanel();
		panelDerecha.setLayout(null);
		add(panelDerecha);
		
		JLabelTitle lblNewLabel = new JLabelTitle("¡Bienvenid@ a EpicFit!");
		lblNewLabel.setBounds(180, 111, 300, 31);
		panelDerecha.add(lblNewLabel);

		JButtonPrimary btnNewButton = new JButtonPrimary("Iniciar sesión");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Obtener los datos introducidos
					String usuarioIntroducido = txtIntroduceTuCorreo.getText();
					String passIntroducido = txtIntroduceTuPass.getText();

					boolean hayConexion = GestorDeConexiones.getInstance().hayConexion();
					ArrayList<Usuario> usuarios = null;
					Usuario usuario = null;

					// CARGAR DEPENDIENDO DE CONEXIÓN
					if (hayConexion) {
						GestorDeUsuarios gestorDeUsuarios = new GestorDeUsuarios(Conexion.getInstance().getConexion());
						usuarios = gestorDeUsuarios.obtenerTodosLosUsuarios();
					} else {
						GestorDeFicherosBinarios<Usuario> gdfb = new GestorDeFicherosBinarios<Usuario>(FICHERO_USUARIOS);
						usuarios = gdfb.leer();
					}
					
					// Devolverá el usuario si los datos introducidos son correctos
					Controlador controlador = new Controlador();
					usuario = controlador.comprobarUsuario(usuarios, usuarioIntroducido, passIntroducido);
					if (usuario != null) {
						// si usuario y login es correcto
						WindowUtils.confirmationPane("Hola, " + usuario.getNombre() + ", ¡bienvenid@ a EpicFit!", "Acceso concedido");
						UsuarioLogueado.getInstance().setUsuario(usuario);
						MainFrame.getInstance().getContentPane().removeAll();
						MainFrame.getInstance().getContentPane().add(new PanelMenu());
						MainFrame.getInstance().revalidate();
						MainFrame.getInstance().repaint();
					} else {
						WindowUtils.errorPane("No se ha podido completar el inicio de sesión." , "Acceso denegado");
					}
				} catch (Exception e1) {
					WindowUtils.errorPane("No se ha podido completar el inicio de sesión." , "Acceso denegado");
				}
			}
		});
		btnNewButton.setBounds(180, 418, 241, 31);
		panelDerecha.add(btnNewButton);

		txtIntroduceTuCorreo = new JTextField();
		txtIntroduceTuCorreo.setText("");
		txtIntroduceTuCorreo.setBounds(180, 275, 241, 26);
		panelDerecha.add(txtIntroduceTuCorreo);
		txtIntroduceTuCorreo.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Usuario");
		lblNewLabel_1.setBounds(180, 250, 100, 14);
		panelDerecha.add(lblNewLabel_1);

		txtIntroduceTuPass = new JTextField();
		txtIntroduceTuPass.setBounds(180, 347, 241, 31);
		panelDerecha.add(txtIntroduceTuPass);
		txtIntroduceTuPass.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Contraseña");
		lblNewLabel_2.setBounds(180, 322, 100, 14);
		panelDerecha.add(lblNewLabel_2);

		JButtonPrimary btnNewButton_1 = new JButtonPrimary("Registrarme");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.getInstance().getContentPane().removeAll();
				MainFrame.getInstance().getContentPane().add(new PanelRegistro(frame));
				MainFrame.getInstance().revalidate();
				MainFrame.getInstance().repaint();
			}
		});
		btnNewButton_1.setBounds(180, 575, 241, 31);
		panelDerecha.add(btnNewButton_1);

		// Persistencia de los datos si esta opción está seleccionada
		JCheckBox chckbxNewCheckBox = new JCheckBox("Mantener sesión iniciada");
		chckbxNewCheckBox.setBounds(180, 486, 183, 23);
		panelDerecha.add(chckbxNewCheckBox);

		JLabel lblNewLabel_2_1 = new JLabel("¿Todavia no tienes cuenta?");
		lblNewLabel_2_1.setBounds(180, 550, 300, 20);
		panelDerecha.add(lblNewLabel_2_1);
		
		// logo de la compania
		ImageIcon img = WindowUtils.cargarImagen(Rutas.LOGO_EF, 500, 500);
		JLabel lblNewLabel_3 = new JLabel(img);
		panelIzquierda.add(lblNewLabel_3);
	}
}

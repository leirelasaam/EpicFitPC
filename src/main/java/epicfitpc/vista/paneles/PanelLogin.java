package epicfitpc.vista.paneles;

import javax.swing.JPanel;

import epicfitpc.bbdd.GestorDeUsuarios;
import epicfitpc.controlador.Controlador;
import epicfitpc.ficheros.GestorDeFicherosBinarios;
import epicfitpc.modelo.Usuario;
import epicfitpc.utils.Conexion;
import epicfitpc.utils.GestorDeConexiones;
import epicfitpc.utils.UsuarioLogueado;
import epicfitpc.vista.MainFrame;
import epicfitpc.vista.componentes.JButtonOutlined;
import epicfitpc.vista.componentes.JButtonPrimary;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

import java.awt.Font;

public class PanelLogin extends JPanel {
	private static final long serialVersionUID = 3044079574914466193L;
	private JTextField txtIntroduceTuCorreo;
	private JTextField txtIntroduceTuPass;
	private static final String CARPETA_BACKUP = "src\\main\\java\\epicfitpc\\ficheros\\backup\\";
	private static final String FICHERO_USUARIOS = CARPETA_BACKUP + "usuarios.dat";

	public PanelLogin() {
		initialize();
	}

	private void initialize() {
		setLayout(null);
		setBounds(100, 100, 1200, 750);

		JLabel lblNewLabel = new JLabel("¡Bienvenid@ a EpicFit!");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(825, 110, 153, 31);
		add(lblNewLabel);

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
						// JOptionPane.showMessageDialog(frame, "Bienvenido a EpicFit");
						UsuarioLogueado.getInstance().setUsuario(usuario);
						MainFrame.getInstance().getContentPane().removeAll();
						MainFrame.getInstance().getContentPane().add(new PanelMenu());
						MainFrame.getInstance().revalidate();
						MainFrame.getInstance().repaint();
					} else {
						// si usuario y login es correcto
						// JOptionPane.showMessageDialog(MainFrame.getInstance(), "El login y el
						// password es incorrecto");
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(772, 418, 241, 31);
		add(btnNewButton);

		txtIntroduceTuCorreo = new JTextField();
		txtIntroduceTuCorreo.setText("");
		txtIntroduceTuCorreo.setBounds(772, 275, 241, 26);
		add(txtIntroduceTuCorreo);
		txtIntroduceTuCorreo.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Usuario");
		lblNewLabel_1.setBounds(772, 250, 46, 14);
		add(lblNewLabel_1);

		txtIntroduceTuPass = new JTextField();
		txtIntroduceTuPass.setBounds(772, 347, 241, 31);
		add(txtIntroduceTuPass);
		txtIntroduceTuPass.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Contraseña");
		lblNewLabel_2.setBounds(772, 322, 80, 14);
		add(lblNewLabel_2);

		JButtonOutlined btnNewButton_1 = new JButtonOutlined("Registrarme");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.getInstance().getContentPane().removeAll();
				MainFrame.getInstance().getContentPane().add(new PanelRegistro());
				MainFrame.getInstance().revalidate();
				MainFrame.getInstance().repaint();
			}
		});
		btnNewButton_1.setBounds(772, 575, 241, 31);
		add(btnNewButton_1);

		// logo de la compania
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\in2dm3-v\\Downloads\\Logo.PNG"));
		lblNewLabel_3.setBounds(0, -1, 602, 751);
		add(lblNewLabel_3);

		// Persistencia de los datos si esta opción está seleccionada
		JCheckBox chckbxNewCheckBox = new JCheckBox("Mantener sesión iniciada");
		chckbxNewCheckBox.setBounds(772, 486, 183, 23);
		add(chckbxNewCheckBox);

		JLabel lblNewLabel_2_1 = new JLabel("¿Todavia no tienes cuenta?");
		lblNewLabel_2_1.setBounds(825, 550, 134, 14);
		add(lblNewLabel_2_1);
	}
}

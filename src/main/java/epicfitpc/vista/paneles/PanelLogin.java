package epicfitpc.vista.paneles;

import javax.swing.JPanel;

import epicfitpc.bbdd.GestorDeUsuarios;
import epicfitpc.modelo.Usuario;
import epicfitpc.utils.Conexion;
import epicfitpc.utils.UsuarioLogueado;
import epicfitpc.vista.MainFrame;
import epicfitpc.vista.componentes.JButtonOutlined;
import epicfitpc.vista.componentes.JButtonPrimary;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

import java.awt.Font;
import java.awt.HeadlessException;
import javax.swing.JPasswordField;

public class PanelLogin extends JPanel {
	private static final long serialVersionUID = 3044079574914466193L;
	private JTextField txtIntroduceTuCorreo;
	private JTextField txtIntroduceTuPass;
	private JPasswordField passwordField;
	private JCheckBox chckbxNewCheckBox;

	// Declarar el gestor de usuarios como un atributo de la clase
	private GestorDeUsuarios gestorDeUsuarios;

	public PanelLogin(MainFrame frame) {
		initialize(frame);
	}

	private void initialize(MainFrame frame) {
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

					// Devolverá el usuario si los datos introducidos son correctos
					Usuario usuario = gestorDeUsuarios.comprobarUsuario(usuarioIntroducido, passIntroducido);
					if (usuario != null) { // si usuario == null significa que los datos introducidos son incorrectos
						// si usuario y login es correcto
						// JOptionPane.showMessageDialog(frame, "Bienvenido a EpicFit");
						UsuarioLogueado.getInstance().setUsuario(usuario);
                        MainFrame.getInstance().getContentPane().removeAll();
                        MainFrame.getInstance().getContentPane().add(new PanelMenu());
                        MainFrame.getInstance().revalidate();
                        MainFrame.getInstance().repaint();
					} else {
						// si usuario y login es correcto
						JOptionPane.showMessageDialog(MainFrame.getInstance(), "El login y el password es incorrecto");
					}
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
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

		txtIntroduceTuPass = new JPasswordField();
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
				MainFrame.getInstance().getContentPane().add(new PanelRegistro(frame));
				MainFrame.getInstance().revalidate();
				MainFrame.getInstance().repaint();
			}
		});
		btnNewButton_1.setBounds(772, 575, 241, 31);
		add(btnNewButton_1);

		// Logo de la compania
		JLabel lblNewLabel_3 = new JLabel("LogoEpicFit");
		lblNewLabel_3.setIcon((Icon) new ImageIcon("resources/Logo.PNG"));
		lblNewLabel_3.setBounds(0, -1, 602, 751);
		add(lblNewLabel_3);

		chckbxNewCheckBox.setBounds(772, 486, 183, 23);
		add(chckbxNewCheckBox);

		JLabel lblNewLabel_2_1 = new JLabel("¿Todavia no tienes cuenta?");
		lblNewLabel_2_1.setBounds(825, 550, 134, 14);
		add(lblNewLabel_2_1);

	}
}

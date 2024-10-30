package epicfitpc.vista.paneles;

import javax.swing.JPanel;

import epicfitpc.bbdd.GestorDeUsuarios;
import epicfitpc.modelo.Usuario;
import epicfitpc.utils.Conexion;
import epicfitpc.utils.UsuarioLogueado;
import epicfitpc.vista.MainFrame;
import epicfitpc.vista.componentes.JButtonOutlined;
import epicfitpc.vista.componentes.JButtonPrimary;
import epicfitpc.vista.componentes.JLabelText;

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
import java.awt.Image;

import javax.swing.JPasswordField;

public class PanelLogin extends JPanel {
	private static final long serialVersionUID = 3044079574914466193L;
	private JTextField txtIntroduceTuCorreo;
	private JTextField txtIntroduceTuPass;
	private JPasswordField passwordField;

	// Declarar el gestor de usuarios como un atributo de la clase
	private GestorDeUsuarios gestorDeUsuarios;

	public PanelLogin(MainFrame frame) {
		initialize(frame);
	}

	private void initialize(MainFrame frame) {
		setLayout(null);
		setBounds(100, 100, 1200, 750);

		JLabelText lblNewLabel = new JLabelText("¡Bienvenid@ a EpicFit!");
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

		JLabelText lblNewLabel_1 = new JLabelText("Usuario");
		lblNewLabel_1.setBounds(772, 250, 241, 14);
		add(lblNewLabel_1);

		txtIntroduceTuPass = new JPasswordField();
		txtIntroduceTuPass.setBounds(772, 347, 241, 31);
		add(txtIntroduceTuPass);
		txtIntroduceTuPass.setColumns(10);

		JLabelText lblNewLabel_2 = new JLabelText("Contraseña");
		lblNewLabel_2.setBounds(772, 322, 241, 14);
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
		JLabel lblNewLabel_3 = new JLabel("");
		
		ImageIcon icon = new ImageIcon("resources/Logo.PNG");
		Image img = icon.getImage();
		Image resizedImg = img.getScaledInstance(600, 600, java.awt.Image.SCALE_SMOOTH);
		lblNewLabel_3.setIcon(new ImageIcon(resizedImg));
		lblNewLabel_3.setBounds(94, 87, 579, 547);
		add(lblNewLabel_3);

	}
}

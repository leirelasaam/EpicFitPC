package epicfitpc.vista.paneles;

import javax.swing.JPanel;

import epicfitpc.modelo.bbdd.GestorDeUsuarios;
import epicfitpc.modelo.pojos.Usuario;
import epicfitpc.utils.Conexion;
import epicfitpc.utils.UsuarioLogueado;
import epicfitpc.vista.MainFrame;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

import java.awt.Font;
import java.awt.HeadlessException;

public class PanelLogin extends JPanel {
	private static final long serialVersionUID = 3044079574914466193L;
	private JTextField txtIntroduceTuCorreo;
	private JTextField txtIntroduceTuPass;

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

		JButton btnNewButton = new JButton("Iniciar sesión");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					GestorDeUsuarios gestorDeUsuarios = new GestorDeUsuarios(Conexion.getInstance().getConexion());

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
					// TODO Auto-generated catch block
					e1.printStackTrace();
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

		JButton btnNewButton_1 = new JButton("Registrarme");
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

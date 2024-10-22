package epicfitpc.modelo.bbdd;


package views;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import panelControllers.LoginPanelController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;

public class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel panel = null;
	private JTextField textFieldLogin;
	private JTextField textFieldPassword;
	private int tryNumber = 0;
	private boolean isCorrectLoginPassword = false;
	private boolean isBlockedAccount = false;

	/**
	 * Create the panel.
	 */
	public LoginPanel(ArrayList<JPanel> panels) {

		panel = new JPanel();
		panel.setBackground(new Color(58, 58, 58));
		panel.setBounds(0, 0, 1000, 650);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));

		textFieldLogin = new JTextField();
		textFieldLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldLogin.setForeground(new Color(62, 202, 27));
		textFieldLogin.setBackground(new Color(58, 58, 58));
		textFieldLogin.setBounds(496, 157, 115, 38);
		panel.add(textFieldLogin);
		textFieldLogin.setColumns(10);

		textFieldPassword = new JPasswordField();
		textFieldPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldPassword.setForeground(new Color(62, 202, 27));
		textFieldPassword.setBackground(new Color(58, 58, 58));
		textFieldPassword.setBounds(496, 221, 115, 38);
		panel.add(textFieldPassword);
		textFieldPassword.setColumns(10);

		JLabel lblNewLabelLogin = new JLabel("Login");
		lblNewLabelLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabelLogin.setForeground(new Color(62, 202, 27));
		lblNewLabelLogin.setBounds(350, 159, 86, 35);
		panel.add(lblNewLabelLogin);

		JLabel lblNewLabelPassword = new JLabel("Password");
		lblNewLabelPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabelPassword.setForeground(new Color(62, 202, 27));
		lblNewLabelPassword.setBounds(350, 223, 86, 35);
		panel.add(lblNewLabelPassword);

		JButton btnNewButtonConfirm = new JButton("Confirmar");
		btnNewButtonConfirm.setForeground(new Color(58, 58, 58));
		btnNewButtonConfirm.setBackground(new Color(62, 202, 27));
		btnNewButtonConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPanelController loginPanelController = new LoginPanelController();
				try {
					isBlockedAccount = loginPanelController.checkBlockedAcount(textFieldLogin.getText().trim());
				} catch (SQLException sqle) {
					JOptionPane.showMessageDialog(null, "Error con la BBDD - " + sqle.getMessage());
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null, "Error generico - " + a.getMessage());
				}
					if (isBlockedAccount)
						JOptionPane.showMessageDialog(panel,
								"El usuario está bloqueado, póngase en contacto con el administrador.");
					else {
						try {
							isCorrectLoginPassword = loginPanelController.logInPasswordCorrect(
									textFieldLogin.getText().trim(), textFieldPassword.getText().trim());
						} catch (SQLException sqle) {
							JOptionPane.showMessageDialog(null, "Error con la BBDD - " + sqle.getMessage());
						} catch (Exception c) {
							JOptionPane.showMessageDialog(null, "Error generico - " + c.getMessage());
						}
							if (isCorrectLoginPassword) {
								try {
									loginPanelController.changeLastConection(textFieldLogin.getText().trim());
								}catch (SQLException sqle) {
									JOptionPane.showMessageDialog(null, "Error con la BBDD - " + sqle.getMessage());
								}catch (Exception c) {
									JOptionPane.showMessageDialog(null, "Error generico - " + c.getMessage());
								}
								panels.get(7).setVisible(false);
								panels.get(8).setVisible(true);
							} else {
								JOptionPane.showMessageDialog(panel, "El login y el password es incorrecto");
								if (tryNumber < 2)
									JOptionPane.showMessageDialog(panel,
											"Numero de intentos restantes " + (2 - tryNumber));
								tryNumber++;
								if (tryNumber == 3) {
									try {
										loginPanelController.changeUserToBlock(textFieldLogin.getText().trim());
										JOptionPane.showMessageDialog(panel, "Su usuario ha sido bloqueado");
									} catch (SQLException sqle) {
										JOptionPane.showMessageDialog(null, "Error con la BBDD - " + sqle.getMessage());
									} catch (Exception blocking) {
										JOptionPane.showMessageDialog(null,
												"Error generico - " + blocking.getMessage());
									}
								}
							}
						}
					}
				

			
		});

		btnNewButtonConfirm.setBounds(517, 325, 89, 23);
		panel.add(btnNewButtonConfirm);

		panel.setLayout(null);

		JButton btnNewButtonGoBack = new JButton("Volver");
		btnNewButtonGoBack.setForeground(new Color(58, 58, 58));
		btnNewButtonGoBack.setBackground(new Color(62, 202, 27));
		btnNewButtonGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panels.get(7).setVisible(false);
				panels.get(1).setVisible(true);
			}
		});
		btnNewButtonGoBack.setBounds(331, 325, 89, 23);
		panel.add(btnNewButtonGoBack);

	}

	public JPanel getPanel() {
		return panel;
	}
}
LoginPanel.java
Mostrando LoginPanel.java.
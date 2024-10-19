package epicfitpc.vista.paneles;

import javax.swing.JPanel;

import epicfitpc.modelo.pojos.Usuario;
import epicfitpc.vista.MainFrame;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.event.ActionEvent;

public class PanelLogin extends JPanel {
	private static final long serialVersionUID = 3044079574914466193L;
	private MainFrame frame = null;

	public PanelLogin(MainFrame frame) {
		this.frame = frame;
		initialize();
	}
	
	private void initialize() {
		setLayout(null);
		setBounds(100, 100, 1200, 750);
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setBounds(10, 10, 45, 20);
		add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Acceder");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuario usuario = new Usuario("1", "Leire", "Lasa", "leire@gmail.com", "1234", LocalDate.now(), LocalDate.now(), 0, false);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new PanelMenu(frame, usuario));
				frame.revalidate();
				frame.repaint();
			}
		});
		btnNewButton.setBounds(10, 40, 85, 21);
		add(btnNewButton);
	}
}

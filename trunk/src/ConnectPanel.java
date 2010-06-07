import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ConnectPanel extends JPanel {
	JPanel nickPanel, clientPanel, serverPanel, csPanel, finalPanel;
	JTextField nickField, IPField, cPortField, sPortField;
	JButton clientButton, serverButton, finalButton;
	
	ConnectPanel() {
		/* The GUI */
		GUI();
	}
		

	private void GUI() {
		this.setLayout(new BorderLayout());
		/* Nickname */
		nickPanel = new JPanel();
		add(nickPanel, "North");
		nickPanel.add(new JLabel("Nickname", SwingConstants.RIGHT));
		nickField = new JTextField(10);
		nickPanel.add(nickField);

		/* Client-Server */
		csPanel = new JPanel(new BorderLayout());
		add(csPanel, "Center");
		
		/* Client */
		clientPanel = new JPanel(new GridBagLayout());
		clientPanel.setBorder(BorderFactory.createTitledBorder("Client"));
		csPanel.add(clientPanel, "North");
		GridBagConstraints gbc1 = new GridBagConstraints(); // for (width,height) = (1,1)
		gbc1.gridwidth = 1;
		gbc1.gridheight = 1;
		GridBagConstraints gbc2 = new GridBagConstraints(); // for (width,height) = (2,1)
		gbc2.gridwidth = 2;
		gbc2.gridheight = 1;

		gbc1.gridx = 0;
		gbc1.gridy = 0;
		clientPanel.add(new JLabel("IP", SwingConstants.RIGHT), gbc1);
		gbc2.gridx = 1;
		gbc2.gridy = 0;
		IPField = new JTextField(10);
		clientPanel.add(IPField, gbc2);
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		clientPanel.add(new JLabel("Port", SwingConstants.RIGHT), gbc1);
		gbc2.gridx = 1;
		gbc2.gridy = 1;
		cPortField = new JTextField(10);
		clientPanel.add(cPortField, gbc2);
		gbc1.gridx = 1;
		gbc1.gridy = 2;
		clientButton = new JButton("Connect");
		clientPanel.add(clientButton, gbc1);

		
		/* Server */
		serverPanel = new JPanel(new GridBagLayout());		
		serverPanel.setBorder(BorderFactory.createTitledBorder("Server"));
		csPanel.add(serverPanel, "South");
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		serverPanel.add(new JLabel("Port", SwingConstants.RIGHT), gbc1);
		gbc2.gridx = 1;
		gbc2.gridy = 0;
		sPortField = new JTextField(10);
		serverPanel.add(sPortField, gbc2);
		gbc1.gridx = 1;
		gbc1.gridy = 1;
		serverButton = new JButton(" Create ");
		serverPanel.add(serverButton, gbc1);
		
		/* Ready / Start Game */
		finalPanel = new JPanel();
		add(finalPanel, "South");		
		finalButton = new JButton("Ready");
		finalButton.setEnabled(false);
		finalButton.setFont(new Font("Arial Black", Font.PLAIN, 24));
		finalPanel.add(finalButton);
	}
}


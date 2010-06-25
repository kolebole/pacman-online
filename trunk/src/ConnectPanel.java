/* File: template.java
 * Start: 2010/06/07
 * Modification: 2010/06/25
 * Description: The bottom-right JPanel for connection establishment.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

public class ConnectPanel extends JPanel implements Constants, ActionListener {
	JPanel nickPanel, clientPanel, serverPanel, csPanel, finalPanel;
	JTextField nickField, addrField;
	JButton clientButton, serverButton, finalButton;
	
	/* Network相關的東西：到最後可能獨立出去 */
	Socket cs, cons;  // client socket, connected socket
	String addr;
	
	
	ConnectPanel() {
		/* The GUI */
		GUI();
		addListeners();
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
		clientPanel.add(new JLabel("Address", SwingConstants.RIGHT), gbc1);
		gbc2.gridx = 1;
		gbc2.gridy = 0;
		addrField = new JTextField(10);
		clientPanel.add(addrField, gbc2);
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		gbc2.gridx = 1;
		gbc2.gridy = 1;
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
		gbc2.gridx = 1;
		gbc2.gridy = 0;
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
		finalButton.setForeground(Color.blue);
		finalPanel.add(finalButton);
	}
	
	private void addListeners() {
		clientButton.addActionListener(this);
		serverButton.addActionListener(this);
		finalButton.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent evt) {
		JButton src = (JButton)evt.getSource();
		/* Server: Create a game */
		if (src == serverButton) {			
			try {
				/* new a ServerThread to do the I/O blocking jobs */
				new ServerThread();

				/* disable other textfields or buttons */
				nickField.setEnabled(false);
				addrField.setEnabled(false);
				clientButton.setEnabled(false);
				serverButton.setEnabled(false);
			} catch (Exception e) {
				Utility.error(e);
			}
		}
		/* Client: Connect to a server */
		else if (src == clientButton) {
			addr = addrField.getText();
			try {
				/* default address is "localhost" */
				if (addr == "") addr = "localhost";
				System.out.println("Client: Connect to " + addr + " ...");
				/* new a Socket and connect at the same time */
				cs = new Socket(addr, port);
				if (cs.isConnected()) {
					System.out.println("Connection succeeded.");
				}
				else {
					System.out.println("Connection failed.");
				}
				nickField.setEnabled(false);
				addrField.setEnabled(false);
				clientButton.setEnabled(false);
				serverButton.setEnabled(false);				
			} catch (Exception e) {
				Utility.error(e);
			}
		}
	}
}


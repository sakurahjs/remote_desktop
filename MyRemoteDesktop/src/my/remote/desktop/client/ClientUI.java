package my.remote.desktop.client;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientUI extends JFrame implements ActionListener {
	public JTextField tfServerIp = new JTextField(16);
	public JTextField tfServerPort = new JTextField(5);
	public JTextField tfName = new JTextField(5);
	public JButton btnConnect = new JButton("Connect");
	public JLabel lbStatus = new JLabel();
	
	public ClientUI() {
		doLayoutSetting();
		doDefaultSetting();
	}
	
	private void doLayoutSetting() {
		btnConnect.addActionListener(this);
		
		JLabel lbServerIp = new JLabel("Server IP : ");
		JLabel lbServerPort = new JLabel("Server Port : ");
		JLabel lbName = new JLabel("Name : ");
		
		GridLayout backgroundLayout = new GridLayout(5, 1);
		JPanel firstLine = new JPanel();
		JPanel secondLine = new JPanel();
		JPanel thirdLine = new JPanel();
		JPanel forthLine = new JPanel();
		JPanel fifthLine = new JPanel();
		
		firstLine.add(lbServerIp); firstLine.add(tfServerIp);
		secondLine.add(lbServerPort); secondLine.add(tfServerPort);
		thirdLine.add(lbName); thirdLine.add(tfName);
		forthLine.add(lbStatus);
		fifthLine.add(btnConnect);
		
		setLayout(backgroundLayout);
		add(firstLine); add(secondLine); add(thirdLine); add(forthLine); add(fifthLine);
		
		pack();
	}
	
	public void doDefaultSetting() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		int x = (screenWidth / 2) - (this.getWidth() / 2);
		int y = (screenHeight / 2) - (this.getHeight() / 2);
		
		setLocation(x, y);
	}
	
	public static void main(String[] args) {
		new ClientUI().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnConnect) {
			int serverPort = Integer.parseInt(tfServerPort.getText());
			String serverIp = tfServerIp.getText();
			String name = tfName.getText();
			
			try {
				ClientManager manager = new ClientManager(serverIp, serverPort, name, this);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		}
	}

}

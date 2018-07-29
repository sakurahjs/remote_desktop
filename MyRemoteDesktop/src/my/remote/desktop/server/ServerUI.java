package my.remote.desktop.server;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ServerUI extends JFrame implements MouseListener, ActionListener {
	private String [] titles = {"이름", "IP"};
	public DefaultTableModel model = new DefaultTableModel(titles, 0) {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	private JTable table = new JTable(model);
	
	private JTextField tfServerPort = new JTextField(5);
	private JButton btnOpen = new JButton("Server Open");
	private ServerManager manager;
	
	public CaptureDialog captureDialog;
	
	public ServerUI() {
		doLayoutSetting();
		doDefaultSetting();
	}
	
	private void doLayoutSetting() {
		table.addMouseListener(this);
		btnOpen.addActionListener(this);
		JScrollPane scroll = new JScrollPane(table);
		JPanel north = new JPanel();
		JLabel lbServerPort = new JLabel("Server Port : ");
		north.add(lbServerPort); north.add(tfServerPort); north.add(btnOpen);
		
		add(north, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
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
		new ServerUI().setVisible(true);
	}

	public void drawCapture(Image image) {
		//TODO draw capture
		if (captureDialog == null) {
			captureDialog = new CaptureDialog(this);
			captureDialog.setVisible(true);
		}
		
		captureDialog.drawCapture(image);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() >= 2) {
			int selectedRow = table.getSelectedRow();
			String selectedName = (String)model.getValueAt(selectedRow, 0);
			//JOptionPane.showMessageDialog(null, selectedName);
			//send image offer
			Client selectedClient = manager.clients.get(selectedName);
			try {
				selectedClient.sender.sendStart();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "serverUI : "+ex.getMessage());
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOpen) {
			int serverPort = Integer.parseInt(tfServerPort.getText());
			try {
				if (manager == null) {
					manager = new ServerManager(serverPort, this);
					manager.start();
					JOptionPane.showMessageDialog(this, "Server opened.");
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		}
	}

}

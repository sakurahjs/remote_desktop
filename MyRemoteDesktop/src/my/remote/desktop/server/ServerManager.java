package my.remote.desktop.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class ServerManager extends Thread {
	private ServerSocket serverSocket;
	private int serverPort;
	
	public ServerUI serverUI;
	public HashMap<String, Client> clients = new HashMap<>();
	
	public ServerManager(int serverPort, ServerUI serverUI) throws Exception {
		this.serverPort = serverPort;
		this.serverUI = serverUI;
	}
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(serverPort);
			
			while(true) {
				Socket socket = serverSocket.accept();
				new Client(socket, this);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}

package my.remote.desktop.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientManager {
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	private ResponseReceiver receiver;
	private RequestSender sender;
	
	public ClientUI clientUI;
	
	public ClientManager(String serverIp, int serverPort, String name, ClientUI clientUI) throws Exception {
		socket = new Socket(serverIp, serverPort);
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
		this.clientUI = clientUI; 
		
		receiver = new ResponseReceiver(ois, oos, this);
		receiver.start();
		
		sender = new RequestSender(oos);
		sender.sendName(name);
		
		clientUI.lbStatus.setText("connected");
	}
}

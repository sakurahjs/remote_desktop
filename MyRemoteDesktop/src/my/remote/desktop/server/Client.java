package my.remote.desktop.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public RequestReceiver receiver;
	public ResponseSender sender;
	
	private String name;

	public Client(Socket socket, ServerManager manager) throws Exception {
		this.socket = socket;
		this.oos = new ObjectOutputStream(socket.getOutputStream());
		this.ois = new ObjectInputStream(socket.getInputStream());
		receiver = new RequestReceiver(ois, oos, manager, this);
		receiver.start();
		
		sender = new ResponseSender();
		sender.setOos(oos);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Socket getSocket() {
		return socket;
	}

	public ObjectOutputStream getOos() {
		return oos;
	}

	public ObjectInputStream getOis() {
		return ois;
	}

	public RequestReceiver getReceiver() {
		return receiver;
	}

	public ResponseSender getSender() {
		return sender;
	}

}

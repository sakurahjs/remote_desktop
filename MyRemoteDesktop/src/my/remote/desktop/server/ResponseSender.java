package my.remote.desktop.server;

import java.io.ObjectOutputStream;

import my.remote.desktop.common.Constants;

public class ResponseSender {
	private ObjectOutputStream oos;
	
	public ResponseSender() {	}

	public ObjectOutputStream getOos() {
		return oos;
	}

	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}
	
	public void sendStart() throws Exception {
//		System.out.println("server : response sender : sendStart() : oos : "+oos);
		Object [] response = {Constants.CMD_CAPTURE_START};
		oos.writeObject(response);
	}
	
	public void sendEnd() throws Exception {
		Object [] response = {Constants.CMD_CAPTURE_END};
		oos.writeObject(response);
	}
	
	public void sendScreenSize(int width, int height) throws Exception {
		Object [] response = {Constants.CMD_SCREEN_SIZE, width, height};
		oos.writeObject(response);
	}
	
	public void sendKey(int key) throws Exception {
		
	}
	
	public void sendMouseMove(int x, int y) throws Exception {
		
	}
	
	public void sendMouseClick(int key) throws Exception {
		
	}
}



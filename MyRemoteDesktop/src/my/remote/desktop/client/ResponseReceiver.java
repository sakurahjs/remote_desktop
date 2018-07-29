package my.remote.desktop.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import my.remote.desktop.common.Constants;

public class ResponseReceiver extends Thread {
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private int outputWidth;
	private int outputHeight;
	private ClientManager manager;
	
	public ResponseReceiver(ObjectInputStream ois, ObjectOutputStream oos, ClientManager manager) {
		this.ois = ois;
		this.oos = oos;
		this.manager = manager;
	}

	@Override
	public void run() {
		CaptureSender captureSender = null;
		try {
			while(true) {
				Object[] fromServer = (Object[]) ois.readObject();
				int command = (int)fromServer[0];
				
				switch (command) {
					case Constants.CMD_CAPTURE_START:
						if (captureSender == null) {
							captureSender = new CaptureSender(oos, outputWidth, outputHeight);
							captureSender.start();
							manager.clientUI.lbStatus.setText("sending...");
						}
						break;
					case Constants.CMD_CAPTURE_END:
						captureSender.end();
						captureSender = null;
						manager.clientUI.lbStatus.setText("connected");
						break;
		
					case Constants.CMD_SCREEN_SIZE:
						outputWidth = (int)fromServer[1];
						outputHeight = (int)fromServer[2];
						break;
					default:
						break;
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(manager.clientUI, "ResponseReceiver : " + e.getMessage());
		}
	}
}

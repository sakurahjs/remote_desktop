package my.remote.desktop.server;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import my.remote.desktop.common.Constants;

public class RequestReceiver extends Thread {
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private ResponseSender sender = new ResponseSender();
	private ServerManager manager;
	private Client client;
	
	public RequestReceiver(ObjectInputStream ois, ObjectOutputStream oos, ServerManager manager, Client client) {
		this.ois = ois;
		this.oos = oos;
		sender.setOos(oos);
		this.manager = manager;
		this.client = client;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				Object [] fromClient = (Object[])ois.readObject();
				int command = (int)fromClient[0];
				
				switch (command) {
					case Constants.CMD_SET_USER_NAME:
						String name = (String)fromClient[1];
						client.setName(name);
						manager.clients.put(name, client);
						String [] tableData = {name, client.getSocket().getRemoteSocketAddress().toString()};
						manager.serverUI.model.addRow(tableData);
						int width = Toolkit.getDefaultToolkit().getScreenSize().width;
						int height = Toolkit.getDefaultToolkit().getScreenSize().height;
						sender.sendScreenSize(width, height);
						break;
					case Constants.CMD_CAPTURE_IMAGE:
						//System.out.println("server receiver : CMD_CAPTURE_IMAGE");
						int [][] imageArray = (int [][])fromClient[1];
						BufferedImage image = new BufferedImage(imageArray.length, imageArray[0].length, BufferedImage.TYPE_INT_RGB);
						
						//System.out.println(imageArray.length +" : "+ imageArray[0].length);
						
						for (int i = 0 ; i < imageArray.length ; i++) {
							for (int j = 0 ; j < imageArray[i].length ; j++) {
								image.setRGB(i, j, imageArray[i][j]);
								//System.out.printf("%d ", imageArray[i][j]);
							}
							//System.out.println();
						}
						
						imageArray = null;
						
						System.gc();
						
						manager.serverUI.drawCapture(
								image.getScaledInstance(
										Toolkit.getDefaultToolkit().getScreenSize().width,
										Toolkit.getDefaultToolkit().getScreenSize().height, 
										BufferedImage.SCALE_SMOOTH)
								);
						break;
					default:
						break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}

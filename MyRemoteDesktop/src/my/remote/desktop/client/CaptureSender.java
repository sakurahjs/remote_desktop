package my.remote.desktop.client;

import java.awt.Image;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import my.remote.desktop.common.Constants;

public class CaptureSender extends Thread {
	private ObjectOutputStream oos = null;
	private boolean sendSwitch = true;
	private int outputWidth;
	private int outputHeight;
	
	public CaptureSender(ObjectOutputStream oos, int outputWidth, int outputHeight) {
		this.oos = oos;
		this.outputWidth = outputWidth;
		this.outputHeight = outputHeight;
	}

	@Override
	public void run() {
		try {
			CaptureTaker captureTaker = new CaptureTaker(outputWidth, outputHeight);
		
			while(sendSwitch) {
				Image img = captureTaker.takeScaledImage();
				
				ByteAr
				
				Object [] toServer = {Constants.CMD_CAPTURE_IMAGE, ..};
				oos.writeObject(toServer);
				oos.flush();
				
				sleep(Constants.CAPTURE_INTERVAL);
				
				System.gc();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "CaptureSender : "+e.getMessage());
		} catch (OutOfMemoryError oome) {
			JOptionPane.showMessageDialog(null, "CaptureSender : "+oome.getMessage());
			oome.printStackTrace();
		}
	}
	
	public void end() {
		sendSwitch = false;
	}
}

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.JFrame;
import javax.swing.JPanel;

import my.remote.desktop.client.CaptureTaker;

public class TestMain extends JFrame {
	private Image img;
	private JPanel p = new JPanel() {
		@Override
		public void paint(Graphics g) {
			// TODO Auto-generated method stub
			super.paint(g);
			
			ImageObserver observer = null;
			g.drawImage(img, 0, 0, observer );
		}
	};
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		new TestMain().setVisible(true);
		
	}

	public TestMain() throws Exception {
		int outputWidth = 1024;
		int outputHeight = 768;
		CaptureTaker captureTaker = new CaptureTaker(outputWidth, outputHeight);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(p);
		
		this.img = captureTaker.take();
		p.invalidate();
	}
}

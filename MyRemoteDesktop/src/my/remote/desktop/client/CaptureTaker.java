package my.remote.desktop.client;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class CaptureTaker {
	private int displayWidth; //display width of client
	private int displayHeight; //display height of client
	
	private int outputWidth; //output image width requested from server
	private int outputHeight; //output image height requested from server
	
	private Rectangle screenRect;
	private Image image;
	private Robot robot;
	
	private int[][] rgbArray;
	
	public CaptureTaker() throws Exception {
		robot = new Robot();
	}

	public CaptureTaker(int outputWidth, int outputHeight) throws Exception {
		this();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		this.displayWidth = screenSize.width;
		this.displayHeight = screenSize.height;
		this.outputWidth = outputWidth;
		this.outputHeight = outputHeight;
		
		rgbArray = new int[displayWidth][displayHeight];
		screenRect = new Rectangle(0, 0, displayWidth, displayHeight);
		calculateOutputSize();
	}
	
	//calculate output image width and height to fit server screen size
	protected void calculateOutputSize() {
		//when server screen size is smaller than client screen size
		if (outputWidth < displayWidth) { //screen width is the criteria
			//calculate percentage of scale between server and client
			double scale = (double)outputWidth / (double)displayWidth;
			
			//calculate output screen height with the percentage of scale
			outputHeight = (int)(displayHeight * scale);
		}
	}
	
	public Image takeScaledImage() {
		return take().getScaledInstance(outputWidth, outputHeight, BufferedImage.SCALE_SMOOTH);
	}
	
	public BufferedImage take() {
		return robot.createScreenCapture(screenRect);
	}
	
	public int[][] takeRGBArray() {
		BufferedImage bufferedImage = take();
		
		for(int i = 1 ; i < displayWidth ; i++) {
			for (int j = 1 ; j < displayHeight ; j++) {
				rgbArray[i][j] = bufferedImage.getRGB(i - 1, j - 1);
			}
		}
		
		return rgbArray;
	}
	
	public Image getCurrentImage() {
		return image;
	}
}

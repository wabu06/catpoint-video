package com.udacity.catpoint.application;


import com.udacity.catpoint.data.*;
import com.udacity.catpoint.service.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.IOException;

import org.opencv.core.*;
import org.opencv.imgcodecs.*;
import org.opencv.imgproc.Imgproc;


public class FeedDisplayWindow implements StatusListener
{
	private static FeedDisplayWindow fdw = null;
	
	private SecurityService securityService;
	
	private int IMAGE_WIDTH = 950; //640;
	private int IMAGE_HEIGHT = 535; //360; 
	
	private JLabel displayLabel;
	private JPanel displayPanel;
	
	private JFrame displayFrame;
	
	private FeedDisplayWindow(SecurityService securityService)
	{
		this.securityService = securityService;
		
		securityService.addStatusListener(this);

		displayLabel = new JLabel();
		displayLabel.setSize( new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT) );
		displayLabel.setBackground(Color.WHITE);
		
		try( InputStream is = getClass().getClassLoader().getResourceAsStream("color-bars.png") )
		{
				//BufferedImage buffImg = ImageIO.read( new File("cameraFeeds/color-bar.png") );
			BufferedImage buffImg = ImageIO.read(is);
			
			Image tmp = new ImageIcon(buffImg).getImage();
		
			displayLabel.setIcon( new ImageIcon( tmp.getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH) ) );
			displayLabel.repaint();
		}
		catch(Exception exp) {
			exp.printStackTrace();
		}

		displayPanel = new JPanel();
		BoxLayout displayPanelLayout = new BoxLayout(displayPanel, BoxLayout.PAGE_AXIS);
		displayPanel.setLayout( displayPanelLayout );
		displayPanel.setSize( new Dimension(IMAGE_WIDTH + 10, IMAGE_HEIGHT + 45) );
		displayPanel.add(displayLabel);
		
		displayFrame = new JFrame("Feed Display"); // "Motion Sensor Feed"
		displayFrame.setSize( new Dimension(IMAGE_WIDTH + 10, IMAGE_HEIGHT + 45) );
		displayFrame.add(displayPanel);
		
		//displayFrame.setVisible(true);
		
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	}
	
	public static FeedDisplayWindow getInstance(SecurityService securityService)
	{
		if(fdw == null)
			fdw = new FeedDisplayWindow(securityService);
		
		return fdw;
	}
	
	public static FeedDisplayWindow getInstance() {
		return fdw;
	}
	
	public void setDisplaySize(int w, int h)
	{
		IMAGE_WIDTH = w; IMAGE_HEIGHT = h;
		
		displayLabel.setSize( new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT) );
		displayPanel.setSize( new Dimension(IMAGE_WIDTH + 10, IMAGE_HEIGHT + 45) );
		displayFrame.setSize( new Dimension(IMAGE_WIDTH + 10, IMAGE_HEIGHT + 45) );
	}
	
	public JFrame getDisplayFrame() {
		return displayFrame;
	}
	
	public JLabel getDisplay() {
		return displayLabel;
	}
	
	public void repaint() {
		displayPanel.repaint();
	}
	
	@Override
    public void notify(AlarmStatus status) {} // no behavior necessary

    @Override
    public void catDetected(boolean catDetected, Sensor sensor) {} // no behavior necessary

    @Override
    public void sensorStatusChanged(Sensor sensor) {}
    
    @Override
    public void resetCameraHeaderMsg() {}
    
    @Override
    public void updateSystemStatus() {}

    @Override
    public void armingStatusChanged() {}
    
    @Override
    public void updateSensors() {}
    
	@Override
    public void enableAddSensor(boolean enable) {}
    
    @Override
    public void setFeedDisplayTitle(Sensor sensor) {
    	displayFrame.setTitle(sensor.getSensorType().toString() + " Sensor Feed");
    }
    
    @Override
    public void setFeedDisplayTitle() {
    	displayFrame.setTitle("Feed Display");
    }
    
    @Override
    public void showOrHideFeedDisplay()
    {
    	if( displayFrame.isVisible() )
    		displayFrame.setVisible(false);
    	else
    		displayFrame.setVisible(true);
    }
    
    @Override
    public void showFeed(Mat frame, int sensorHash)
	{
		//Imgproc.putText(frame, elapse.toString(), new Point(10, 50), Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(255, 255, 255), 2);
		
		if( sensorHash != securityService.getSelectedFeed() )
			return;
					
		MatOfByte mob = new MatOfByte();
		Imgcodecs.imencode(".jpg", frame, mob);
				
		byte[] byteArray = mob.toArray(); 

		//InputStream is = new ByteArrayInputStream(byteArray); 
			
		try( InputStream is = new ByteArrayInputStream(byteArray) )
		{
			BufferedImage buffImg = ImageIO.read(is);

			Image tmp = new ImageIcon(buffImg).getImage();
				
			displayLabel.setIcon( new ImageIcon( tmp.getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH) ) );
					
			displayLabel.repaint();
		}
		catch(Exception exp) {
			exp.printStackTrace();
		}
	}
}


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


public class FeedDisplayWindow
{
	private static FeedDisplayWindow fdw = null;
	
	private int IMAGE_WIDTH;
	private int IMAGE_HEIGHT; 
	
	private JLabel displayLabel;
	private JPanel displayPanel;
	
	private JFrame displayFrame;
	
	private FeedDisplayWindow()
	{
		displayLabel = new JLabel();
		displayLabel.setBackground(Color.WHITE);
		
		displayPanel = new JPanel();
		
		BoxLayout displayPanelLayout = new BoxLayout(displayPanel, BoxLayout.PAGE_AXIS);
		displayPanel.setLayout( displayPanelLayout );
		
		displayFrame = new JFrame("Feed Display"); // "Motion Sensor Feed"
		
		displayFrame.add(displayPanel);
		displayFrame.setVisible(true);
	}
	
	public static FeedDisplayWindow getInstance()
	{
		if(fdw == null)
			fdw = new FeedDisplayWindow();
		
		return fdw;
	}
	
	public void setDisplaySize(int w, int h)
	{
		IMAGE_WIDTH = w; IMAGE_HEIGHT = h;
		
		displayLabel.setSize( new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT) );
		displayPanel.setSize( new Dimension(IMAGE_WIDTH + 10, IMAGE_HEIGHT + 45) );
		displayFrame.setSize( new Dimension(IMAGE_WIDTH + 10, IMAGE_HEIGHT + 45) );
	}
	
	public JLabel getDisplay() {
		return displayLabel;
	}
	
	public void repaint() {
		displayPanel.repaint();
	}
}


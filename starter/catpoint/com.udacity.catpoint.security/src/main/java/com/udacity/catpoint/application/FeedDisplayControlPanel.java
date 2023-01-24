package com.udacity.catpoint.application;

import com.udacity.catpoint.data.*;
import com.udacity.catpoint.service.*;
//import com.udacity.catpoint.service.StyleService;

import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.IOException;

import java.nio.charset.Charset;
import java.time.Instant;

import java.util.*;
//import java.nio.file.*;
import java.util.stream.*;

import org.opencv.core.Mat;


/** Panel containing the 'camera' output. Allows users to 'refresh' the camera
 * by uploading their own picture, and 'scan' the picture, sending it for image analysis
 */
public class FeedDisplayControlPanel extends JPanel implements StatusListener // FeedDisplayControlPanel
{
    private SecurityService securityService;
    
    private FeedDisplayWindow fdw;
    
	private JPanel headerPanel;
	private JPanel camPanel;
	private JPanel bttnPanel;

    //private JLabel cameraHeader;
    private JLabel panelHeader;
    
    private JButton showFeedButton;
	
	//private Random RNG = new Random( Instant.now().toEpochMilli() );

    public FeedDisplayControlPanel(SecurityService securityService, FeedDisplayWindow fdw)
	{
        super();
		
        setLayout( new MigLayout() );
        
        this.securityService = securityService;
        securityService.addStatusListener(this);
        
        this.fdw = fdw;

		panelHeader = new JLabel("APP Control"); panelHeader.setFont(StyleService.HEADING_FONT);
        //cameraHeader = new JLabel("No Cats Detected As Yet");
        //cameraHeader.setFont(StyleService.HEADING_FONT);
        
        //camPanel = new JPanel(); camPanel.setLayout( new MigLayout() ); camPanel.add(cameraLabel, "span 3");

        this.showFeedButton = new JButton("Show/Hide Feed Display Window");
        
        showFeedButton.addActionListener( e -> {
												if( fdw.getDisplayFrame().isVisible() )
													fdw.getDisplayFrame().setVisible(false);
												else
													fdw.getDisplayFrame().setVisible(true);
						 					  });
		
		JComboBox imgDetector = new JComboBox( new String[] {"FAKE", "OPENCV"} );
        imgDetector.setSelectedItem( securityService.getDetectionService() );
        
        imgDetector.addItemListener( e -> {
        										if( e.getStateChange() == ItemEvent.SELECTED )
        										{
        											securityService.setDetectionService( (String) e.getItem() );
        											System.out.println( securityService.getDetectionService() );
        										}
        										
        										if( e.getStateChange() == ItemEvent.DESELECTED )
        											System.out.println( e.getItem() );
        									});

		JCheckBox stateBttn = new JCheckBox("Revert To Default State At Startup");
		
		if( securityService.getState().equals("YES") )
        	stateBttn.setSelected(true);
        
        stateBttn.addItemListener( e -> {
        									if( e.getStateChange() == ItemEvent.SELECTED )
        										securityService.setState("YES");
        									else
        										securityService.setState("NO");
        								});
        
        JButton stopFeeds = new JButton("Shutdown All Feeds");
        
        stopFeeds.addActionListener( e -> securityService.stopAllFeeds() );

		add(panelHeader, "span 3, wrap");
		//add(cameraHeader, "span 3, wrap");
		add(showFeedButton, "span 3, wrap");
		add(stopFeeds, "span 3, wrap");
		//add(imgDetector, "span 3, wrap");
		add(imgDetector);
		add(stateBttn, "span 3, wrap");
    }

    @Override
    public void notify(AlarmStatus status) {} //no behavior necessary

    @Override
    public void catDetected(boolean cat, Sensor sensor)
	{
        //if(cat)
        	//cameraHeader.setText( "DANGER - CAT DETECTED" + Character.toString(0x1F63C) + sensor.getSensorType().toString() );
        //else
            //cameraHeader.setText("No Cats Detected");
    }

    @Override
    public void sensorStatusChanged() {} //no behavior necessary
    
    @Override
    public void resetCameraHeaderMsg() { /*cameraHeader.setText("Camera Feed");*/ }
	
	@Override
    public void armingStatusChanged() {}
    
    @Override
    public void updateSystemStatus() {}
    
    @Override
    public void showFeed(Mat frame, int sensorHash) {}
    
}

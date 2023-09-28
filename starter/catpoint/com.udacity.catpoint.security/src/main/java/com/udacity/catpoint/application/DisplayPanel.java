package com.udacity.catpoint.application;

import com.udacity.catpoint.data.*;
import com.udacity.catpoint.service.*;
//import com.udacity.catpoint.service.StyleService;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

import org.opencv.core.Mat;

/**
 * Displays the current status of the system. Implements the StatusListener
 * interface so that it can be notified whenever the status changes.
 */
public class DisplayPanel extends JPanel implements StatusListener {

    private JLabel currentStatusLabel;
    
    //private JLabel detectMsg;
    
    private SecurityService securityService;

    public DisplayPanel(SecurityService securityService)
    {
        super();
        setLayout(new MigLayout());
        
        this.securityService = securityService;

        securityService.addStatusListener(this);

        JLabel panelLabel = new JLabel("The Cat Detection System");
        JLabel systemStatusLabel = new JLabel("System Status:");
        currentStatusLabel = new JLabel();

        panelLabel.setFont(StyleService.HEADING_FONT);

        //notify(securityService.getAlarmStatus());
        
        updateSystemStatus();
        
        //detectMsg = new JLabel("No Cats Detected As Yet");

        add(panelLabel, "span 2, wrap");
        add(systemStatusLabel);
        add(currentStatusLabel, "wrap");
        //add(detectMsg, "span 3, wrap");

    }

    @Override
    public void notify(AlarmStatus status)
    {
		// currentStatusLabel.setText(status.getDescription());
		// currentStatusLabel.setBackground(status.getColor());
		// currentStatusLabel.setOpaque(true);
    }

    @Override
    public void catDetected(boolean cat, Sensor sensor)
    {
    	/*if(cat)
        	detectMsg.setText( "DANGER - CAT DETECTED" + Character.toString(0x1F63C) + sensor.getSensorType().toString() );
        else
            detectMsg.setText("No Cats Detected");*/
    }

    @Override
    public void sensorStatusChanged(Sensor sensor) {} // no behavior necessary
    
    @Override
    public void resetCameraHeaderMsg() {}
    
    @Override
    public void showFeed(Mat frame, int sensorHash) {}
    
    @Override
    public void armingStatusChanged() {}
    
    @Override
    public void updateSystemStatus()
    {
    	ArmingStatus status = securityService.getArmingStatus();
    	
    	currentStatusLabel.setText(status.getDescription());
        currentStatusLabel.setBackground(status.getColor());
        currentStatusLabel.setOpaque(true);
    }
    
    @Override
    public void updateSensors() {}
    
    @Override
    public void enableAddSensor(boolean enable) {}
    
    @Override
    public void setFeedDisplayTitle(Sensor sensor) {}
    
    @Override
    public void setFeedDisplayTitle() {}
    
    @Override
    public void showOrHideFeedDisplay() {}
    
    @Override
    public void stopFeedsEnable(boolean enable) {}
}

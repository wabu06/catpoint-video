package com.udacity.catpoint.application;


import com.udacity.catpoint.service.*;
import com.udacity.catpoint.data.*;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.*;

import org.opencv.core.Mat;


public class SensorCntrlUnit extends JPanel implements StatusListener
{
	private SecurityService securityService;
	private Sensor sensor;

	private Map<Sensor, SensorFeedService> sensorServiceMap;
	
	private JLabel sensorLabel;
	private JLabel alarmStatusLabel = new JLabel();
	
	private JButton showFeedButton;
	private JButton sensorToggleButton;
	private JButton sensorRemoveButton;
	
	public SensorCntrlUnit(SecurityService securityService, Sensor sensor)
    {
    	super();
    	
    	this.setLayout(new MigLayout());
    	
    	this.securityService = securityService;
    	securityService.addStatusListener(this); // must be removed when sensor is removed
    	
    	this.sensor = sensor;
    	sensorServiceMap = securityService.getSensorFeeds();
    	
    	SensorFeedService sfs = new SensorFeedService(securityService, sensor);
    	sensorServiceMap.put(sensor, sfs);
    	
    	sensorLabel = new JLabel (	String.format("%s(%s): %s", sensor.getName(), sensor.getSensorType().toString(),
    								(sensor.getActive() ? "Active" : "Inactive")) );
    	
    	AlarmStatus status = securityService.getSensorFeeds().get(sensor).getSensorAlarmStatus();
			
		alarmStatusLabel.setText(status.getDescription());
        alarmStatusLabel.setBackground(status.getColor());
        alarmStatusLabel.setOpaque(true);
        
        showFeedButton = new JButton( String.format("%s %s %s", "Show", sensor.getSensorType().toString(), "Sensor Feed") );
		sensorToggleButton = new JButton((sensor.getActive() ? "Deactivate" : "Activate"));
        sensorRemoveButton = new JButton("Remove Sensor");
        
        this.add(sensorLabel, "span 1");
        this.add(showFeedButton, "span 1, wrap push");
        this.add(alarmStatusLabel, "span 0");
        this.add(sensorToggleButton, "split 2");
        this.add(sensorRemoveButton);
        
        this.repaint();
        this.revalidate();
    }

	@Override
    public void notify(AlarmStatus status) {} // no behavior necessary

    @Override
    public void catDetected(boolean cat, Sensor sensor) {}
    
    @Override
    public void sensorStatusChanged(Sensor sensor) {}
    
    @Override
    public void resetCameraHeaderMsg() {}
    
    @Override
    public void showFeed(Mat frame, int sensorHash) {}
    
    @Override
    public void armingStatusChanged() {}
    
    @Override
    public void updateSystemStatus() {}
    
    @Override
    public void updateSensors() {}
    
    @Override
    public void enableAddSensor(boolean enable){}
    
    @Override
    public void setFeedDisplayTitle(Sensor sensor) {}
    
    @Override
    public void setFeedDisplayTitle() {}
    
    @Override
    public void showOrHideFeedDisplay() {}
    
    @Override
    public void stopFeedsEnable(boolean enable) {}
}


package com.udacity.catpoint.service;


import com.udacity.catpoint.data.*;
//import com.udacity.catpoint.service.*;
import com.udacity.catpoint.application.*;

import com.udacity.detection.service.*;

import org.opencv.core.*;
import org.opencv.imgcodecs.*;
import org.opencv.imgproc.Imgproc;

//import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;

import java.time.*;

import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import javax.swing.JOptionPane;

import java.util.UUID;

import java.util.concurrent.ArrayBlockingQueue;

import java.lang.reflect.Proxy;

import javax.swing.JLabel;
import javax.swing.JButton;

import org.slf4j.Logger;


		// SensorService sfs = new SensorService(new SecurityRepository(), new Sensor(name, type) )
public class SensorFeedService
{
	private SecurityService securityService;
	
	private Logger log;
	
	private Sensor sensor;
	private int sensorHash;
	
	private AlarmStatus sensorAlarmStatus;
	
	private ArrayBlockingQueue<Mat> feedFrameBuffer;
	
	private boolean show, feed;
	
	private JLabel sensorLabel, alarmStatusLabel;
	
	private JButton showFeedButton, sensorToggleButton, sensorRemoveButton;

	private DetectionService detectionService;
	
	private String feedSource;
	
	private VideoCapture capture;

	public SensorFeedService(SecurityService ss, Sensor sensor)
	{
		this.securityService = ss;
		
		log = ss.getLogger();

		this.sensor = sensor;
		this.sensorHash = sensor.hashCode();
		
		show = false;
		feed = true;
		
		this.feedFrameBuffer = ss.getFeedFrameBuffer();

		sensorAlarmStatus = AlarmStatus.NO_ALARM;
		
		this.detectionService = (DetectionService) Proxy.newProxyInstance
		(
			SecurityService.class.getClassLoader(),
			new Class<?>[] { DetectionService.class },
			new DetectionServiceHandler(securityService)
		);
		
		this.feedSource = null; getFeedSource();
		
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		
		capture = new VideoCapture(); 

		if( !capture.open(feedSource) )
		{
			showFeedButton.setText("Unable To Connect To Feed Source");
			showFeedButton.setEnabled(false);
			sensorToggleButton.setEnabled(false);

			feed = false;
			show = false;
		}
	}
	
	private void getFeedSource() {
		feedSource = "cameraFeeds/feed" + securityService.getRandNum() + ".mp4";
	}
	
	public void setFeed(boolean feed) { this.feed = feed; }
	
	public void setShow(boolean show) { this.show = show; }
	
	public boolean getFeed() { return feed; }
	public boolean getShow() { return show; }
	
	public void startFeed() { feed = true; }
	public void stopFeed() { feed = false; }
	
	public SensorFeedService setSensorLabel(JLabel sensorLabel) {
		this.sensorLabel = sensorLabel;
		return this;
	}
	
	public JLabel getSensorLabel() {
		return sensorLabel;
	}
	
	public SensorFeedService setAlarmStatusLabel(JLabel alarmStatusLabel) {
		this.alarmStatusLabel = alarmStatusLabel;
		return this;
	}
	
	public JLabel getAlarmStatusLabel() {
		return alarmStatusLabel;
	}
	
	public SensorFeedService setShowFeedButton(JButton showFeedButton) {
		this.showFeedButton = showFeedButton;
		return this;
	}
	
	public JButton getShowFeedButton() {
		return showFeedButton;
	}
	
	public SensorFeedService setSensorToggleButton(JButton sensorToggleButton) {
		this.sensorToggleButton = sensorToggleButton;
		return this;
	}
	
	public JButton getSensorToggleButton() {
		return sensorToggleButton;
	}
	
	public SensorFeedService setSensorRemoveButton(JButton sensorRemoveButton) {
		this.sensorRemoveButton = sensorRemoveButton;
		return this;
	}
	
	public JButton getSensorRemoveButton() {
		return sensorRemoveButton;
	}
	
	public Sensor getSensor() {
		return sensor;
	}
	
	public UUID getSensorId() {
		return sensor.getSensorId();
	}

	public void setSensorAlarmStatus(AlarmStatus status, Boolean cat) {
        sensorAlarmStatus = status;
    }
    
    public void setSensorAlarmStatus(AlarmStatus status) {
    	sensorAlarmStatus = status;
    }
    
    public AlarmStatus getSensorAlarmStatus() {
        return sensorAlarmStatus;
    }
    
    private void updateAlarmStatusLabel()
    {
    	alarmStatusLabel.setText(sensorAlarmStatus.getDescription());
        alarmStatusLabel.setBackground(sensorAlarmStatus.getColor());
       	alarmStatusLabel.setOpaque(true);
    }
	
	private void catDetected(Boolean cat)
	{
        if(cat)
        {
        	switch( securityService.getArmingStatus() )
        	{
            	case ARMED_HOME -> setSensorAlarmStatus(AlarmStatus.ALARM);
            	
            	case ARMED_AWAY -> setSensorAlarmStatus(AlarmStatus.PENDING_ALARM); 
            	
            	case DISARMED -> setSensorAlarmStatus(AlarmStatus.NO_ALARM);
            };
        }
        else
        	setSensorAlarmStatus(AlarmStatus.NO_ALARM);

        securityService.getStatusListeners().forEach( sl -> sl.catDetected(cat, sensor) );
        updateAlarmStatusLabel();

        //securityService.getStatusListeners().forEach( sl -> sl.sensorStatusChanged(sensor) );
    }
	
	private void processFrame(Mat frame)
    {
    	if( !sensor.getActive() )
			return;

        catDetected(detectionService.frameContainsCat(frame, 50.0f)); // OpencvDetectService(Mat frame, confidenceThreshhold)
   	}
	
	public void getAndAnalyzeFeed()
	{
		Mat frame = new Mat();

		while(feed)
		{	
			if( !capture.read(frame) )
			{
				capture.release();
					
				if( !capture.open(feedSource) )
				{
					showFeedButton.setText("Feed Source Connection Lost");
					showFeedButton.setEnabled(false);
					sensorToggleButton.setEnabled(false);

					feed = false;
					show = false;
				}
					
				continue;
			}

			processFrame(frame);
			
			try
			{
				if(feedFrameBuffer != null)
					feedFrameBuffer.put(frame);
			}
			catch(Exception exp) {
				log.warn("A Frame Was Dropped, Feed Frame Buffer Write Error!!");
				continue;
			}
			
			//securityService.getStatusListeners().forEach( sl -> sl.showFeed(frame, sensorHash) );
		}
	}
}

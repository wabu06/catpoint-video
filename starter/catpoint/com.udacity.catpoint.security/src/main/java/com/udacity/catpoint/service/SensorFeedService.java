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

import java.lang.reflect.Proxy;



		// SensorService sfs = new SensorService(new SecurityRepository(), new Sensor(name, type) )
public class SensorFeedService
{
	//private SecurityRepository securityRepository;
	private SecurityService securityService;
	
	private Sensor sensor;
	private int sensorHash;
	
	private AlarmStatus sensorAlarmStatus;
	
	private boolean show;
	private boolean feed;

	private DetectionService detectionService;
	
	//private FeedDisplayWindow fdw = FeedDisplayWindow.getInstance();
	
	private String feedSource;
	
	private VideoCapture capture;
	
	//public SensorService(SecurityRepository sr, SecurityService ss, Sensor sensor, String feedSource)
	public SensorFeedService(SecurityService ss, Sensor sensor)
	{
		//this.securityRepository = sr;
		this.securityService = ss;

		this.sensor = sensor;
		this.sensorHash = sensor.hashCode();
		
		show = false;
		feed = true;

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
			// System.out.println("Unable to open video file!");
			JOptionPane.showMessageDialog(null, "Unable To Connect To Video Feed!", "ERROR", JOptionPane.ERROR_MESSAGE);
			feed = false;
			show = false;
			//System.exit(0);
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
        securityService.getStatusListeners().forEach( sl -> sl.sensorStatusChanged(sensor) );
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
			//Long start = Instant.now().getEpochSecond();
			
			if( !capture.read(frame) )
			{
				capture.release();
					
				if( !capture.open(feedSource) )
				{
					//System.out.println("Unable to open video file!");
					JOptionPane.showMessageDialog(null, "Feed Source Connection Lost!", "ERROR", JOptionPane.ERROR_MESSAGE);
					feed = false;
					show = false;
					//System.exit(0);
				}
					
				continue;
			}
			
			//elapse += Instant.now().getEpochSecond() - start;
			
			processFrame(frame);

				// show currently selected feed
			//if( sensor.equals( ss.getSelectedFeed().getSensor() ))
			
			securityService.getStatusListeners().forEach( sl -> sl.showFeed(frame, sensorHash) );
		}
	}
}

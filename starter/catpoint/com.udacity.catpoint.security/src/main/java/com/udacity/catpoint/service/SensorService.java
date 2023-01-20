package com.udacity.catpoint.service;


import com.udacity.catpoint.data.*;
import com.udacity.catpoint.service.*;
import com.udacity.catpoint.application.*;

import com.udacity.detection.service.*;

import org.opencv.core.*;
import org.opencv.imgcodecs.*;
import org.opencv.imgproc.Imgproc;

import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;

import java.time.*;

import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import javax.swing.JOptionPane;

import java.util.UUID;

import java.lang.reflect.Proxy;



		// SensorService sfs = new SensorService(new SecurityRepository(), new Sensor(name, type) )
public class SensorService
{
	private SecurityRepository securityRepository;
	
	private SecurityService securityService;
	
	private Sensor sense;
	private boolean show;
	private boolean feed;
	
	private DetectionService detectionService;
	
	private static FeedDisplayWindow fdw = FeedDisplayWindow.getInstance();
	
	private String feedSource;
	
	private VideoCapture capture;
	
	public SensorService(SecurityRepository sr, SecurityService ss, Sensor sense, String feedSource)
	{
		this.securityRepository = sr;
		
		this.securityService = ss;

		this.sense = sense;
		show = false;
		
		this.detectionService = (DetectionService) Proxy.newProxyInstance
		(
			SecurityService.class.getClassLoader(),
			new Class<?>[] { DetectionService.class },
			new DetectionServiceHandler(securityRepository)
		);
		
		feed = true;
		
		this.feedSource = feedSource;
		
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		
		capture = new VideoCapture(); 

		if( !capture.open(feedSource) )
		{
			System.out.println("Unable to open video file!");
			System.exit(0);
		}
	}
	
	public void setFeed(boolean feed) { this.feed = feed; }
	
	public void setShow(boolean show) { this.show = show; }
	
	public UUID getSensorId() {
		return sense.getSensorId();
	}
	
	private void showFeed(Mat frame)
	{
			//Imgproc.putText(frame, elapse.toString(), new Point(10, 50), Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(255, 255, 255), 2);
						
			MatOfByte mob = new MatOfByte();
			Imgcodecs.imencode(".jpg", frame, mob);
				
			byte[] byteArray = mob.toArray(); 

			//InputStream is = new ByteArrayInputStream(byteArray); 
			
			try( InputStream is = new ByteArrayInputStream(byteArray) )
			{
				BufferedImage buffImg = ImageIO.read(is);

				Image tmp = new ImageIcon(buffImg).getImage();
				
				fdw.getDisplay().setIcon( new ImageIcon( tmp.getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH) ) );
					
				fdw.repaint();
			}
			catch(Exception exp) {
				exp.printStackTrace();
			}
	}
	
	public void startFeed() { feed = true; }
	public void stopFeed() { feed = false; }
	
	public void setSensorAlarmStatus(AlarmStatus status)
    {
        securityRepository.setAlarmStatus(sense, status);
        //statusListeners.forEach(sl -> sl.notify(status));
    }
    
    public AlarmStatus getSensorAlarmStatus() {
        return securityRepository.getAlarmStatus(sense);
    }
	
	private void catDetected(Boolean cat)
	{
		//Object[] sensors = getSensors().stream().filter( s -> s.getActive() ).toArray();
		
        if(cat)
        {
        	switch( getArmingStatus() )
        	{
            	case ARMED_HOME -> setSensorAlarmStatus(AlarmStatus.ALARM);
            	
            	case ARMED_AWAY -> setSensorAlarmStatus(AlarmStatus.PENDING_ALARM);; 
            	
            	case DISARMED -> setSensorAlarmStatus(AlarmStatus.NO_ALARM);
            };
        }
        else
        	setSensorAlarmStatus(AlarmStatus.NO_ALARM);
        	
        //statusListeners.forEach( sl -> sl.catDetected(cat, sensors) );
    }
	
	private void processFrame(Mat frame)
    {
    	if( securityService.getArmingStatus() == ArmingStatus.DISARMED && !sense.getActive() )
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
					System.out.println("Unable to open video file!");
					System.exit(0);
				}
					
				continue;
			}
			
			//elapse += Instant.now().getEpochSecond() - start;
			
			processFrame(frame);

			if(show)
				showFeed(frame);
		}
	}
}

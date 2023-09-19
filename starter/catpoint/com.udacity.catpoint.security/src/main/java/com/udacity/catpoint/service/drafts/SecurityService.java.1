package com.udacity.catpoint.service;

import com.udacity.catpoint.application.StatusListener;
import com.udacity.catpoint.data.AlarmStatus;
import com.udacity.catpoint.data.ArmingStatus;
import com.udacity.catpoint.data.SecurityRepository;
import com.udacity.catpoint.data.Sensor;

import com.udacity.image.service.*;

import java.awt.image.BufferedImage;
import java.awt.Color;

import java.util.HashSet;
import java.util.Set;
import java.util.Random;

import com.google.inject.Guice;
import javax.inject.Inject;

import java.lang.reflect.Proxy;


/**
 * Service that receives information about changes to the security system. Responsible for
 * forwarding updates to the repository and making any decisions about changing the system state.
 *
 * This is the class that should contain most of the business logic for our system, and it is the
 * class you will be writing unit tests for.
 */
public class SecurityService
{
	private ImageService imageService;
	
    private SecurityRepository securityRepository;
    private Set<StatusListener> statusListeners = new HashSet<>();
    
    private Random RNG = new Random();
    
    ImageService proxyImageService;

	//@Inject
	public SecurityService(SecurityRepository securityRepository)
	{
        this.securityRepository = securityRepository;

		//this.imageService = imageService;
		
		//imageService.log.info("Using: " + imageService.getClass() + "\n");
		
		this.imageService = (ImageService) Proxy.newProxyInstance
															(
																SecurityService.class.getClassLoader(),
																new Class<?>[] { ImageService.class },
																new ImageServiceHandler(securityRepository)
															);
    }

    /**
     * Sets the current arming status for the system. Changing the arming status
     * may update both the alarm status.
     * @param armingStatus
     */
    public void setArmingStatus(ArmingStatus armingStatus)
	{
        //if(armingStatus == ArmingStatus.DISARMED)
        	//setAlarmStatus(AlarmStatus.NO_ALARM);
		
		if(armingStatus == ArmingStatus.DISARMED)
		{
			setAlarmStatus(AlarmStatus.NO_ALARM);
			getSensors().forEach( s -> s.setActive(Boolean.FALSE) );
			statusListeners.forEach( sl -> { sl.sensorStatusChanged(); sl.resetCameraHeaderMsg(); } );
		}

        securityRepository.setArmingStatus(armingStatus);
		
		//if( armingStatus == ArmingStatus.ARMED_HOME )
			//processImage( getCurrentCameraImage() );
    }
    
    private void handleArmedAway(int length) 
    {
    	if( length > 0 )
    		setAlarmStatus(AlarmStatus.ALARM);
    	else
    	{
    		AlarmStatus.PENDING_ALARM.setDescription("I'm in Danger...");
    		AlarmStatus.PENDING_ALARM.setColor( new Color(0xff8c00) );
    		setAlarmStatus(AlarmStatus.PENDING_ALARM);
    		AlarmStatus.PENDING_ALARM.setColor( new Color(200,150,20) );
    		AlarmStatus.PENDING_ALARM.setDescription("Am I in Danger?");
    	}
    }

    /**
     * Internal method that handles alarm status changes based on whether
     * the camera currently shows a cat.
     * @param cat True if a cat is detected, otherwise false.
     */
    private void catDetected(Boolean cat)
	{
		Object[] sensors = getSensors().stream().filter( s -> s.getActive() ).toArray();
		
        if(cat)
        {
        	switch( getArmingStatus() )
        	{
            	case ARMED_HOME:
            		setAlarmStatus(AlarmStatus.ALARM);
            	break;
            	
            	case ARMED_AWAY:
            		handleArmedAway(sensors.length); 
            	break;
            	
            	case DISARMED:
            		setAlarmStatus(AlarmStatus.NO_ALARM);
            	break;
            }
        }
        else
        	setAlarmStatus(AlarmStatus.NO_ALARM);
        	
        statusListeners.forEach( sl -> sl.catDetected(cat, sensors) );
    }

    /**
     * Register the StatusListener for alarm system updates from within the SecurityService.
     * @param statusListener
     */
    public void addStatusListener(StatusListener statusListener) {
        statusListeners.add(statusListener);
    }

    public void removeStatusListener(StatusListener statusListener) {
        statusListeners.remove(statusListener);
    }

    /**
     * Change the alarm status of the system and notify all listeners.
     * @param status
     */
    public void setAlarmStatus(AlarmStatus status)
    {
    	/*if( (status == AlarmStatus.PENDING_ALARM) && (getArmingStatus() == ArmingStatus.ARMED_AWAY) )
    	{
    		status.setDescription("I'm in Danger...");
    		securityRepository.setAlarmStatus(status);
        	statusListeners.forEach(sl -> sl.notify(status));
    		status.setDescription("Am I in Danger?");
    	}
    	else
    	{*/
        	securityRepository.setAlarmStatus(status);
        	statusListeners.forEach(sl -> sl.notify(status));
       // }
    }

    /**
     * Internal method for updating the alarm status when a sensor has been activated.
     */
    private void handleSensorActivated() {
        if(securityRepository.getArmingStatus() == ArmingStatus.DISARMED) {
            return; //no problem if the system is disarmed
        }
        switch(securityRepository.getAlarmStatus()) {
            case NO_ALARM: /* NO_ALARM -> */ setAlarmStatus(AlarmStatus.PENDING_ALARM);
				break;
            case PENDING_ALARM: /* PENDING_ALARM -> */ setAlarmStatus(AlarmStatus.ALARM);
        }
    }

    /**
     * Internal method for updating the alarm status when a sensor has been deactivated
     */
    private void handleSensorDeactivated()
    {
        switch(securityRepository.getAlarmStatus())
        {
            case PENDING_ALARM: /* PENDING_ALARM -> */ setAlarmStatus(AlarmStatus.NO_ALARM);
            	//break;
			//case ALARM: /* ALARM -> */ setAlarmStatus(AlarmStatus.PENDING_ALARM);
        }
    }

    /**
     * Change the activation status for the specified sensor and update alarm status if necessary.
     * @param sensor
     * @param active
     */
    public void changeSensorActivationStatus(Sensor sensor, Boolean active)
    {
        /*if(!sensor.getActive() && active) {
            handleSensorActivated();
        } else if (sensor.getActive() && !active) {
            handleSensorDeactivated();
        }*/
        sensor.setActive(active);
        securityRepository.updateSensor(sensor);
    }

    /**
     * Send an image to the SecurityService for processing. The securityService will use its provided
     * ImageService to analyze the image for cats and update the alarm status accordingly.
     * @param currentCameraImage
     */
    public void processImage(BufferedImage currentCameraImage)
    {
    	if( getArmingStatus() == ArmingStatus.DISARMED )
		{   
    		boolean active = getSensors().stream().anyMatch( s -> s.getActive().booleanValue()  );
    		
    		if(!active)
    			return;
    	}
    	
    	//imageService = proxyImageService;
        
        catDetected(imageService.imageContainsCat(currentCameraImage, 50.0f));
    }

    public AlarmStatus getAlarmStatus() {
        return securityRepository.getAlarmStatus();
    }

    public Set<Sensor> getSensors() {
        return securityRepository.getSensors();
    }

    public void addSensor(Sensor sensor) {
        securityRepository.addSensor(sensor);
    }

    public void removeSensor(Sensor sensor) {
        securityRepository.removeSensor(sensor);
    }

    public ArmingStatus getArmingStatus() {
        return securityRepository.getArmingStatus();
    }
    
    public void setCurrentImage(BufferedImage currentCameraImage) { securityRepository.setCurrentImage(currentCameraImage); }
	public BufferedImage getCurrentImage() { return securityRepository.getCurrentImage(); }
	
	public void setImageService(String imageService) { securityRepository.setImageService(imageService); }
	public String getImageService() { return securityRepository.getImageService(); }
	
	public void setState(String restore) { securityRepository.setState(restore); }
	public String getState() { return securityRepository.getState(); }
}


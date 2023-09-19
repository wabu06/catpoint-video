package com.udacity.catpoint.service;

import com.udacity.catpoint.application.StatusListener;
//import com.udacity.catpoint.data.AlarmStatus;
//import com.udacity.catpoint.data.ArmingStatus;
//import com.udacity.catpoint.data.SecurityRepository;
import com.udacity.catpoint.data.*;

import com.udacity.detection.service.*;

//import java.awt.image.BufferedImage;
import java.awt.Color;

import java.util.*;
//import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import org.opencv.core.Mat;

import com.google.inject.Guice;
import javax.inject.Inject;

import java.lang.reflect.Proxy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.time.Instant;

import java.util.stream.Stream;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Service that receives information about changes to the security system. Responsible for
 * forwarding updates to the repository and making any decisions about changing the system state.
 *
 * This is the class that should contain most of the business logic for our system, and it is the
 * class you will be writing unit tests for.
 */
public class SecurityService
{
    private SecurityRepository securityRepository;
    private Set<StatusListener> statusListeners = new HashSet<>();
    
    private Logger log;
    
    private ArrayBlockingQueue<Mat> feedFrameBuffer = new ArrayBlockingQueue<>(20);
    
    private Map<Sensor, SensorFeedService> sensorServiceMap;
    
    private Random RNG;
    
    private boolean[] select = new boolean[] {false, false, false, false};
    
    private ExecutorService pool;

	public SecurityService(SecurityRepository securityRepository)
	{
        this.securityRepository = securityRepository;
        
        log = LoggerFactory.getLogger(SecurityService.class);
		
		pool = Executors.newFixedThreadPool​(5);
		
		RNG = new Random( Instant.now().toEpochMilli() );
		
		initSensorServiceMap();
    }
    
    public Logger getLogger() {
    	return log;
    }
    
    public ArrayBlockingQueue<Mat> getFeedFrameBuffer() {
    	return this.feedFrameBuffer;
    }
    
    public Random getRNG() {
    	return RNG;
    }
    
    public int getRandNum()
    {
    	int i;
    	
    	do { i = RNG.nextInt(4);  } while( select[i] );
    	
    	select[i] = true;
    	
    	return i + 1;
    }
    
    public void stopFeeds()
    {
    	sensorServiceMap.forEach( (s, sfs) -> {
    											sfs.stopFeed();
    											sfs.getSensorToggleButton().setEnabled(false);
												sfs.getShowFeedButton().setEnabled(false);
    										});
    	unSelectFeed(); //pool.shutdown();

    	statusListeners.forEach( sl -> sl.enableAddSensor(false) );
    }
    
    public void startFeeds()
    {
    	//pool = Executors.newFixedThreadPool​(5);
    	
    	sensorServiceMap.forEach( (s, sfs) -> {
    											sfs.getSensorToggleButton().setEnabled(true);
    											
    											sfs.getShowFeedButton().setEnabled(true);
												
    											sfs.startFeed();
    											pool.submit( () -> sfs.getAndAnalyzeFeed() );
    										});
    	
    	statusListeners.forEach( sl -> sl.enableAddSensor(true) );
    }
    
    private void initSensorServiceMap()
    {
    	sensorServiceMap = new HashMap<>();
    	
    	Set<Sensor> sensors = securityRepository.getSensors();
    	
    	for(Sensor S: sensors)
    	{
    		SensorFeedService sfs = new SensorFeedService(this, S);
    		sensorServiceMap.put(S, sfs);
 
    		pool.submit( () -> sfs.getAndAnalyzeFeed() );
    	}
    }
    
    public SecurityRepository getRepository() {
    	return securityRepository;
    }
    
    public ExecutorService getPool() {
    	return pool;
    }

    /**
     * Sets the current arming status for the system. Changing the arming status
     * may update both the alarm status.
     * @param armingStatus
     */
    public void setArmingStatus(ArmingStatus armingStatus)
	{
		if(armingStatus == ArmingStatus.DISARMED)
		{
			sensorServiceMap.forEach( (s, sfs) -> sfs.setSensorAlarmStatus(AlarmStatus.NO_ALARM) );
			getSensors().forEach( s -> s.setActive(Boolean.FALSE) );
			
			statusListeners.forEach( sl -> sl.updateSensors() );
			//statusListeners.forEach( sl -> sl.sensorStatusChanged() );
		}

        securityRepository.setArmingStatus(armingStatus);
        
        statusListeners.forEach( sl -> sl.updateSystemStatus() );
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
    
    public Set<StatusListener> getStatusListeners() {
    	return statusListeners;
    }
    
    public int getSelectedFeed() {
    	return securityRepository.getSelectedFeed();
    }
    
    public int selectFeed(Sensor sensor) {
    	return securityRepository.selectFeed(sensor);
    }
    
    public int unSelectFeed() {
    	return securityRepository.unSelectFeed();
    }
    
    public Sensor getCurrentSenorFeed() {
    	return securityRepository.getCurrentSenorFeed();
    }

    /**
     * Change the activation status for the specified sensor and update alarm status if necessary.
     * @param sensor
     * @param active
     */
    public void changeSensorActivationStatus(Sensor sensor, Boolean active)
    {
        sensor.setActive(active);
        
        securityRepository.updateSensor(sensor);
    }

    public Set<Sensor> getSensors() {
        return securityRepository.getSensors();
    }
    
    public Map<Sensor, SensorFeedService> getSensorFeeds() {
        return sensorServiceMap;
    }

    public void addSensor(Sensor sensor)
    {
    	securityRepository.addSensor(sensor);
    	
    	SensorFeedService sfs = new SensorFeedService(this, sensor);
    	sensorServiceMap.put(sensor, sfs);
 
    	pool.submit( () -> sfs.getAndAnalyzeFeed() );
    	//pool.submit( () -> sensorServiceMap.get( sensor.getSensorId() ).getAndAnalyzeFeed() );
    }

    public void removeSensor(Sensor sensor)
    {
        if( sensor.hashCode() == getSelectedFeed() )
        {
        	unSelectFeed();
        	statusListeners.forEach( sl -> sl.setFeedDisplayTitle() );
        }

        SensorFeedService sfs = sensorServiceMap.remove(sensor);
        sfs.stopFeed();
        securityRepository.removeSensor(sensor);
    }

    public ArmingStatus getArmingStatus() {
        return securityRepository.getArmingStatus();
    }
    
    public ArmingStatus getPrevArmingStatus() {
    	return securityRepository.getPrevArmingStatus();
    }
    
    public void setDetectionService(String ds) {
    	securityRepository.setDetectionService(ds);
    }
    
    public String getDetectionService() {
    	return securityRepository.getDetectionService();
    }
	
	public void setState(String restore) { securityRepository.setState(restore); }
	
	public String getState() { return securityRepository.getState(); }
}


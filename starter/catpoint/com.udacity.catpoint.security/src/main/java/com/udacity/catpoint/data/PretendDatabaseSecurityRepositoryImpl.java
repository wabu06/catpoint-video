package com.udacity.catpoint.data;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
//import java.util.Set;
import java.util.*;
import java.util.prefs.Preferences;

import java.awt.image.BufferedImage;

import com.udacity.catpoint.service.*;


/**
 * Fake repository implementation for demo purposes. Stores state information in local
 * memory and writes it to user preferences between app loads. This implementation is
 * intentionally a little hard to use in unit tests, so watch out!
 */
public class PretendDatabaseSecurityRepositoryImpl implements SecurityRepository
{
    private Set<Sensor> sensors;
    private AlarmStatus alarmStatus;
    private ArmingStatus armingStatus;
    
    private String imageService, restore;
    
    private BufferedImage currentCameraImage;

    //preference keys
    private static final String SENSORS = "SENSORS";
    private static final String ALARM_STATUS = "ALARM_STATUS";
    private static final String ARMING_STATUS = "ARMING_STATUS";
    
    private static final String IMG_SERVICE = "IMAGE_SERVICE";
    private static final String STATE = "DEFAULT";

    private static final Preferences prefs = Preferences.userNodeForPackage(PretendDatabaseSecurityRepositoryImpl.class);
    private static final Gson gson = new Gson(); //used to serialize objects into JSON

	private Map<UUID, Sensor> sensorServiceMap;

    public PretendDatabaseSecurityRepositoryImpl()
	{
		imageService = prefs.get(IMG_SERVICE, "AWS"); // gets stored image service, if none stored set default
		restore = prefs.get(STATE, "YES");
		
		if( restore.equals("YES") )
		{
			try { prefs.clear(); } catch(Exception exp) {}
			prefs.put(IMG_SERVICE, imageService);
			prefs.put(STATE, restore);
		}
		
		//load system state from prefs, or else default
        //alarmStatus = AlarmStatus.valueOf(prefs.get(ALARM_STATUS, AlarmStatus.NO_ALARM.toString()));
        armingStatus = ArmingStatus.valueOf(prefs.get(ARMING_STATUS, ArmingStatus.DISARMED.toString()));
        
        if( armingStatus == ArmingStatus.DISARMED )
        	alarmStatus = AlarmStatus.NO_ALARM;
        else
        	alarmStatus = AlarmStatus.PENDING_ALARM;

        //we've serialized our sensor objects for storage, which should be a good warning sign that
        // this is likely an impractical solution for a real system
        String sensorString = prefs.get(SENSORS, null);
        if(sensorString == null)
            sensors = new TreeSet<>();
        else
        {		// create empty anonymous class, because TypeToken has a protected constructor
            Type type = new TypeToken<Set<Sensor>>() {}.getType(); 
            sensors = gson.fromJson(sensorString, type);
        }
        
        sensorServiceMap = new HashMap<>();
    }

    @Override
    public void addSensor(Sensor sensor)
    {
        sensors.add(sensor);
        sensorServiceMap.put( sensor.getSensorId(), new SensorService(securityService, sensor, feedSource) );
        prefs.put(SENSORS, gson.toJson(sensors));
    }

    @Override
    public void removeSensor(Sensor sensor)
    {
        sensors.remove(sensor);
        sensorServiceMap.remove( sensor.getSensorId() );
        prefs.put(SENSORS, gson.toJson(sensors));
    }

    @Override
    public void updateSensor(Sensor sensor) {
        sensors.remove(sensor);
        sensors.add(sensor);
        prefs.put(SENSORS, gson.toJson(sensors));
    }

    @Override
    public void setAlarmStatus(AlarmStatus alarmStatus) {
        this.alarmStatus = alarmStatus;
        //prefs.put(ALARM_STATUS, this.alarmStatus.toString());
    }

    @Override
    public void setArmingStatus(ArmingStatus armingStatus) {
        this.armingStatus = armingStatus;
        prefs.put(ARMING_STATUS, this.armingStatus.toString());
    }

    @Override
    public Set<Sensor> getSensors() {
        return sensors;
    }
    
    @Override
    public SensorService getSensorService(Sensor sensor) {
    	return sensorServiceMap.get( sensor.getSensorId() );
    }

    @Override
    public AlarmStatus getAlarmStatus() {
        return alarmStatus;
    }

    @Override
    public ArmingStatus getArmingStatus() {
        return armingStatus;
    }
    
    @Override
    public void setCurrentImage(BufferedImage currentCameraImage) { this.currentCameraImage = currentCameraImage;}
    
    @Override
    public BufferedImage getCurrentImage() { return currentCameraImage; }
    
    @Override
    public String getImageService() { return imageService; }
    
    @Override
    public void setImageService(String imageService)
    { 
    	this.imageService = imageService;
    	prefs.put(IMG_SERVICE, imageService);
    }
    
    @Override
    public void setState(String restore)
    {
    	this.restore = restore;
    	prefs.put(STATE, restore);
    }
    
    @Override
    public String getState() { return this.restore; }
}

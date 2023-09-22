package com.udacity.catpoint.data;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
//import java.util.Set;
import java.util.*;
import java.util.prefs.Preferences;


/**
 * Fake repository implementation for demo purposes. Stores state information in local
 * memory and writes it to user preferences between app loads. This implementation is
 * intentionally a little hard to use in unit tests, so watch out!
 */
public class PretendDatabaseSecurityRepositoryImpl implements SecurityRepository
{
    private Set<Sensor> sensors;

    private ArmingStatus armingStatus;
    private ArmingStatus prevArmingStatus;
    
    private String detectionService, restore;

    private Sensor curfeed; // sensor who's feed is currently being displayed

    	//preference keys
    private static final String SENSORS = "SENSORS";

    private static final String ARMING_STATUS = "ARMING_STATUS";
    
    private static final String PREV_ARMING_STATUS = "PREV_ARMING_STATUS";
    
    private static final String DETECTION_SERVICE = "DETECTION_SERVICE";
    private static final String STATE = "DEFAULT";
    
    //private static final String SELECTED_FEED = "SELECTED_FEED";

    private static final Preferences prefs = Preferences.userNodeForPackage(PretendDatabaseSecurityRepositoryImpl.class);
    private static final Gson gson = new Gson(); //used to serialize objects into JSON

    public PretendDatabaseSecurityRepositoryImpl()
	{
		detectionService = prefs.get(DETECTION_SERVICE, "FAKE"); // gets stored image service, if none stored set default
		restore = prefs.get(STATE, "YES");
		
		if( restore.equals("YES") )
		{
			try {
				prefs.clear();
			} 
			catch(Exception exp) {
				exp.printStackTrace();
			}
			
			prefs.put(DETECTION_SERVICE, detectionService);
			prefs.put(STATE, restore);
		}
		
		prevArmingStatus = ArmingStatus.valueOf(prefs.get(PREV_ARMING_STATUS, ArmingStatus.DISARMED.toString()));
		
			// load system state from prefs, or else default
        	// alarmStatus = AlarmStatus.valueOf(prefs.get(ALARM_STATUS, AlarmStatus.NO_ALARM.toString()));
        armingStatus = ArmingStatus.valueOf(prefs.get(ARMING_STATUS, ArmingStatus.DISARMED.toString()));

        	// we've serialized our sensor objects for storage, which should be a good warning sign that
        	// this is likely an impractical solution for a real system
        String sensorString = prefs.get(SENSORS, null);
        
        if(sensorString == null)
            sensors = new TreeSet<>();
        else
        {		// create empty anonymous class, because TypeToken has a protected constructor
            Type type = new TypeToken<Set<Sensor>>() {}.getType(); 
            sensors = gson.fromJson(sensorString, type);
        }

        curfeed = null;
    }

    @Override
    public void addSensor(Sensor sensor)
    {
        sensors.add(sensor);
        prefs.put(SENSORS, gson.toJson(sensors));
    }

    @Override
    public void removeSensor(Sensor sensor)
    {
        sensors.remove(sensor);
        prefs.put(SENSORS, gson.toJson(sensors));
    }

    @Override
    public void updateSensor(Sensor sensor)
    {
        removeSensor(sensor);
        addSensor(sensor);
    }
    
    @Override
    public Sensor setSelectedFeed(Sensor sensor) {
    	curfeed = sensor;
    	return sensor;
    }
    
    @Override
    public Sensor getCurrentSensorFeed() {
    	return curfeed;
    }

    @Override
    public void setArmingStatus(ArmingStatus armingStatus)
    {
        prevArmingStatus = this.armingStatus;
        this.armingStatus = armingStatus;
        
        prefs.put(ARMING_STATUS, this.armingStatus.toString());
        prefs.put(PREV_ARMING_STATUS, prevArmingStatus.toString());
    }
    
    @Override
    public void setDetectionService(String ds) {
    	detectionService = ds;
    	prefs.put(DETECTION_SERVICE, ds);
    }

    @Override
    public Set<Sensor> getSensors() {
        return sensors;
    }

    @Override
    public ArmingStatus getArmingStatus() {
        return armingStatus;
    }
    
    @Override
    public ArmingStatus getPrevArmingStatus() {
        return prevArmingStatus;
    }
    
    @Override
    public String getDetectionService() {
    	return detectionService;
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

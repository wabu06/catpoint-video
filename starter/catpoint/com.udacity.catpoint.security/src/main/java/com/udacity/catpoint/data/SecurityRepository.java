package com.udacity.catpoint.data;

import java.util.Set;


/**
 * Interface showing the methods our security repository will need to support
 */
public interface SecurityRepository
{
    void addSensor(Sensor sensor);
    void removeSensor(Sensor sensor);
    void updateSensor(Sensor sensor);
    Sensor setSelectedFeed(Sensor sensor);
    void setArmingStatus(ArmingStatus armingStatus);
    Set<Sensor> getSensors();
    ArmingStatus getArmingStatus();
    ArmingStatus getPrevArmingStatus();
	void setState(String restore);
	String getState();
	void setDetectionService(String ds);
	String getDetectionService();
	Sensor getCurrentSensorFeed();
	void setFeeds(boolean feeds);
	boolean feedsEnabled();
}

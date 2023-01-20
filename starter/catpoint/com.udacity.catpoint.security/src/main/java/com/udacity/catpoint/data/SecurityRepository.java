package com.udacity.catpoint.data;

import java.util.Set;

import java.awt.image.BufferedImage;

import com.udacity.catpoint.service.*;


/**
 * Interface showing the methods our security repository will need to support
 */
public interface SecurityRepository
{
    void addSensor(Sensor sensor);
    void removeSensor(Sensor sensor);
    void updateSensor(Sensor sensor);
    void setAlarmStatus(AlarmStatus alarmStatus);
    void setArmingStatus(ArmingStatus armingStatus);
    Set<Sensor> getSensors();
    AlarmStatus getAlarmStatus();
    ArmingStatus getArmingStatus();
	void setCurrentImage(BufferedImage currentCameraImage);
	BufferedImage getCurrentImage();
	String getImageService();
	void setImageService(String imageService);
	void setState(String restore);
	String getState();
	SensorService getSensorService(Sensor sensor);
}

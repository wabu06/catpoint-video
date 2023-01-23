package com.udacity.catpoint.application;

import com.udacity.catpoint.data.AlarmStatus;

import org.opencv.core.Mat;



/**
 * Identifies a component that should be notified whenever the system status changes
 */
public interface StatusListener
{
    void notify(AlarmStatus status);
    void catDetected(boolean cat, Sensor sensor);
    void sensorStatusChanged();
    void resetCameraHeaderMsg();
    void showFeed(Mat frame);
    void armingStatusChanged();
    void updateSystemStatus();
}

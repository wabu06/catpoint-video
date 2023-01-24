package com.udacity.detection.service;


//import java.awt.image.BufferedImage;

import org.opencv.core.Mat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public interface DetectionService
{
	static final Logger log = LoggerFactory.getLogger(DetectionService.class);
	
	boolean frameContainsCat(Mat frame, float confidenceThreshhold);
}

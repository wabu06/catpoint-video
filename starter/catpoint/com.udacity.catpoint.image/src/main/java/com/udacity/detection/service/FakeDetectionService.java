package com.udacity.detection.service;


import org.opencv.core.Mat;
//import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Arrays;
import java.util.Optional;

import java.util.stream.Stream;

import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;



// int[] dataBuffInt = image.getRGB(0, 0, w, h, null, 0, w);

/**
 * Service that tries to guess if an frame displays a cat.
 */
public class FakeDetectionService implements DetectionService
{
    private final Random rng = new Random();
	
	public FakeDetectionService()
	{
		
	}

    @Override
	public boolean frameContainsCat(Mat frame, float confidenceThreshhold)
	{
		//return (r.nextFloat()*confidenceThreshhold) > (0.7*confidenceThreshhold);

		return false;
    }
}

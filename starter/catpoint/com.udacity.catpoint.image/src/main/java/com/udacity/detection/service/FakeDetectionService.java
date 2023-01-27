package com.udacity.detection.service;


import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.Objects;

//import java.awt.image.BufferedImage;
import java.util.Random;
//import java.util.Arrays;
//import java.util.Optional;

//import java.util.stream.Stream;

//import java.io.*;
//import javax.imageio.ImageIO;
//import javax.swing.JOptionPane;


/**
 * Service that tries to guess if an frame displays a cat.
 */
public class FakeDetectionService implements DetectionService
{
    private Random rng;
    
    private int frameHash, frameX, periodX, frameCount, periodCount;
    
    private float confidence;
    
	private boolean detected;
	
	public FakeDetectionService(Random rng)
	{
		this.rng = rng;
		
		frameX = rng.nextInt(3000) + 1;
		periodX = rng.nextInt(700) + 300;
		
		frameHash = frameCount = periodCount = 0;
		
		confidence = rng.nextFloat();
		
		detected = false;

				//log.info( String.format("%s %.1f%% %s", "confidence:", confidence, "\n") );
		log.info( String.format("%s %f %s %d %s %d", "confidence:", confidence, "*** frameX:", frameX, "*** periodX:", periodX) );
	}

    @Override
	public boolean frameContainsCat(Mat frame, float threshold)
	{	
		threshold += 30.0;
		
		MatOfByte mob = new MatOfByte();
		Imgcodecs.imencode(".jpg", frame, mob);
		
		//log.info( String.format("%s %d %s %d %s %d", "frameHash:", frameHash, "*** periodCount:", periodCount, "*** frameCount:", frameCount) );

		//log.info( String.format("%s %d", "hashCode:", Objects.hashCode(mob) ));
		
		log.info( String.format("%s %d %s %d", "frameCount:", frameCount, "periodCount", periodCount) );

		if(frameHash <= 0)
		{
			if(frameCount < frameX)
			{
				frameCount++;
				return false;
			}
			else
			{
				frameHash = Objects.hashCode(mob);
				detected = true;
				return true;
			}
		}
		
		if(frameHash > 0)
		{
			if( frameHash == Objects.hashCode(mob) )
			{
				periodCount = 0;
				detected = true;
				return true;
			}
		}
		
		if(detected)
		{
			if(periodCount < periodX)
			{
				periodCount++;
				return true;
			}
			else
			{
				detected = false;
				return true;
			}
		}
		
		return false;

		//return rng.nextFloat() < threshold;
	}
}

package com.udacity.detection.service;


import org.opencv.core.Mat;

import java.util.Objects;

//import java.awt.image.BufferedImage;
import java.util.Random;
//import java.util.Arrays;
//import java.util.Optional;

//import java.util.stream.Stream;

//import java.io.*;
//import javax.imageio.ImageIO;
//import javax.swing.JOptionPane;


// int[] dataBuffInt = image.getRGB(0, 0, w, h, null, 0, w);

/**
 * Service that tries to guess if an frame displays a cat.
 */
public class FakeDetectionService implements DetectionService
{
    private Random rng;
    
    private boolean detect;
    
    private int frameHash, start_count, period_count, start, period;
    
    private float confidence;
	
	public FakeDetectionService(Random rng)
	{
		this.rng = rng;
		
		start = rng.nextInt(101);
		period = rng.nextInt(16);
		
		frameHash = start_count = period_count = 0;
		
		detect = false;
		
		confidence = rng.nextFloat();
		
				//log.info( String.format("%s %.1f%% %s", "confidence:", confidence, "\n") );
		log.info( String.format("%s %f %s %d %s %d", "confidence:", confidence, "*** start:", start, "*** period:", period) );
	}

    @Override
	public boolean frameContainsCat(Mat frame, float threshold)
	{	
		threshold += 10.0;

		/*if(frameHash <= 0)
		{
			if(start_count > start)
			{
				frameHash = Objects.hashCode(frame);
				return true;
			}
			else
			{
				start_count++;
				return false;
			}
		}
		else
		{
			if(period_count < period)
			{
				period_count++;
				
				if(confidence < threshold)
					return true;
				else
					return false;
			}
			else
			{
				if( frameHash == Objects.hashCode(frame) )
				{
					period_count = 0;
					confidence = rng.nextFloat();
					
					if(confidence < threshold)
						return true;
					else
						return false;
				}
				else
					return false;
			}
		}*/
		
		return rng.nextFloat() < threshold;
	}
}

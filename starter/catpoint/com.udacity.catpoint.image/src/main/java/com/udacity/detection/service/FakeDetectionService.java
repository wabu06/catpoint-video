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
    private final Random r = new Random();
	
	int catHash = 0, cat1Hash = 0;
	
	public FakeDetectionService()
	{
		try( InputStream is = getClass().getClassLoader().getResourceAsStream("camera/sample-cat.jpg") )
		{
			BufferedImage img = ImageIO.read(is);
					
			int w = img.getWidth();
			int h = img.getHeight();
					
			int[] rgb = img.getRGB(0, 0, w, h, null, 0, w);
					
			catHash = Arrays.hashCode(rgb);
		}
		catch(Exception exp)
		{
			JOptionPane.showMessageDialog
				(
					null,
					"Unable To Find A Critical Resource, Application Will Now Terminate",
					"ERROR",
					JOptionPane.ERROR_MESSAGE
				);

			System.exit(1);
		}
		
		try( InputStream is = getClass().getClassLoader().getResourceAsStream("camera/sample-cat1.jpg") )
		{
			BufferedImage img = ImageIO.read(is);
					
			int w = img.getWidth();
			int h = img.getHeight();
					
			int[] rgb = img.getRGB(0, 0, w, h, null, 0, w);
					
			cat1Hash = Arrays.hashCode(rgb);
		}
		catch(Exception exp)
		{
			JOptionPane.showMessageDialog
				(
					null,
					"Unable To Find A Critical Resource, Application Will Now Terminate",
					"ERROR",
					JOptionPane.ERROR_MESSAGE
				);

			System.exit(1);
		}
	}

    @Override
	public boolean frameContainsCat(Mat frame, float confidenceThreshhold)
	{
		//return (r.nextFloat()*confidenceThreshhold) > (0.7*confidenceThreshhold);
		
		int w = image.getWidth();
		int h = image.getHeight();
		
		int[] rgb = image.getRGB(0, 0, w, h, null, 0, w);
		
		boolean confident = r.nextFloat() > (0.001*confidenceThreshhold);
		
		return (Arrays.hashCode(rgb) == catHash) || (Arrays.hashCode(rgb) == cat1Hash) && confident;
    }
}

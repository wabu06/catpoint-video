package com.udacity.image.service;

import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Arrays;

import java.io.File;
import javax.imageio.ImageIO;



// int[] dataBuffInt = image.getRGB(0, 0, w, h, null, 0, w);

/**
 * Service that tries to guess if an image displays a cat.
 */
public class FakeImageService implements ImageService
{
    private final Random r = new Random();
	
	int imgHash = 0;
	
	public FakeImageService()
	{
		for(String imgFile: new File("camera").list() )
		{
			if( imgFile.compareTo("sample-cat.jpg") == 0 )
			{
				try
				{
					BufferedImage img = ImageIO.read( new File("camera/" + imgFile) );
					
					int w = img.getWidth();
					int h = img.getHeight();
					
					int[] rgb = img.getRGB(0, 0, w, h, null, 0, w);
					
					imgHash = Arrays.hashCode(rgb);
				}
				catch(Exception exp) { System.out.println(exp + ": File I/O Error"); }
				
				break;
			} 
		}
	}

    @Override
	public boolean imageContainsCat(BufferedImage image, float confidenceThreshhold)
	{
		//return (r.nextFloat()*confidenceThreshhold) > (0.7*confidenceThreshhold);
		
		int w = image.getWidth();
		int h = image.getHeight();
		
		int[] rgb = image.getRGB(0, 0, w, h, null, 0, w);
		
		return Arrays.hashCode(rgb) == imgHash;
    }
}

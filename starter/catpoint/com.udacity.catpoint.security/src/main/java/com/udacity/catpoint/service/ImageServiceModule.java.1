package com.udacity.catpoint.service;

import com.google.inject.AbstractModule;

import java.nio.file.*;
import java.io.*;
import java.util.Properties;

import com.udacity.image.service.*;



public class ImageServiceModule extends AbstractModule
{
	@Override
	protected void configure()
	{
		//String config = "com.udacity.catpoint.security/src/main/config/config.properties";
		
		//try( InputStream is = Files.newInputStream( Path.of(config) ) 
		try ( InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties") )
		{
			Properties props = new Properties();
			
			props.load(is);
			
			String awsId = props.getProperty("aws.id");
        	String awsSecret = props.getProperty("aws.secret");
        	String awsRegion = props.getProperty("aws.region");
			
			try
			{
				if( awsId.isBlank() || awsSecret.isBlank() || awsRegion.isBlank() )
					bind(ImageService.class).toInstance( new FakeImageService() );
				else
					bind(ImageService.class).toInstance( new AwsImageService(awsId, awsSecret, awsRegion) );
			}
			catch(Throwable exp)
			{
				bind(ImageService.class).toInstance( new FakeImageService() );
			}
		}
		catch(Throwable exp)
		{
			bind(ImageService.class).toInstance( new FakeImageService() );
		}
	}
}

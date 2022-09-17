package com.udacity.catpoint.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.udacity.catpoint.data.*;
import com.udacity.image.service.*;

import java.awt.image.BufferedImage;

import java.nio.file.*;
import java.io.*;
import java.util.Properties;



public class ImageServiceHandler implements InvocationHandler
{
	private SecurityRepository securityRepository;
	
	private ImageService fakeImageService = new FakeImageService();
	private ImageService awsImageService;
	private ImageService googleImageService;
	
	ImageServiceHandler(SecurityRepository securityRepository)
	{
		this.securityRepository = securityRepository;
		setupAwsImageService();
		
		googleImageService = fakeImageService;
	}
	
	private void setupAwsImageService()
	{
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
					awsImageService = fakeImageService;
				else
					awsImageService = new AwsImageService(awsId, awsSecret, awsRegion);
			}
			catch(Throwable exp)
			{
				awsImageService = fakeImageService;
			}
		}
		catch(Throwable exp)
		{
			awsImageService = fakeImageService;
		}
		
		ImageService.log.info("AWS is - " + awsImageService.getClass() + "\n");
	}
	
	private Object handleAwsImageService(Method method, Object[] args) throws Throwable
	{
		ImageService.log.info("Using Proxy\n");
		
		return method.invoke(awsImageService, args);
	}
	
	@Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		Object obj = null;
		
		switch( securityRepository.getImageService() )
        {
        	case "AWS":
        		 obj = handleAwsImageService(method, args);
        	break;
        	
        	case "GOOGLE":
        		obj = method.invoke(googleImageService, args);
        	break;
        }
        
        return obj;
	}
}

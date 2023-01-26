package com.udacity.catpoint.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.Random;

import com.udacity.catpoint.data.*;
import com.udacity.catpoint.service.*;
import com.udacity.detection.service.*;


public class DetectionServiceHandler implements InvocationHandler
{
	private SecurityService securityService;
	private SecurityRepository securityRepository;
	
	private Random RNG;

	private DetectionService fakeDetectionService;
	private DetectionService opencvDetectionService;
	
	DetectionServiceHandler(SecurityService securityService)
	{
		this.securityService = securityService;
		this.securityRepository = securityService.getRepository();
		
		this.RNG = securityService.getRNG();
		
		fakeDetectionService = new FakeDetectionService(RNG);

		opencvDetectionService = fakeDetectionService;
	}
	
	@Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception
	{
		//DetectionService.log.info("Using Proxy\n");
		
		return switch( securityRepository.getDetectionService() )
        		{
        			case "OPENCV" -> method.invoke(opencvDetectionService, args);
        		
        			default -> method.invoke(fakeDetectionService, args);
        		};
	}
}

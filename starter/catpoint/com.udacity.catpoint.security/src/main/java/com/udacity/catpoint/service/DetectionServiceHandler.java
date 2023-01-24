package com.udacity.catpoint.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.udacity.catpoint.data.*;
import com.udacity.detection.service.*;


public class DetectionServiceHandler implements InvocationHandler
{
	private SecurityRepository securityRepository;
	
	private DetectionService fakeDetectionService;
	//private DetectionService opencvDetectionService;
	
	DetectionServiceHandler(SecurityRepository securityRepository)
	{
		this.securityRepository = securityRepository;
		
		fakeDetectionService = new FakeDetectionService();

		//opencvDetectionService = fakeDetectionService;
	}
	
	@Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception
	{
		DetectionService.log.info("Using Proxy\n");
		
		return switch( securityRepository.getDetectionService() )
        		{
        			//case "OPENCV" -> method.invoke(opencvDetectionService, args);
        		
        			default -> method.invoke(fakeDetectionService, args);
        		};
	}
}

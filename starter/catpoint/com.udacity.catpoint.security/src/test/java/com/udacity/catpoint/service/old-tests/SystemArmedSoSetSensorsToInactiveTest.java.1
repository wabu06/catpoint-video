package com.udacity.catpoint.service;


import com.udacity.catpoint.data.*;
import com.udacity.catpoint.application.*;
import com.udacity.image.service.*;

import java.util.*;
import java.util.stream.*;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javax.inject.Inject;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


/*
* If the system is armed, reset all sensors to inactive
*/
public class SystemArmedSoSetSensorsToInactiveTest // No 10
{
	//@ParameterizedTest
	//@EnumSource(SensorType.class)
	@ParameterizedTest
	@EnumSource(ArmingStatus.class)
	public void SensorsToInactiveTest(ArmingStatus arm)
	{
		ImageService mockImageService = mock(ImageService.class);
		
		SecurityRepository securityRepository = new MockSecurityRepository();
		
		Injector ssInj = Guice.createInjector
			(
				b->b.bind(ImageService.class).toInstance(mockImageService),
				b->b.bind(SecurityRepository.class).toInstance(securityRepository)
			);
	
		SecurityService securityService = ssInj.getInstance(SecurityService.class);
		
		securityService.addSensor( new Sensor("test", SensorType.DOOR) );
		securityService.addSensor( new Sensor("test", SensorType.WINDOW) );
		securityService.addSensor( new Sensor("test", SensorType.MOTION) );
		
		for(Sensor s: securityService.getSensors())
			s.setActive(Boolean.TRUE);
		
		assertEquals( ArmingStatus.DISARMED, securityService.getArmingStatus() );
		securityService.setArmingStatus(arm);
		
		for(Sensor s: securityService.getSensors())
		{
			if(arm == ArmingStatus.DISARMED)
				assertTrue(Boolean.TRUE);
			else
				assertEquals( Boolean.FALSE, s.getActive() );
		}
	}
}

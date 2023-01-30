package com.udacity.catpoint.service;


import com.udacity.catpoint.data.*;
import com.udacity.image.service.*;
import com.udacity.catpoint.application.*;

import java.util.*;

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


	// Unit test for making no changes to alarm state, if deactivating a sensor that is already inactive
public class NoChangesToAlarmStateWhenDeactivatingInactiveSensorTest // No. 6
{
	List<AlarmStatus> AlarmStatusList = List.of(AlarmStatus.NO_ALARM, AlarmStatus.PENDING_ALARM, AlarmStatus.ALARM);
	
	@ParameterizedTest
	@EnumSource(SensorType.class)
	public void DeactivatingInactiveSensorTest(SensorType type)
	{
		ImageService mockImageService = mock(ImageService.class);
		
		SecurityRepository securityRepository = new MockSecurityRepository();
		
		Injector ssInj = Guice.createInjector
			(
				b->b.bind(ImageService.class).toInstance(mockImageService),
				b->b.bind(SecurityRepository.class).toInstance(securityRepository)
			);
	
		SecurityService securityService = ssInj.getInstance(SecurityService.class);
		
		//SecurityService securityService = new SecurityService(securityRepository);
		
		AlarmStatus cas = securityService.getAlarmStatus(); // current alarm status
		
		Sensor testSensor = new Sensor("test", type);
		
		for(AlarmStatus S: AlarmStatusList)
		{
			securityService.setAlarmStatus(S);
			
			testSensor.setActive(Boolean.FALSE);
		
			securityService.changeSensorActivationStatus(testSensor, Boolean.FALSE);
		
			assertEquals( S, securityService.getAlarmStatus() );
		}
	}
}


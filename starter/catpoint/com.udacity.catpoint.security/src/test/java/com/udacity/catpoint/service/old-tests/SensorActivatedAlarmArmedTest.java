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


/**
 * Unit test for Pending Alarm Status, when a sensor becomes active, and alarm armed.
 */

	// duplicate of 2
public class SensorActivatedAlarmArmedTest 
{
	ImageService mockImageService = mock(ImageService.class);
	
	private SecurityRepository securityRepository = new MockSecurityRepository();
	
	Injector ssInj = Guice.createInjector
			(
				b->b.bind(ImageService.class).toInstance(mockImageService),
				b->b.bind(SecurityRepository.class).toInstance(securityRepository)
			);
	
	SecurityService securityService = ssInj.getInstance(SecurityService.class);
	
	//private SecurityService securityService = new SecurityService(securityRepository);
	
	List<ArmingStatus> armingStatusList  = List.of(ArmingStatus.ARMED_HOME, ArmingStatus.ARMED_AWAY);
	
    @ParameterizedTest
	@EnumSource(SensorType.class)
    public void PendingAlarmStatusTest(SensorType type)
    {
		for(ArmingStatus AS: armingStatusList)
		{
			Sensor sensor = new Sensor("test sensor", type);
			securityService.addSensor(sensor);

			securityService.setAlarmStatus(AlarmStatus.PENDING_ALARM);
			securityService.setArmingStatus(AS);
		
			securityService.changeSensorActivationStatus(sensor, !Boolean.FALSE);
		
			assertEquals( AlarmStatus.ALARM, securityService.getAlarmStatus() );
		}
	}
}

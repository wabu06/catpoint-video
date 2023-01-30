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
 * If alarm is armed and a sensor becomes activated, put the system into pending alarm status.
 */

public class SensorActivatedPendingAlarmArmedTest  // No 1
{	
	List<ArmingStatus> armingStatusList  = List.of(ArmingStatus.ARMED_HOME, ArmingStatus.ARMED_AWAY);
	
    @ParameterizedTest
	@EnumSource(SensorType.class)
	public void PendingAlarmStatusTest(SensorType type)
    {
		SecurityRepository securityRepository = new MockSecurityRepository();
		
		ImageService mockImageService = mock(ImageService.class);
		
		Injector ssInj = Guice.createInjector
			(
				b->b.bind(ImageService.class).toInstance(mockImageService),
				b->b.bind(SecurityRepository.class).toInstance(securityRepository)
			);
	
		SecurityService securityService = ssInj.getInstance(SecurityService.class);
		
		//SecurityService securityService = new SecurityService(securityRepository);
		
		SensorTestPanel panel = new SensorTestPanel(securityService);
		
			// create a sensor
		panel.getSensorTypeDropdown().setSelectedItem(type);
		panel.getAddSensorBttn().doClick();
		
		for(ArmingStatus AS: armingStatusList)
		{
			securityService.setAlarmStatus(AlarmStatus.NO_ALARM);
			securityService.setArmingStatus(AS);
				
				// activate sensor
			panel.getSensorToggleBttn(0).doClick();
		
			assertEquals( AlarmStatus.PENDING_ALARM, securityService.getAlarmStatus() );
			
				// deactivate sensor for next iteration
			panel.getSensorToggleBttn(0).doClick();
		} 
	}
}


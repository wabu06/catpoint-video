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

import javax.swing.*;


/**
 * If a sensor is activated while already active and the system is in pending state, change it to alarm state
 */

public class ActivatedActiveSensorPendingToAlarmStateTest  // No 5
{
	List<ArmingStatus> armingStatusList = List.of(ArmingStatus.ARMED_HOME, ArmingStatus.ARMED_AWAY);
	
	List<SensorType> sensorTypeList = List.of(SensorType.DOOR, SensorType.WINDOW, SensorType.MOTION);

	@ParameterizedTest
	@EnumSource(SensorType.class)
	public void ActivatedActiveSensorTest(SensorType type)
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
		
		SensorTestPanel panel = new SensorTestPanel(securityService);
		
		JButton addSensorBttn = panel.getAddSensorBttn();
		
			// create sensors
		for(SensorType T: sensorTypeList)
		{
			if( T != type )
			{
				panel.getSensorTypeDropdown().setSelectedItem(T);
				addSensorBttn.doClick();
			}
		}
		
		for(ArmingStatus S: armingStatusList)
		{
			securityService.setArmingStatus(S);
			securityService.setAlarmStatus(AlarmStatus.NO_ALARM);
		
				// activate first sensor
			panel.getSensorToggleBttn(0).doClick();
		
				// activate second sensor
			panel.getSensorToggleBttn(1).doClick();
		
			assertEquals( AlarmStatus.ALARM, securityService.getAlarmStatus() );
			
			securityService.setArmingStatus(ArmingStatus.DISARMED);
			
				// deactivate sensors
			panel.getSensorToggleBttn(0).doClick();
			panel.getSensorToggleBttn(1).doClick();
		}
	}
}


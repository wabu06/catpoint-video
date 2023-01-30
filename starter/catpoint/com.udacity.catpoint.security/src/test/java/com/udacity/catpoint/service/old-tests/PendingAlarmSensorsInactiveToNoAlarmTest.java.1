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
 * If pending alarm and all sensors are inactive, return to no alarm state
 */

public class PendingAlarmSensorsInactiveToNoAlarmTest  // No 3
{
	@Test
	public void SensorsInactiveTest()
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
		
		panel.getSensorTypeDropdown().setSelectedItem(SensorType.DOOR);
		addSensorBttn.doClick();
		
		panel.getSensorTypeDropdown().setSelectedItem(SensorType.WINDOW);
		addSensorBttn.doClick();
		
		panel.getSensorTypeDropdown().setSelectedItem(SensorType.MOTION);
		addSensorBttn.doClick();
		
			// activate sensors
		panel.getSensorToggleBttn(0).doClick();
		panel.getSensorToggleBttn(1).doClick();
		panel.getSensorToggleBttn(2).doClick();
		
		securityService.setAlarmStatus(AlarmStatus.PENDING_ALARM);
		
			// deactivate sensors
		panel.getSensorToggleBttn(0).doClick();
		panel.getSensorToggleBttn(1).doClick();
		panel.getSensorToggleBttn(2).doClick();
		
		assertEquals( AlarmStatus.NO_ALARM, securityService.getAlarmStatus() );
	}
}


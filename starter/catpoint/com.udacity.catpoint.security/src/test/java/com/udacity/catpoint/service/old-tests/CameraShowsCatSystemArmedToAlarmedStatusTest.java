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

import java.awt.image.BufferedImage;


public class CameraShowsCatSystemArmedToAlarmedStatusTest // Unit Test 11
{
	@ParameterizedTest
	@EnumSource(ArmingStatus.class)
	public void CameraShowsCatTest(ArmingStatus arm)
	{
		ImageService mockImageService = mock(ImageService.class);
		
		SecurityRepository securityRepository = new MockSecurityRepository();
		
		Injector ssInj = Guice.createInjector
			(
				b->b.bind(ImageService.class).toInstance(mockImageService),
				b->b.bind(SecurityRepository.class).toInstance(securityRepository)
			);
	
		SecurityService securityService = ssInj.getInstance(SecurityService.class);
		
		when( mockImageService.imageContainsCat( any(BufferedImage.class), anyFloat() ) ).thenReturn(Boolean.TRUE);
		
		ControlTestPanel panel = new ControlTestPanel
										(
											securityService,
											new SensorTestPanel(securityService),
											new ImageTestPanel(securityService)
										);
		
		if(arm != ArmingStatus.ARMED_HOME)
		{
			securityService.setArmingStatus(arm);
			panel.getArmedHomeButton().doClick(); // simulate clicking of ArmedHome button
			assertEquals( AlarmStatus.ALARM, securityService.getAlarmStatus() );
		}
		else
			assertTrue( true );
	}
}


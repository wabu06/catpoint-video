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
* If the system is disarmed, set the status to no alarm
*/
public class SystemDisarmedSetAlarmStatusToNoAlarmTest // No 9
{
	private static Stream<Arguments> setToNoAlarm()
	{
		List<Arguments> args = new ArrayList<>();
		
		for(ArmingStatus arm: List.of(ArmingStatus.ARMED_HOME, ArmingStatus.ARMED_AWAY))
		{
			for(AlarmStatus alarm: List.of(AlarmStatus.NO_ALARM, AlarmStatus.PENDING_ALARM, AlarmStatus.ALARM))
				args.add( Arguments.of(arm, alarm) );
		}
		
		return args.stream();
	}
	
	@ParameterizedTest
	@DisplayName("Unit Test 9")
	@MethodSource("setToNoAlarm")
	public void AlarmStatusToNoAlarm(ArmingStatus arm, AlarmStatus alarm)
	{
		ImageService mockImageService = mock(ImageService.class);
		
		SecurityRepository securityRepository = new MockSecurityRepository();
		
		Injector ssInj = Guice.createInjector
			(
				b->b.bind(ImageService.class).toInstance(mockImageService),
				b->b.bind(SecurityRepository.class).toInstance(securityRepository)
			);
	
		SecurityService securityService = ssInj.getInstance(SecurityService.class);
		
		securityService.setArmingStatus(arm);
		securityService.setAlarmStatus(alarm);
		securityService.setArmingStatus(ArmingStatus.DISARMED);
		
		assertEquals( AlarmStatus.NO_ALARM, securityService.getAlarmStatus() );
	}
}

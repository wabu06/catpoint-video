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


public class SetAlarmStatusTest
{
	private SecurityRepository securityRepository;
	private SecurityService securityService;
	
	@ParameterizedTest
	@EnumSource(AlarmStatus.class)
	public void alarmStatusTest(AlarmStatus as)
	{
		ImageService mockImageService = mock(ImageService.class);
		
		securityRepository = new MockSecurityRepository();
		
		Injector ssInj = Guice.createInjector
			(
				b->b.bind(ImageService.class).toInstance(mockImageService),
				b->b.bind(SecurityRepository.class).toInstance(securityRepository)
			);
	
		securityService = ssInj.getInstance(SecurityService.class);
		
		//securityService = new SecurityService(securityRepository);
		
		securityService.setAlarmStatus(as);
		
		assertEquals( as, securityService.getAlarmStatus() );
	}
}

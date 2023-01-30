package com.udacity.catpoint.service;


import com.udacity.catpoint.data.*;
//import com.udacity.catpoint.service.*;
import com.udacity.catpoint.application.*;
import com.udacity.image.service.*;

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
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;



public class ImageServiceIdentifiesCatWhenArmedHomeAlarmStatusTest  // No. 7
{
	@Test
	public void ImageServiceIdentifiesCat()
	{
		ImageService mockImageService = mock(ImageService.class);
		
		SecurityRepository securityRepository = new MockSecurityRepository();
		//SecurityService securityService = new SecurityService(securityRepository);
		
		Injector ssInj = Guice.createInjector
			(
				b->b.bind(ImageService.class).toInstance(mockImageService),
				b->b.bind(SecurityRepository.class).toInstance(securityRepository)
			);
	
		SecurityService securityService = ssInj.getInstance(SecurityService.class);
		
		when( mockImageService.imageContainsCat( any(BufferedImage.class), anyFloat() ) ).thenReturn(Boolean.TRUE);
		
		securityService.setArmingStatus(ArmingStatus.ARMED_HOME);
		
		//securityService.catDetected(Boolean.TRUE);
		
		securityService.processImage( new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB) );
		
		assertEquals( AlarmStatus.ALARM, securityService.getAlarmStatus() );
	}
}


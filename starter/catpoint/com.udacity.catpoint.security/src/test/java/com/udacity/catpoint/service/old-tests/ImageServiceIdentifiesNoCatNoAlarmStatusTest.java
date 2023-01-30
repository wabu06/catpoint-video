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


/*
* If the image service identifies an image that does not contain a cat,
* change the status to no alarm as long as the sensors are not active.
*/
public class ImageServiceIdentifiesNoCatNoAlarmStatusTest  // No. 8
{
	private static List<AlarmStatus> AlarmStatusList = List.of(AlarmStatus.NO_ALARM, AlarmStatus.PENDING_ALARM, AlarmStatus.ALARM);
	private static List<ArmingStatus> ArmingStatusList = List.of(ArmingStatus.DISARMED, ArmingStatus.ARMED_HOME, ArmingStatus.ARMED_AWAY);
	private static List<SensorType> SensorTypeList = List.of(SensorType.DOOR, SensorType.WINDOW, SensorType.MOTION);
	private static List<Boolean> BoolList = List.of(Boolean.FALSE, Boolean.TRUE);
	
	private static Stream<Arguments> noCatNoSensors()
	{
		List<Arguments> args = new ArrayList<>();
		
		for(AlarmStatus alarm: AlarmStatusList)
		{
			for(ArmingStatus arm: ArmingStatusList)
			{
				args.add( Arguments.of(alarm, arm) );
			}
		}
		
		return args.stream();
	}
	
	@ParameterizedTest
	@DisplayName("Unit Test 8, without sensors")
	@MethodSource("noCatNoSensors")
	public void ImageServiceIdentifiesNoCatNoSensors(AlarmStatus alarm, ArmingStatus arm)
	{
		ImageService mockImageService = mock(ImageService.class);
		
		SecurityRepository securityRepository = new MockSecurityRepository();
		
		Injector ssInj = Guice.createInjector
			(
				b->b.bind(ImageService.class).toInstance(mockImageService),
				b->b.bind(SecurityRepository.class).toInstance(securityRepository)
			);
	
		SecurityService securityService = ssInj.getInstance(SecurityService.class);
		
		when( mockImageService.imageContainsCat( any(BufferedImage.class), anyFloat() ) ).thenReturn(Boolean.FALSE);
		
		securityService.setArmingStatus(arm);
		securityService.setAlarmStatus(alarm);
		
		securityService.processImage( new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB) );
		
		assertEquals( AlarmStatus.NO_ALARM, securityService.getAlarmStatus() );
	}
	
	private static Stream<Arguments> NoCatOneSensor()
	{
		List<Arguments> args = new ArrayList<>();
		
		for(Boolean active: BoolList)
		{
			for(SensorType type: SensorTypeList)
			{
				for(AlarmStatus alarm: AlarmStatusList)
				{
					for(ArmingStatus arm: ArmingStatusList)
					{
						args.add( Arguments.of(active, type, alarm, arm) );
					}
				}
			}
		}
		
		return args.stream();
	}
	
	@ParameterizedTest
	@DisplayName("Unit Test 8, with just 1 sensor")
	@MethodSource("NoCatOneSensor")
	public void ImageServiceIdentifiesNoCatOneSensor(Boolean active, SensorType type, AlarmStatus alarm, ArmingStatus arm)
	{
		ImageService mockImageService = mock(ImageService.class);
		
		SecurityRepository securityRepository = new MockSecurityRepository();

		Injector ssInj = Guice.createInjector
			(
				b->b.bind(ImageService.class).toInstance(mockImageService),
				b->b.bind(SecurityRepository.class).toInstance(securityRepository)
			);
	
		SecurityService securityService = ssInj.getInstance(SecurityService.class);
		
		when( mockImageService.imageContainsCat( any(BufferedImage.class), anyFloat() ) ).thenReturn(Boolean.FALSE);
		
		Sensor testSensor = new Sensor("test", type);
		
		securityService.addSensor(testSensor);
		
		testSensor.setActive(active);
		
		securityService.setArmingStatus(arm);
		securityService.setAlarmStatus(alarm);
		
		securityService.processImage( new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB) );
		
		if( testSensor.getActive() && alarm != AlarmStatus.NO_ALARM )
			assertNotEquals( AlarmStatus.NO_ALARM, securityService.getAlarmStatus() );
		else
			assertEquals( AlarmStatus.NO_ALARM, securityService.getAlarmStatus() );
	}


	private static Stream<Arguments> NoCatTwoSensors()
	{
		List<Arguments> args = new ArrayList<>();
		
		for(Boolean active1: BoolList)
		{
			for(Boolean active2: BoolList)
			{
				for(SensorType type: SensorTypeList)
				{
					for(AlarmStatus alarm: AlarmStatusList)
					{
						for(ArmingStatus arm: ArmingStatusList)
						{
							args.add( Arguments.of(active1, active2, type, alarm, arm) );
						}
					}
				}
			}
		}
		return args.stream();
	}
	
	@ParameterizedTest
	@DisplayName("Unit Test 8, with 2 sensors")
	@MethodSource("NoCatTwoSensors")
	public void ImageServiceIdentifiesNoCatTwoSensors(Boolean active1, Boolean active2, SensorType type, AlarmStatus alarm, ArmingStatus arm)
	{
		ImageService mockImageService = mock(ImageService.class);
		
		SecurityRepository securityRepository = new MockSecurityRepository();

		Injector ssInj = Guice.createInjector
			(
				b->b.bind(ImageService.class).toInstance(mockImageService),
				b->b.bind(SecurityRepository.class).toInstance(securityRepository)
			);
	
		SecurityService securityService = ssInj.getInstance(SecurityService.class);
		
		when( mockImageService.imageContainsCat( any(BufferedImage.class), anyFloat() ) ).thenReturn(Boolean.FALSE);
		
		//Sensor testSensor = new Sensor("test", type);
		
		Sensor[] testSensors = new Sensor[3]; int s = 0;
		
		for(SensorType sense: SensorTypeList)
		{
			if(sense != type)
			{
				testSensors[s] = new Sensor("test", sense);
				securityService.addSensor(testSensors[s]);
				s++;
			}
		}
		
		testSensors[0].setActive(active1);
		testSensors[1].setActive(active2);
		
		securityService.setArmingStatus(arm);
		securityService.setAlarmStatus(alarm);
		
		securityService.processImage( new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB) );
		
			// if either sensor is active, and there is an alarm, then it should remain alarmed
		if( (testSensors[0].getActive() || testSensors[1].getActive()) && alarm != AlarmStatus.NO_ALARM )
			assertNotEquals( AlarmStatus.NO_ALARM, securityService.getAlarmStatus() );
		else
			assertEquals( AlarmStatus.NO_ALARM, securityService.getAlarmStatus() );
	}
	
	private static Stream<Arguments> NoCatThreeSensors()
	{
		List<Arguments> args = new ArrayList<>();
		
		for(Boolean active1: BoolList)
		{
			for(Boolean active2: BoolList)
			{
				for(Boolean active3: BoolList)
				{
					for(AlarmStatus alarm: AlarmStatusList)
					{
						for(ArmingStatus arm: ArmingStatusList)
						{
							args.add( Arguments.of(active1, active2, active3, alarm, arm) );
						}
					}
				}
			}
		}
		return args.stream();
	}
	
	@ParameterizedTest
	@DisplayName("Unit Test 8, with 3 sensors")
	@MethodSource("NoCatThreeSensors")
	public void ImageServiceIdentifiesNoCatThreeSensors(Boolean active1, Boolean active2, Boolean active3, AlarmStatus alarm, ArmingStatus arm)
	{
		ImageService mockImageService = mock(ImageService.class);
		
		SecurityRepository securityRepository = new MockSecurityRepository();

		Injector ssInj = Guice.createInjector
			(
				b->b.bind(ImageService.class).toInstance(mockImageService),
				b->b.bind(SecurityRepository.class).toInstance(securityRepository)
			);
	
		SecurityService securityService = ssInj.getInstance(SecurityService.class);
		
		when( mockImageService.imageContainsCat( any(BufferedImage.class), anyFloat() ) ).thenReturn(Boolean.FALSE);
		
		Sensor testSensor1 = new Sensor("test1", SensorType.DOOR);
		Sensor testSensor2 = new Sensor("test2", SensorType.WINDOW);
		Sensor testSensor3 = new Sensor("test3", SensorType.MOTION);
		
		securityService.addSensor(testSensor1);
		securityService.addSensor(testSensor2);
		securityService.addSensor(testSensor3);
		
		securityService.setArmingStatus(arm);
		securityService.setAlarmStatus(alarm);
		
		testSensor1.setActive(active1);
		testSensor2.setActive(active2);
		testSensor3.setActive(active3);
		
		securityService.processImage( new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB) );
		
			// if either sensor is active, and there is an alarm, then it should remain alarmed
		if( ( testSensor1.getActive() || testSensor2.getActive() || testSensor3.getActive() ) && (alarm != AlarmStatus.NO_ALARM) )
			assertNotEquals( AlarmStatus.NO_ALARM, securityService.getAlarmStatus() );
		else
			assertEquals( AlarmStatus.NO_ALARM, securityService.getAlarmStatus() );
	}
	
}

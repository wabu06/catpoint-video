package com.udacity.catpoint.service;


import com.udacity.catpoint.data.*;
import com.udacity.catpoint.application.*;
import com.udacity.image.service.*;

import java.util.*;
import java.util.stream.*;

import javax.swing.*;
import java.awt.image.BufferedImage;

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


public class SecurityServiceTest
{
	private SecurityRepository securityRepository;
	
	private ImageService mockImageService;
	
	private SecurityService securityService;
	
	private Injector ssInj;
	
	@BeforeEach
    void init()
    {
    	securityRepository = mock(SecurityRepository.class);
    	mockImageService = mock(ImageService.class);
    	
    	ssInj = Guice.createInjector
			(
				b->b.bind(ImageService.class).toInstance(mockImageService),
				b->b.bind(SecurityRepository.class).toInstance(securityRepository)
			);
	
		securityService = ssInj.getInstance(SecurityService.class);
    }
    
	private static Stream<Arguments> sensorActivatedSetAlarm()
	{
		List<ArmingStatus> armingStatusList  = List.of(ArmingStatus.ARMED_HOME, ArmingStatus.ARMED_AWAY);
		
		List<Arguments> args = new ArrayList<>();
		
		for(SensorType sType: List.of(SensorType.DOOR, SensorType.WINDOW, SensorType.MOTION) )
		{
			for(ArmingStatus arm: armingStatusList)
				args.add( Arguments.of(sType, arm) );
		}
		
		return args.stream();
	}
    
    // No 1
    /*
 	* If alarm is armed and a sensor becomes activated, put the system into pending alarm status.
	*/
    @ParameterizedTest
	@MethodSource("sensorActivatedSetAlarm")
	public void alarmArmedSensorActivatedPendingAlarmTest(SensorType sType, ArmingStatus arm)
    {	
		when( securityRepository.getAlarmStatus() ).thenReturn(AlarmStatus.NO_ALARM);
		when( securityRepository.getArmingStatus() ).thenReturn(arm);
		
		SensorTestPanel panel = new SensorTestPanel(securityService);
		
		Sensor testSensor = new Sensor("test", sType);
		
		when( securityRepository.getSensors() ).thenReturn( Set.of(testSensor) );
		
			// create a sensor
		panel.getSensorTypeDropdown().setSelectedItem(sType);
		panel.getAddSensorBttn().doClick();
		
			// activate sensor
		panel.getSensorToggleBttn(0).doClick();
			
		verify(securityRepository).setAlarmStatus(AlarmStatus.PENDING_ALARM);
	}
	
	// No 2
	/*
 	* If alarm is armed and a sensor becomes activated and the system is already pending alarm, set the alarm status to alarm
 	*/
	@ParameterizedTest
	@MethodSource("sensorActivatedSetAlarm")
	public void AlarmArmedTest(SensorType sType, ArmingStatus arm)
    {	
		when( securityRepository.getAlarmStatus() ).thenReturn(AlarmStatus.PENDING_ALARM);
		when( securityRepository.getArmingStatus() ).thenReturn(arm);
		
		Sensor testSensor = new Sensor("test", sType);
		
		when( securityRepository.getSensors() ).thenReturn( Set.of(testSensor) );
	
		SensorTestPanel panel = new SensorTestPanel(securityService);
		
			// create a sensor
		panel.getSensorTypeDropdown().setSelectedItem(sType);
		panel.getAddSensorBttn().doClick();
				
			// activate sensor
		panel.getSensorToggleBttn(0).doClick();
		
		verify(securityRepository).setAlarmStatus(AlarmStatus.ALARM);
	}
	
	// No 3
	/*
 	* If pending alarm and all sensors are inactive, return to no alarm state
 	*/
 	@Test
	public void PendingAlarmSensorsInactiveToNoAlarmTest()
    {	
		SensorTestPanel panel = new SensorTestPanel(securityService);
		
		JButton addSensorBttn = panel.getAddSensorBttn();
		
		Sensor testSensor = new Sensor("test", SensorType.DOOR);
		when( securityRepository.getSensors() ).thenReturn( Set.of(testSensor) );
		panel.getSensorTypeDropdown().setSelectedItem(SensorType.DOOR);
		addSensorBttn.doClick();
		
		Sensor testSensor1 = new Sensor("test", SensorType.WINDOW);
		when( securityRepository.getSensors() ).thenReturn( Set.of(testSensor, testSensor1) );
		panel.getSensorTypeDropdown().setSelectedItem(SensorType.WINDOW);
		addSensorBttn.doClick();
		
		Sensor testSensor2 = new Sensor("test", SensorType.MOTION);
		when( securityRepository.getSensors() ).thenReturn( Set.of(testSensor, testSensor1, testSensor2) );
		panel.getSensorTypeDropdown().setSelectedItem(SensorType.MOTION);
		addSensorBttn.doClick();
		
		when( securityRepository.getAlarmStatus() ).thenReturn(AlarmStatus.NO_ALARM);
		when( securityRepository.getArmingStatus() ).thenReturn(ArmingStatus.DISARMED);
		
			// activate sensors
		panel.getSensorToggleBttn(0).doClick();
		panel.getSensorToggleBttn(1).doClick();
		panel.getSensorToggleBttn(2).doClick();
		
		when( securityRepository.getAlarmStatus() ).thenReturn(AlarmStatus.PENDING_ALARM);
		
			// deactivate sensors
		panel.getSensorToggleBttn(0).doClick();
		panel.getSensorToggleBttn(1).doClick();
		panel.getSensorToggleBttn(2).doClick();
		
		verify(securityRepository, times(3)).setAlarmStatus(AlarmStatus.NO_ALARM);
	}
	
	// No 4
	/*
 	* If alarm is active, change in sensor state should not affect the alarm state
 	*/
	private static Stream<Arguments> noAlarmChange()
	{
		List<ArmingStatus> armingStatusList  = List.of(ArmingStatus.DISARMED, ArmingStatus.ARMED_HOME, ArmingStatus.ARMED_AWAY);
		
		List<Arguments> args = new ArrayList<>();
		
		for(SensorType sType: List.of(SensorType.DOOR, SensorType.WINDOW, SensorType.MOTION) )
		{
			for(ArmingStatus arm: armingStatusList)
				args.add( Arguments.of(sType, arm) );
		}
		
		return args.stream();
	}
	
	@ParameterizedTest
	@MethodSource("noAlarmChange")
	public void alarmActiveSensorStateNoAffectAlarmTest(SensorType sType, ArmingStatus arm)
    {
		Sensor testSensor = new Sensor("test", sType);
		when( securityRepository.getSensors() ).thenReturn( Set.of(testSensor) );
		
		when( securityRepository.getArmingStatus() ).thenReturn(arm);
		when( securityRepository.getAlarmStatus() ).thenReturn(AlarmStatus.ALARM);
		
		SensorTestPanel panel = new SensorTestPanel(securityService);
		
		panel.getSensorTypeDropdown().setSelectedItem(sType);
		panel.getAddSensorBttn().doClick();
	
		panel.getSensorToggleBttn(0).doClick();
		verify(securityRepository, never()).setAlarmStatus( any(AlarmStatus.class) );
		
		panel.getSensorToggleBttn(0).doClick();
		verify(securityRepository, never()).setAlarmStatus( any(AlarmStatus.class) );
	}
	
	// No 5
	/*
 	* If a sensor is activated while already active and the system is in pending state, change it to alarm state
 	*/
	private static Stream<Arguments> pendingAlarmToAlarm()
	{
		List<ArmingStatus> armingStatusList = List.of(ArmingStatus.ARMED_HOME, ArmingStatus.ARMED_AWAY);
		List<SensorType> sensorTypeList = List.of(SensorType.DOOR, SensorType.WINDOW, SensorType.MOTION);
		
		List<Arguments> args = new ArrayList<>();
		
		for(SensorType sType: sensorTypeList)
		{
			for(ArmingStatus arm: armingStatusList)
				args.add( Arguments.of(sType, arm) );
		}
		
		return args.stream();
	}
	
	@ParameterizedTest
	@MethodSource("pendingAlarmToAlarm")
	public void ActivatedActiveSensorPendingToAlarmStateTest(SensorType sType, ArmingStatus arm)
    {
    	List<SensorType> sensorTypeList = List.of(SensorType.DOOR, SensorType.WINDOW, SensorType.MOTION);
    	
		SensorTestPanel panel = new SensorTestPanel(securityService);
		
		Set<Sensor> sensors = new HashSet<>();
		
		JButton addSensorBttn = panel.getAddSensorBttn();
		
			// create sensors
		for(SensorType T: sensorTypeList)
		{
			if( T != sType )
			{
				Sensor testSensor = new Sensor("test", T);
				sensors.add(testSensor);
				when( securityRepository.getSensors() ).thenReturn(sensors);
				
				panel.getSensorTypeDropdown().setSelectedItem(T);
				addSensorBttn.doClick();
			}
		}
		
		when( securityRepository.getArmingStatus() ).thenReturn(arm);
		
		when( securityRepository.getAlarmStatus() ).thenReturn(AlarmStatus.NO_ALARM);
				// activate first sensor
		panel.getSensorToggleBttn(0).doClick();
		
		verify(securityRepository).setAlarmStatus(AlarmStatus.PENDING_ALARM);
		
		when( securityRepository.getAlarmStatus() ).thenReturn(AlarmStatus.PENDING_ALARM);
				// activate second sensor
		panel.getSensorToggleBttn(1).doClick();
		
		verify(securityRepository).setAlarmStatus(AlarmStatus.ALARM);
	}
	
	// No 6
	/*
	* No changes to alarm state, if deactivating a sensor that is already inactive
	*/	
	private static Stream<Arguments> noAlarmStateChange()
	{
		List<AlarmStatus> alarmStatusList = List.of(AlarmStatus.NO_ALARM, AlarmStatus.PENDING_ALARM, AlarmStatus.ALARM);
		List<SensorType> sensorTypeList = List.of(SensorType.DOOR, SensorType.WINDOW, SensorType.MOTION);
	
		List<Arguments> args = new ArrayList<>();
		
		for(SensorType sType: sensorTypeList)
		{
			for(AlarmStatus alarm: alarmStatusList)
				args.add( Arguments.of(sType, alarm) );
		}
		
		return args.stream();
	}
	
	@ParameterizedTest
	@MethodSource("noAlarmStateChange")
	public void noChangesToAlarmStateWhenDeactivatingInactiveSensorTest(SensorType sType, AlarmStatus alarm)
	{	
		Sensor testSensor = new Sensor("test", sType);
		
		when( securityRepository.getAlarmStatus() ).thenReturn(alarm);
			
		testSensor.setActive(Boolean.FALSE);
		
		securityService.changeSensorActivationStatus(testSensor, Boolean.FALSE);
			
		verify(securityRepository, times(0) ).setAlarmStatus( any(AlarmStatus.class) );
	}
	
	// No 7
	/*
	* If the image service identifies an image containing a cat while the system is armed-home, put the system into alarm status.
	*/
	@Test
	public void ImageServiceIdentifiesCatWhenArmedHomeAlarmStatusTest()
	{
		when( mockImageService.imageContainsCat( any(BufferedImage.class), anyFloat() ) ).thenReturn(Boolean.TRUE);
		
		when( securityRepository.getArmingStatus() ).thenReturn(ArmingStatus.ARMED_HOME);
		
		securityService.processImage( new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB) );
		
		verify(securityRepository).setAlarmStatus(AlarmStatus.ALARM);
	}
	
	// No 8
	/*
	* If the image service identifies an image that does not contain a cat,
	* change the status to no alarm as long as the sensors are not active.
	*/
	private static List<AlarmStatus> noCatAlarmStatusList = List.of(AlarmStatus.NO_ALARM, AlarmStatus.PENDING_ALARM, AlarmStatus.ALARM);
	private static List<ArmingStatus> noCatArmingStatusList = List.of(ArmingStatus.DISARMED, ArmingStatus.ARMED_HOME, ArmingStatus.ARMED_AWAY);
	private static List<SensorType> noCatSensorTypeList = List.of(SensorType.DOOR, SensorType.WINDOW, SensorType.MOTION);
	private static List<Boolean> noCatBoolList = List.of(Boolean.FALSE, Boolean.TRUE);
	
	private static Stream<Arguments> noCatNoSensors()
	{
		List<Arguments> args = new ArrayList<>();
		
		for(AlarmStatus alarm: noCatAlarmStatusList)
		{
			for(ArmingStatus arm: noCatArmingStatusList)
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
		when( mockImageService.imageContainsCat( any(BufferedImage.class), anyFloat() ) ).thenReturn(Boolean.FALSE);
		
		when( securityRepository.getArmingStatus() ).thenReturn(arm);
		
		securityService.processImage( new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB) );
		
		verify(securityRepository).setAlarmStatus(AlarmStatus.NO_ALARM);
	}
	
	private static Stream<Arguments> noCatOneSensor()
	{
		List<Arguments> args = new ArrayList<>();
		
		for(Boolean active: noCatBoolList)
		{
			for(SensorType type: noCatSensorTypeList)
			{
				for(AlarmStatus alarm: noCatAlarmStatusList)
				{
					for(ArmingStatus arm: noCatArmingStatusList)
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
	@MethodSource("noCatOneSensor")
	public void ImageServiceIdentifiesNoCatOneSensor(Boolean active, SensorType type, AlarmStatus alarm, ArmingStatus arm)
	{	
		when( mockImageService.imageContainsCat( any(BufferedImage.class), anyFloat() ) ).thenReturn(Boolean.FALSE);
		
		Sensor testSensor = new Sensor("test", type);
		testSensor.setActive(active);
		
		when( securityRepository.getSensors() ).thenReturn( Set.of(testSensor) );
		
		when( securityRepository.getArmingStatus() ).thenReturn(arm);
		
		securityService.processImage( new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB) );
		
		if( testSensor.getActive() )
			verify(securityRepository, never()).setAlarmStatus( any(AlarmStatus.class) );
		else
			verify(securityRepository).setAlarmStatus(AlarmStatus.NO_ALARM);
	}


	private static Stream<Arguments> noCatTwoSensors()
	{
		List<Arguments> args = new ArrayList<>();
		
		for(Boolean active1: noCatBoolList)
		{
			for(Boolean active2: noCatBoolList)
			{
				for(SensorType type: noCatSensorTypeList)
				{
					for(AlarmStatus alarm: noCatAlarmStatusList)
					{
						for(ArmingStatus arm: noCatArmingStatusList)
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
	@MethodSource("noCatTwoSensors")
	public void ImageServiceIdentifiesNoCatTwoSensors(Boolean active1, Boolean active2, SensorType type, AlarmStatus alarm, ArmingStatus arm)
	{	
		when( mockImageService.imageContainsCat( any(BufferedImage.class), anyFloat() ) ).thenReturn(Boolean.FALSE);
		
		Sensor[] testSensors = new Sensor[3]; int s = 0;
		
		Set<Sensor> sensors = new HashSet<>();
		
		for(SensorType sense: noCatSensorTypeList)
		{
			if(sense != type)
			{
				testSensors[s] = new Sensor("test", sense);
				sensors.add(testSensors[s]);
				s++;
			}
		}
		
		when( securityRepository.getSensors() ).thenReturn(sensors);
		
		testSensors[0].setActive(active1);
		testSensors[1].setActive(active2);
		
		when( securityRepository.getArmingStatus() ).thenReturn(arm);
		
		securityService.processImage( new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB) );
		
		if( testSensors[0].getActive() || testSensors[1].getActive() )
			verify(securityRepository, never()).setAlarmStatus( any(AlarmStatus.class) );
		else
			verify(securityRepository).setAlarmStatus(AlarmStatus.NO_ALARM);
	}
	
	private static Stream<Arguments> noCatThreeSensors()
	{
		List<Arguments> args = new ArrayList<>();
		
		for(Boolean active1: noCatBoolList)
		{
			for(Boolean active2: noCatBoolList)
			{
				for(Boolean active3: noCatBoolList)
				{
					for(AlarmStatus alarm: noCatAlarmStatusList)
					{
						for(ArmingStatus arm: noCatArmingStatusList)
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
	@MethodSource("noCatThreeSensors")
	public void ImageServiceIdentifiesNoCatThreeSensors(Boolean active1, Boolean active2, Boolean active3, AlarmStatus alarm, ArmingStatus arm)
	{	
		when( mockImageService.imageContainsCat( any(BufferedImage.class), anyFloat() ) ).thenReturn(Boolean.FALSE);
		
		Sensor testSensor1 = new Sensor("test1", SensorType.DOOR);
		Sensor testSensor2 = new Sensor("test2", SensorType.WINDOW);
		Sensor testSensor3 = new Sensor("test3", SensorType.MOTION);
		
		Set<Sensor> sensors = new HashSet<>();
		sensors.add(testSensor1);
		sensors.add(testSensor2);
		sensors.add(testSensor3);
		
		when( securityRepository.getSensors() ).thenReturn(sensors);
		
		when( securityRepository.getArmingStatus() ).thenReturn(arm);
		
		testSensor1.setActive(active1);
		testSensor2.setActive(active2);
		testSensor3.setActive(active3);
		
		securityService.processImage( new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB) );
		
		if( testSensor1.getActive() || testSensor2.getActive() || testSensor3.getActive() )
			verify(securityRepository, never()).setAlarmStatus( any(AlarmStatus.class) );
		else
			verify(securityRepository).setAlarmStatus(AlarmStatus.NO_ALARM);
	}
	
	// No 9
	/*
	* If the system is disarmed, set the status to no alarm
	*/
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
	public void systemDisarmedSetAlarmStatusToNoAlarmTest(ArmingStatus arm, AlarmStatus alarm)
	{	
		when( securityRepository.getAlarmStatus() ).thenReturn(alarm);
		when( securityRepository.getArmingStatus() ).thenReturn(arm);
		
		securityService.setArmingStatus(ArmingStatus.DISARMED);
		verify( securityRepository, times(1) ).setAlarmStatus(AlarmStatus.NO_ALARM);
	}
	
	// No 10
	/*
	* If the system is armed, reset all sensors to inactive
	*/
	@ParameterizedTest
	@EnumSource(ArmingStatus.class)
	public void SystemArmedSoSetSensorsToInactiveTest(ArmingStatus arm)
	{
		Sensor testSensor1 = new Sensor("test", SensorType.DOOR);
		Sensor testSensor2 = new Sensor("test", SensorType.WINDOW);
		Sensor testSensor3 = new Sensor("test", SensorType.MOTION);
		
		Set<Sensor> sensors = new HashSet<>();
		sensors.add(testSensor1);
		sensors.add(testSensor2);
		sensors.add(testSensor3);
		
		when( securityRepository.getSensors() ).thenReturn(sensors);
		
		for(Sensor s: securityService.getSensors())
			s.setActive(Boolean.TRUE);
		
		if(arm != ArmingStatus.DISARMED)
		{
			securityService.setArmingStatus(arm);
			
			for(Sensor s: securityService.getSensors())
				assertEquals( Boolean.FALSE, s.getActive() );
		}
	}
	
	// No 11
	/*
	* If the system is armed-home while the camera shows a cat, set the alarm status to alarm.
	*/
	@Test
	public void CameraShowsCatSystemArmedToAlarmedStatusTest()
	{
		when( mockImageService.imageContainsCat( any(BufferedImage.class), anyFloat() ) ).thenReturn(Boolean.TRUE);
		
		when( securityRepository.getArmingStatus() ).thenReturn(ArmingStatus.ARMED_HOME);
		
		ControlTestPanel panel = new ControlTestPanel
										(
											securityService,
											new SensorTestPanel(securityService),
											new ImageTestPanel(securityService)
										);
	
		panel.getArmedHomeButton().doClick(); // simulate clicking of ArmedHome button
		verify(securityRepository).setAlarmStatus(AlarmStatus.ALARM);
	}
}


package com.udacity.catpoint.service;


import com.udacity.catpoint.data.*;
//import com.udacity.catpoint.service.*;
import com.udacity.catpoint.application.*;

import java.util.List;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

/* import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.mockito.stubbing.*;
import static org.mockito.Mockito.*;
import static org.mockito.AdditionalAnswers.*; */



/**
 * Unit test for Pending Alarm Status, when a sensor becomes active, and alarm armed.
 */

//@ExtendWith(MockitoExtension.class)
public class SensorActivatedPendingAlarmArmedTest 
{
	private SecurityRepository securityRepository;
	private SecurityService securityService;
	
	@Test
	public void setAlarmStatusToNoAlarmTest()
	{
		securityRepository = new PretendDatabaseSecurityRepositoryImpl();
		securityService = new SecurityService(securityRepository);
		
		securityService.setAlarmStatus(AlarmStatus.NO_ALARM);
		assertEquals( AlarmStatus.NO_ALARM, securityService.getAlarmStatus() );
	}
	
	@ParameterizedTest
	@EnumSource(ArmingStatus.class)
	public void setArmingStatusToArmedTest(ArmingStatus armed)
	{
		securityRepository = new PretendDatabaseSecurityRepositoryImpl();
		securityService = new SecurityService(securityRepository);
		
		securityService.setArmingStatus(armed);
		
		assertEquals( armed, securityService.getArmingStatus() );
	}
	
    @ParameterizedTest
	@EnumSource(SensorType.class)
    public void PendingAlarmStatusTest(SensorType type)
    {
		List<ArmingStatus> armingStatusList  = List.of(ArmingStatus.ARMED_HOME, ArmingStatus.ARMED_AWAY);
		
		for(ArmingStatus AS: armingStatusList)
		{
			securityRepository = new PretendDatabaseSecurityRepositoryImpl();
			securityService = new SecurityService(securityRepository);
		
			Sensor sensor = new Sensor("test sensor", type);
			securityService.addSensor(sensor);

			securityService.setAlarmStatus(AlarmStatus.NO_ALARM);
			securityService.setArmingStatus(AS);
		
			securityService.changeSensorActivationStatus(sensor, !Boolean.FALSE);
		
        //sensorPanel.setSensorActivity(sensor, !Boolean.FALSE);
		
			securityService.removeSensor(sensor);
		
			assertEquals( AlarmStatus.PENDING_ALARM, securityService.getAlarmStatus() );
		}
		
		//assertEquals( Boolean.TRUE, sensor.getActive() );
	}
}

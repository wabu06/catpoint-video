package com.udacity.catpoint.service;


import com.udacity.catpoint.data.*;
//import com.udacity.catpoint.service.*;
import com.udacity.catpoint.application.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;



/**
 * Unit test for Pending Alarm Status, when a sensor becomes active, and alarm armed.
 */

public class SensorActivatedPendingAlarmArmedTest 
{
	private SecurityRepository securityRepository = new MockSecurityRepository();
	private SecurityService securityService = new SecurityService(securityRepository);
	
	List<ArmingStatus> armingStatusList  = List.of(ArmingStatus.ARMED_HOME, ArmingStatus.ARMED_AWAY);
	
    @ParameterizedTest
	@EnumSource(SensorType.class)
    public void PendingAlarmStatusTest(SensorType type)
    {
		for(ArmingStatus AS: armingStatusList)
		{
			Sensor sensor = new Sensor("test sensor", type);
			securityService.addSensor(sensor);

			securityService.setAlarmStatus(AlarmStatus.NO_ALARM);
			securityService.setArmingStatus(AS);
		
			securityService.changeSensorActivationStatus(sensor, !Boolean.FALSE);
		
			assertEquals( AlarmStatus.PENDING_ALARM, securityService.getAlarmStatus() );
		}
	}
}

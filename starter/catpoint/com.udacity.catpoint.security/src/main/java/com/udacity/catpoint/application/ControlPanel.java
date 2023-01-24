package com.udacity.catpoint.application;

import com.udacity.catpoint.data.*;
import com.udacity.catpoint.service.*;
//import com.udacity.catpoint.service.StyleService;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.opencv.core.Mat;

/**
 * JPanel containing the buttons to manipulate arming status of the system.
 */
public class ControlPanel extends JPanel implements StatusListener
{
    private SecurityService securityService;
    private Map<ArmingStatus, JButton> buttonMap;
	
	SensorPanel sensors;

    public ControlPanel(SecurityService securityService)
	{
        super();
		
        setLayout(new MigLayout());
		
        this.securityService = securityService;

        JLabel panelLabel = new JLabel("System Control");
        panelLabel.setFont(StyleService.HEADING_FONT);

        add(panelLabel, "span 3, wrap");

        	//create a map of each status type to a corresponding JButton
        buttonMap = Arrays.stream(ArmingStatus.values())
                .collect(Collectors.toMap(status -> status, status -> new JButton(status.getDescription())));

        	//add an action listener to each button that applies its arming status and recolors all the buttons
        buttonMap.forEach( (k, v) ->
		{
            v.addActionListener(e ->
			{
                boolean active = securityService.getSensors().stream().anyMatch( s -> s.getActive().booleanValue()  );
    		
    			if(!active)
    			{
    				if( securityService.getArmingStatus() == ArmingStatus.DISARMED )
    					return;
    			} 
                
                buttonMap.get( securityService.getArmingStatus() ).setEnabled(true);
                buttonMap.get( securityService.getArmingStatus() ).setForeground​(Color.black);
                
                securityService.setArmingStatus(k);
                buttonMap.forEach((status, button) -> button.setBackground(status == k ? status.getColor() : null));
				
				v.setEnabled(false);
				v.setForeground​(Color.white);
            });
        });

        	//map order above is arbitrary, so loop again in order to add buttons in enum-order
        Arrays.stream(ArmingStatus.values()).forEach(status -> add(buttonMap.get(status)));

        ArmingStatus currentStatus = securityService.getArmingStatus();
        
        buttonMap.get(currentStatus).setBackground( currentStatus.getColor() );
        buttonMap.get(currentStatus).setEnabled(false);
    }
    
    @Override
    public void notify(AlarmStatus status) {} // no behavior necessary

    @Override
    public void catDetected(boolean catDetected, Sensor sensor) {} // no behavior necessary

    @Override
    public void sensorStatusChanged() {}
    
    @Override
    public void resetCameraHeaderMsg() {}
    
    @Override
    public void showFeed(Mat frame, int sensorHash) {}
    
    @Override
    public void armingStatusChanged()
    {
    	ArmingStatus prevStatus = securityService.getPrevArmingStatus();
    	
    	buttonMap.get(prevStatus).setBackground( prevStatus.getColor() );
        buttonMap.get(prevStatus).setEnabled(true);
        
    	ArmingStatus currentStatus = securityService.getArmingStatus();
    	
    	buttonMap.get(currentStatus).setBackground( currentStatus.getColor() );
        buttonMap.get(currentStatus).setEnabled(false);
    }
    
    @Override
    public void updateSystemStatus() {}
}

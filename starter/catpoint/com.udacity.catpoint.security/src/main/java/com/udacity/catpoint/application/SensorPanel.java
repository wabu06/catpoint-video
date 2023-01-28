package com.udacity.catpoint.application;

//import com.udacity.catpoint.data.Sensor;
//import com.udacity.catpoint.data.SensorType;
//import com.udacity.catpoint.service.SecurityService;
import com.udacity.catpoint.service.*;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

import java.awt.event.ActionEvent;

import java.util.*;

//import java.util.UUID;

import com.udacity.catpoint.data.*;

import org.opencv.core.Mat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Panel that allows users to add sensors to their system. Sensors may be
 * manually set to "active" and "inactive" to test the system.
 */
public class SensorPanel extends JPanel implements StatusListener
{

    private SecurityService securityService;

    private JLabel panelLabel = new JLabel("Sensor Management");
    private JLabel newSensorName = new JLabel("Name:");
    private JLabel newSensorType = new JLabel("Sensor Type:");
    private JTextField newSensorNameField = new JTextField();
    private JComboBox newSensorTypeDropdown = new JComboBox(SensorType.values());
    private JButton addNewSensorButton = new JButton("Add New Sensor");

    private JPanel sensorListPanel;
    private JPanel newSensorPanel;
    
    private Map<Sensor, JLabel> labelMap;

    public SensorPanel(SecurityService securityService)
    {
        super();

        setLayout(new MigLayout());
        
        this.securityService = securityService;
        
        securityService.addStatusListener(this);
        
        labelMap = new HashMap<>();

        panelLabel.setFont(StyleService.HEADING_FONT);
        addNewSensorButton.addActionListener(e ->
                addSensor(new Sensor(newSensorNameField.getText(),
                        SensorType.valueOf(newSensorTypeDropdown.getSelectedItem().toString()))));

        newSensorPanel = buildAddSensorPanel();
        sensorListPanel = new JPanel();
        sensorListPanel.setLayout(new MigLayout());

        updateSensorList(sensorListPanel);

        add(panelLabel, "wrap");
        add(newSensorPanel, "span");
        add(sensorListPanel, "span");
    }

    /**
     * Builds the panel with the form for adding a new sensor
     */
    private JPanel buildAddSensorPanel()
    {
        JPanel p = new JPanel();
        p.setLayout(new MigLayout());
        p.add(newSensorName);
        p.add(newSensorNameField, "width 50:100:200");
        p.add(newSensorType);
        p.add(newSensorTypeDropdown, "wrap");
        p.add(addNewSensorButton, "span 3, wrap");
        
        JLabel sensorLabel = new JLabel("Sensors:");
        sensorLabel.setFont(StyleService.SUB_HEADING_FONT);
        p.add(sensorLabel);
        
        return p;
    }
    
    private void updateFeedSelection(ActionEvent event, Sensor sensor)
    {
    	securityService.selectFeed(sensor);
    	
    	updateSensorList(sensorListPanel);
    }

    /**
     * Requests the current list of sensors and updates the provided panel to display them. Sensors
     * will display in the order that they are created.
     * @param p The Panel to populate with the current list of sensors
     */
    private void updateSensorList(JPanel p)
	{
        p.removeAll();
        
        labelMap.clear();
        
        //ExecutorService pool = securityService.getPool();
		
        securityService.getSensors().stream().sorted().forEach(s ->
		{
            JPanel sensorPanel = new JPanel();
            sensorPanel.setLayout(new MigLayout());
            
            JLabel sensorLabel = new JLabel
			(
				String.format("%s(%s): %s", s.getName(),
				s.getSensorType().toString(),(s.getActive() ? "Active" : "Inactive"))
			);
			
			JLabel alarmStatusLabel = new JLabel();
			
			AlarmStatus status = securityService.getSensorFeeds().get(s).getSensorAlarmStatus();
			
			alarmStatusLabel.setText(status.getDescription());
        	alarmStatusLabel.setBackground(status.getColor());
        	alarmStatusLabel.setOpaque(true);
        	
        	labelMap.put(s, alarmStatusLabel);
            
            JButton showFeedButton = new JButton( String.format("%s %s %s", "Show", s.getSensorType().toString(), "Sensor Feed") );
			JButton sensorToggleButton = new JButton((s.getActive() ? "Deactivate" : "Activate"));
            JButton sensorRemoveButton = new JButton("Remove Sensor");
            
            sensorToggleButton.setEnabled(s.isEnabled());
            
            if( s.hashCode() == securityService.getSelectedFeed() )
    			showFeedButton.setEnabled(false);
    		else
    			showFeedButton.setEnabled(true);

            showFeedButton.addActionListener( e -> updateFeedSelection(e, s) );
            sensorToggleButton.addActionListener(e -> setSensorActivity(s, !s.getActive()) );
            sensorRemoveButton.addActionListener(e -> removeSensor(s));
            
            //p.add(sensorLabel, "width 300:300:300");
            sensorPanel.add(sensorLabel, "span 1"); // p.add(sensorLabel, "width 100:100:100");
            sensorPanel.add(showFeedButton, "span 1, wrap"); // p.add(showFeedButton, "width 100:100:100, wrap");
            	// p.add(alarmStatusLabel, "width 100:100:100");
            sensorPanel.add(alarmStatusLabel, "span 0");
            	// p.add(sensorToggleButton, "width 100:100:100");
            sensorPanel.add(sensorToggleButton, "span 0");
            	// p.add(sensorRemoveButton, "width 100:100:100, wrap");
            sensorPanel.add(sensorRemoveButton, "span 1");

	            //p.add(sensorLabel, "width 300:300:300");
            //p.add(sensorLabel, "span 1"); // p.add(sensorLabel, "width 100:100:100");
            //p.add(showFeedButton, "span 1, wrap"); // p.add(showFeedButton, "width 100:100:100, wrap");
            	// p.add(alarmStatusLabel, "width 100:100:100");
            //p.add(alarmStatusLabel, "gapx 0 0");
            	// p.add(sensorToggleButton, "width 100:100:100");
           //p.add(sensorToggleButton, "gapx 0 0");
            	// p.add(sensorRemoveButton, "width 100:100:100, wrap");
            //p.add(sensorRemoveButton, "left, wrap");
            
            p.add(sensorPanel, "wrap");
        });

        repaint();
        revalidate();
    }

    /**
     * Asks the securityService to change a sensor activation status and then rebuilds the current sensor list
     * @param sensor The sensor to update
     * @param isActive The sensor's activation status
     */
    private void setSensorActivity(Sensor sensor, Boolean isActive)
    {
        securityService.changeSensorActivationStatus(sensor, isActive);
        
        if(!isActive)
        	securityService.getSensorFeeds().get(sensor).setSensorAlarmStatus(AlarmStatus.NO_ALARM);
        
        updateSensorList(sensorListPanel);
    }

    /**
     * Adds a sensor to the securityService and then rebuilds the sensor list
     * @param sensor The sensor to add
     */
    /*private void addSensor(Sensor sensor)
    {
        if(securityService.getSensors().size() < 4) {
            securityService.addSensor(sensor);
            updateSensorList(sensorListPanel);
        } else {
            JOptionPane.showMessageDialog(null, "To add more than 4 sensors, please subscribe to our Premium Membership!");
        }
    }*/
    
    private void addSensor(Sensor sensor)
    {
        securityService.addSensor(sensor);
        updateSensorList(sensorListPanel);
        
        if( securityService.getSensors().size() + 1 > 3)
        	addNewSensorButton.setEnabled(false);
    }

    /**
     * Remove a sensor from the securityService and then rebuild the sensor list
     * @param sensor The sensor to remove
     */
    private void removeSensor(Sensor sensor)
    {
        securityService.removeSensor(sensor);
        updateSensorList(sensorListPanel);
        
        if( !addNewSensorButton.isEnabled() )
        {
        	if( securityService.getPool().isShutdown() )
        		return;

        	if( securityService.getSensors().size() < 3 )
       			addNewSensorButton.setEnabled(true);
        }
    }
	
	public void extUpdateSensorList() { updateSensorList(sensorListPanel); }
	
	@Override
    public void notify(AlarmStatus status) {} // no behavior necessary

    @Override
    public void catDetected(boolean catDetected, Sensor sensor) {} // no behavior necessary

    /*@Override
    public void sensorStatusChanged() {
        updateSensorList(sensorListPanel);
    }*/
    
    @Override
    public void sensorStatusChanged(Sensor sensor)
    {
        AlarmStatus status = securityService.getSensorFeeds().get(sensor).getSensorAlarmStatus();
			
		labelMap.get(sensor).setText(status.getDescription());
       	labelMap.get(sensor).setBackground(status.getColor());
       	labelMap.get(sensor).setOpaque(true);
    }
    
    @Override
    public void resetCameraHeaderMsg() {}
    
    @Override
    public void showFeed(Mat frame, int sensorHash) {}
    
    @Override
    public void armingStatusChanged() {}
    
    @Override
    public void updateSystemStatus() {}
    
    @Override
    public void updateSensors() {
    	updateSensorList(sensorListPanel);
    }
    
    @Override
    public void enableAddSensor(boolean enable)
    {
		if(enable)
		{
			if( securityService.getSensors().size() < 3 )
       			addNewSensorButton.setEnabled(enable);
		}
		else
			addNewSensorButton.setEnabled(enable);
    }
}

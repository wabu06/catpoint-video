package com.udacity.catpoint.application;

//import com.udacity.catpoint.data.Sensor;
import com.udacity.catpoint.data.*;
import com.udacity.catpoint.service.SecurityService;
import com.udacity.catpoint.service.StyleService;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

import java.util.List;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

/**
 * Panel that allows users to add sensors to their system. Sensors may be
 * manually set to "active" and "inactive" to test the system.
 */
public class SensorTestPanel extends JPanel {

    private SecurityService securityService;

    private JLabel panelLabel = new JLabel("Sensor Management");
    private JLabel newSensorName = new JLabel("Name:");
    private JLabel newSensorType = new JLabel("Sensor Type:");
    private JTextField newSensorNameField = new JTextField();
    private JComboBox newSensorTypeDropdown = new JComboBox(SensorType.values());
    private JButton addNewSensorButton = new JButton("Add New Sensor");

    private JPanel sensorListPanel;
    private JPanel newSensorPanel;
	
	private JButton[] sensorToggleBttns = new JButton[3];
	
	private int b = 0; // sensor toggle button count

    public SensorTestPanel(SecurityService securityService) {
        super();
        setLayout(new MigLayout());
        this.securityService = securityService;

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
    private JPanel buildAddSensorPanel() {
        JPanel p = new JPanel();
        p.setLayout(new MigLayout());
        p.add(newSensorName);
        p.add(newSensorNameField, "width 50:100:200");
        p.add(newSensorType);
        p.add(newSensorTypeDropdown, "wrap");
        p.add(addNewSensorButton, "span 3");
        return p;
    }

    /**
     * Requests the current list of sensors and updates the provided panel to display them. Sensors
     * will display in the order that they are created.
     * @param p The Panel to populate with the current list of sensors
     */
    private void updateSensorList(JPanel p)
	{
        p.removeAll();
		b = 0; // sensor toggle button count
		
        securityService.getSensors().stream().sorted().forEach(s ->
		{
            JLabel sensorLabel = new JLabel
			(
				String.format("%s(%s): %s", s.getName(),
				s.getSensorType().toString(),(s.getActive() ? "Active" : "Inactive"))
			);
            
			JButton sensorToggleButton = new JButton((s.getActive() ? "Deactivate" : "Activate"));
            JButton sensorRemoveButton = new JButton("Remove Sensor");

            sensorToggleButton.addActionListener(e -> setSensorActivity(s, !s.getActive()) );
            sensorRemoveButton.addActionListener(e -> removeSensor(s));
			
			sensorToggleBttns[b] = sensorToggleButton; b++;

            //hard code some sizes, tsk tsk
            p.add(sensorLabel, "width 300:300:300");
            p.add(sensorToggleButton, "width 100:100:100");
            p.add(sensorRemoveButton, "wrap");
        });

        repaint();
        revalidate();
    }

    /**
     * Asks the securityService to change a sensor activation status and then rebuilds the current sensor list
     * @param sensor The sensor to update
     * @param isActive The sensor's activation status
     */
    private void setSensorActivity(Sensor sensor, Boolean isActive) {
        securityService.changeSensorActivationStatus(sensor, isActive);
        updateSensorList(sensorListPanel);
    }

    /**
     * Adds a sensor to the securityService and then rebuilds the sensor list
     * @param sensor The sensor to add
     */
    private void addSensor(Sensor sensor) {
        if(securityService.getSensors().size() < 4) {
            securityService.addSensor(sensor);
            updateSensorList(sensorListPanel);
        } else {
            JOptionPane.showMessageDialog(null, "To add more than 4 sensors, please subscribe to our Premium Membership!");
        }
    }

    /**
     * Remove a sensor from the securityService and then rebuild the sensor list
     * @param sensor The sensor to remove
     */
    private void removeSensor(Sensor sensor) {
        securityService.removeSensor(sensor);
        updateSensorList(sensorListPanel);
    }
	
	public JButton getSensorToggleBttn(int bttn) { return sensorToggleBttns[bttn]; }
	
	public JButton getAddSensorBttn() { return addNewSensorButton; }
	
	public JComboBox getSensorTypeDropdown() { return newSensorTypeDropdown; }
	
	public void extUpdateSensorList() { updateSensorList(sensorListPanel); }
}

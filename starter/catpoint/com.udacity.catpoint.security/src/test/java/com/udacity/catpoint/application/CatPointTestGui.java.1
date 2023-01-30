package com.udacity.catpoint.application;

import com.udacity.catpoint.data.PretendDatabaseSecurityRepositoryImpl;
import com.udacity.catpoint.data.SecurityRepository;
//import com.udacity.catpoint.service.FakeImageService;
import com.udacity.catpoint.service.SecurityService;

import com.udacity.image.service.*;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * This is the primary JFrame for the application that contains all the top-level JPanels.
 *
 * We're not using any dependency injection framework, so this class also handles constructing
 * all our dependencies and providing them to other classes as necessary.
 */
public class CatPointTestGui extends JFrame
{
    //private SecurityRepository securityRepository = new PretendDatabaseSecurityRepositoryImpl();
	
    //private FakeImageService imageService = new FakeImageService();
    
	//private SecurityService securityService = new SecurityService(securityRepository, imageService);
	
	private SecurityService securityService;
	
    private DisplayPanel displayPanel;
    private ControlPanel controlPanel;
    private SensorTestPanel sensorPanel;
    private ImagePanel imagePanel;

    public CatPointTestGui(SecurityService securityService)
	{
        this.securityService = securityService;
		
		displayPanel = new DisplayPanel(securityService);
    	controlPanel = new ControlPanel(securityService);
    	sensorPanel = new SensorTestPanel(securityService);
		imagePanel = new ImagePanel(securityService);
		
		//setLocation(100, 100);
		setLocation(50, 50);
        //setSize(600, 850);
		setSize(600, 720);
        setTitle("Very Secure App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ImageIcon icon = new ImageIcon("no-cats.jpeg");
		
		setIconImage( icon.getImage() );

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout());
        mainPanel.add(displayPanel, "wrap");
        mainPanel.add(imagePanel, "wrap");
        mainPanel.add(controlPanel, "wrap");
        mainPanel.add(sensorPanel);

        getContentPane().add(mainPanel);

    }
}

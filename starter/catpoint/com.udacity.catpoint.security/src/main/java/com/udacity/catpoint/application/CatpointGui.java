package com.udacity.catpoint.application;

import com.udacity.catpoint.data.PretendDatabaseSecurityRepositoryImpl;
import com.udacity.catpoint.data.SecurityRepository;
//import com.udacity.catpoint.service.FakeImageService;
//import com.udacity.catpoint.service.SecurityService;
import com.udacity.catpoint.service.*;

//import com.udacity.detection.service.*;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javax.inject.Inject;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Here we setup the JFrame that contains the controls for the application,
 * as well as the JFrame for displaying the currently selected sensor feed.
**/
public class CatpointGui
{
	private SecurityRepository securityRepository;
	private SecurityService securityService;

	private static CatpointGui cpg = null;
	
	private CatpointController controller;
	private FeedDisplayWindow fdw;
	
	private CatpointGui()
	{
		this.securityRepository = new PretendDatabaseSecurityRepositoryImpl();
		this.securityService = new SecurityService(securityRepository);
		
		this.controller = new CatpointController(securityService);
		
		this.fdw = FeedDisplayWindow.getInstance(securityService);
		
		this.controller.setVisible(true);
		this.fdw.getDisplayFrame().setVisible(true);
	}
	
	public static CatpointGui create()
	{
		if(cpg == null)
			cpg = new CatpointGui();

		return cpg;
	}

	class CatpointController extends JFrame
	{
    	//private SecurityRepository securityRepository;
	
		private SecurityService securityService;
	
    	private DisplayPanel displayPanel;
    	private SensorPanel sensorPanel;

		private FeedDisplayControlPanel feedDisplayControlPanel;
		private ControlPanel controlPanel;

		//private FeedDisplayWindow fdw;
	
		private JFrame sensorPanelWindow;

    	private CatpointController(SecurityService securityService)
		{
			//this.securityRepository = new PretendDatabaseSecurityRepositoryImpl();
		
			//this.securityService = new SecurityService(securityRepository);
			
			this.securityService = securityService;
		
			this.displayPanel = new DisplayPanel(securityService);
    		this.sensorPanel = new SensorPanel(securityService);
			this.feedDisplayControlPanel = new FeedDisplayControlPanel(securityService);
			this.controlPanel = new ControlPanel(securityService);
	
        	setLocation(975, 0);
			//setLocation(50, 50);
        	//setSize(600, 850);
        
			setSize(390, 740);
        	setTitle("NoCats");
        	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
			try( InputStream is = getClass().getClassLoader().getResourceAsStream("no-cats.jpeg") )
			{
				ImageIcon icon = new ImageIcon( is.readAllBytes() );
				setIconImage( icon.getImage() );
			}
			catch(Exception exp)
			{
				Logger log = LoggerFactory.getLogger(CatpointGui.class);
				log.info("No Icon", exp);
			}

        	JPanel mainPanel = new JPanel();
        	mainPanel.setLayout(new MigLayout());
        	mainPanel.add(displayPanel, "wrap");
        	mainPanel.add(feedDisplayControlPanel, "wrap");
        	mainPanel.add(controlPanel, "wrap");
        	//mainPanel.add(controlPanel);
        	mainPanel.add(sensorPanel);

        	getContentPane().add(mainPanel);
        
        	//sensorPanelWindow = new JFrame("Sensor Panel");
        	//sensorPanelWindow.getContentPane().add(sensorPanel);
    	}
    
    	/*public JFrame getFeedDisplay() {
    		return fdw.getDisplayFrame();
    	}*/
    }
}

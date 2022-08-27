package com.udacity.catpoint.application;

import com.udacity.catpoint.data.AlarmStatus;
import com.udacity.catpoint.service.SecurityService;
import com.udacity.catpoint.service.StyleService;

import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.IOException;

import java.nio.charset.Charset;
import java.time.Instant;

import java.util.*;
//import java.nio.file.*;
import java.util.stream.*;

/** Panel containing the 'camera' output. Allows users to 'refresh' the camera
 * by uploading their own picture, and 'scan' the picture, sending it for image analysis
 */
public class ImagePanel extends JPanel implements StatusListener
{
    private SecurityService securityService;

    private JLabel cameraHeader;
    private JLabel cameraLabel;
	
	//private java.util.List<BufferedImage> bImages;
    private BufferedImage currentCameraImage;
    
    private java.util.List<String> fileNames;
	
	private Random RNG = new Random( Instant.now().toEpochMilli() );
	
	private int idx; // filenames list index

    private int IMAGE_WIDTH = 300;
    private int IMAGE_HEIGHT = 225;

    public ImagePanel(SecurityService securityService)
	{
        super();
		
        setLayout(new MigLayout());
        this.securityService = securityService;
        securityService.addStatusListener(this);

        cameraHeader = new JLabel("Camera Feed");
        cameraHeader.setFont(StyleService.HEADING_FONT);

        cameraLabel = new JLabel();
        cameraLabel.setBackground(Color.WHITE);
        cameraLabel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        cameraLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        	//button allowing users to select a file to be the current camera image
        JButton refreshButton = new JButton("Refresh Camera");
		
        refreshButton.addActionListener( e -> { showRandImage(); /* securityService.processImage(currentCameraImage); */ } );

        	//button that sends the image to the image service
        JButton scanPictureButton = new JButton("Scan Picture");
        scanPictureButton.addActionListener( e -> securityService.processImage(currentCameraImage) );

        getImageFileNames();
		
		add(cameraHeader, "span 3, wrap");
        add(cameraLabel, "span 3, wrap");
        add(refreshButton);
        add(scanPictureButton);
		
		idx = RNG.nextInt( fileNames.size() );
		
		showRandImage();
    }
	
	private void showRandImage()
	{
		//int size = fileNames.size();
		
		if( idx <  fileNames.size() - 1 )
			idx++;
		else
			idx = 0;
			
		//System.out.println( idx + "\t" + fileNames.size() );
		
		try( InputStream is = getClass().getClassLoader().getResourceAsStream( "camera/" + fileNames.get(idx) ) )
		{
			currentCameraImage = ImageIO.read(is);
		}
		catch(Exception exp)
		{
			JOptionPane.showMessageDialog
				(
					null,
					"Unable To Find A Critical Resource, Application Will Now Terminate",
					"ERROR",
					JOptionPane.ERROR_MESSAGE
				);

			System.exit(1);
		}
		
		Image tmp = new ImageIcon(currentCameraImage).getImage();
		
		cameraLabel.setIcon( new ImageIcon( tmp.getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH) ) );
		
		repaint();
	}
	
	private int showDialog(String msg)
	{
		Object[] options = {"TRY AGAIN", "QUIT"};
		
		return JOptionPane.showOptionDialog
					(
						null,
						msg,
						"ERROR",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.ERROR_MESSAGE,
						null,
						options,
						options[1]
					);
	}
	
	private void getImageFileNames()
	{	
		try( InputStream is = getClass().getClassLoader().getResourceAsStream("camera/manifest.img") )
		{
			fileNames = new BufferedReader( new InputStreamReader(is, Charset.defaultCharset()) ).lines().collect( Collectors.toList() );
		}
		catch(Exception exp)
		{
			JOptionPane.showMessageDialog
				(
					null,
					"Unable To Find A Critical Resource, Application Will Now Terminate",
					"ERROR",
					JOptionPane.ERROR_MESSAGE
				);
				
			System.exit(1);
		}
		
		int j;
		
		for(int i = 0; i < fileNames.size(); i++)
		{
			j = RNG.nextInt( fileNames.size() );
			Collections.swap(fileNames, i, j);
		}
		//JOptionPane.showMessageDialog(null, "image count: " + bImages.size());
	}

    @Override
    public void notify(AlarmStatus status) {} //no behavior necessary

    @Override
    public void catDetected(boolean catDetected, String sensor)
	{
        if(catDetected)
            cameraHeader.setText("DANGER - CAT DETECTED - " + sensor);
        else
            cameraHeader.setText("Camera Feed - No Cats Detected");
    }

    @Override
    public void sensorStatusChanged() {} //no behavior necessary
	
	public BufferedImage getCurrentCameraImage() { return currentCameraImage; }
}

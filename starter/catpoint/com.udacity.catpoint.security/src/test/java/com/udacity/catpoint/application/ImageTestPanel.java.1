package com.udacity.catpoint.application;

import com.udacity.catpoint.data.AlarmStatus;
import com.udacity.catpoint.service.SecurityService;
import com.udacity.catpoint.service.StyleService;

import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.*;
import java.nio.file.*;
import java.util.stream.*;


/** Panel containing the 'camera' output. Allows users to 'refresh' the camera
 * by uploading their own picture, and 'scan' the picture, sending it for image analysis
 */
public class ImageTestPanel extends JPanel implements StatusListener
{
    private SecurityService securityService;

    private JLabel cameraHeader;
    private JLabel cameraLabel;
	
	private java.util.List<BufferedImage> bImages;
    private BufferedImage currentCameraImage;
	
	private Random RNG = new Random();

    private int IMAGE_WIDTH = 300;
    private int IMAGE_HEIGHT = 225;

    public ImageTestPanel(SecurityService securityService)
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

        loadImages();
		
		add(cameraHeader, "span 3, wrap");
        add(cameraLabel, "span 3, wrap");
        add(refreshButton);
        add(scanPictureButton);
		
		showRandImage();
    }
	
	private void showRandImage()
	{
		int size = bImages.size();
		
		currentCameraImage = bImages.get( RNG.nextInt(size) );
		
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
	
	private void loadImages()
	{
		java.util.List<Path> cameraList = null;
		
		boolean repeat = true; int n;
		
		do {
		
			try( Stream<Path> pStream = Files.walk( Path.of(".").toAbsolutePath().resolve("..").normalize(), 5 ) )
			{
				cameraList = pStream.filter( p -> Files.isDirectory(p) )
									.filter( p -> p.getFileName().toString().equalsIgnoreCase("camera") )
									.collect( Collectors.toList() );
			}
			catch(Exception exp)
			{
				if( showDialog("No Camera Found") == 1 )
					System.exit(1);
			}
		
			if( cameraList.size() == 0 )
			{
				if( showDialog("No Camera Found") == 1 )
					System.exit(1);
			}
			else
				repeat = false;
		
		} while(repeat);
		
		Set<BufferedImage> imageSet = new HashSet<>();
		BufferedImage img;
		
		repeat = true;
		
		do {
		
			for(Path cam: cameraList)
			{
   				try( DirectoryStream<Path> stream = Files.newDirectoryStream(cam) )
				{
					for(Path P: stream)
					{
						try
						{
							img = ImageIO.read( P.toFile() );
					
							if( img != null )
								imageSet.add(img);
						}
						catch(Throwable exp)
						{
							continue;
						}
					}
				}
				catch(Throwable exp)
				{
					continue;
				}
			}
		
			bImages = new ArrayList<>(imageSet);
			
			if( bImages.size() == 0 )
			{
				if( showDialog("Cannot Get A Camera Feed") == 1 )
					System.exit(1);
			}
			else
				repeat = false;
		
		} while(repeat);
		
		//JOptionPane.showMessageDialog(null, "image count: " + bImages.size());
	}

    @Override
    public void notify(AlarmStatus status) {} //no behavior necessary

    @Override
    public void catDetected(boolean catDetected)
	{
        if(catDetected)
            cameraHeader.setText("DANGER - CAT DETECTED");
        else
            cameraHeader.setText("Camera Feed - No Cats Detected");
    }

    @Override
    public void sensorStatusChanged() {} //no behavior necessary
	
	public BufferedImage getCurrentCameraImage() { return currentCameraImage; }
}


package com.udacity.image.service;


import com.google.cloud.vision.v1.*;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.protobuf.ByteString;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class GoogleImageService implements ImageService
{
	private Feature feat;
	
	public GoogleImageService() { feat = Feature.newBuilder().setType(Type.LABEL_DETECTION).build(); }
	
	@Override 
    public boolean imageContainsCat(BufferedImage image, float confidenceThreshhold)
    {
    	try( ImageAnnotatorClient vClient = ImageAnnotatorClient.create() )
    	{
    		ByteArrayOutputStream os = new ByteArrayOutputStream();
    	
    		//ByteString imgBytes = null;
    		//try{ 
    	
    		ImageIO.write(image, "jpg", os); byte[] data = os.toByteArray(); ByteString imgBytes = ByteString.copyFrom(data);
    	
    		//} catch(IOException exp) {}
    	
    		// Builds the image annotation request
		
		
      		Image img = Image.newBuilder().setContent(imgBytes).build();
      		
      		List<AnnotateImageRequest> requests = new ArrayList<>();
 
      		AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
      	
      		requests.add(request);

      		// Performs label detection on the image file
      		BatchAnnotateImagesResponse response = vClient.batchAnnotateImages(requests);
      		List<AnnotateImageResponse> responses = response.getResponsesList();
      	
      		for (AnnotateImageResponse res : responses)
      		{
        		if (res.hasError())
        		{
          			System.out.format("Error: %s%n", res.getError().getMessage());
          			return false;
        		}

        		for (EntityAnnotation annotation : res.getLabelAnnotationsList())
					annotation.getAllFields().forEach((k, v) -> System.out.format("%s : %s%n", k, v.toString()));
			}
		}
		catch(Exception exp) {}
		
		return false;
	}
}

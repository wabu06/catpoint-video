module com.udacity.catpoint.image
{
	exports com.udacity.detection.service;
	
	requires java.desktop;
	requires org.slf4j;
	//requires software.amazon.awssdk.auth;
	//requires software.amazon.awssdk.core;
	//requires software.amazon.awssdk.regions;
	//requires software.amazon.awssdk.services.rekognition;
	
	requires opencv;
	
	opens com.udacity.detection.service;
}

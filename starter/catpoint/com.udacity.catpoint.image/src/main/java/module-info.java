module com.udacity.catpoint.image
{
	exports com.udacity.image.service;
	
	requires java.desktop;
	requires org.slf4j;
	requires software.amazon.awssdk.auth;
	requires software.amazon.awssdk.core;
	requires software.amazon.awssdk.regions;
	requires software.amazon.awssdk.services.rekognition;
	
	//requires proto.google.cloud.vision.v1;
	//requires google.cloud.vision;
	requires com.udacity.catpoint.google;
	
	
	//requires com.google.protobuf; //try adding as dependency for image service
	
	opens com.udacity.image.service;
}

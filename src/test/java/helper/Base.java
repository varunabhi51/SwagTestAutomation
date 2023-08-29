package helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Base {
	RequestSpecification req;
	
	public static RequestSpecification setup() throws IOException {
		
		PrintStream log = new PrintStream(new FileOutputStream("log.text"));
	RequestSpecification req = new RequestSpecBuilder().setBaseUri(getConfigData("baseURI")).addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).
			addFilter(RequestLoggingFilter.logRequestTo(log))
			.build();
	return req;
		 
	}
	 public static String getConfigData(String key) throws IOException {
		 
		 FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"/src/test/java/resource/env.properties");
		 Properties prop = new Properties(); 
		 prop.load(file);
		 
		return prop.getProperty(key);
		 
		 
	 }
	
	
	

}

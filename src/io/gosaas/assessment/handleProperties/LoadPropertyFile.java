package io.gosaas.assessment.handleProperties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class LoadPropertyFile {
	public String jdbcURL;
	public String jdbcName;
	public String jdbcPassword;
	public String jdbcDriver;

	public LoadPropertyFile() {
		
		WritePropertyFile wprop = new WritePropertyFile();
		// TODO Auto-generated constructor stub
		 try (InputStream input = new FileInputStream("/home/dev/eclipse-workspace/TemplateProject/WebContent/WEB-INF/classes/config.properties")) {

	            Properties prop = new Properties();

	            // load a properties file
	            prop.load(input);

	            // get the property value and print it out
	            jdbcURL = prop.getProperty("jdbcURL");
	            jdbcName = prop.getProperty("jdbcName");
	            jdbcPassword = prop.getProperty("jdbcPassword");
	            jdbcDriver = prop.getProperty("jdbcDriver");

	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	}

}

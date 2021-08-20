package io.gosaas.assessment.handleProperties;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class WritePropertyFile {

	public WritePropertyFile() {
		// TODO Auto-generated constructor stub
		try (OutputStream output = new FileOutputStream("/home/dev/eclipse-workspace/TemplateProject/WebContent/WEB-INF/classes/config.properties")) {

            Properties prop = new Properties();

            // set the properties value
            prop.setProperty("jdbcURL", "jdbc:mysql://localhost:3306/gosaasAssessmentDb?useSSL=false");
            prop.setProperty("jdbcName", "root");
            prop.setProperty("jdbcPassword", "root");
            prop.setProperty("jdbcDriver", "com.mysql.jdbc.Driver");
            
            // save properties to project root folder
            prop.store(output, null);

            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }
	}

}

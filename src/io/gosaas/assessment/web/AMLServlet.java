package io.gosaas.assessment.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.google.gson.Gson;

import io.gosaas.assessment.databaseAccessObject.AMLDAO;
import io.gosaas.assessment.models.AML;

@WebServlet("/amlservlet")
public class AMLServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AMLDAO amldb;
	private Gson gson = new Gson();
	private static Logger logger = Logger.getLogger(AMLServlet.class.getName());
	
   public AMLServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void init() throws ServletException {
		amldb = new AMLDAO();
		logger.info("Started");	
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("doGet() Triggered");	
		if (request.getParameter("mitemid") == null)
		{	}
		else if (request.getParameter("mitemid") != null && request.getParameter("mid") == null)
		{
			String stringItemId = request.getParameter("mitemid");  
	        int itemId = Integer.parseInt(stringItemId);  
			logger.info("Searching for AMLs having Item ID " + itemId);	
	
			String getAMLJsonString = this.gson.toJson(amldb.getAllAMLsOfItemId(itemId));
			logger.info("Converting Response to JSON String " + getAMLJsonString);	
			System.out.println(getAMLJsonString);
	        PrintWriter out = response.getWriter();
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        out.print(getAMLJsonString);
	        out.flush();
			logger.info("FINISHED ");	
		}
		else if (request.getParameter("mitemid") != null && request.getParameter("mid") != null)
		{	}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("doDelete() Triggered");	
		JSONObject json = convertToJSON(request);
	    int mid = json.getInt("mid");

		logger.info("Deleting AML with ID " + mid);	
		amldb.deleteAMLFromDb(mid);  
	}

	private JSONObject convertToJSON(HttpServletRequest request) throws IOException {
		BufferedReader reader = request.getReader();
	    StringBuilder stringBuilder = new StringBuilder();
	    String line = reader.readLine();
	    while (line != null) {
	    	stringBuilder.append(line + "\n");
			line = reader.readLine();
	    }
		reader.close();
		
		String params = stringBuilder.toString();
	    System.out.println(params);
			    
	    JSONObject json = new JSONObject(params);  
	    System.out.println(json);
		return json;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
		logger.info("doPost() Triggered");			
		JSONObject json = convertToJSON(request);
	    int mitemid = json.getInt("mitemid");
	    String description = json.getString("description");
	    String mpart = json.getString("mpart");
	    String manufacturer = json.getString("manufacturer");
	    String amlstatus = json.getString("amlstatus");
	    String mstatus = json.getString("mstatus");
	    int registryid = json.getInt("registryid");
	    System.out.println(mitemid+mpart+manufacturer+registryid+amlstatus+description+mstatus);
	    AML aml = new AML(mitemid,mpart,manufacturer,registryid,amlstatus,description,mstatus);
		amldb.insertAMLInDb(aml);
		doGet(request, response);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("doPut() Triggered");	
		JSONObject json = convertToJSON(request);
        System.out.println(json);
                
	    int mid = json.getInt("mid");
	    String description = json.getString("description");
	    String mpart = json.getString("mpart");
	    String manufacturer = json.getString("manufacturer");
	    String amlstatus = json.getString("amlstatus");
	    String mstatus = json.getString("mstatus");
	    int registryid = json.getInt("registryid");
	    System.out.println(mid+mpart+manufacturer+registryid+amlstatus+description+mstatus);		
	    AML aml = new AML(mpart,manufacturer,registryid,amlstatus,description,mstatus);
	    
		System.out.println(aml);
		logger.info("Updating AML with ID " + mid);	
        aml.setMid(mid);
		amldb.updateAMLInDb(aml);
		doGet(request, response);
	}

}

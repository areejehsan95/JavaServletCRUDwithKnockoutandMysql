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
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.gosaas.assessment.databaseAccessObject.StructureDAO;
import io.gosaas.assessment.models.Structure;

@WebServlet("/structureservlet")
public class StructureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StructureDAO structuredb;
	private Gson gson = new Gson();
	Logger logger;
	
   public StructureServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void init() throws ServletException {
		structuredb = new StructureDAO();
		logger = Logger.getLogger(StructureServlet.class.getName());
		logger.info("Started");	
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("doGet() Triggered");	
		if (request.getParameter("sitemid") == null)
		{	}
		else if (request.getParameter("sitemid") != null && request.getParameter("sid") == null)
		{
			String stringItemId = request.getParameter("sitemid");  
	        int itemId = Integer.parseInt(stringItemId);  
			logger.info("Searching for Structures having Item ID " + itemId);	
	
			String getStructureJsonString = this.gson.toJson(structuredb.getAllStructuresOfItemId(itemId));
			logger.info("Converting Response to JSON String " + getStructureJsonString);	
			System.out.println(getStructureJsonString);
	        PrintWriter out = response.getWriter();
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        out.print(getStructureJsonString);
	        out.flush();
			logger.info("FINISHED ");	
		}
		else if (request.getParameter("sitemid") != null && request.getParameter("sid") != null)
		{	}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("doDelete() Triggered");	
		String params = convertToJSON(request);
			    
	    JSONObject json = new JSONObject(params);  
	    System.out.println(json);
	    int sid = json.getInt("sid");

		logger.info("Deleting Structure with ID " + sid);	
		structuredb.deleteStructureFromDb(sid);  
	}

	private String convertToJSON(HttpServletRequest request) throws IOException {
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
		return params;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
		logger.info("doPost() Triggered");			
		String params = convertToJSON(request);
	    
	    JSONObject json = new JSONObject(params);  
	    System.out.println(json);
	    int sitemid = json.getInt("sitemid");
	    String Class = json.getString("Class");
	    String description = json.getString("description");
	    String lifecyclephase = json.getString("lifecyclephase");
	    String createdby = json.getString("createdby");
	    System.out.println(sitemid+Class+description+lifecyclephase+createdby);
	    Structure structure = new Structure(sitemid,Class,description,lifecyclephase,createdby);
		structuredb.insertStructureInDb(structure);
		doGet(request, response);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("doPut() Triggered");	
		String params = convertToJSON(request);
	    
	    JSONObject json = new JSONObject(params);  
        System.out.println(json);
        
        
	    int sid = json.getInt("sid");
	    String Class = json.getString("Class");
	    String description = json.getString("description");
	    String lifecyclephase = json.getString("lifecyclephase");
	    String createdby = json.getString("createdby");
	    System.out.println(Class+description+lifecyclephase+createdby);
	    Structure structure = new Structure(Class,description,lifecyclephase,createdby);
	    
		System.out.println(structure);
		logger.info("Updating Structure with ID " + sid);	
        structure.setSid(sid);
		structuredb.updateStructureInDb(structure);
		doGet(request, response);
	}

}

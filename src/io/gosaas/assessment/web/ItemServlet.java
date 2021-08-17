package io.gosaas.assessment.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.gosaas.assessment.databaseAccessObject.ItemDAO;
import io.gosaas.assessment.models.Item;

@WebServlet("/itemservlet")
public class ItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ItemDAO itemdb;
	private Gson gson = new Gson();
	Logger logger;

	public ItemServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {
		itemdb = new ItemDAO();

		// initialize log4j here
		String log4Jpath = System.getProperty("/home/dev/eclipse-workspace/TemplateProject/WebContent/WEB-INF/classes/log4j.properties");
		PropertyConfigurator.configure(log4Jpath);
		logger = Logger.getLogger(ItemServlet.class.getName());
		logger.info("Started");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("doGet() Triggered");
		if (request.getParameter("id") == null) {
			logger.info("Displaying the existing items list");
			List<Item> allItems = itemdb.getAllItems();
			String itemsListJsonString = this.gson.toJson(allItems);
			logger.info("Converting Response to JSON String " + itemsListJsonString);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(itemsListJsonString);
			out.flush();
			logger.info("FINISHED ");
		} else {
			String stringId = request.getParameter("id");
			int id = Integer.parseInt(stringId);
			logger.info("Searching for Item having ID " + id);

			String getItemJsonString = this.gson.toJson(itemdb.findItemById(id));
			logger.info("Converting Response to JSON String " + getItemJsonString);

			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(getItemJsonString);
			out.flush();
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("doDelete() Triggered");
		JSONObject json = convertToJSON(request);
		int id = json.getInt("id");

		logger.info("Deleting Item with ID " + id);
		itemdb.deleteItemFromDb(id);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("doPost() Triggered");
		JSONObject json = convertToJSON(request);
		String Class = json.getString("Class");
		String description = json.getString("description");
		System.out.println(Class + description);
		Item item = new Item(Class, description);
		itemdb.insertItemInDb(item);
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


	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("doPut() Triggered");
		JSONObject json = convertToJSON(request);
		int id = json.getInt("id");
		String Class = json.getString("Class");
		String description = json.getString("description");
		System.out.println(Class + description);
		Item item = new Item(Class, description);
		System.out.println(item);
		logger.info("Updating Item with ID " + id);
		item.setId(id);
		itemdb.updateItemInDb(item);
		doGet(request, response);
	}

}

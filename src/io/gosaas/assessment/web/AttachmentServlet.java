package io.gosaas.assessment.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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

import io.gosaas.assessment.databaseAccessObject.AttachmentDAO;
import io.gosaas.assessment.models.Attachment;

@WebServlet("/attachmentservlet")
public class AttachmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AttachmentDAO attachmentdb;
	private Gson gson = new Gson();
	private static Logger logger = Logger.getLogger(AttachmentServlet.class.getName());

	public AttachmentServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {
		attachmentdb = new AttachmentDAO();
		logger.info("Started");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("doGet() Triggered");
		if (request.getParameter("itemid") == null) {
		} else if (request.getParameter("itemid") != null && request.getParameter("aid") == null) {
			String stringItemId = request.getParameter("itemid");
			int itemId = Integer.parseInt(stringItemId);
			logger.info("Searching for Attachments having Item ID " + itemId);

			String getAttachmentJsonString = this.gson.toJson(attachmentdb.getAllAttachmentsOfItemId(itemId));
			logger.info("Converting Response to JSON String " + getAttachmentJsonString);
			System.out.println(getAttachmentJsonString);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(getAttachmentJsonString);
			out.flush();
			logger.info("FINISHED ");
		} else if (request.getParameter("itemid") != null && request.getParameter("aid") != null) {
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("doDelete() Triggered");
		String params = convertToJSON(request);

		JSONObject json = new JSONObject(params);
		System.out.println(json);
		int aid = json.getInt("aid");

		logger.info("Deleting Attachment with ID " + aid);
		attachmentdb.deleteAttachmentFromDb(aid);
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("doPost() Triggered");
		String params = convertToJSON(request);

		JSONObject json = new JSONObject(params);
		System.out.println(json);
		int itemid = json.getInt("itemid");
		String filename = json.getString("filename");
		String description = json.getString("description");
		String category = json.getString("category");
		String shared = json.getString("shared");
		String checkedoutby = json.getString("checkedoutby");
		int revision = json.getInt("revision");
		System.out.println(itemid + filename + description + category + shared + checkedoutby + revision);
		Attachment attachment = new Attachment(itemid, filename, description, category, shared, checkedoutby, revision);
		attachmentdb.insertAttachmentInDb(attachment);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("doPut() Triggered");
		String params = convertToJSON(request);
		JSONObject json = new JSONObject(params);
		System.out.println(json);

		int aid = json.getInt("aid");
		String filename = json.getString("filename");
		String description = json.getString("description");
		String category = json.getString("category");
		String shared = json.getString("shared");
		String checkedoutby = json.getString("checkedoutby");
		int revision = json.getInt("revision");
		System.out.println(filename + description + category + shared + checkedoutby + revision);
		Attachment attachment = new Attachment(filename, description, category, shared, checkedoutby, revision);

		System.out.println(attachment);
		logger.info("Updating Attachment with ID " + aid);
		attachment.setAid(aid);
		attachmentdb.updateAttachmentInDb(attachment);
		doGet(request, response);
	}

}

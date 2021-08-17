package io.gosaas.assessment.databaseAccessObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import io.gosaas.assessment.handleProperties.LoadPropertyFile;
import io.gosaas.assessment.models.Attachment;

public class AttachmentDAO {

	LoadPropertyFile prop = new LoadPropertyFile();

	private static final String SELECT_ALL_ATTACHMENTS_OF_ITEMID = "select * from attachments where itemid=?";
	private static final String INSERT_ATTACHMENT = "INSERT INTO attachments(itemid,filename,description,category,shared,checkedoutby,revision) VALUES (?,?,?,?,?,?,?);";
	private static final String DELETE_ATTACHMENT = "delete from attachments where aid=?;";
	private static final String UPDATE_ATTACHMENT = "update attachments set filename=?,description=?,category=?,shared=?,checkedoutby=?,revision=? where aid=?;";
	

	private static Logger logger = null;

	public AttachmentDAO() {
		logger = Logger.getLogger(AttachmentDAO.class.getName());
	}
	
	protected Connection getConnection() {
		Connection con = null;
		logger.info("Establishing Connection");

		try {
			// loading the driver as a class
			Class.forName(prop.jdbcDriver);
			con = DriverManager.getConnection(prop.jdbcURL,prop.jdbcName,prop.jdbcPassword);
		} catch (ClassNotFoundException e) {
			logger.warn("Exception :: " + e);
		} catch (SQLException e) {
			logger.warn("Exception :: " + e);
		}
		logger.info("Connection Established Successfully");
		return con;
	}
	
	public Attachment insertAttachmentInDb(Attachment attachment) {
		Connection conn = getConnection();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(INSERT_ATTACHMENT);
			preparedStatement.setInt(1, attachment.getItemid());
			preparedStatement.setString(2, attachment.getFilename());
			preparedStatement.setString(3, attachment.getDescription());
			preparedStatement.setString(4, attachment.getCategory());
			preparedStatement.setString(5, attachment.getShared());
			preparedStatement.setString(6, attachment.getCheckedoutby());
			preparedStatement.setInt(7, attachment.getRevision());
			preparedStatement.executeUpdate();
			System.out.println("Inserting :: " + attachment.getItemid() + attachment.getFilename() + attachment.getDescription() + attachment.getCategory() +attachment.getShared()+attachment.getCheckedoutby()+attachment.getRevision() );
		} catch (SQLIntegrityConstraintViolationException e) {
			logger.warn("Exception :: " + e);
		} catch (SQLException e) {
			logger.warn("Exception :: " + e);
		}
		System.out.println(INSERT_ATTACHMENT);
		logger.info("Executing Query :: " + INSERT_ATTACHMENT);
		return attachment;
	}
	
	public List<Attachment> getAllAttachmentsOfItemId(int itemid) {
		Connection conn = getConnection();
		Attachment attachment = null;
		List<Attachment> allAttachments = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_ATTACHMENTS_OF_ITEMID);
			preparedStatement.setInt(1, itemid);
			System.out.println(SELECT_ALL_ATTACHMENTS_OF_ITEMID);
			logger.info("Executing Query :: " + SELECT_ALL_ATTACHMENTS_OF_ITEMID);	
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println(resultSet.toString());		
			while(resultSet.next()) {
				int aid = resultSet.getInt("aid");
				int item_id = resultSet.getInt("itemid");
				String filename = resultSet.getString("filename");
				String description = resultSet.getString("description");
				String category = resultSet.getString("category");
				String shared = resultSet.getString("shared");
				String checkedoutby = resultSet.getString("checkedoutby");
				int revision = resultSet.getInt("revision");
				attachment = new Attachment(aid, item_id, filename, description, category, shared, checkedoutby, revision);
				
				System.out.println(attachment.toString());
				allAttachments.add(attachment);
			}
		} catch (SQLException e) {
			logger.warn("Exception :: " + e);
		}
		return allAttachments;
	}
	
	public boolean updateAttachmentInDb(Attachment attachment) {
		boolean isRowUpdated = false;
		Connection conn = getConnection();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_ATTACHMENT);
			preparedStatement.setString(1, attachment.getFilename());
			preparedStatement.setString(2, attachment.getDescription());
			preparedStatement.setString(3, attachment.getCategory());
			preparedStatement.setString(4, attachment.getShared());
			preparedStatement.setString(5, attachment.getCheckedoutby());
			preparedStatement.setInt(6, attachment.getRevision());
			preparedStatement.setInt(7, attachment.getAid());
			int rowCount = preparedStatement.executeUpdate();
			logger.info("Executing Query :: " + UPDATE_ATTACHMENT);	
			if (rowCount > 0)
				isRowUpdated = true;
		} catch (SQLException e) {
			logger.warn("Exception :: " + e);
		}
		return isRowUpdated;
	}
	
	public boolean deleteAttachmentFromDb(int aid) {
		boolean isRowDeleted = false;
		Connection conn = getConnection();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(DELETE_ATTACHMENT);
			preparedStatement.setInt(1, aid);
			int rowCount = preparedStatement.executeUpdate();
			logger.info("Executing Query :: " + DELETE_ATTACHMENT);	
			if (rowCount > 0)
				isRowDeleted = true;
		} catch (SQLException e) {
			logger.warn("Exception :: " + e);
		}
		return isRowDeleted;
	}
	
}

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
import io.gosaas.assessment.models.Structure;

public class StructureDAO {

	LoadPropertyFile prop = new LoadPropertyFile();

	private static final String SELECT_ALL_STRUCTURES_OF_ITEMID = "select * from structures where sitemid=?";
	private static final String INSERT_STRUCTURE = "INSERT INTO structures(Class,description,lifecyclephase,createdby,sitemid) VALUES (?,?,?,?,?);";
	private static final String DELETE_STRUCTURE = "delete from structures where sid=?;";
	private static final String UPDATE_STRUCTURE = "update structures set Class=?,description=?,lifecyclephase=?,createdby=? where sid=?;";
	

	private static Logger logger = null;

	public StructureDAO() {
		logger = Logger.getLogger(StructureDAO.class.getName());
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
	
	public Structure insertStructureInDb(Structure structure) {
		Connection conn = getConnection();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(INSERT_STRUCTURE);
			preparedStatement.setString(1, structure.getclass());
			preparedStatement.setString(2, structure.getDescription());
			preparedStatement.setString(3, structure.getLifecyclephase());
			preparedStatement.setString(4, structure.getCreatedby());
			preparedStatement.setInt(5, structure.getSitemid());
			preparedStatement.executeUpdate();
			System.out.println("Inserting :: " + structure.getclass() + structure.getDescription() + structure.getLifecyclephase() + structure.getCreatedby() + structure.getSitemid());
		} catch (SQLIntegrityConstraintViolationException e) {
			logger.warn("Exception :: " + e);
		} catch (SQLException e) {
			logger.warn("Exception :: " + e);
		}
		System.out.println(INSERT_STRUCTURE);
		logger.info("Executing Query :: " + INSERT_STRUCTURE);
		return structure;
	}
	
	public List<Structure> getAllStructuresOfItemId(int sitemid) {
		Connection conn = getConnection();
		Structure structure = null;
		List<Structure> allStructures = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_STRUCTURES_OF_ITEMID);
			preparedStatement.setInt(1, sitemid);
			System.out.println(SELECT_ALL_STRUCTURES_OF_ITEMID);
			logger.info("Executing Query :: " + SELECT_ALL_STRUCTURES_OF_ITEMID);	
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println(resultSet.toString());		
			while(resultSet.next()) {
				String Class = resultSet.getString("Class");
				String description = resultSet.getString("description");
				String lifecyclephase = resultSet.getString("lifecyclephase");
				String createdby = resultSet.getString("createdby");
				int sid = resultSet.getInt("sid");
				int s_itemid = resultSet.getInt("sitemid");
				structure = new Structure(sid, s_itemid, Class, description, lifecyclephase, createdby);
				
				System.out.println(structure.toString());
				allStructures.add(structure);
			}
		} catch (SQLException e) {
			logger.warn("Exception :: " + e);
		}
		return allStructures;
	}
	
	public boolean updateStructureInDb(Structure structure) {
		boolean isRowUpdated = false;
		Connection conn = getConnection();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_STRUCTURE);
			preparedStatement.setString(1, structure.getclass());
			preparedStatement.setString(2, structure.getDescription());
			preparedStatement.setString(3, structure.getLifecyclephase());
			preparedStatement.setString(4, structure.getCreatedby());
			preparedStatement.setInt(5, structure.getSid());
			int rowCount = preparedStatement.executeUpdate();
			logger.info("Executing Query :: " + UPDATE_STRUCTURE);	
			if (rowCount > 0)
				isRowUpdated = true;
		} catch (SQLException e) {
			logger.warn("Exception :: " + e);
		}
		return isRowUpdated;
	}
	
	public boolean deleteStructureFromDb(int sid) {
		boolean isRowDeleted = false;
		Connection conn = getConnection();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(DELETE_STRUCTURE);
			preparedStatement.setInt(1, sid);
			int rowCount = preparedStatement.executeUpdate();
			logger.info("Executing Query :: " + DELETE_STRUCTURE);	
			if (rowCount > 0)
				isRowDeleted = true;
		} catch (SQLException e) {
			logger.warn("Exception :: " + e);
		}
		return isRowDeleted;
	}
	
}

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
import io.gosaas.assessment.models.AML;

public class AMLDAO {

	LoadPropertyFile prop = new LoadPropertyFile();

	private static final String SELECT_ALL_AMLS_OF_ITEMID = "select * from aml where mitemid=?";
	private static final String INSERT_AML = "INSERT INTO aml(mpart,manufacturer,registryid,amlstatus,description,mstatus,mitemid) VALUES (?,?,?,?,?,?,?);";
	private static final String DELETE_AML = "delete from aml where mid=?;";
	private static final String UPDATE_AML = "update aml set mpart=?,manufacturer=?,registryid=?,amlstatus=?,description=?,mstatus=? where mid=?;";

	private static Logger logger = null;

	public AMLDAO() {
		logger = Logger.getLogger(AMLDAO.class.getName());
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
	
	public AML insertAMLInDb(AML aml) {
		Connection conn = getConnection();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(INSERT_AML);
			preparedStatement.setString(1, aml.getMpart());
			preparedStatement.setString(2, aml.getManufacturer());
			preparedStatement.setInt(3, aml.getRegistryid());
			preparedStatement.setString(4, aml.getAmlstatus());
			preparedStatement.setString(5, aml.getDescription());
			preparedStatement.setString(6, aml.getMstatus());
			preparedStatement.setInt(7, aml.getMitemid());
			preparedStatement.executeUpdate();

			System.out.println("Inserting :: " + aml.getMitemid() + aml.getMpart() + aml.getManufacturer() + aml.getRegistryid() + aml.getAmlstatus() + aml.getDescription()+aml.getMstatus());
		} catch (SQLIntegrityConstraintViolationException e) {
			logger.warn("Exception :: " + e);
		} catch (SQLException e) {
			logger.warn("Exception :: " + e);
		}
		System.out.println(INSERT_AML);
		logger.info("Executing Query :: " + INSERT_AML);
		return aml;
	}
	
	public List<AML> getAllAMLsOfItemId(int mitemid) {
		Connection conn = getConnection();
		AML aml = null;
		List<AML> allAMLs = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_AMLS_OF_ITEMID);
			preparedStatement.setInt(1, mitemid);
			System.out.println(SELECT_ALL_AMLS_OF_ITEMID);
			logger.info("Executing Query :: " + SELECT_ALL_AMLS_OF_ITEMID);	
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println(resultSet.toString());		
			while(resultSet.next()) {
				String description = resultSet.getString("description");
				int registryid = resultSet.getInt("registryid");
				String mpart = resultSet.getString("mpart");
				String manufacturer = resultSet.getString("manufacturer");
				String amlstatus = resultSet.getString("amlstatus");
				String mstatus = resultSet.getString("mstatus");
				int mid = resultSet.getInt("mid");
				int m_itemid = resultSet.getInt("mitemid");
				aml = new AML(mid, m_itemid, mpart, manufacturer, registryid, amlstatus, description, mstatus);
				
				System.out.println(aml.toString());
				allAMLs.add(aml);
			}
		} catch (SQLException e) {
			logger.warn("Exception :: " + e);
		}
		return allAMLs;
	}
	
	public boolean updateAMLInDb(AML aml) {
		boolean isRowUpdated = false;
		Connection conn = getConnection();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_AML);
			preparedStatement.setString(1, aml.getMpart());
			preparedStatement.setString(2, aml.getManufacturer());
			preparedStatement.setInt(3, aml.getRegistryid());
			preparedStatement.setString(4, aml.getAmlstatus());
			preparedStatement.setString(5, aml.getDescription());
			preparedStatement.setString(6, aml.getMstatus());
			preparedStatement.setInt(7, aml.getMid());
			int rowCount = preparedStatement.executeUpdate();
			logger.info("Executing Query :: " + UPDATE_AML);	
			if (rowCount > 0)
				isRowUpdated = true;
		} catch (SQLException e) {
			logger.warn("Exception :: " + e);
		}
		return isRowUpdated;
	}
	
	public boolean deleteAMLFromDb(int mid) {
		boolean isRowDeleted = false;
		Connection conn = getConnection();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(DELETE_AML);
			preparedStatement.setInt(1, mid);
			int rowCount = preparedStatement.executeUpdate();
			logger.info("Executing Query :: " + DELETE_AML);	
			if (rowCount > 0)
				isRowDeleted = true;
		} catch (SQLException e) {
			logger.warn("Exception :: " + e);
		}
		return isRowDeleted;
	}

}

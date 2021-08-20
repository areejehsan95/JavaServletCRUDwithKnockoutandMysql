package io.gosaas.assessment.databaseAccessObject;

import java.io.IOException;
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
import io.gosaas.assessment.models.Item;

public class ItemDAO {

	LoadPropertyFile prop = new LoadPropertyFile();
	
	private static final String SELECT_ALL_ITEMS = "select * from items";
	private static final String SELECT_ITEM_BY_ID = "select id,class,description from items where id=?;";
	private static final String INSERT_ITEM = "INSERT INTO items (class,description) VALUES (?,?);";
	private static final String DELETE_ITEM = "delete from items where id=?;";
	private static final String UPDATE_ITEM = "update items set class=?,description=? where id=?;";

	private static Logger logger = null;

	public ItemDAO() {
		logger = Logger.getLogger(ItemDAO.class.getName());
	}
	
	protected Connection getConnection() {
		Connection con = null;
		logger.info("Establishing Connection");

		try {
			// loading the driver as a class
			System.out.println(prop.jdbcDriver);
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
	
	public Item insertItemInDb(Item item) {
		Connection conn = getConnection();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(INSERT_ITEM);
			preparedStatement.setString(1, item.getclass());
			preparedStatement.setString(2, item.getDescription());
			preparedStatement.executeUpdate();
			System.out.println("Inserting :: " + item.getclass() + item.getDescription());
		} catch (SQLIntegrityConstraintViolationException e) {
			logger.warn("Exception :: " + e);
		} catch (SQLException e) {
			logger.warn("Exception :: " + e);
		}
		System.out.println(INSERT_ITEM);
		logger.info("Executing Query :: " + INSERT_ITEM);
		return item;
	}
	
	public Item findItemById(int id) {
		Connection conn = getConnection();
		Item item = null;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ITEM_BY_ID);
			preparedStatement.setInt(1, id);
			// execute or update the query --> ResultSet
			ResultSet resultSet = preparedStatement.executeQuery();
			//process the ResultSet object
			logger.info("Executing Query :: " + SELECT_ITEM_BY_ID);	
			while(resultSet.next()) {
				String Class = resultSet.getString("class");
				String description = resultSet.getString("description");
				item = new Item(id,Class,description);
			}
		} catch (SQLException e) {
			logger.warn("Exception :: " + e);
		}
		return item;
	}
	
	public List<Item> getAllItems() {
		Connection conn = getConnection();
		Item item = null;
		List<Item> allItems = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_ITEMS);
			System.out.println(SELECT_ALL_ITEMS);
			logger.info("Executing Query :: " + SELECT_ALL_ITEMS);	
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println(resultSet.toString());		
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String Class = resultSet.getString("class");
				String description = resultSet.getString("description");
				item = new Item(id,Class,description);
				System.out.println(item.toString());
				allItems.add(item);
			}
		} catch (SQLException e) {
			logger.warn("Exception :: " + e);
		}
		return allItems;
	}
	
	public boolean updateItemInDb(Item item) {
		boolean isRowUpdated = false;
		Connection conn = getConnection();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_ITEM);
			preparedStatement.setString(1, item.getclass());
			preparedStatement.setString(2, item.getDescription());
			preparedStatement.setInt(3, item.getId());
			int rowCount = preparedStatement.executeUpdate();
			logger.info("Executing Query :: " + UPDATE_ITEM);	
			if (rowCount > 0)
				isRowUpdated = true;
		} catch (SQLException e) {
			logger.warn("Exception :: " + e);
		}
		return isRowUpdated;
	}
	
	public boolean deleteItemFromDb(int id) {
		boolean isRowDeleted = false;
		Connection conn = getConnection();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(DELETE_ITEM);
			preparedStatement.setInt(1, id);
			int rowCount = preparedStatement.executeUpdate();
			logger.info("Executing Query :: " + DELETE_ITEM);	
			if (rowCount > 0)
				isRowDeleted = true;
		} catch (SQLException e) {
			logger.warn("Exception :: " + e);
		}
		return isRowDeleted;
	}
	
}

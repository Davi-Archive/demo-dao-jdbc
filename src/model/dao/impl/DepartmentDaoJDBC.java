package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement("INSERT INTO department(Name) VALUES (?)",
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, obj.getName());

			int rowsAffected = statement.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
					int id = resultSet.getInt(1);
					obj.setId(id);
				}

				DB.closeResultSet(resultSet);

			} else {
				throw new DbException("Error while creating an Department.");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(statement);
		}

	}

	@Override
	public void update(Department obj) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement("UPDATE department SET Name = ? WHERE Id = ?",
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, obj.getName());
			preparedStatement.setInt(2, obj.getId());

			preparedStatement.executeUpdate();

			System.out.println(obj.getName() + " Successfully edited");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {

		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = conn.prepareStatement("DELETE FROM department WHERE Id = ? ");
			preparedStatement.setInt(1, id);

			int rowsAffected = preparedStatement.executeUpdate();

			System.out.println(rowsAffected);

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Department department = null;
		try {
			preparedStatement = conn.prepareStatement("SELECT * FROM department WHERE Id = ? ");

			preparedStatement.setInt(1, id);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				department = new Department(resultSet.getInt("Id"), resultSet.getString("Name"));
			}

			return department;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

	@Override
	public List<Department> findAll() {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = conn.prepareStatement("SELECT * FROM department");

			resultSet = preparedStatement.executeQuery();

			List<Department> depList = new ArrayList<>();

			while (resultSet.next()) {

				Department department = new Department(resultSet.getInt("Id"), resultSet.getString("Name"));

				depList.add(department);

			}

			return depList;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

}

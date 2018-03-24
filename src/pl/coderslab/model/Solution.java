package pl.coderslab.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Solution {
	private int id;
	private Date created;
	private Date updated;
	private String description;
	private int exercise_id;
	private int user_id;

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getExercise_id() {
		return exercise_id;
	}

	public void setExercise_id(int exercise_id) {
		this.exercise_id = exercise_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getId() {
		return id;
	}

	
	public Solution[] loadAllByUserId(Connection conn, int id) throws SQLException{
		ArrayList<Solution> solutions = new ArrayList<>();
		String sql = "SELECT * FROM Solutions WHERE user_id = ?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = rs.getInt("id");
			loadedSolution.created = rs.getDate("created");
			loadedSolution.updated = rs.getDate("updated");
			loadedSolution.description = rs.getString("description");
			loadedSolution.exercise_id = rs.getInt("exercise_id");
			loadedSolution.user_id = rs.getInt("user_id");
			solutions.add(loadedSolution);
		}
		Solution[] result = new Solution[solutions.size()];
		result = solutions.toArray(result);
		return result;
	}
	public Solution[] loadAllByExerciseId (Connection conn, int id) throws SQLException {
		ArrayList<Solution> solutions = new ArrayList<>();
		String sql = "SELECT * FROM solutions WHERE exercise_id = ? ORDER BY created ASC";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = rs.getInt("id");
			loadedSolution.created = rs.getDate("created");
			loadedSolution.updated = rs.getDate("updated");
			loadedSolution.description = rs.getString("description");
			loadedSolution.exercise_id = rs.getInt("exercise_id");
			loadedSolution.user_id = rs.getInt("user_id");
			solutions.add(loadedSolution);
		}
		Solution[] result = new Solution[solutions.size()];
		result = solutions.toArray(result);
		return result;
	}

	public void saveToDB(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO Solutions(created, updated, description, exercise_id, user_id) VALUES ?, ?, ?, ?, ?";
			String generatedColumns[] = { "id" };
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setDate(1, this.created);
			preparedStatement.setDate(2, this.updated);
			preparedStatement.setString(3, this.description);
			preparedStatement.setInt(4, this.exercise_id);
			preparedStatement.setInt(5, this.user_id);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt("id");
			}
		} else {
			String sql = "UPDATE Solutions	SET	created=?, updated=?, description=?, exercise_id=?, user_id=?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setDate(1, this.created);
			preparedStatement.setDate(2, this.updated);
			preparedStatement.setString(3, this.description);
			preparedStatement.setInt(4, this.exercise_id);
			preparedStatement.setInt(5, this.user_id);
			preparedStatement.executeUpdate();
		}
	}

	static public Solution loadById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM Solutions where id = ?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.created = resultSet.getDate("created");
			loadedSolution.updated = resultSet.getDate("updated");
			loadedSolution.description = resultSet.getString("description");
			loadedSolution.exercise_id = resultSet.getInt("exercise_id");
			loadedSolution.user_id = resultSet.getInt("user_id");
			return loadedSolution;
		}
		return null;
	}

	static public Solution[] loadAll(Connection conn) throws SQLException {
		ArrayList<Solution> Solutions = new ArrayList<>();
		String sql = "SELECT * FROM	Solutions";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.created = resultSet.getDate("created");
			loadedSolution.updated = resultSet.getDate("updated");
			loadedSolution.description = resultSet.getString("description");
			loadedSolution.exercise_id = resultSet.getInt("exercise_id");
			loadedSolution.user_id = resultSet.getInt("user_id");
			Solutions.add(loadedSolution);
		}
		Solution[] gArray = new Solution[Solutions.size()];
		gArray = Solutions.toArray(gArray);
		return gArray;
	}

	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM Solutions WHERE id = ?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, this.id);
			preparedStatement.executeUpdate();
			this.id = 0;
		}
	}
}

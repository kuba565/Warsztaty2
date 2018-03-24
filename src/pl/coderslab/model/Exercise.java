package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Exercise {
	private int id;
	private String title;
	private String description;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void saveToDB(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO Exercises(name) VALUES (?, ?, ?)";
			String generatedColumns[] = { "id" };
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, this.title);
			preparedStatement.setString(2, this.description);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt("id");
			}
		} else {
			String sql = "UPDATE Exercises	SET	title = ?, description = ?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, this.title);
			preparedStatement.setString(2, this.description);
			preparedStatement.executeUpdate();
		}
	}

	static public Exercise loadById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM Exercises where id = ?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			Exercise loadedExercise = new Exercise();
			loadedExercise.title = resultSet.getString("title");
			loadedExercise.id = resultSet.getInt("id");
			loadedExercise.description = resultSet.getString("description");
			return loadedExercise;
		}
		return null;
	}

	static public Exercise[] loadAll(Connection conn) throws SQLException {
		ArrayList<Exercise> Exercises = new ArrayList<>();
		String sql = "SELECT * FROM	Exercises";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Exercise loadedExercise = new Exercise();
			loadedExercise.title = resultSet.getString("title");
			loadedExercise.id = resultSet.getInt("id");
			loadedExercise.description = resultSet.getString("description");
			Exercises.add(loadedExercise);
		}
		Exercise[] eArray = new Exercise[Exercises.size()];
		eArray = Exercises.toArray(eArray);
		return eArray;
	}

	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM Exercises WHERE id = ?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, this.id);
			preparedStatement.executeUpdate();
			this.id = 0;
		}
	}
}

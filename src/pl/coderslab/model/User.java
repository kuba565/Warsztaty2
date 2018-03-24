package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
	private int id;
	private String username;
	private String email;
	private String password;
	private int userGroupId;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(int userGroupId) {
		this.userGroupId = userGroupId;
	}

	public int getId() {
		return id;
	}

	public void saveToDB(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO Users(username, email, password, userGroupId) VALUES (?, ?, ?, ?)";
			String generatedColumns[] = { "id" };
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, this.username);
			preparedStatement.setString(2, this.email);
			preparedStatement.setString(3, this.password);
			preparedStatement.setInt(4, this.userGroupId);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt("id");
			}
		} else {
			String sql = "UPDATE Users	SET	username = ?, email = ?, password = ?, userGroupId = ?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, this.username);
			preparedStatement.setString(2, this.email);
			preparedStatement.setString(3, this.password);
			preparedStatement.setInt(4, this.userGroupId);
			preparedStatement.executeUpdate();
		}
	}

	static public User loadById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM Users where id = ?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			User loadedUser = new User();
			loadedUser.id = resultSet.getInt("id");
			loadedUser.username = resultSet.getString("username");
			loadedUser.email = resultSet.getString("email");
			loadedUser.password = resultSet.getString("password");
			loadedUser.userGroupId = resultSet.getInt("userGroupId");
			return loadedUser;
		}
		return null;
	}

	static public User[] loadAllByGroupId(Connection conn, int id) throws SQLException {
		ArrayList<User> Users = new ArrayList<>();
		String sql = "SELECT * FROM	Users WHERE userGroupId = ?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			User loadedUser = new User();
			loadedUser.id = resultSet.getInt("id");
			loadedUser.username = resultSet.getString("username");
			loadedUser.email = resultSet.getString("email");
			loadedUser.password = resultSet.getString("password");
			loadedUser.userGroupId = resultSet.getInt("userGroupId");
			Users.add(loadedUser);
		}
		User[] uArray = new User[Users.size()];
		uArray = Users.toArray(uArray);
		return uArray;
	}
	static public User[] loadAll(Connection conn) throws SQLException {
		ArrayList<User> Users = new ArrayList<>();
		String sql = "SELECT * FROM	Users";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			User loadedUser = new User();
			loadedUser.id = resultSet.getInt("id");
			loadedUser.username = resultSet.getString("username");
			loadedUser.email = resultSet.getString("email");
			loadedUser.password = resultSet.getString("password");
			loadedUser.userGroupId = resultSet.getInt("userGroupId");
			Users.add(loadedUser);
		}
		User[] uArray = new User[Users.size()];
		uArray = Users.toArray(uArray);
		return uArray;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", userGroupId=" + userGroupId + "]";
	}

	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM Users WHERE id = ?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, this.id);
			preparedStatement.executeUpdate();
			this.id = 0;
		}
	}
}

package pl.coderslab.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InitApp {

	private static final String CREATE_EXERCISES = "CREATE TABLE Exercises"
			+ "("
			+ " id INT,"
			+ " title VARCHAR(255),"
			+ " description TEXT,"
			+ " PRIMARY KEY(id)"
			+ ")";
	
	private static final String CREATE_SOLUTIONS = "CREATE TABLE Solutions"
			+ "("
			+ " id INT,"
			+ " created DATETIME,"
			+ " updated DATETIME,"
			+ " description TEXT,"
			+ " exercise_id INT,"
			+ " user_id BIGINT,"
			+ " PRIMARY KEY (id),"
			+ " FOREIGN KEY (exercise_id) REFERENCES Exercises(id)"
			+ " ON DELETE CASCADE,"
			+ " FOREIGN KEY (user_id) REFERENCES Users(id)"
			+ " ON DELETE CASCADE"
			+ ")";

	private static final String CREATE_USERS_TABLE = "CREATE TABLE Users "
			+ "("
			+ " id BIGINT AUTO_INCREMENT,"
			+ " username VARCHAR(255),"
			+ " email VARCHAR(255) UNIQUE,"
			+ " password VARCHAR (245),"
			+ " user_group_id INT,"
			+ " PRIMARY KEY(id)," 
			+ " FOREIGN KEY (user_group_id) REFERENCES Users_Groups (id)"
			+ " ON DELETE SET NULL"
			+ ")";

	private static final String CREATE_USERS_GROUPS_TABLE = "CREATE TABLE Users_Groups"
			+ " ("
			+ " id INT AUTO_INCREMENT,"
			+ " name VARCHAR (255),"
			+ " PRIMARY KEY (id)"
			+ ")";

	public static void main(String[] args) {
		try (Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/warsztaty?useSSL=false&characterEncoding=utf8", "root", "coderslab")) {
			Statement statement = con.createStatement();

			statement.executeUpdate(CREATE_USERS_GROUPS_TABLE);
			statement.executeUpdate(CREATE_USERS_TABLE);
			statement.executeUpdate(CREATE_EXERCISES);
			statement.executeUpdate(CREATE_SOLUTIONS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

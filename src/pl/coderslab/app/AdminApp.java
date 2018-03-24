package pl.coderslab.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import pl.coderslab.model.User;

public class AdminApp {

	public static void main(String[] args) {
		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/warsztaty?useSSL=false&characterEncoding=utf8", "root", "coderslab")) {
			loadAll(conn);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		Scanner scan = new Scanner(System.in);
		String command = scan.nextLine();
		

	}
	static public String commandInp(String command){
		if (command.equals("add")) {
		} else if (command.equals("edit")) {
			return 
		} else if (command.equals("delete")) {
			
		} else if (command.equals("quit")) {
			
		} else {
			return "wrong command";
		}
	}

	static public void loadAll(Connection conn) throws SQLException {
		User[] uArray = User.loadAll(conn);
		for (int i = 0; i < uArray.length; i++) {
			System.out.println(uArray[i].toString());
		}
	}

}

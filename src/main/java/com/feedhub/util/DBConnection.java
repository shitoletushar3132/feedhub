package com.feedhub.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/feedhub";
	private static final String USER = "root";
	private static final String PASS = "root";

	public static void initializeDatabase(){
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS); Statement stmt = conn.createStatement()) {

			String createRolesTable = "CREATE TABLE IF NOT EXISTS roles ("
			        + "id INT AUTO_INCREMENT PRIMARY KEY, "
			        + "name VARCHAR(20) NOT NULL UNIQUE"
			        + ");";

			String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("
			        + "id INT AUTO_INCREMENT PRIMARY KEY, "
			        + "username VARCHAR(50) NOT NULL UNIQUE, "
			        + "email VARCHAR(100) NOT NULL UNIQUE, "
			        + "password VARCHAR(100) NOT NULL, "
			        + "role_id INT, "
			        + "FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE SET NULL"
			        + ");";

			String createTeachersTable = "CREATE TABLE IF NOT EXISTS teachers ("
			        + "id INT AUTO_INCREMENT PRIMARY KEY, "
			        + "user_id INT NOT NULL UNIQUE, "
			        + "name VARCHAR(100) NOT NULL, "
			        + "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE"
			        + ");";

			String createStudentsTable = "CREATE TABLE IF NOT EXISTS students ("
			        + "id INT AUTO_INCREMENT PRIMARY KEY, "
			        + "user_id INT NOT NULL UNIQUE, "
			        + "name VARCHAR(100) NOT NULL, "
			        + "roll_no VARCHAR(50) UNIQUE NOT NULL, "
			        + "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE"
			        + ");";

			String createSubjectsTable = "CREATE TABLE IF NOT EXISTS subjects ("
			        + "id INT AUTO_INCREMENT PRIMARY KEY, "
			        + "name VARCHAR(100) NOT NULL UNIQUE"
			        + ");";

			String createTeacherSubjectTable = "CREATE TABLE IF NOT EXISTS teacher_subject ("
			        + "teacher_id INT NOT NULL, "
			        + "subject_id INT NOT NULL, "
			        + "PRIMARY KEY (teacher_id, subject_id), "
			        + "FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE, "
			        + "FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE"
			        + ");";

			String createFeedbackTable = "CREATE TABLE IF NOT EXISTS feedback ("
			        + "id INT AUTO_INCREMENT PRIMARY KEY, "
			        + "student_id INT NOT NULL, "
			        + "teacher_id INT NOT NULL, "
			        + "subject_id INT NOT NULL, "
			        + "comment TEXT NOT NULL, "
			        + "rating INT CHECK (rating BETWEEN 1 AND 5), "
			        + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
			        + "FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE, "
			        + "FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE, "
			        + "FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE"
			        + ");";


			// Execute all creation statements
			stmt.execute(createRolesTable);
			stmt.execute(createUsersTable);
			stmt.execute(createTeachersTable);
			stmt.execute(createStudentsTable);
			stmt.execute(createSubjectsTable);
			stmt.execute(createTeacherSubjectTable);
			stmt.execute(createFeedbackTable);

			System.out.println("All tables created successfully (if they didn't already exist).");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		initializeDatabase();
		return DriverManager.getConnection(URL, USER, PASS);
	}
}

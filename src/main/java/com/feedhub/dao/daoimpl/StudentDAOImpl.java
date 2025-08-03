package com.feedhub.dao.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.feedhub.dao.daoInterfaces.StudentDAO;
import com.feedhub.model.Role;
import com.feedhub.model.Student;
import com.feedhub.model.User;

public class StudentDAOImpl implements StudentDAO {

	private Connection conn;

	public StudentDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean addStudent(Student student) {
		String sql = "INSERT INTO students (user_id, name, roll_no) VALUES (?, ?, ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, student.getUser().getId());
			pstmt.setString(2, student.getName());
			pstmt.setString(3, student.getRollNo());

			int rs = pstmt.executeUpdate();
			return rs > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public Student getStudentById(int id) {
		String sql = "SELECT " + "s.name AS student_name, s.roll_no, s.id AS studentId, "
				+ "u.id AS userId, u.username, u.email, " + "r.id AS roleId, r.name AS role_name " + "FROM students s "
				+ "JOIN users u ON s.user_id = u.id " + "JOIN roles r ON u.role_id = r.id " + "WHERE s.id = ?";

		Student student = null;

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int studentId = rs.getInt("studentId");
				String studentName = rs.getString("student_name");
				String rollNo = rs.getString("roll_no");
				int userId = rs.getInt("userId");
				String username = rs.getString("username");
				String email = rs.getString("email");
				int roleId = rs.getInt("roleId");
				String roleName = rs.getString("role_name");

				Role role = new Role(roleId, roleName);
				User user = new User(userId, username, email, null, role);
				student = new Student(studentId, user, studentName, rollNo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return student;
	}

	@Override
	public Student getStudentByRollNo(String rollNo) {
		String sql = "SELECT " + "s.name AS student_name, s.roll_no, s.id AS studentId, "
				+ "u.id AS userId, u.username, u.email, " + "r.id AS roleId, r.name AS role_name " + "FROM students s "
				+ "JOIN users u ON s.user_id = u.id " + "JOIN roles r ON u.role_id = r.id " + "WHERE s.roll_no = ?";

		Student student = null;

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, rollNo);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int studentId = rs.getInt("studentId");
				String studentName = rs.getString("student_name");
				int userId = rs.getInt("userId");
				String username = rs.getString("username");
				String email = rs.getString("email");
				int roleId = rs.getInt("roleId");
				String roleName = rs.getString("role_name");

				Role role = new Role(roleId, roleName);
				User user = new User(userId, username, email, null, role);
				student = new Student(studentId, user, studentName, rollNo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return student;
	}

	@Override
	public List<Student> getAllStudents() {
		String sql = "SELECT " + "s.name AS student_name, s.roll_no, s.id AS studentId, "
				+ "u.id AS userId, u.username, u.email, " + "r.id AS roleId, r.name AS role_name " + "FROM students s "
				+ "JOIN users u ON s.user_id = u.id " + "JOIN roles r ON u.role_id = r.id";

		List<Student> students = new ArrayList<>();

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int studentId = rs.getInt("studentId");
				String studentName = rs.getString("student_name");
				String rollNo = rs.getString("roll_no");
				int userId = rs.getInt("userId");
				String username = rs.getString("username");
				String email = rs.getString("email");
				int roleId = rs.getInt("roleId");
				String roleName = rs.getString("role_name");

				Role role = new Role(roleId, roleName);
				User user = new User(userId, username, email, null, role);
				Student student = new Student(studentId, user, studentName, rollNo);
				students.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return students;
	}

	@Override
	public boolean updateStudent(Student student) {
		String fetchUserIdSql = "SELECT user_id FROM students WHERE id = ?";
		String updateStudentSql = "UPDATE students SET name = ?, roll_no = ? WHERE id = ?";
		String updateUserSql = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";

		try (PreparedStatement fetchStmt = conn.prepareStatement(fetchUserIdSql)) {
			fetchStmt.setInt(1, student.getId());
			ResultSet rs = fetchStmt.executeQuery();

			if (rs.next()) {
				int userId = rs.getInt("user_id");

				// Start transaction
				conn.setAutoCommit(false);

				// 1. Update student info
				try (PreparedStatement updateStudentStmt = conn.prepareStatement(updateStudentSql)) {
					updateStudentStmt.setString(1, student.getName());
					updateStudentStmt.setString(2, student.getRollNo());
					updateStudentStmt.setInt(3, student.getId());
					updateStudentStmt.executeUpdate();
				}

				// 2. Update user info
				try (PreparedStatement updateUserStmt = conn.prepareStatement(updateUserSql)) {
					updateUserStmt.setString(1, student.getUser().getUsername());
					updateUserStmt.setString(2, student.getUser().getEmail());
					updateUserStmt.setString(3, student.getUser().getPassword());
					updateUserStmt.setInt(4, userId);
					updateUserStmt.executeUpdate();
				}

				// Commit transaction
				conn.commit();
				conn.setAutoCommit(true);

				return true;

			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback(); // rollback in case of error
				conn.setAutoCommit(true);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return false;
	}

	@Override
	public boolean deleteStudent(int studentId) {
		String getUserIdQuery = "SELECT user_id FROM students WHERE id = ?";
		String deleteUserQuery = "DELETE FROM users WHERE id = ?";

		try (PreparedStatement getUserStmt = conn.prepareStatement(getUserIdQuery);) {
			getUserStmt.setInt(1, studentId);
			ResultSet rs = getUserStmt.executeQuery();

			if (rs.next()) {
				int userId = rs.getInt("user_id");

				try (PreparedStatement deleteUserStmt = conn.prepareStatement(deleteUserQuery)) {
					deleteUserStmt.setInt(1, userId);
					int rows = deleteUserStmt.executeUpdate();
					return rows > 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

}

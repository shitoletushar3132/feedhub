package com.feedhub.dao.daoimpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.feedhub.dao.daoInterfaces.TeacherDAO;
import com.feedhub.model.Role;
import com.feedhub.model.Teacher;
import com.feedhub.model.User;

public class TeacherDAOImpl implements TeacherDAO {

	private final Connection conn;

	public TeacherDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean addTeacher(Teacher teacher) {
		String sql = "INSERT INTO teachers (user_id, name) VALUES (?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, teacher.getUser().getId());
			ps.setString(2, teacher.getName());
			int rs = ps.executeUpdate();
			return rs > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Teacher getTeacherById(int id) {
		System.out.println("id" + id);
		String sql = "SELECT t.id AS teacher_id, t.name AS teacher_name, "
		           + "u.id AS user_id, u.email, u.role_id, "
		           + "u.username as username,"
		           + "r.name AS role_name "
		           + "FROM teachers AS t "
		           + "JOIN users AS u ON t.user_id = u.id "
		           + "JOIN roles AS r ON u.role_id = r.id "
		           + "WHERE t.id = ?";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Teacher t = new Teacher();
				t.setId(rs.getInt("teacher_id"));
				t.setName(rs.getString("teacher_name"));

				Role role = new Role();
				role.setId(rs.getInt("role_id"));
				role.setName(rs.getString("role_name"));

				User user = new User();
				user.setId(rs.getInt("user_id"));
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setRole(role);

				t.setUser(user);

				System.out.println(t);
				
				return t;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Teacher getTeacherByName(String name) {
		String sql = "SELECT * FROM teachers AS t JOIN users as u ON t.user_id = u.id WHERE name = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Teacher t = new Teacher();
				t.setId(rs.getInt("id"));
				t.setName(rs.getString("name"));
				User user = new User();
				user.setId(rs.getInt("user_id"));
				user.setEmail(rs.getString("email"));
				t.setUser(user);
				return t;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Teacher> getAllTeachers() {
		List<Teacher> teachers = new ArrayList<>();
		String sql = "SELECT * FROM teachers AS t JOIN users AS u ON u.id = t.user_id";
		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				Teacher t = new Teacher();
				t.setId(rs.getInt("id"));
				t.setName(rs.getString("name"));
				User user = new User();
				user.setId(rs.getInt("user_id"));
				t.setUser(user);
				user.setEmail(rs.getString("email"));
				teachers.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teachers;
	}

	@Override
	public boolean updateTeacher(Teacher teacher) {
		String sql = "UPDATE teachers SET user_id = ?, name = ? WHERE id = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, teacher.getUser().getId());
			ps.setString(2, teacher.getName());
			ps.setInt(3, teacher.getId());
			int rs = ps.executeUpdate();
			return rs > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteTeacher(int id) {
		String getUserIdSql = "SELECT user_id FROM teachers WHERE id = ?";
		String deleteUserSql = "DELETE FROM users WHERE id = ?";

		try (PreparedStatement ps1 = conn.prepareStatement(getUserIdSql)) {
			ps1.setInt(1, id);
			ResultSet rs = ps1.executeQuery();
			if (rs.next()) {
				int userId = rs.getInt("user_id");

				try (PreparedStatement ps2 = conn.prepareStatement(deleteUserSql)) {
					ps2.setInt(1, userId);
					int rowsAffected = ps2.executeUpdate();
					return rowsAffected > 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

}

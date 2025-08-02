package com.feedhub.dao.daoimpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.feedhub.dao.daoInterfaces.TeacherDAO;
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
		String sql = "SELECT * FROM teachers WHERE id = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Teacher t = new Teacher();
				t.setId(rs.getInt("id"));
				t.setName(rs.getString("name"));
				User user = new User();
				user.setId(rs.getInt("user_id"));
				t.setUser(user);
				return t;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Teacher getTeacherByName(String name) {
		String sql = "SELECT * FROM teachers WHERE name = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Teacher t = new Teacher();
				t.setId(rs.getInt("id"));
				t.setName(rs.getString("name"));
				User user = new User();
				user.setId(rs.getInt("user_id"));
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
		String sql = "SELECT * FROM teachers";
		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				Teacher t = new Teacher();
				t.setId(rs.getInt("id"));
				t.setName(rs.getString("name"));
				User user = new User();
				user.setId(rs.getInt("user_id"));
				t.setUser(user);
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
		String sql = "DELETE FROM teachers WHERE id = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			int rs = ps.executeUpdate();
			return rs > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}

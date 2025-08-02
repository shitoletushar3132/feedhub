package com.feedhub.dao.daoimpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.feedhub.dao.daoInterfaces.Teacher_SubjectDAO;
import com.feedhub.model.TeacherSubjectDTO;

public class Teacher_SubjectDAOImpl implements Teacher_SubjectDAO {

	private final Connection conn;

	public Teacher_SubjectDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean assignTeacherToSubject(int teacherId, int subjectId) {
		String sql = "INSERT INTO teacher_subject (teacher_id, subject_id) VALUES (?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, teacherId);
			stmt.setInt(2, subjectId);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean removeTeacherFromSubject(int teacherId, int subjectId) {
		String sql = "DELETE FROM teacher_subject WHERE teacher_id = ? AND subject_id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, teacherId);
			stmt.setInt(2, subjectId);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<TeacherSubjectDTO> getSubjectsByTeacher(int teacherId) {
		List<TeacherSubjectDTO> list = new ArrayList<>();
		String sql = "SELECT t.teacher_id, t.teacher_name, s.subject_id, s.subject_name " + "FROM teacher_subject ts "
				+ "JOIN teacher t ON ts.teacher_id = t.teacher_id " + "JOIN subject s ON ts.subject_id = s.subject_id "
				+ "WHERE t.teacher_id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, teacherId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(new TeacherSubjectDTO(rs.getInt("teacher_id"), rs.getString("teacher_name"),
						rs.getInt("subject_id"), rs.getString("subject_name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<TeacherSubjectDTO> getTeachersBySubject(int subjectId) {
		List<TeacherSubjectDTO> list = new ArrayList<>();
		String sql = "SELECT t.teacher_id, t.teacher_name, s.subject_id, s.subject_name " + "FROM teacher_subject ts "
				+ "JOIN teacher t ON ts.teacher_id = t.teacher_id " + "JOIN subject s ON ts.subject_id = s.subject_id "
				+ "WHERE s.subject_id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, subjectId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(new TeacherSubjectDTO(rs.getInt("teacher_id"), rs.getString("teacher_name"),
						rs.getInt("subject_id"), rs.getString("subject_name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<TeacherSubjectDTO> getAllTeacherSubjectMappings() {
		List<TeacherSubjectDTO> list = new ArrayList<>();
		String sql = "SELECT t.teacher_id, t.teacher_name, s.subject_id, s.subject_name " + "FROM teacher_subject ts "
				+ "JOIN teacher t ON ts.teacher_id = t.teacher_id " + "JOIN subject s ON ts.subject_id = s.subject_id";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(new TeacherSubjectDTO(rs.getInt("teacher_id"), rs.getString("teacher_name"),
						rs.getInt("subject_id"), rs.getString("subject_name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}

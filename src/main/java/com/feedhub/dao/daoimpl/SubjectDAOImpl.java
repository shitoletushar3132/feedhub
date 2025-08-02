package com.feedhub.dao.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.feedhub.dao.daoInterfaces.SubjectDAO;
import com.feedhub.model.Subject;

public class SubjectDAOImpl implements SubjectDAO {

	Connection conn = null;

	public SubjectDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean addSubject(Subject subject) {
		String sql = "INSERT INTO subjects (name) VALUES (?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, subject.getName());
			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Subject getSubjectById(int id) {
		String sql = "SELECT * FROM subjects WHERE id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Subject subject = new Subject();
				subject.setId(rs.getInt("id"));
				subject.setName(rs.getString("name"));
				return subject;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Subject> getAllSubjects() {
		List<Subject> list = new ArrayList<>();
		String sql = "SELECT * FROM subjects";
		try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				Subject subject = new Subject();
				subject.setId(rs.getInt("id"));
				subject.setName(rs.getString("name"));
				list.add(subject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Subject> getSubjectsByTeacherId(int teacherId) {
		List<Subject> list = new ArrayList<>();
		String sql = "SELECT s.id, s.name FROM subjects s " + "JOIN teacher_subject ts ON s.id = ts.subject_id "
				+ "WHERE ts.teacher_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, teacherId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Subject subject = new Subject();
				subject.setId(rs.getInt("id"));
				subject.setName(rs.getString("name"));
				list.add(subject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean updateSubject(Subject subject) {
		String sql = "UPDATE subjects SET name = ? WHERE id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, subject.getName());
			pstmt.setInt(3, subject.getId());
			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteSubject(int id) {
		String sql = "DELETE FROM subjects WHERE id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}

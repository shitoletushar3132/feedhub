package com.feedhub.dao.daoimpl;

import com.feedhub.dao.daoInterfaces.FeedbackDAO;
import com.feedhub.model.Feedback;
import com.feedhub.model.Student;
import com.feedhub.model.Subject;
import com.feedhub.model.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAOImpl implements FeedbackDAO {

	private Connection conn;

	public FeedbackDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean addFeedback(Feedback feedback) {
		String sql = "INSERT INTO feedback (student_id, teacher_id, subject_id, comment, rating, created_at) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, feedback.getStudent().getId());
			stmt.setInt(2, feedback.getTeacher().getId());
			stmt.setInt(3, feedback.getSubject().getId());
			stmt.setString(4, feedback.getComment());
			stmt.setInt(5, feedback.getRating());
			stmt.setTimestamp(6, feedback.getCreatedAt());

			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Feedback getFeedbackById(int id) {
		String sql = "SELECT * FROM feedback WHERE id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return extractFeedback(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Feedback> getFeedbacksByTeacherId(int teacherId) {
		String sql = "SELECT * FROM feedback WHERE teacher_id = ?";
		List<Feedback> list = new ArrayList<>();
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, teacherId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(extractFeedback(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Feedback> getFeedbacksByStudentId(int studentId) {
		String sql = "SELECT * FROM feedback WHERE student_id = ?";
		List<Feedback> list = new ArrayList<>();
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, studentId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(extractFeedback(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Feedback> getAllFeedbacks() {
		String sql = "SELECT * FROM feedback";
		List<Feedback> list = new ArrayList<>();
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(extractFeedback(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean deleteFeedback(int id) {
		String sql = "DELETE FROM feedback WHERE id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private Feedback extractFeedback(ResultSet rs) throws SQLException {
		Feedback feedback = new Feedback();
		feedback.setId(rs.getInt("id"));

		Student student = new Student();
		student.setId(rs.getInt("student_id"));
		feedback.setStudent(student);

		Teacher teacher = new Teacher();
		teacher.setId(rs.getInt("teacher_id"));
		feedback.setTeacher(teacher);

		Subject subject = new Subject();
		subject.setId(rs.getInt("subject_id"));
		feedback.setSubject(subject);

		feedback.setComment(rs.getString("comment"));
		feedback.setRating(rs.getInt("rating"));
		feedback.setCreatedAt(rs.getTimestamp("created_at"));

		return feedback;
	}
}

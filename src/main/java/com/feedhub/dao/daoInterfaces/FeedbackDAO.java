package com.feedhub.dao.daoInterfaces;

import java.util.List;

import com.feedhub.model.Feedback;

public interface FeedbackDAO {
    boolean addFeedback(Feedback feedback);
    Feedback getFeedbackById(int id);
    List<Feedback> getFeedbacksByTeacherId(int teacherId);
    List<Feedback> getFeedbacksByStudentId(int studentId);
    List<Feedback> getAllFeedbacks();
    boolean deleteFeedback(int id);
}

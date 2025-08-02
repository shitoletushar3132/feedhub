package com.feedhub.model;

import java.sql.Timestamp;

public class Feedback {
    private int id;
    private Student student;
    private Teacher teacher;
    private Subject subject;
    private String comment;
    private int rating;
    private Timestamp createdAt;

    public Feedback() {}
    public Feedback(int id, Student student, Teacher teacher, Subject subject, String comment, int rating, Timestamp createdAt) {
        this.id = id;
        this.student = student;
        this.teacher = teacher;
        this.subject = subject;
        this.comment = comment;
        this.rating = rating;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}

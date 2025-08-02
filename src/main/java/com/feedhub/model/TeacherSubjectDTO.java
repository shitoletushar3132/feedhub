package com.feedhub.model;

public class TeacherSubjectDTO {
	private int teacherId;
	private String teacherName;
	private int subjectId;
	private String subjectName;

	// Constructor
	public TeacherSubjectDTO(int teacherId, String teacherName, int subjectId, String subjectName) {
		this.teacherId = teacherId;
		this.teacherName = teacherName;
		this.subjectId = subjectId;
		this.subjectName = subjectName;
	}

	// Getters & Setters
	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
}

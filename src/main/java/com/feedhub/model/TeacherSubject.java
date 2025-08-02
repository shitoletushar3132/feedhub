package com.feedhub.model;

public class TeacherSubject {
	private Teacher teacher;
	private Subject subject;

	public TeacherSubject() {
	};

	public TeacherSubject(Teacher teacher, Subject subject) {
		this.subject = subject;
		this.teacher = teacher;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

}

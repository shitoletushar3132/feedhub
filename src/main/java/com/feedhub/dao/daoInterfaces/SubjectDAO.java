package com.feedhub.dao.daoInterfaces;

import java.util.List;

import com.feedhub.model.Subject;

public interface SubjectDAO {
	boolean addSubject(Subject subject);

	Subject getSubjectById(int id);

	List<Subject> getAllSubjects();

	List<Subject> getSubjectsByTeacherId(int teacherId);

	boolean updateSubject(Subject subject);

	boolean deleteSubject(int id);
}

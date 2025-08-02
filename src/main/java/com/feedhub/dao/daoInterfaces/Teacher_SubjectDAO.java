package com.feedhub.dao.daoInterfaces;

import java.util.List;
import com.feedhub.model.TeacherSubjectDTO;

public interface Teacher_SubjectDAO {
	boolean assignTeacherToSubject(int teacherId, int subjectId);

	boolean removeTeacherFromSubject(int teacherId, int subjectId);

	List<TeacherSubjectDTO> getSubjectsByTeacher(int teacherId);

	List<TeacherSubjectDTO> getTeachersBySubject(int subjectId);

	List<TeacherSubjectDTO> getAllTeacherSubjectMappings(); // optional, if you want everything
}

package com.feedhub.dao.daoInterfaces;

import java.util.List;

import com.feedhub.model.Teacher;

public interface TeacherDAO {
	boolean addTeacher(Teacher teacher);

	Teacher getTeacherById(int id);

	Teacher getTeacherByName(String name);

	List<Teacher> getAllTeachers();

	boolean updateTeacher(Teacher teacher);

	boolean deleteTeacher(int id);
}

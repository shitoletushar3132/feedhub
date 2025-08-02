package com.feedhub.dao.daoInterfaces;

import java.util.List;

import com.feedhub.model.Role;

public interface RoleDAO {
		boolean addRole(Role role);
	    Role getRoleById(int id);
	    Role getRoleByName(String name);
	    List<Role> getAllRoles();
	    boolean updateRole(Role role);
	    boolean deleteRole(int id);
}

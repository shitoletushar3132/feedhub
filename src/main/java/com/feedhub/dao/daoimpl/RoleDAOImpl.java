package com.feedhub.dao.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.feedhub.dao.daoInterfaces.RoleDAO;
import com.feedhub.model.Role;

public class RoleDAOImpl implements RoleDAO {

	Connection conn;

	public RoleDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean addRole(Role role) {
		String sql = "INSERT INTO roles (name) VAlUES (?)";
		try {

			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, role.getName());
			int rows = pstmt.executeUpdate();

			return rows > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public Role getRoleById(int id) {

		String sql = "SELECT * FROM roles WHERE id = ?";
		Role role = null;

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				role = new Role();
				role.setId(rs.getInt("id"));
				role.setName(rs.getString("name"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return role;

	}

	@Override
	public Role getRoleByName(String name) {
		String sql = "SELECT * FROM roles WHERE name = ?";
		Role role = null;

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				role = new Role();
				role.setId(rs.getInt("id"));
				role.setName(rs.getString("name"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return role;
	}

	@Override
	public List<Role> getAllRoles() {
		String sql = "SELECT * FROM roles";
		List<Role> roles = new ArrayList<>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Role role = new Role(rs.getInt("id"), rs.getString("name"));
				roles.add(role);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return roles;
	}

	@Override
	public boolean updateRole(Role role) {
		String sql = "UPDATE roles SET name= ? where id = ?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, role.getName());
			pstmt.setInt(2, role.getId());
			int rs = pstmt.executeUpdate();

			if (rs > 0) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean deleteRole(int id) {
		String sql = "DELETE FROM roles where id = ?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int rs = pstmt.executeUpdate();
			if (rs > 0) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

}

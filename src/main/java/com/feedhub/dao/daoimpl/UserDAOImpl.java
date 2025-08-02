package com.feedhub.dao.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.feedhub.dao.daoInterfaces.UserDAO;
import com.feedhub.model.Role;
import com.feedhub.model.User;

public class UserDAOImpl implements UserDAO {

//	int id, String username, String email, String password, Role role

	Connection conn;

	public UserDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean addUser(User user) {
		boolean res = false;
		String sql = "INSERT INTO users (userName,email,password,role) VALUES (?,?,?,?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getPassword());
			pstmt.setInt(4, user.getRole().getId());

			int rs = pstmt.executeUpdate();

			if (rs > 0) {
				res = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;

	}

	@Override
	public User getUserById(int id) {

		String sql = "SELECT u.id AS user_id, u.username, u.email, r.id AS role_id, r.name AS role_name "
				+ "FROM users u JOIN roles r ON u.role_id = r.id WHERE u.id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));

				Role role = new Role();
				role.setId(rs.getInt("role_id"));
				role.setName(rs.getString("role_name"));

				user.setRole(role);

				return user;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	@Override
	public User authenticate(String usernameOrEmail, String password) {
		String sql = "SELECT u.id AS user_id, u.username, u.email, u.password, r.id AS role_id, r.name AS role_name "
				+ "FROM users u JOIN roles r ON u.role_id = r.id "
				+ "WHERE (u.username = ? OR u.email = ?) AND u.password = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, usernameOrEmail);
			pstmt.setString(2, usernameOrEmail);
			pstmt.setString(3, password);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));

				Role role = new Role();
				role.setId(rs.getInt("role_id"));
				role.setName(rs.getString("role_name"));
				user.setRole(role);

				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		String sql = "SELECT * FROM users";
		List<User> users = new ArrayList<>();
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));

				Role role = new Role();
				role.setId(rs.getInt("role_id"));
				role.setName(rs.getString("role_name"));
				user.setRole(role);
				users.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return users;
	}

	@Override
	public boolean updateUser(User user) {
		String updateSql = "UPDATE users SET username = ?, email = ?, password = ?, role_id = ? WHERE id = ?";

		boolean res = false;
		try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getPassword());
			pstmt.setInt(4, user.getRole().getId());
			pstmt.setInt(5, user.getId());
			int rows = pstmt.executeUpdate();
			res = rows > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;

	}

	@Override
	public boolean deleteUser(int id) {
		String sql = "DELETE FROM users WHERE id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			int rs = pstmt.executeUpdate();
			if (rs > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

}

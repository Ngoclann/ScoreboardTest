package com.lan.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.lan.model.User;

public class UserDAO {
	private DBContext db;

//	private this.db.getConnection() this.db.getConnection() = this.db.getthis.db.getConnection()();
	
	public UserDAO() throws Exception {
		this.db = DBContext.getInstance();
	}

	public Long createUser(User u) {
		String sql = "insert into user (name, wins_count, loses_count) values (?,?,?)";
		PreparedStatement statement = null;
		ResultSet result = null;
		Long id = null;
		try {
//			this.db.getConnection().setAutoCommit(false);
			statement = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, u.getName());
			statement.setInt(2, u.getWins_count());
			statement.setInt(3, u.getLoses_count());
			statement.executeUpdate();
			result = statement.getGeneratedKeys();
			while(result.next()) {
				id = result.getLong(1);
			}
//			this.db.getConnection().commit();
			return id;
		} catch (SQLException e) {
//			if(this.db.getConnection() != null) {
//				try {
//					this.db.getConnection().rollback();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
			e.printStackTrace();
			return null;
		} finally {
			try {
				if(this.db.getConnection() != null) {
					this.db.getConnection().close();
				} else if(statement != null) {
					statement.close();
				} else if (result != null) {
					result.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	public void updateUser(User u) {
		String sql = "update user" + "set name = ?,wins_count = ?,loses_count = ?" + "where id = ?";
		PreparedStatement statement = null;
		try {
			statement = this.db.getConnection().prepareStatement(sql);
			statement.setString(1, u.getName());
			statement.setInt(2, u.getWins_count());
			statement.setInt(3, u.getLoses_count());
			statement.executeUpdate();
		} catch (SQLException e) {
			if(this.db.getConnection() != null) {
				try {
					this.db.getConnection().rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			try {
				if(this.db.getConnection() != null) {
					this.db.getConnection().close();
				} else if(statement != null) {
					statement.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
	}

	public void deleteUser(Long id) {
		String sql = "delete from user where id = ?";
		PreparedStatement statement = null;
		try {
			statement = this.db.getConnection().prepareStatement(sql);
			statement.setLong(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			if(this.db.getConnection() != null) {
				try {
					this.db.getConnection().rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			try {
				if(this.db.getConnection() != null) {
					this.db.getConnection().close();
				} else if(statement != null) {
					statement.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
	}

	public User getUserbyID(Long id) {
		User user = null;
		String sql = "select* from user where id = ?";
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = this.db.getConnection().prepareStatement(sql);
			statement.setLong(1, id);
			result = statement.executeQuery();
			while (result.next()) {
				user = new User(id, result.getString(1), result.getInt(2), result.getInt(3));
			}
			return user;
		} catch (SQLException e) {
			if(this.db.getConnection() != null) {
				try {
					this.db.getConnection().rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
			return null;
		} finally {
			try {
				if(this.db.getConnection() != null) {
					this.db.getConnection().close();
				} else if(statement != null) {
					statement.close();
				} else if (result != null) {
					result.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	public ArrayList<User> getAllUsers() {
		ArrayList<User> list = new ArrayList<>();
		String sql = "select * from user";
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = this.db.getConnection().prepareStatement(sql);
			rs = st.executeQuery();
			while (rs.next()) {
				list.add(new User(rs.getLong(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}	finally {
			try {
				if(this.db.getConnection() != null) {
					this.db.getConnection().close();
				} else if(st != null) {
					st.close();
				} else if (rs != null) {
					rs.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
}

package com.lan.DAO.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.lan.mapper.IRowMapper;

public class AbstractDAO {
  private Connection getConnection() {
    String driverName = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/ScoreboardTest";
    String user = "root";
    String password = "123456";
    try {
      Class.forName(driverName);
      return DriverManager.getConnection(url, user, password);
    } catch (ClassNotFoundException | SQLException e) {
      Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, e);
      return null;
    }
  }

  private void setParameter(PreparedStatement statement, Object... parameters) {
    try {
      for (int i = 0; i < parameters.length; i++) {
        int index = i + 1;
        Object parameter = parameters[i];
        if (parameter instanceof String) {
          statement.setString(index, (String) parameter);
        } else if (parameter instanceof Long) {
          statement.setLong(index, (long) parameter);
        } else if (parameter instanceof Integer) {
          statement.setInt(index, (int) parameter);
        } else if (parameter instanceof Timestamp) {
          statement.setTimestamp(index, (Timestamp) parameter);
        } else if (parameter == null) {
          statement.setNull(index, Types.NULL);
        }
      }
    } catch (SQLException e) {
      Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, e);
    }
  }

  public <T> List<T> query(String sql, IRowMapper<T> rowMapper, Object... parameters) {
    List<T> results = new ArrayList<T>();
    Connection connection = getConnection();
    PreparedStatement statement = null;
    ResultSet result = null;
    try {
      statement = connection.prepareStatement(sql);
      setParameter(statement, parameters);
      result = statement.executeQuery();
      while (result.next()) {
        results.add(rowMapper.mapRow(result));
      }
      return results;
    } catch (SQLException e) {
      Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, e);
      return null;
    } finally {
      try {
        if (connection != null) {
          connection.close();
        } else if (statement != null) {
          statement.close();
        } else if (result != null) {
          result.close();
        }
      } catch (SQLException e) {
        Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, e);
        return null;
      }
    }
  }

  public <T> Long queryOne(String sql, IRowMapper<T> rowMapper, Object... parameters) {
    Long point = null;
    Connection connection = getConnection();
    PreparedStatement statement = null;
    ResultSet result = null;
    try {
      statement = connection.prepareStatement(sql);
      setParameter(statement, parameters);
      result = statement.executeQuery();
      while (result.next()) {
        point = rowMapper.mapPoint(result);
      }
      return point;
    } catch (SQLException e) {
      Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, e);
      return null;
    } finally {
      try {
        if (connection != null) {
          connection.close();
        } else if (statement != null) {
          statement.close();
        } else if (result != null) {
          result.close();
        }
      } catch (SQLException e) {
        Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, e);
        return null;
      }

    }
  }

  public <T> Long save(String sql, Object... parameters) {
    Connection connection = getConnection();
    PreparedStatement statement = null;
    ResultSet result = null;
    Long id = null;
    try {
      connection.setAutoCommit(false);
      statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      setParameter(statement, parameters);
      statement.executeUpdate();
      result = statement.getGeneratedKeys();
      while (result.next()) {
        id = result.getLong(1);
      }
      connection.commit();
      return id;
    } catch (SQLException e) {
      if (connection != null) {
        try {
          connection.rollback();
        } catch (SQLException e1) {
          Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, e1);
        }
      }
      Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, e);
      return null;
    } finally {
      try {
        if (connection != null) {
          connection.close();
        } else if (statement != null) {
          statement.close();
        } else if (result != null) {
          result.close();
        }
      } catch (SQLException e) {
        Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, e);
      }
    }
  }

  public void update(String sql, Object... parameters) {
    Connection connection = getConnection();
    PreparedStatement statement = null;
    try {
      connection.setAutoCommit(false);
      statement = connection.prepareStatement(sql);
      setParameter(statement, parameters);
      statement.executeUpdate();
      connection.commit();
    } catch (SQLException e) {
      if (connection != null) {
        try {
          connection.rollback();
        } catch (SQLException e1) {
          Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, e1);
        }
      }
    } finally {
      try {
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e2) {
        Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, e2);
      }
    }
  }

}

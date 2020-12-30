package com.lan.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.lan.mapper.CoreMapper;

public class AbstractDAO {
  Connection getConnection() {
    try {
      String driverName = "com.mysql.cj.jdbc.Driver";
      String url = "jdbc:mysql://localhost:3306/training";
      String user = "root";
      String password = "Lan123456@";
      Class.forName(driverName);
      return DriverManager.getConnection(url, user, password);
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      return null;
    }

  }

  private void setParameters(PreparedStatement statement, Object... parameters) {
    try {
      for (int i = 0; i < parameters.length; i++) {
        int index = i + 1;
        Object parameter = parameters[i];
        if (parameter instanceof Long) {
          statement.setLong(index, (long) parameter);
        } else if (parameter instanceof String) {
          statement.setString(index, (String) parameter);
        } else if (parameter instanceof Integer) {
          statement.setInt(index, (int) parameter);
        } else if (parameter == null) {
          statement.setNull(index, Types.NULL);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  public <T> List<T> query(String sql, CoreMapper<T> mapper, Object... parameters) {
    List<T> results = new ArrayList<>();
    Connection connection = getConnection();
    PreparedStatement statement = null;
    ResultSet result = null;
    try {
      statement = connection.prepareStatement(sql);
      setParameters(statement, parameters);
      result = statement.executeQuery();
      while (result.next()) {
        results.add(mapper.mapper(result));
      }
      return results;
    } catch (SQLException e) {
      e.printStackTrace();
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
        e.printStackTrace();
      }

    }
  }
  
  public <T> Long queryOne(String sql, CoreMapper<T> mapper, Object... parameters) {
    Long point = null;
    Connection connection = getConnection();
    PreparedStatement statement = null;
    ResultSet result = null;

    try {
      statement = connection.prepareStatement(sql);
      setParameters(statement, parameters);
      result = statement.executeQuery();
      while (result.next()) {
        point = (Long) mapper.mapper(result);
      }
      return point;
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
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
        // TODO Auto-generated catch block
        e.printStackTrace();
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
      setParameters(statement, parameters);
      statement.executeUpdate();
      result = statement.getGeneratedKeys();
      while (result.next()) {
        id = result.getLong(1);
      }
      connection.commit();
      return id;
    } catch (SQLException e) {
      e.printStackTrace();
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
        e.printStackTrace();
        return null;
      }

    }
  }

  public void update(String sql, Object... parameters) {
    Connection connection = getConnection();
    PreparedStatement statement = null;

    try {
      connection.setAutoCommit(false);
      statement = connection.prepareStatement(sql);
      setParameters(statement, parameters);
      statement.executeUpdate();
      connection.commit();
    } catch (SQLException e) {
      if (connection != null) {
        try {
          connection.rollback();
        } catch (SQLException e1) {
          e1.printStackTrace();
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
      } catch (SQLException e) {
        e.printStackTrace();

      }
    }

  }

}

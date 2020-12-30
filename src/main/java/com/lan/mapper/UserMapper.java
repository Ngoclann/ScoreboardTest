package com.lan.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lan.model.User;

public class UserMapper implements CoreMapper<User>{

  @Override
  public User mapper(ResultSet result) {
    User user = new User();
    try {
      user.setId(result.getLong("id"));
      user.setName(result.getString("name"));
      user.setWins_count(result.getInt("wins_count"));
      user.setLoses_count(result.getInt("loses_count"));
      return user;
    } catch(SQLException e) {
      e.printStackTrace();
      return null;
      
    }
  }

}

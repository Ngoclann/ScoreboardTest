package com.lan.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.lan.model.PlayerModel;

public class PlayerMapper implements IRowMapper<PlayerModel>{

  @Override
  public PlayerModel mapRow(ResultSet result) {
    PlayerModel model = new PlayerModel();
    try {
        model.setPlayer_id(result.getLong("id"));
        model.setFullname(result.getString("fullname"));
        model.setLosecount(result.getInt("losecount"));
        model.setWinscount(result.getInt("winscount"));
        model.setPassword(result.getString("password"));
        model.setUsername(result.getString("username"));
        model.setStatus(result.getInt("status"));
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return model;
  }

  @Override
  public Long mapPoint(ResultSet result) {
    Long point = null;
    try {
      point = result.getLong("points");
  } catch(SQLException e) {
      e.printStackTrace();
  }
  return point;
  }

  @Override
  public Timestamp mapTime(ResultSet result) {
    return null;
  }

}

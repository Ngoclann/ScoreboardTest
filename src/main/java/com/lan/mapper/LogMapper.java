package com.lan.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.lan.model.LogModel;

public class LogMapper implements IRowMapper<LogModel>{

  @Override
  public LogModel mapRow(ResultSet result) {
     LogModel model = new LogModel();
     try {
       model.setId(result.getLong("id"));
       model.setStatus(result.getInt("status"));
       model.setPlayer1(result.getLong("player1"));
       model.setPlayer2(result.getLong("player2"));
       model.setPoint1(result.getLong("point1"));
       model.setPoint2(result.getLong("point2"));
       model.setCpoint1(result.getLong("cpoint1"));
       model.setCpoint2(result.getLong("cpoint2"));
       model.setGameID(result.getLong("gameID"));
     } catch(SQLException e) {
       Logger.getLogger(LogMapper.class.getName()).log(Level.SEVERE, null, e);
     }
     return model;
  }

  @Override
  public Long mapPoint(ResultSet result) {
    return null;
  }

  @Override
  public Timestamp mapTime(ResultSet result) {
    try {
     Timestamp time = result.getTimestamp("timeplay");
     return time;
    } catch(SQLException e) {
      Logger.getLogger(LogMapper.class.getName()).log(Level.SEVERE, null, e);
      return null;
    }
  }

}

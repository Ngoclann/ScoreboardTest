package com.lan.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.lan.model.GameModel;

public class GameMapper implements IRowMapper<GameModel> {

  @Override
  public GameModel mapRow(ResultSet result) {
    GameModel model = new GameModel();
    try {
      model.setId(result.getLong("id"));
      model.setPlayer1(result.getLong("player1"));
      model.setPlayer2(result.getLong("player2"));
      model.setWinner(result.getLong("winner"));
    } catch (SQLException e) {
      Logger.getLogger(GameMapper.class.getName()).log(Level.SEVERE, null, e);
    }
    return model;
  }

  @Override
  public Long mapPoint(ResultSet result) {
    return null;
  }

  @Override
  public Timestamp mapTime(ResultSet result) {
    return null;
  }

}

package com.lan.DAO.impl;

import com.lan.DAO.IGameDAO;
import com.lan.model.GameModel;

public class GameDAO extends AbstractDAO implements IGameDAO {

  @Override
  public Long save(GameModel model) {
    String sql = "INSERT INTO game (winner, player1, player2) VALUES(?, ?, ?)";
    return save(sql, model.getWinner(), model.getPlayer1(), model.getPlayer2());
  }


}

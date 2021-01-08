package com.lan.service.impl;

import com.lan.DAO.IGameDAO;
import com.lan.DAO.impl.GameDAO;
import com.lan.model.GameModel;
import com.lan.service.IGameService;

public class GameService implements IGameService {

  private IGameDAO gameDAO = new GameDAO();

  @Override
  public Long save(GameModel model) {
    return gameDAO.save(model);
  }

}

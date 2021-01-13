package com.lan.utils;

import java.util.List;
import java.util.Map;

import com.lan.model.GameModel;
import com.lan.viewmodel.GameInfoModel;
import com.lan.viewmodel.ShowGameInfo;

public class SetGameInfo {
  public void setGameInfo(List<Map<String, String>> listPlayers, GameModel gameModel, Long id, ShowGameInfo gameInfo) {
    GameInfoModel game = new GameInfoModel();
    game.setPlayers(listPlayers);
    game.setId(id);
    game.setWinner(gameModel.getWinner());
    gameInfo.setGame(game);
  }
}

package com.lan.mapper;

import com.lan.model.PlayerModel;
import com.lan.viewmodel.PlayerInfoModel;

public class PlayerToInfo {
  public PlayerInfoModel mapper(PlayerModel playerModel) {
    PlayerInfoModel infoModel = new PlayerInfoModel();
    infoModel.setFullname(playerModel.getFullname());
    infoModel.setId(playerModel.getPlayer_id());
    infoModel.setLosecount(playerModel.getLosecount());
    infoModel.setWinscount(playerModel.getWinscount());
    return infoModel;
  }
}

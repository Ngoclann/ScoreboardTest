package com.lan.service;

import java.util.List;

import com.lan.model.PlayerModel;

public interface IPlayerService {
  Long save(PlayerModel model);

  List<PlayerModel> findAll();
  
  List<PlayerModel> findAllByCount();

  PlayerModel findOne(Long id);

  void update(PlayerModel model);

  void delete(Long[] ids);

  Long findPointById(Long id);

  PlayerModel findByUsernameAndPassword(String username, String password);

  void updateStatus(Long id, int status);
  
  void updatePoint(Long id, Long point);
}

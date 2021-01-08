package com.lan.DAO;

import java.util.List;

import com.lan.model.PlayerModel;

public interface IPlayerDAO {
  Long save(PlayerModel model);

  List<PlayerModel> findAll();
  
  List<PlayerModel> findAllbyCount();

  PlayerModel findOne(Long id);

  void update(PlayerModel model);

  void delete(Long[] ids);

  Long findPointById(Long id);

  PlayerModel findByUsernameAndPassword(String username, String password);

  void updateStatus(Long id, int status);
  
  void updatePoint(Long id, Long point);
}

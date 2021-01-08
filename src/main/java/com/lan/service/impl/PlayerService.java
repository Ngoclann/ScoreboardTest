package com.lan.service.impl;

import java.util.List;

import com.lan.DAO.IPlayerDAO;
import com.lan.DAO.impl.PlayerDAO;
import com.lan.model.PlayerModel;
import com.lan.service.IPlayerService;

public class PlayerService implements IPlayerService {

  private IPlayerDAO playerDAO = new PlayerDAO();

  @Override
  public Long save(PlayerModel model) {
    return playerDAO.save(model);
  }

  @Override
  public List<PlayerModel> findAll() {
    return playerDAO.findAll();
  }
  
  @Override
  public List<PlayerModel> findAllByCount() {
    return playerDAO.findAllbyCount();
  }

  @Override
  public PlayerModel findOne(Long id) {
    return playerDAO.findOne(id);
  }

  @Override
  public void update(PlayerModel model) {
    playerDAO.update(model);

  }

  @Override
  public void delete(Long[] ids) {
    playerDAO.delete(ids);

  }

  @Override
  public Long findPointById(Long id) {
    return playerDAO.findPointById(id);
  }

  @Override
  public PlayerModel findByUsernameAndPassword(String username, String password) {
    return playerDAO.findByUsernameAndPassword(username, password);
  }

  @Override
  public void updateStatus(Long id, int status) {
    playerDAO.updateStatus(id, status);

  }

  @Override
  public void updatePoint(Long id, Long point) {
    playerDAO.updatePoint(id, point);
    
  }

}

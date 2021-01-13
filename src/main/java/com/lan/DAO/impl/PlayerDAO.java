package com.lan.DAO.impl;

import java.util.List;

import com.lan.DAO.IPlayerDAO;
import com.lan.mapper.PlayerMapper;
import com.lan.model.PlayerModel;

public class PlayerDAO extends AbstractDAO implements IPlayerDAO {
  @Override
  public Long save(PlayerModel model) {
    String sql = "INSERT INTO player (fullname, winscount, losecount, username, password, status) VALUES(?, ?, ?, ?, ?, ?)";
    return save(sql, model.getFullname(), model.getWinscount(), model.getLosecount(), model.getUsername(),
        model.getPassword(), model.getStatus());
  }

  @Override
  public List<PlayerModel> findAll() {
    String sql = "SELECT * FROM player";
    return query(sql, new PlayerMapper());
  }
  
  @Override
  public List<PlayerModel> findAllbyCount() {
    String sql = "SELECT * FROM player ORDER BY (winscount - losecount) DESC";
    return query(sql, new PlayerMapper());
  }

  @Override
  public PlayerModel findOne(Long id) {
    String sql = "SELECT * FROM player WHERE id=?";
    List<PlayerModel> list = query(sql, new PlayerMapper(), id);
    return list.isEmpty() ? null : list.get(0);
  }

  @Override
  public void update(PlayerModel model) {
    String sql = "UPDATE player set fullname=?, winscount=?, losecount=?, username=?, password=?, status=? WHERE id=?";
    PlayerModel modelDB = findOne(model.getPlayer_id());
    if (modelDB != null) {
      if (model.getFullname() == null) {
        model.setFullname(modelDB.getFullname());
      }
      if (model.getLosecount() == null) {
        model.setLosecount(modelDB.getLosecount());
      }
      if (model.getWinscount() == null) {
        model.setWinscount(modelDB.getWinscount());
      }
      if (model.getUsername() == null) {
        model.setUsername(modelDB.getUsername());
      }
      if (model.getPassword() == null) {
        model.setPassword(modelDB.getPassword());
      }
      if (model.getStatus() == null) {
        model.setStatus(modelDB.getStatus());
      }
    }
    update(sql, model.getFullname(), model.getWinscount(), model.getLosecount(), model.getUsername(),
        model.getPassword(), model.getStatus(), model.getPlayer_id());
  }

  @Override
  public void delete(Long[] ids) {
    String sql = "DELETE FROM player WHERE id=?";
    for (Long id : ids) {
      PlayerModel model = findOne(id);
      if (model != null) {
        update(sql, id);
      }
    }
  }

  @Override
  public Long findPointById(Long id) {
    String sql = "SELECT points FROM player WHERE id=?";
    Long point = queryOne(sql, new PlayerMapper(), id);
    return point;
  }

  @Override
  public PlayerModel findByUsernameAndPassword(String username, String password) {
    String sql = "SELECT * FROM player WHERE username=? AND password=?";
    List<PlayerModel> models = query(sql, new PlayerMapper(), username, password);
    return models.isEmpty() ? null : models.get(0);
  }

  @Override
  public void updateStatus(Long id, int status) {
    String sql = "UPDATE player SET status=? WHERE id=?";
    save(sql, status, id);

  }

  @Override
  public void updatePoint(Long id, Long point) {
    String sql = "UPDATE player SET point=? WHERE id=?";
    save(sql, point, id);    
  }

  
}

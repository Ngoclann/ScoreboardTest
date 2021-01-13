package com.lan.DAO.impl;

import java.util.List;

import com.lan.DAO.ILogDAO;
import com.lan.mapper.LogMapper;
import com.lan.model.LogModel;

public class LogDAO extends AbstractDAO implements ILogDAO {

  @Override
  public Long save(LogModel model) {
    String sql = "INSERT INTO log (status, player1, player2, point1, cpoint1, point2, cpoint2, gameID) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    return save(sql.toString(), model.getStatus(), model.getPlayer1(), model.getPlayer2(), model.getPoint1(),
        model.getCpoint1(), model.getPoint2(), model.getCpoint2(), model.getGameID());
  }

  @Override
  public void updateStatusEndGame(Long id, int status) {
    String sql = "UPDATE log SET status=? WHERE gameID=? order by id desc";
    save(sql, status, id);
  }

  @Override
  public LogModel findByPlayerAndStatus(Long id, int status) {
    String sql = "SELECT * FROM log WHERE status=? AND (player1=? OR player2=?) order by id desc";
    List<LogModel> list = query(sql, new LogMapper(), status, id, id);
    return list.isEmpty() ? null : list.get(0);
  }

  @Override
  public LogModel findLast() {
    String sql = "SELECT * from log order by id desc";
    List<LogModel> models = query(sql, new LogMapper());
    return models.isEmpty() ? null : models.get(0);
  }

  @Override
  public LogModel findOne(Long id, int status) {
    String sql = "SELECT * FROM log WHERE gameID=? and status=? order by id desc";
    List<LogModel> models = query(sql, new LogMapper(), id, status);
    return models.isEmpty() ? null : models.get(0);
  }

  @Override
  public LogModel findNearLastByPlayerAndStatus(Long id, int status) {
    String sql = "SELECT * FROM log WHERE status=? AND (player1=? OR player2=?) order by id desc";
    List<LogModel> list = query(sql, new LogMapper(), status, id, id);
    if (list.size() >= 2) {
      return list.get(1);
    } else {
      return null;
    }
  }

  @Override
  public void deleteLastLog(Long id) {
    String sql = "delete from log where id=?";
    save(sql, id);

  }

}

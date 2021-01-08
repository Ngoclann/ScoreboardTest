package com.lan.service.impl;

import com.lan.DAO.ILogDAO;
import com.lan.DAO.impl.LogDAO;
import com.lan.model.LogModel;
import com.lan.service.ILogService;

public class LogService implements ILogService {
  private ILogDAO logDAO = new LogDAO();

  @Override
  public Long save(LogModel model) {
    return logDAO.save(model);
  }

  @Override
  public void updateStatusEndGame(Long id, int status) {
    logDAO.updateStatusEndGame(id, status);
  }

  @Override
  public LogModel findByPlayerAndStatus(Long id, int status) {
    return logDAO.findByPlayerAndStatus(id, status);
  }

  @Override
  public LogModel findOne(Long id, int status) {
    return logDAO.findOne(id, status);
  }

  @Override
  public LogModel findLast() {
    return logDAO.findLast();
  }

  @Override
  public LogModel findNearLastByPlayerAndStatus(Long id, int status) {
    return logDAO.findNearLastByPlayerAndStatus(id, status);
  }

  @Override
  public void deleteLastLog(Long id) {
    logDAO.deleteLastLog(id);
  }
}

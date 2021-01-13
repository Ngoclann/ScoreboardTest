package com.lan.DAO;

import com.lan.model.LogModel;

public interface ILogDAO {
  Long save(LogModel model);

  LogModel findByPlayerAndStatus(Long id, int status);
  
  LogModel findLast();
  
  LogModel findNearLastByPlayerAndStatus(Long id, int status);

  LogModel findOne(Long id, int status);

  void updateStatusEndGame(Long id, int status);

  void deleteLastLog(Long id);
  
}

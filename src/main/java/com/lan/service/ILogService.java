package com.lan.service;

import com.lan.model.LogModel;

public interface ILogService {
  Long save(LogModel model);

  void updateStatusEndGame(Long id, int status);

  LogModel findByPlayerAndStatus(Long id, int status);

  LogModel findOne(Long id, int status);

  LogModel findLast();

  LogModel findNearLastByPlayerAndStatus(Long id, int status);

  void deleteLastLog(Long id);
}

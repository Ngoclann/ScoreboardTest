package com.lan.mapper;

import java.sql.ResultSet;
import java.sql.Timestamp;

public interface IRowMapper<T> {
  T mapRow(ResultSet result);
  Long mapPoint(ResultSet result);
  Timestamp mapTime(ResultSet result);
}

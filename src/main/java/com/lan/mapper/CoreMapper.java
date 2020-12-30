package com.lan.mapper;

import java.sql.ResultSet;

public interface CoreMapper<T>{
  T mapper(ResultSet result);
}

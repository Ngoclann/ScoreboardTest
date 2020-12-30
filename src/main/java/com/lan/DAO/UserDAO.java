package com.lan.DAO;

import java.util.List;

import com.lan.mapper.UserMapper;
import com.lan.model.User;

public class UserDAO extends AbstractDAO {
  public Long save(User user) {
    String sql = "insert into user (name, wins_count, loses_count) values (?,?,?)";
    return save(sql, user.getName(), user.getWins_count(), user.getLoses_count());
  }

  public void update(User user) {
    String sql = "update user set name = ?,wins_count = ?,loses_count = ? where id = ?";
    update(sql, user.getName(), user.getWins_count(), user.getLoses_count(), user.getId());
  }

  public void delete(Long[] ids) {
    String sql = "delete from user where id = ?";
    for (Long id : ids) {
      update(sql, id);
    }
  }

  public User findOne(Long id) {
    String sql = "select* from user where id = ?";
    List<User> list = query(sql, new UserMapper(), id);
    return list.isEmpty() ? null : list.get(0);
  }

  public List<User> findAll() {
    String sql = "select * from user";
    return query(sql, new UserMapper());
  }

  public Long findPointById(Long id) {
    String sql = "select points from user where id=?";
    Long point = queryOne(sql, new UserMapper(), id);
    return point;
  }

}

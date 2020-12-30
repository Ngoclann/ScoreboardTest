package com.lan.model;

import java.util.ArrayList;
import java.util.List;

public class User {
  private Long id;
  private String name;
  private int wins_count, loses_count;
  private Long points;
  private Long[] ids;
  private List<User> listUser = new ArrayList<>();

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getWins_count() {
    return wins_count;
  }

  public void setWins_count(int wins_count) {
    this.wins_count = wins_count;
  }

  public int getLoses_count() {
    return loses_count;
  }

  public void setLoses_count(int loses_count) {
    this.loses_count = loses_count;
  }

  public Long[] getIds() {
    return ids;
  }

  public void setIds(Long[] ids) {
    this.ids = ids;
  }

  public List<User> getListUser() {
    return listUser;
  }

  public void setListUser(List<User> listUser) {
    this.listUser = listUser;
  }

  public Long getPoints() {
    return points;
  }

  public void setPoints(Long points) {
    this.points = points;
  }
  
  
}

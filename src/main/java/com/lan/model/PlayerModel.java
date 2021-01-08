package com.lan.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerModel {
  private Long player_id;
  private String fullname;
  private Integer winscount;
  private Integer losecount;

  private Long ids[];
  private String username;
  private String password;
  private Integer status;
  private List<PlayerModel> listPlayerModel = new ArrayList<>();
  private Map<String, String> players = new HashMap<String, String>();

  public Long getPlayer_id() {
    return player_id;
  }

  public void setPlayer_id(Long id) {
    this.player_id = id;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public Integer getWinscount() {
    return winscount;
  }

  public void setWinscount(Integer winscount) {
    this.winscount = winscount;
  }

  public Integer getLosecount() {
    return losecount;
  }

  public void setLosecount(Integer losecount) {
    this.losecount = losecount;
  }

  public Long[] getIds() {
    return ids;
  }

  public void setIds(Long[] ids) {
    this.ids = ids;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public List<PlayerModel> getListPlayerModel() {
    return listPlayerModel;
  }

  public void setListPlayerModel(List<PlayerModel> listPlayerModel) {
    this.listPlayerModel = listPlayerModel;
  }

  public Map<String, String> getPlayers() {
    return players;
  }

  public void setPlayers(Map<String, String> players) {
    this.players = players;
  }

}

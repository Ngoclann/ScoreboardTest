package com.lan.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
  private Long id;
  private Map<String, String> players = new HashMap<String, String>();
  private Long winner;
  private List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();
  
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  public Long getWinner() {
    return winner;
  }
  public void setWinner(Long winner) {
    this.winner = winner;
  }

  public Map<String, String> getPlayers() {
    return players;
  }
  public void setPlayers(Map<String, String> players) {
    this.players = players;
  }
  public List<Map<String, String>> getListPlayers() {
    return listPlayers;
  }
  public void setListPlayers(List<Map<String, String>> listPlayers) {
    this.listPlayers = listPlayers;
  }
  
  
}

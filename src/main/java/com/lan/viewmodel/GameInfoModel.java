package com.lan.viewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameInfoModel {
  private Long id;
  private List<Map<String, String>> players = new ArrayList<Map<String, String>>();
  private Long winner;

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

  public List<Map<String, String>> getPlayers() {
    return players;
  }

  public void setPlayers(List<Map<String, String>> players) {
    this.players = players;
  }
}

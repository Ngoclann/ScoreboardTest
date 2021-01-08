package com.lan.viewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Leaderboard {
  private List<Map<String, String>> players = new ArrayList<Map<String, String>>();

  public List<Map<String, String>> getPlayers() {
    return players;
  }

  public void setPlayers(List<Map<String, String>> players) {
    this.players = players;
  }

}

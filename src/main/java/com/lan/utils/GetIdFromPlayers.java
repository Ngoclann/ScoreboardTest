package com.lan.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lan.model.GameModel;

public class GetIdFromPlayers {
  public static List<Long> getIdFromPlayers(GameModel model) {
    List<Long> ids = new ArrayList<>();
    Map<String, String> players = model.getPlayers();
    Set<String> set = players.keySet();
    for (String key : set) {
      ids.add(Long.parseLong(players.get(key)));
    }
    return ids;
  }
}

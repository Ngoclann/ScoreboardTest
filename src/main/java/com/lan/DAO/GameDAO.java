package com.lan.DAO;

import java.util.ArrayList;
import java.util.Set;

import com.lan.mapper.UserMapper;
import com.lan.model.Game;

public class GameDAO extends AbstractDAO{
  public Long save(Game game) {
    ArrayList<Long> player_ids = new ArrayList<>();
    String sql = "insert into game (player_id1, player_id2, winner) values (?,?,?)";
    Set<String> set = game.getPlayers().keySet();
    
    for (String key : set) {
      player_ids.add(Long.parseLong(game.getPlayers().get(key)));
    }
    Long id1 = player_ids.get(0);
    Long id2 = player_ids.get(1);
    
    return save(sql, id1, id2, game.getWinner());
  }
  
}

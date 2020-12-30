package com.lan.controller;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lan.DAO.GameDAO;
import com.lan.DAO.UserDAO;
import com.lan.model.Game;
import com.lan.util.HttpUtil;

@WebServlet(urlPatterns = { "/games" })
public class GameAPI extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      ObjectMapper mapper = new ObjectMapper();
      request.setCharacterEncoding("UTF-8");
      response.setContentType("application/json");

      GameDAO gdb = new GameDAO();
      UserDAO udb = new UserDAO();
      Game model = HttpUtil.of(request.getReader()).toModel(Game.class);
      
      Map<String, String> players = model.getPlayers();
      List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();
      Set<String> set = players.keySet();
      List<Long> ids = new ArrayList<>();
      for (String key : set) {
        ids.add(Long.parseLong(players.get(key)));
      }
      
      model.setWinner(0L);
      for (int i = 0; i < 2; i++) {
        Map<String, String> player = new HashMap<String, String>();
        player.put("id", ids.get(i).toString());
        player.put("points", udb.findPointById(ids.get(i)).toString());
        listPlayers.add(player);
      }
      
      model.setListPlayers(listPlayers);
      Long id = gdb.save(model);
      model.setId(id);

      
      mapper.writeValue(response.getOutputStream(), model);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
}

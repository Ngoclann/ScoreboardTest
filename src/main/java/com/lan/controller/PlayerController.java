package com.lan.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lan.mapper.PlayerToInfo;
import com.lan.model.PlayerModel;
import com.lan.service.IPlayerService;
import com.lan.service.impl.PlayerService;
import com.lan.utils.HttpUtil;
import com.lan.viewmodel.PlayerInfoModel;
import com.lan.viewmodel.ShowPlayerInfo;

@Path("/player")
public class PlayerController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private IPlayerService playerService = new PlayerService();
  private PlayerToInfo mapperToModel = new PlayerToInfo();

  @POST
  @Path("/")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  public void doPost(@Context HttpServletRequest request, @Context HttpServletResponse response,
      InputStream requestBody) throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
    PlayerModel model = HttpUtil.of(reader).toModel(PlayerModel.class);
    playerService.save(model);
    mapper.writeValue(response.getOutputStream(), "New player added to database!");
  }

  @GET
  @Path("/")
  @Produces({ MediaType.APPLICATION_JSON })
  public void doGet(@Context HttpServletRequest request, @Context HttpServletResponse response)
      throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    List<Map<String, String>> players = new ArrayList<Map<String, String>>();
    PlayerModel playerModel = new PlayerModel();
    playerModel.setListPlayerModel(playerService.findAll());
    for (PlayerModel model : playerModel.getListPlayerModel()) {
      Map<String, String> player = new HashMap<String, String>();
      player.put("id", model.getPlayer_id().toString());
      player.put("name", model.getFullname());
      player.put("wins_count", model.getWinscount().toString());
      player.put("loses_count", model.getLosecount().toString());
      players.add(player);
    }
    mapper.writeValue(response.getOutputStream(), players);
  }

  @GET
  @Path("/{id}")
  @Produces({ MediaType.APPLICATION_JSON })
  public void getOne(@PathParam("id") Long id, @Context HttpServletRequest request,
      @Context HttpServletResponse response) throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    ShowPlayerInfo showPlayer = new ShowPlayerInfo();
    boolean isExist = true;
    PlayerModel model = playerService.findOne(id);
    if (model == null) {
      mapper.writeValue(response.getOutputStream(), "Id not found to show");
      isExist = false;
    }
    if (isExist) {
      PlayerInfoModel playerInfo = mapperToModel.mapper(model);
      playerInfo.setPoint(0L);
      showPlayer.setPlayerInfo(playerInfo);
      mapper.writeValue(response.getOutputStream(), showPlayer);
    }
  }

  @PUT
  @Path("/")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  public void doPut(@Context HttpServletRequest request, @Context HttpServletResponse response, InputStream requestBody)
      throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
    PlayerModel model = HttpUtil.of(reader).toModel(PlayerModel.class);
    boolean isExist = true;
    PlayerModel modelDB = new PlayerModel();
    modelDB.setListPlayerModel(playerService.findAll());
    Long id = model.getPlayer_id();
    PlayerModel playerModel = playerService.findOne(id);
    if (playerModel == null) {
      mapper.writeValue(response.getOutputStream(), "Id not found to update");
      isExist = false;
    }
    if (isExist) {
      playerService.update(model);
      mapper.writeValue(response.getOutputStream(), "Info of player is updated!");
    }
  }

  @DELETE
  @Path("/")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  public void doDelete(@Context HttpServletRequest request, @Context HttpServletResponse response,
      InputStream requestBody) throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
    PlayerModel model = HttpUtil.of(reader).toModel(PlayerModel.class);
    boolean isExist = true;
    for (Long id : model.getIds()) {
      PlayerModel playerModel = playerService.findOne(id);
      if (playerModel == null) {
        mapper.writeValue(response.getOutputStream(), "Id not found to delete");
        isExist = false;
        return;
      }
    }
    if (isExist) {
      playerService.delete(model.getIds());
      mapper.writeValue(response.getOutputStream(), "Player is deleted!");
    }

  }

}

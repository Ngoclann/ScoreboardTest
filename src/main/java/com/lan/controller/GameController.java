package com.lan.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lan.filter.Secured;
import com.lan.model.GameModel;
import com.lan.model.LogModel;
import com.lan.model.PlayerModel;
import com.lan.service.IGameService;
import com.lan.service.ILogService;
import com.lan.service.IPlayerService;
import com.lan.service.impl.GameService;
import com.lan.service.impl.LogService;
import com.lan.service.impl.PlayerService;
import com.lan.utils.GetIdFromPlayers;
import com.lan.utils.SetGameInfo;
import com.lan.utils.SetListPlayer;
import com.lan.viewmodel.GameInfoModel;
import com.lan.viewmodel.Leaderboard;
import com.lan.viewmodel.ShowGameInfo;

@Path("/games")
public class GameController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private IGameService gameService = new GameService();
  private IPlayerService playerService = new PlayerService();
  private ILogService logService = new LogService();
  private SetListPlayer setListPlayer = new SetListPlayer();

  @POST
  @Path("/logout")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  @Secured
  public Response logout(PlayerModel playerModel) {
    PlayerModel playerLogout = playerService.findOne(playerModel.getPlayer_id());
    if (playerLogout == null) {
      return Response.ok().entity("Invalid player id").build();
    } else {
      playerService.updateStatus(playerLogout.getPlayer_id(), 0);
      return Response.ok().entity("Logged out").build();
    }
  }

  @POST
  @Path("/")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  @Secured
  public Response startGame(GameModel model) {
    List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();
    List<Long> ids = GetIdFromPlayers.getIdFromPlayers(model);
    GameInfoModel game = new GameInfoModel();
    game.setWinner(0L);
    for (int i = 0; i < 2; i++) {
      Map<String, String> player = new HashMap<String, String>();
      Long id = ids.get(i);
      PlayerModel modelLogIn = playerService.findOne(id);
      if (modelLogIn == null || modelLogIn.getStatus() == 0) {
        return Response.ok().entity("Invalid player id or Not login. Please restart").build();
      } else if (logService.findByPlayerAndStatus(id, 1) != null) {
        return Response.ok().entity("1-2 players are already in game. Please choose another players").build();
      } else {
        player.put("id", id.toString());
        player.put("points", "0");
        listPlayers.add(player);
      }
    }
    Long id;
    LogModel modelLog = logService.findLast();
    if (modelLog == null) {
      LogModel logModel = new LogModel(1, ids.get(0), ids.get(1), 0L, 0L, 0L, 0L, 1L);
      id = logService.save(logModel);
      game.setPlayers(listPlayers);
      model.setGame(game);
      model.setId(id);
      game.setId(id);
      ShowGameInfo gameInfo = new ShowGameInfo();
      gameInfo.setGame(game);
      return Response.ok().entity(gameInfo).build();
    } else {
      LogModel logModel = new LogModel(1, ids.get(0), ids.get(1), 0L, 0L, 0L, 0L, modelLog.getGameID() + 1);
      id = logService.save(logModel);
      game.setPlayers(listPlayers);
      model.setGame(game);
      model.setId(id);
      game.setId(id);
      ShowGameInfo gameInfo = new ShowGameInfo();
      gameInfo.setGame(game);
      return Response.ok().entity(gameInfo).build();
    }

  }

  @POST
  @Path("/{id}/{action}/{point}")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  @Secured
  public Response scorePoint(@PathParam("id") Long id, @PathParam("action") String action,
      @PathParam("point") Long point, PlayerModel playerModel) {
    LogModel model = logService.findOne(id, 1);
    if (model == null) {
      return Response.ok().entity("Invalid game id").build();
    } else {
      if (action.equals("score")) {
        Long playerId = playerModel.getPlayer_id();
        LogModel logModel = logService.findByPlayerAndStatus(playerId, 1);
        if (logModel != null) {
          if (logModel.getPlayer1() == playerId) {
            logModel.setCpoint1(logModel.getCpoint1() + point);
            logModel.setCpoint2(logModel.getCpoint2());
          } else if (logModel.getPlayer2() == playerId) {
            logModel.setCpoint1(logModel.getCpoint1());
            logModel.setCpoint2(logModel.getCpoint2() + point);
          }
          logModel.setGameID(id);
          logService.save(logModel);

          List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();
          setListPlayer.setListPlayer(logModel, listPlayers);
          GameInfoModel game = new GameInfoModel();
          game.setPlayers(listPlayers);
          game.setId(id);
          game.setWinner(0L);

          ShowGameInfo gameInfo = new ShowGameInfo();
          gameInfo.setGame(game);

          return Response.ok().entity(gameInfo).build();
        } else {
          return Response.ok().entity("Invalid player id").build();
        }
      } else {
        return Response.ok().entity("Invalid action").build();
      }
    }
  }

  @DELETE
  @Path("/{id}/{action}")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  @Secured
  public Response resetPoint(@PathParam("id") Long id, @PathParam("action") String action, PlayerModel playerModel) {
    LogModel model = logService.findOne(id, 1);
    LogModel logModel = logService.findByPlayerAndStatus(playerModel.getPlayer_id(), 1);
    LogModel modelnear = logService.findNearLastByPlayerAndStatus(playerModel.getPlayer_id(), 1);
    if (model == null) {
      return Response.ok().entity("Invalid game id").build();
    } else {
      if (action.equals("reset_point")) {
        if (logModel.getPlayer1() == playerModel.getPlayer_id()) {
          logService.deleteLastLog(logModel.getId());
        } else if (logModel.getPlayer2() == playerModel.getPlayer_id()) {
          logService.deleteLastLog(logModel.getId());
        } else {
          return Response.ok().entity("Invalid player id").build();
        }
      }
      List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();
      setListPlayer.setListPlayer(modelnear, listPlayers);
      GameInfoModel game = new GameInfoModel();
      game.setPlayers(listPlayers);
      game.setId(id);
      game.setWinner(0L);
      ShowGameInfo gameInfo = new ShowGameInfo();
      gameInfo.setGame(game);
      return Response.ok().entity(gameInfo).build();
    }
  }

  @PUT
  @Path("/{id}/{action}")
  @Produces({ MediaType.APPLICATION_JSON })
  @Secured
  public Response endGame(@PathParam("id") Long id, @PathParam("action") String action) {
    GameModel gameModel = new GameModel();
    LogModel logModel = logService.findOne(id, 1);
    if (logModel == null) {
      return Response.ok().entity("Invalid gane id or game have not started to end").build();
    } else {
      if (action.equals("end-match")) {
        GameInfoModel game = new GameInfoModel();
        if (logModel.getCpoint1() > logModel.getCpoint2()) {
          game.setWinner(logModel.getPlayer1());
        } else if (logModel.getCpoint1() < logModel.getCpoint2()) {
          game.setWinner(logModel.getPlayer2());
        } else {
          game.setWinner(0L);
        }
        List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();
        logModel.setPoint1(logModel.getPoint1() + logModel.getCpoint1());
        logModel.setPoint2(logModel.getPoint2() + logModel.getCpoint2());
        logModel.setCpoint1(0L);
        logModel.setCpoint2(0L);
        logModel.setGameID(id);
        logService.save(logModel);
        setListPlayer.setListPlayer(logModel, listPlayers);
        game.setPlayers(listPlayers);
        game.setId(id);
        ShowGameInfo gameInfo = new ShowGameInfo();
        gameInfo.setGame(game);
        return Response.ok().entity(gameInfo).build();
      } else if (action.equals("end-game")) {
        Long gameId = null;
        logModel.setPoint1(logModel.getPoint1() + logModel.getCpoint1());
        logModel.setPoint2(logModel.getPoint2() + logModel.getCpoint2());
        logModel.setCpoint1(0L);
        logModel.setCpoint2(0L);
        if (logModel.getPoint1() > logModel.getPoint2()) {
          gameModel.setWinner(logModel.getPlayer1());
          gameModel.setPlayer1(logModel.getPlayer1());
          gameModel.setPlayer2(logModel.getPlayer2());
          PlayerModel winner = playerService.findOne(logModel.getPlayer1());
          PlayerModel loser = playerService.findOne(logModel.getPlayer2());
          winner.setWinscount(winner.getWinscount() + 1);
          loser.setLosecount(loser.getLosecount() + 1);
          playerService.update(winner);
          playerService.update(loser);
          gameId = gameService.save(gameModel);
        } else if (logModel.getPoint1() < logModel.getPoint2()) {
          gameModel.setWinner(logModel.getPlayer2());
          gameModel.setWinner(logModel.getPlayer2());
          gameModel.setPlayer1(logModel.getPlayer1());
          gameModel.setPlayer2(logModel.getPlayer2());
          PlayerModel winner = playerService.findOne(logModel.getPlayer2());
          PlayerModel loser = playerService.findOne(logModel.getPlayer1());
          winner.setWinscount(winner.getWinscount() + 1);
          loser.setLosecount(loser.getLosecount() + 1);
          playerService.update(winner);
          playerService.update(loser);
          gameId = gameService.save(gameModel);
        } else {
          gameModel.setWinner(0L);
          gameModel.setPlayer1(logModel.getPlayer1());
          gameModel.setPlayer2(logModel.getPlayer2());
          gameId = gameService.save(gameModel);
        }
        playerService.updatePoint(logModel.getPlayer1(), logModel.getPoint1());
        playerService.updatePoint(logModel.getPlayer2(), logModel.getPoint2());
        logModel.setGameID(id);
        logService.save(logModel);
        logService.updateStatusEndGame(id, 0);
        List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();
        Map<String, String> player1 = new HashMap<String, String>();
        player1.put("id", logModel.getPlayer1().toString());
        player1.put("points", logModel.getPoint1().toString());
        listPlayers.add(player1);
        Map<String, String> player2 = new HashMap<String, String>();
        player2.put("id", logModel.getPlayer2().toString());
        player2.put("points", logModel.getPoint2().toString());
        listPlayers.add(player2);
        SetGameInfo setGameIndo = new SetGameInfo();
        ShowGameInfo gameInfo = new ShowGameInfo();
        setGameIndo.setGameInfo(listPlayers, gameModel, gameId, gameInfo);
        return Response.ok().entity(gameInfo).build();
      } else {
        return Response.ok().entity("Invalid action").build();
      }
    }
  }

  @GET
  @Path("/{id}")
  @Produces({ MediaType.APPLICATION_JSON })
  @Secured
  public Response getGameDetail(@PathParam("id") Long id) {
    LogModel logModel = logService.findOne(id, 0);
    if (logModel != null) {
      List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();
      setListPlayer.setListPlayer(logModel, listPlayers);
      GameInfoModel game = new GameInfoModel();
      game.setPlayers(listPlayers);
      game.setId(id);
      if (logModel.getCpoint1() > logModel.getCpoint2()) {
        game.setWinner(logModel.getPlayer1());
      } else if (logModel.getCpoint1() < logModel.getCpoint2()) {
        game.setWinner(logModel.getPlayer2());
      } else {
        game.setWinner(0L);
      }
      ShowGameInfo gameInfo = new ShowGameInfo();
      gameInfo.setGame(game);
      return Response.ok().entity(gameInfo).build();
    } else {
      return Response.ok().entity("Id not found to show or game have not ended").build();
    }
  }

  @GET
  @Path("/leaderboard")
  @Produces({ MediaType.APPLICATION_JSON })
  @Secured
  public Response getLeaderboard() {
    Leaderboard leaderboard = new Leaderboard();
    PlayerModel playerModel = new PlayerModel();
    playerModel.setListPlayerModel(playerService.findAllByCount());
    List<Map<String, String>> players = new ArrayList<Map<String, String>>();
    for (PlayerModel model : playerModel.getListPlayerModel()) {
      Map<String, String> player = new HashMap<String, String>();
      player.put("id", model.getPlayer_id().toString());
      player.put("name", model.getFullname());
      player.put("wins_count", model.getWinscount().toString());
      player.put("loses_count", model.getLosecount().toString());
      players.add(player);
    }
    leaderboard.setPlayers(players);
    return Response.ok().entity(leaderboard).build();
  }
}
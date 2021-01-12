package com.lan.controller;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lan.model.Credentials;
import com.lan.model.PlayerModel;
import com.lan.service.IPlayerService;
import com.lan.service.impl.PlayerService;

@Path("authentication")
public class AuthenticationEndpoint {

  private IPlayerService playerService = new PlayerService();

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response authenticateUser(Credentials credentials) {
    try {
      String username = credentials.getUsername();
      String password = credentials.getPassword();
      PlayerModel playerModel = playerService.findByUsernameAndPassword(username, password);
      // Authenticate the user using the credentials provided
      if (playerModel != null) {
        if (playerModel.getStatus() == 1) {
          return Response.ok("Already logged in").build();
        }
        // Issue a token for the user
        String token = issueToken(credentials.getUsername());

        playerService.updateStatus(playerModel.getPlayer_id(), 1);
        // Return the token on the response
        return Response.ok("Login successfully with token: " + token).build();
      } else {
        return Response.ok("Invalid Username or Password").build();
      }
    } catch (Exception e) {
      return Response.status(Response.Status.FORBIDDEN).build();
    }

  }

  private String issueToken(String username) {
    // Issue a token (can be a random String persisted to a database or a JWT token)
    // The issued token must be associated to a user
    // Return the issued token
    Random random = new SecureRandom();
    String token = new BigInteger(130, random).toString(32);
    return token;
  }
}

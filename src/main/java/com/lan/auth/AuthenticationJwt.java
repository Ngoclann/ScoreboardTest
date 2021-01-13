package com.lan.auth;

import javax.crypto.SecretKey;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lan.auth.credentials.Credentials;
import com.lan.model.Key;
import com.lan.model.PlayerModel;
import com.lan.service.IPlayerService;
import com.lan.service.impl.PlayerService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

@Path("authentication")
public class AuthenticationJwt {
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
        // Issue a token for the user
        String token = issueToken(username);
        if (playerModel.getStatus() == 1) {
          return Response.ok("Already logged in with token: " + token).build();
        }
        playerService.updateStatus(playerModel.getPlayer_id(), 1);
        // Return the token on the response
        return Response.ok("Login successfully with token: " + token).build();
      } else {
        return Response.ok("Invalid Username or Password").build();
      }
    } catch (Exception e) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
  }

  /**
   * Issue a token (can be a random String persisted to a database or a JWT token)
   * The issued token must be associated to a user Return the issued token
   * 
   * @param username
   * @return
   */
  private String issueToken(String username) {
    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    String secretString = Encoders.BASE64.encode(key.getEncoded());
    Key.setKey(secretString);
    String jws = Jwts.builder().setSubject(username).signWith(key).compact();
    return jws;
  }

}

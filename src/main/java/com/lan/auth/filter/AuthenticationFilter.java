package com.lan.auth.filter;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.lan.auth.annotations.Secured;
import com.lan.model.Key;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    // Get the Authorization header from the request
    String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

    // Check if the HTTP Authorization header is present and formatted correctly
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      System.out.println("No JWT token!");
      requestContext.setProperty("auth-failed", true);
      requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
      return;
    }

    // Extract the token from the HTTP Authorization header
    String java_web_token = authorizationHeader.substring("Bearer".length()).trim();
    try {
      // Validate the token
      System.out.println("JWT based Auth in action... time to verify the signature");
      System.out.println("JWT being tested:\n" + java_web_token);
      validateToken(java_web_token);

    } catch (Exception e) {
      System.out.println("JWT validation failed");
      requestContext.setProperty("auth-failed", true);
      requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
  }

  private void validateToken(String java_web_token) {
    String key = Key.getKey();
    Jws<Claims> jws;
    try {
      jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(java_web_token);
      // we can safely trust the JWT
    } catch (JwtException ex) { // (5)
      // we *cannot* use the JWT as intended by its creator
    }
  }
}

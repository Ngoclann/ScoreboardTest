package com.lan.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lan.DAO.UserDAO;
import com.lan.model.User;
import com.lan.util.HttpUtil;

@WebServlet(urlPatterns = { "/players" })
public class UserAPI extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      ObjectMapper mapper = new ObjectMapper();
      request.setCharacterEncoding("UTF-8");
      response.setContentType("application/json");

      UserDAO udb = new UserDAO();

      List<User> users = udb.findAll();
      mapper.writeValue(response.getOutputStream(), users);

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      ObjectMapper mapper = new ObjectMapper();
      request.setCharacterEncoding("UTF-8");
      response.setContentType("application/json");

      UserDAO udb = new UserDAO();
      User model = HttpUtil.of(request.getReader()).toModel(User.class);
      Long id = udb.save(model);
      model.setId(id);
      mapper.writeValue(response.getOutputStream(), model);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      ObjectMapper mapper = new ObjectMapper();
      request.setCharacterEncoding("UTF-8");
      response.setContentType("application/json");

      UserDAO udb = new UserDAO();
      User model = HttpUtil.of(request.getReader()).toModel(User.class);
      udb.update(model);
      mapper.writeValue(response.getOutputStream(), model);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  protected void doDelete(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      ObjectMapper mapper = new ObjectMapper();
      request.setCharacterEncoding("UTF-8");
      response.setContentType("application/json");

      UserDAO udb = new UserDAO();
      User model = HttpUtil.of(request.getReader()).toModel(User.class);
      udb.delete(model.getIds());
      mapper.writeValue(response.getOutputStream(), "Complete");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

}

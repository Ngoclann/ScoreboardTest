package com.lan.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lan.DAO.UserDAO;
import com.lan.model.User;
import com.lan.util.HttpUtil;

@WebServlet(urlPatterns = { "/api" })
public class API extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			UserDAO udb = new UserDAO();

			ArrayList<User> users = udb.getAllUsers();
			mapper.writeValue(response.getOutputStream(), users);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		UserDAO udb = new UserDAO();
		User model = HttpUtil.of(request.getReader()).toModel(User.class);
		Long id = udb.createUser(model);
		model.setId(id);
		mapper.writeValue(response.getOutputStream(), model);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

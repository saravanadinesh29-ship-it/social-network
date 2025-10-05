package com.socialnetwork.controller;

import com.socialnetwork.dao.UserDAO;
import com.socialnetwork.model.User;
import com.socialnetwork.util.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/auth/*")
public class AuthServlet extends HttpServlet {
    private UserDAO userDAO;
    private ObjectMapper objectMapper;
    
    @Override
    public void init() {
        userDAO = new UserDAO();
        objectMapper = new ObjectMapper();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String path = request.getPathInfo();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        JsonResponse jsonResponse = new JsonResponse();
        
        try {
            if (path == null || path.equals("/login")) {
                login(request, jsonResponse);
            } else if (path.equals("/register")) {
                register(request, jsonResponse);
            } else if (path.equals("/logout")) {
                logout(request, jsonResponse);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        } catch (Exception e) {
            jsonResponse.setSuccess(false);
            jsonResponse.setMessage("Operation failed: " + e.getMessage());
        }
        
        objectMapper.writeValue(response.getWriter(), jsonResponse);
    }
    
    private void login(HttpServletRequest request, JsonResponse jsonResponse) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        User user = userDAO.getUserByUsername(username);
        
        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(30 * 60);
            
            jsonResponse.setSuccess(true);
            jsonResponse.setMessage("Login successful");
            jsonResponse.addData("user", user);
        } else {
            jsonResponse.setSuccess(false);
            jsonResponse.setMessage("Invalid username or password");
        }
    }
    
    private void register(HttpServletRequest request, JsonResponse jsonResponse) {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        
        if (userDAO.getUserByUsername(username) != null) {
            jsonResponse.setSuccess(false);
            jsonResponse.setMessage("Username already exists");
            return;
        }
        
        User newUser = new User(username, email, password, firstName, lastName);
        User createdUser = userDAO.createUser(newUser);
        
        if (createdUser.getId() > 0) {
            HttpSession session = request.getSession();
            session.setAttribute("user", createdUser);
            
            jsonResponse.setSuccess(true);
            jsonResponse.setMessage("Registration successful");
            jsonResponse.addData("user", createdUser);
        } else {
            jsonResponse.setSuccess(false);
            jsonResponse.setMessage("Registration failed");
        }
    }
    
    private void logout(HttpServletRequest request, JsonResponse jsonResponse) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        jsonResponse.setSuccess(true);
        jsonResponse.setMessage("Logged out successfully");
    }
}
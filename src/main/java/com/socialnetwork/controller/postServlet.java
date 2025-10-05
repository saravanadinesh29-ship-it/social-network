package com.socialnetwork.controller;

import com.socialnetwork.dao.PostDAO;
import com.socialnetwork.model.Post;
import com.socialnetwork.model.User;
import com.socialnetwork.util.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/post/*")
public class PostServlet extends HttpServlet {
    private PostDAO postDAO;
    private ObjectMapper objectMapper;
    
    @Override
    public void init() {
        postDAO = new PostDAO();
        objectMapper = new ObjectMapper();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String path = request.getPathInfo();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        JsonResponse jsonResponse = new JsonResponse();
        User user = (User) request.getSession().getAttribute("user");
        
        if (user == null) {
            jsonResponse.setSuccess(false);
            jsonResponse.setMessage("Not authenticated");
            objectMapper.writeValue(response.getWriter(), jsonResponse);
            return;
        }
        
        try {
            if (path == null || path.equals("/feed")) {
                List<Post> posts = postDAO.getPostsForUser(user.getId());
                jsonResponse.setSuccess(true);
                jsonResponse.setMessage("Posts retrieved successfully");
                jsonResponse.addData("posts", posts);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        } catch (Exception e) {
            jsonResponse.setSuccess(false);
            jsonResponse.setMessage("Failed to retrieve posts: " + e.getMessage());
        }
        
        objectMapper.writeValue(response.getWriter(), jsonResponse);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String path = request.getPathInfo();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        JsonResponse jsonResponse = new JsonResponse();
        User user = (User) request.getSession().getAttribute("user");
        
        if (user == null) {
            jsonResponse.setSuccess(false);
            jsonResponse.setMessage("Not authenticated");
            objectMapper.writeValue(response.getWriter(), jsonResponse);
            return;
        }
        
        try {
            if (path == null || path.equals("/create")) {
                createPost(request, user, jsonResponse);
            } else if (path.equals("/like")) {
                likePost(request, user, jsonResponse);
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
    
    private void createPost(HttpServletRequest request, User user, JsonResponse jsonResponse) {
        String content = request.getParameter("content");
        String imagePath = request.getParameter("imagePath");
        
        if (content == null || content.trim().isEmpty()) {
            jsonResponse.setSuccess(false);
            jsonResponse.setMessage("Post content cannot be empty");
            return;
        }
        
        Post post = new Post(user.getId(), content);
        post.setImagePath(imagePath);
        
        Post createdPost = postDAO.createPost(post);
        
        if (createdPost.getId() > 0) {
            jsonResponse.setSuccess(true);
            jsonResponse.setMessage("Post created successfully");
            jsonResponse.addData("post", createdPost);
        } else {
            jsonResponse.setSuccess(false);
            jsonResponse.setMessage("Failed to create post");
        }
    }
    
    private void likePost(HttpServletRequest request, User user, JsonResponse jsonResponse) {
        String postIdParam = request.getParameter("postId");
        
        if (postIdParam == null || postIdParam.trim().isEmpty()) {
            jsonResponse.setSuccess(false);
            jsonResponse.setMessage("Post ID is required");
            return;
        }
        
        try {
            int postId = Integer.parseInt(postIdParam);
            boolean success = postDAO.likePost(user.getId(), postId);
            
            if (success) {
                jsonResponse.setSuccess(true);
                jsonResponse.setMessage("Post liked successfully");
            } else {
                jsonResponse.setSuccess(false);
                jsonResponse.setMessage("Failed to like post");
            }
        } catch (NumberFormatException e) {
            jsonResponse.setSuccess(false);
            jsonResponse.setMessage("Invalid post ID");
        }
    }
}
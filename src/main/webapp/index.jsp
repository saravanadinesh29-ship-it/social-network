<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Social Network</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <c:if test="${empty sessionScope.user}">
        <!-- Login/Register Page -->
        <div class="container">
            <div class="form-container">
                <h2 style="text-align: center; margin-bottom: 30px;">Welcome to Social Network</h2>
                
                <div id="loginFormContainer">
                    <form id="loginForm">
                        <div class="form-group">
                            <label for="username">Username</label>
                            <input type="text" id="username" name="username" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input type="password" id="password" name="password" class="form-control" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Login</button>
                    </form>
                    <p style="text-align: center; margin-top: 20px;">
                        Don't have an account? 
                        <a href="#" onclick="showRegisterForm()" style="color: #1877f2;">Register here</a>
                    </p>
                </div>
                
                <div id="registerFormContainer" style="display: none;">
                    <form id="registerForm">
                        <div class="form-group">
                            <label for="regFirstName">First Name</label>
                            <input type="text" id="regFirstName" name="firstName" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="regLastName">Last Name</label>
                            <input type="text" id="regLastName" name="lastName" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="regUsername">Username</label>
                            <input type="text" id="regUsername" name="username" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="regEmail">Email</label>
                            <input type="email" id="regEmail" name="email" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="regPassword">Password</label>
                            <input type="password" id="regPassword" name="password" class="form-control" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Register</button>
                    </form>
                    <p style="text-align: center; margin-top: 20px;">
                        Already have an account? 
                        <a href="#" onclick="showLoginForm()" style="color: #1877f2;">Login here</a>
                    </p>
                </div>
            </div>
        </div>
        
        <script>
            function showRegisterForm() {
                document.getElementById('loginFormContainer').style.display = 'none';
                document.getElementById('registerFormContainer').style.display = 'block';
            }
            
            function showLoginForm() {
                document.getElementById('registerFormContainer').style.display = 'none';
                document.getElementById('loginFormContainer').style.display = 'block';
            }
        </script>
    </c:if>

    <c:if test="${not empty sessionScope.user}">
        <!-- Main Application -->
        <header class="header">
            <nav class="navbar">
                <a href="#" class="logo">SocialNetwork</a>
                
                <div class="nav-search">
                    <input type="text" class="search-box" placeholder="Search for people, posts...">
                </div>
                
                <ul class="nav-menu">
                    <li><a href="#" class="active">Home</a></li>
                    <li><a href="#">Profile</a></li>
                    <li><a href="#">Friends</a></li>
                    <li><a href="#">Messages</a></li>
                </ul>
                
                <div>
                    <span class="user-name">${sessionScope.user.firstName} ${sessionScope.user.lastName}</span>
                    <button id="logoutBtn" class="btn btn-primary" style="margin-left: 10px;">Logout</button>
                </div>
            </nav>
        </header>

        <div class="container">
            <div class="main-content">
                <!-- Main Feed -->
                <main class="feed">
                    <!-- Create Post -->
                    <div class="create-post">
                        <form id="createPostForm">
                            <div class="form-group">
                                <textarea name="content" id="postContent" class="form-control" 
                                          placeholder="What's on your mind, ${sessionScope.user.firstName}?" 
                                          rows="3"></textarea>
                            </div>
                            <button type="submit" class="btn btn-primary">Post</button>
                        </form>
                    </div>

                    <!-- Posts Feed -->
                    <div id="postsFeed">
                        <div style="text-align: center; padding: 20px;">
                            <div class="spinner"></div>
                            <p>Loading posts...</p>
                        </div>
                    </div>
                </main>
            </div>
        </div>
    </c:if>

    <script src="js/app.js"></script>
</body>
</html>
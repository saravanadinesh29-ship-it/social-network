<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Social Network - Feed</title>
    <style>
        body { font-family: Arial; background: #f0f2f5; margin: 0; }
        .navbar { background: #1877f2; color: white; padding: 15px; display: flex; justify-content: space-between; }
        .container { max-width: 600px; margin: 20px auto; padding: 0 20px; }
        .post-box { background: white; padding: 20px; border-radius: 8px; margin-bottom: 20px; }
        textarea { width: 100%; height: 100px; border: 1px solid #ddd; border-radius: 4px; padding: 10px; }
        .post-btn { background: #1877f2; color: white; border: none; padding: 10px 20px; border-radius: 4px; cursor: pointer; margin-top: 10px; }
        .post { background: white; padding: 15px; border-radius: 8px; margin-bottom: 15px; }
    </style>
</head>
<body>
    <div class="navbar">
        <h2>Social Network</h2>
        <div>
            <span>Welcome, User!</span>
            <a href="/logout" style="color: white; margin-left: 15px;">Logout</a>
        </div>
    </div>
    
    <div class="container">
        <div class="post-box">
            <textarea placeholder="What's on your mind?"></textarea>
            <button class="post-btn">Post</button>
        </div>
        
        <div class="post">
            <strong>Jane Smith</strong>
            <p>Just deployed my new social media app! 🚀 #coding #java</p>
            <small>2 hours ago</small>
            <div style="margin-top: 10px;">
                <button>Like</button>
                <button>Comment</button>
                <button>Share</button>
            </div>
        </div>
        
        <div class="post">
            <strong>Mike Wilson</strong>
            <p>Beautiful sunset today! 🌅 #nature</p>
            <small>5 hours ago</small>
            <div style="margin-top: 10px;">
                <button>Like</button>
                <button>Comment</button>
                <button>Share</button>
            </div>
        </div>
    </div>
</body>
</html>
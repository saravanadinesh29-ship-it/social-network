class SocialNetwork {
    constructor() {
        this.currentUser = null;
        this.init();
    }

    init() {
        this.checkAuth();
        this.bindEvents();
    }

    checkAuth() {
        // Check if user is logged in (simplified)
        if (document.querySelector('.user-name')) {
            this.showAuthenticatedUI();
        } else {
            this.showLoginUI();
        }
    }

    bindEvents() {
        // Auth forms
        document.getElementById('loginForm')?.addEventListener('submit', (e) => this.handleLogin(e));
        document.getElementById('registerForm')?.addEventListener('submit', (e) => this.handleRegister(e));
        
        // Post creation
        document.getElementById('createPostForm')?.addEventListener('submit', (e) => this.handleCreatePost(e));
        
        // Navigation
        document.getElementById('logoutBtn')?.addEventListener('click', () => this.handleLogout());
    }

    async handleLogin(e) {
        e.preventDefault();
        const formData = new FormData(e.target);
        
        try {
            const response = await fetch('auth/login', {
                method: 'POST',
                body: formData
            });
            
            const result = await response.json();
            
            if (result.success) {
                window.location.reload();
            } else {
                this.showNotification(result.message, 'error');
            }
        } catch (error) {
            this.showNotification('Login failed. Please try again.', 'error');
        }
    }

    async handleCreatePost(e) {
        e.preventDefault();
        const formData = new FormData(e.target);
        const content = formData.get('content');
        
        if (!content.trim()) {
            this.showNotification('Please enter some content for your post.', 'error');
            return;
        }
        
        try {
            const response = await fetch('post/create', {
                method: 'POST',
                body: formData
            });
            
            const result = await response.json();
            
            if (result.success) {
                e.target.reset();
                this.loadPosts();
                this.showNotification('Post created successfully!', 'success');
            } else {
                this.showNotification(result.message, 'error');
            }
        } catch (error) {
            this.showNotification('Failed to create post. Please try again.', 'error');
        }
    }

    async loadPosts() {
        try {
            const response = await fetch('post/feed');
            const result = await response.json();
            
            if (result.success) {
                this.renderPosts(result.posts);
            }
        } catch (error) {
            console.error('Failed to load posts:', error);
        }
    }

    renderPosts(posts) {
        const feed = document.getElementById('postsFeed');
        if (!feed) return;
        
        if (posts.length === 0) {
            feed.innerHTML = '<div class="empty-state">No posts yet. Be the first to post!</div>';
            return;
        }
        
        feed.innerHTML = posts.map(post => `
            <div class="post-card" data-post-id="${post.id}">
                <div class="post-header">
                    <img src="${post.user.profilePicture || 'images/default-avatar.png'}" 
                         alt="${post.user.firstName}" class="user-avatar">
                    <div class="post-user-info">
                        <h4>${post.user.firstName} ${post.user.lastName}</h4>
                        <span class="post-time">${this.formatTime(post.createdAt)}</span>
                    </div>
                </div>
                <div class="post-content">
                    <p>${this.escapeHtml(post.content)}</p>
                    ${post.imagePath ? `<img src="${post.imagePath}" alt="Post image" class="post-image">` : ''}
                </div>
                <div class="post-actions">
                    <button class="post-action like-btn" onclick="socialApp.handleLike(${post.id})">
                        <span>👍</span> Like (${post.likeCount})
                    </button>
                    <button class="post-action comment-btn">
                        <span>💬</span> Comment (${post.commentCount})
                    </button>
                </div>
            </div>
        `).join('');
    }

    async handleLike(postId) {
        try {
            const response = await fetch('post/like', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `postId=${postId}`
            });
            
            const result = await response.json();
            
            if (result.success) {
                this.loadPosts();
            }
        } catch (error) {
            this.showNotification('Failed to like post.', 'error');
        }
    }

    showAuthenticatedUI() {
        document.querySelectorAll('.auth-required').forEach(el => el.style.display = 'block');
        document.querySelectorAll('.guest-required').forEach(el => el.style.display = 'none');
        this.loadPosts();
    }

    showLoginUI() {
        document.querySelectorAll('.auth-required').forEach(el => el.style.display = 'none');
        document.querySelectorAll('.guest-required').forEach(el => el.style.display = 'block');
    }

    async handleLogout() {
        try {
            await fetch('auth/logout', { method: 'POST' });
            window.location.reload();
        } catch (error) {
            console.error('Logout error:', error);
        }
    }

    showNotification(message, type = 'info') {
        // Simple notification implementation
        alert(`${type.toUpperCase()}: ${message}`);
    }

    formatTime(timestamp) {
        const date = new Date(timestamp);
        return date.toLocaleDateString() + ' ' + date.toLocaleTimeString();
    }

    escapeHtml(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }
}

// Initialize the application
const socialApp = new SocialNetwork();
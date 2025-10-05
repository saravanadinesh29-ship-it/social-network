package com.socialnetwork.util;

import java.util.HashMap;
import java.util.Map;

public class JsonResponse {
    private boolean success;
    private String message;
    private Map<String, Object> data;
    
    public JsonResponse() {
        this.data = new HashMap<>();
    }
    
    public JsonResponse(boolean success, String message) {
        this();
        this.success = success;
        this.message = message;
    }
    
    public void addData(String key, Object value) {
        this.data.put(key, value);
    }
    
    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public Map<String, Object> getData() { return data; }
    public void setData(Map<String, Object> data) { this.data = data; }
}
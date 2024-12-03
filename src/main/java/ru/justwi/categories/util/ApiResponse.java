package ru.justwi.categories.util;

public class ApiResponse {
    private String message;
    private int status;
    private Object response;

    public ApiResponse(int status, Object response) {
        this.status = status;
        this.response = response;
    }

    public ApiResponse(String message, int status, Object response) {
        this.message = message;
        this.status = status;
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}

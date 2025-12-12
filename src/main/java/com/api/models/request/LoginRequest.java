package com.api.models.request;

public class LoginRequest {

    private String username;
    private String password;

    @Override
    public String toString() {
        return "LoginRequest{" +
                "userName='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }
}

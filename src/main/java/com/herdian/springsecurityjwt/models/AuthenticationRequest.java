package com.herdian.springsecurityjwt.models;
// authenticationRequest is define input user (username & password) to
// authentication method
public class AuthenticationRequest {

    private String username; // input
    private String password; // input

    public AuthenticationRequest() {

    }
    // constructor
    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
    // getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

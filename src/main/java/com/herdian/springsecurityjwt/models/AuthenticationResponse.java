package com.herdian.springsecurityjwt.models;
// authenticationResponse show output JWT value (token)
public class AuthenticationResponse {
    // property
    private final String jwt;
    //constructor
    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }
    // getter
    public String getJwt() {
        return jwt;
    }
}

package com.herdian.springsecurityjwt;

import com.herdian.springsecurityjwt.models.AuthenticationRequest;
import com.herdian.springsecurityjwt.models.AuthenticationResponse;
import com.herdian.springsecurityjwt.services.MyUserDetailsService;
import com.herdian.springsecurityjwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @RequestMapping("/hello")
    public String hello(){ return "Hello World"; }
    // create endpoint untuk mengambil input UserDetails (username&password) dan generate token
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)

    // create method untuk memanggil UserDetails dari body class authenticationRequest
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try{ // jika berhasil try otentikasi username & password dari class authenticationRequest
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }catch (BadCredentialsException e) {
            throw new Exception("username atau password salah", e); // jika otentikasi gagal throw Exception
        }
        // load userDetails untuk meng-otentikasi username untuk menggunakan jwt
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        // memanggil method generateToken() menggunakan class jwtTokenUtil
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        // return response jwt token dilempar kembali ke ResponseEntitity
        return ResponseEntity.ok(new AuthenticationResponse(jwt));


    }
}

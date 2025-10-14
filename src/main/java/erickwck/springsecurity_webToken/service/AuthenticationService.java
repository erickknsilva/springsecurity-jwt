package erickwck.springsecurity_webToken.service;


import erickwck.springsecurity_webToken.entity.Authority;
import erickwck.springsecurity_webToken.entity.Role;
import erickwck.springsecurity_webToken.utilitys.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {


    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public String authenticateUserLogin(String username, String password) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        Set<GrantedAuthority> authoritySet = new HashSet<>();

        User user = new User(username, password, authentication.getAuthorities());

        return jwtUtil.generateToken(user);
    }
}

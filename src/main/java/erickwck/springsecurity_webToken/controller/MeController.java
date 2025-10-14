package erickwck.springsecurity_webToken.controller;

import erickwck.springsecurity_webToken.controller.dto.LoginDto;
import erickwck.springsecurity_webToken.service.AuthenticationService;
import erickwck.springsecurity_webToken.utilitys.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class MeController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestBody @Valid LoginDto request) {


        return  authenticationService.authenticateUserLogin(request.username(), request.password());
    }


}

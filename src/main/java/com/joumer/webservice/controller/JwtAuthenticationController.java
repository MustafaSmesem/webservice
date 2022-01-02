package com.joumer.webservice.controller;


import com.joumer.webservice.config.security.JwtService;
import com.joumer.webservice.document.records.JwtRequest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    private final JwtService jwtService;
    private final MessageSource messageSource;

    public JwtAuthenticationController(JwtService jwtService, MessageSource messageSource) {
        this.jwtService = jwtService;
        this.messageSource = messageSource;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> authenticateUser(@RequestBody JwtRequest authenticationRequest) {
        return jwtService.authenticate(authenticationRequest.username(), authenticationRequest.password());
    }

    @GetMapping("/hello")
    public String helloInternationalized() {
        return messageSource.getMessage("hello.world", null, LocaleContextHolder.getLocale());
    }
}

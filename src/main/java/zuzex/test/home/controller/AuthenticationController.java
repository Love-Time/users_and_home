package zuzex.test.home.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zuzex.test.home.dto.authentication.AuthenticationRefreshRequestDto;
import zuzex.test.home.dto.authentication.AuthenticationRegisterRequestDto;
import zuzex.test.home.dto.authentication.AuthenticationRequestDto;
import zuzex.test.home.dto.authentication.AuthenticationResponseDto;
import zuzex.test.home.exception.ObjectNotFoundException;
import zuzex.test.home.service.AuthenticationService;
import zuzex.test.home.service.JwtService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody @Valid AuthenticationRegisterRequestDto request) throws ObjectNotFoundException {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authentication(@RequestBody @Valid AuthenticationRequestDto request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponseDto> updateAccessToken(@RequestBody @Valid AuthenticationRefreshRequestDto refreshRequest) {
        String userEmail = jwtService.extractUsername(refreshRequest.getRefreshToken());
        if (userEmail != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            System.out.println(userDetails);
            if (jwtService.isTokenValid(refreshRequest.getRefreshToken(), userDetails)) {
                String accessToken = jwtService.generateToken(userDetails, 300000L);
                return new ResponseEntity<>(AuthenticationResponseDto.builder().accessToken(accessToken).build(), HttpStatus.OK);
            }


        }
        Map<String, String> errors = new HashMap<>();
        errors.put("refreshToken", "Token is not valid");
        AuthenticationResponseDto authenticationResponse = AuthenticationResponseDto.builder().errors(errors).build();
        return new ResponseEntity<>(authenticationResponse, HttpStatus.BAD_REQUEST);
    }
}

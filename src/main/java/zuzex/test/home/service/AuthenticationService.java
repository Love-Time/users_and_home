package zuzex.test.home.service;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zuzex.test.home.dto.authentication.AuthenticationRegisterRequestDto;
import zuzex.test.home.dto.authentication.AuthenticationRequestDto;
import zuzex.test.home.dto.authentication.AuthenticationResponseDto;
import zuzex.test.home.entity.Role;
import zuzex.test.home.entity.User;
import zuzex.test.home.exception.ObjectNotFoundException;
import zuzex.test.home.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    private AuthenticationResponseDto getAuthenticationResponse(User user) {
        String accessToken = jwtService.generateToken(user, 300000L);
        String refreshToken = jwtService.generateToken(user, 2592000000L);

        return AuthenticationResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponseDto register(AuthenticationRegisterRequestDto request) throws ObjectNotFoundException {
        User user = User.builder()
                .name(request.getName())
                .age(request.getAge())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        User userCheck = repository.findByEmail(request.getEmail()).orElse(null);
        if (userCheck != null) {
            throw new ObjectNotFoundException("Пользователь с таким email уже существует");
        }

        repository.save(user);
        return this.getAuthenticationResponse(user);
    }

    public AuthenticationResponseDto authenticate(@Valid AuthenticationRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = repository.findByEmail(request.getEmail()).orElseThrow();

        return this.getAuthenticationResponse(user);
    }
}

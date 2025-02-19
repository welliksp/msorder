package br.com.wsp.msorder.controller.v1;

import br.com.wsp.msorder.dto.LoginRequest;
import br.com.wsp.msorder.dto.LoginResponse;
import br.com.wsp.msorder.model.Role;
import br.com.wsp.msorder.model.User;
import br.com.wsp.msorder.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/login")
public class TokenControllerV1 {

    private final JwtEncoder jwtEncoder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public TokenControllerV1(JwtEncoder jwtEncoder, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.jwtEncoder = jwtEncoder;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping()
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        Optional<User> byUsername = userRepository.findByUsername(request.username());

        if (byUsername.isEmpty() || !byUsername.get().passwordEncoder(request, bCryptPasswordEncoder)) {
            throw new BadCredentialsException("USER OR PASSWORD INVALID");
        }

        var now = Instant.now();
        var expiresIn = 300L;

        var scopes = byUsername.get().getRole()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));


        var claims = JwtClaimsSet.builder()
                .issuer("msorder")
                .subject(byUsername.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }
}

package ch.zli.coworkingSpace.controller;

import ch.zli.coworkingSpace.model.TokenResponse;
import ch.zli.coworkingSpace.model.UserEntity;
import ch.zli.coworkingSpace.repository.UserRepository;
import ch.zli.coworkingSpace.security.JwtServiceHMAC;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtServiceHMAC jwtService;

    @Autowired
    private UserRepository userRepository;

    @Operation(
            summary = "Get new token",
            operationId = "getToken",
            tags = {"Authorization"}
    )
    @PostMapping(value = "/token", produces = "application/json")
    public TokenResponse getToken(
            @Parameter(
                    description = "The grant type which will be used to get an new token",
                    required = true,
                    schema = @Schema(allowableValues = {"password", "refresh_token"})
            )
            @RequestParam(name = "grant_type", required = true)
            String grantType,
            @Parameter(description = "If refresh_token is selected as grant type this field is needed")
            @RequestParam(name = "refresh_token", required = false)
            String refreshToken,
            @Parameter(description = "If password is selected as grant type this field is needed", required = false)
            @RequestParam(name = "firstName", required = false)
            String firstName,
            @Parameter(description = "If password is selected as grant type this field is needed", required = false)
            @RequestParam(name = "password", required = false)
            String password) throws GeneralSecurityException, IOException {

        switch (grantType) {
            case "password" -> {
                val optionalMember = userRepository.findByFirstName(firstName);
                if (optionalMember.isEmpty()) {
                    throw new IllegalArgumentException("Firstname or password wrong");
                }

                if (!BCrypt.checkpw(password, optionalMember.get().getPassword())) {
                    throw new IllegalArgumentException("Firstname or password wrong");
                }

                val member = optionalMember.get();

                val id = UUID.randomUUID().toString();
                val scopes = new ArrayList<String>();

                if (member.isAdmin()) {
                    scopes.add("ADMIN");
                }

                val newAccessToken = jwtService.createNewJWT(id, member.getId().toString(), member.getFirstName(), scopes);
                val newRefreshToken = jwtService.createNewJWTRefresh(id, member.getId().toString());

                return new TokenResponse(newAccessToken, newRefreshToken, "Bearer", LocalDateTime.now().plusDays(14).toEpochSecond(ZoneOffset.UTC), LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC));
            }
            case "refresh_token" -> {
                val jwt = jwtService.verifyJwt(refreshToken, false);

                val optionalMember = userRepository.findById(UUID.fromString(jwt.getClaim("user_id").asString()));
                if (optionalMember.isEmpty()) {
                    throw new IllegalArgumentException("Invalid refresh token");
                }

                val member = optionalMember.get();

                val id = UUID.randomUUID().toString();
                val scopes = new ArrayList<String>();

                if (member.isAdmin()) {
                    scopes.add("ADMIN");
                }

                val newAccessToken = jwtService.createNewJWT(id, member.getId().toString(), member.getFirstName(), scopes);
                val newRefreshToken = jwtService.createNewJWTRefresh(id, member.getId().toString());

                return new TokenResponse(newAccessToken, newRefreshToken, "Bearer", LocalDateTime.now().plusDays(14).toEpochSecond(ZoneOffset.UTC), LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC));
            }
            default -> throw new IllegalArgumentException("Not supported grant type: " + grantType);
        }
    }

    @Operation(
            summary = "Register user",
            operationId = "register",
            tags = {"Authorization"}
    )
    @PostMapping(value = "/register", produces = "application/json")
    public TokenResponse register(
            @Parameter(description = "First Name", required = true)
            @RequestParam(name = "firstName", required = true)
                    String firstName,
            @Parameter(description = "Last Name", required = true)
            @RequestParam(name = "lastName", required = true)
                    String lastName,
            @Parameter(description = "gender", required = true)
            @RequestParam(name = "gender", required = true)
                    String gender,
            @Parameter(description = "job", required = true)
            @RequestParam(name = "jobb", required = true)
                    String job,
            @Parameter(description = "user description", required = true)
            @RequestParam(name = "firstName", required = true)
                    String userDescription,
            @Parameter(description = "Password", required = true)
            @RequestParam(name = "password", required = true)
            String password
    ) throws GeneralSecurityException, IOException {
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        val newMember = new UserEntity(UUID.randomUUID(), firstName, lastName, gender, job, userDescription, false, password);
        userRepository.save(newMember);

        return getToken("password", "", firstName, password);
    }
}

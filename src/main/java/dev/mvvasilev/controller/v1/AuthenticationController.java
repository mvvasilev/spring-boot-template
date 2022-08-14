package dev.mvvasilev.controller.v1;

import dev.mvvasilev.controller.ApiController;
import dev.mvvasilev.model.ApiResponse;
import dev.mvvasilev.model.dto.AccessTokenDTO;
import dev.mvvasilev.model.dto.UserRegistrationDTO;
import dev.mvvasilev.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController implements ApiController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AccessTokenDTO>> authenticate(@RequestBody @Valid @NonNull @NotEmpty String usernamePswHash) {
        return ok(authenticationService.login(usernamePswHash));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Boolean>> register(@RequestBody @Valid UserRegistrationDTO dto) {
        return ok(authenticationService.register(dto));
    }

    @PostMapping("/confirm-registration")
    public ResponseEntity<ApiResponse<Boolean>> confirmRegistration(@RequestBody @Valid @NotEmpty String registrationCode) {
        return ok(authenticationService.confirmRegistration(registrationCode));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AccessTokenDTO>> refresh(@RequestBody @Valid @NonNull @NotEmpty String refreshToken) {
        return ok(authenticationService.refresh(refreshToken));
    }
}

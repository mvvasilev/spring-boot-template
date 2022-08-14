package dev.mvvasilev.controller.v1;

import dev.mvvasilev.controller.ApiController;
import dev.mvvasilev.model.ApiResponse;
import dev.mvvasilev.model.dto.UserSelfDTO;
import dev.mvvasilev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController implements ApiController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/self")
    @PreAuthorize("SCOPE_SELF")
    public ResponseEntity<ApiResponse<UserSelfDTO>> self() {
        return ok(userService.self());
    }
}

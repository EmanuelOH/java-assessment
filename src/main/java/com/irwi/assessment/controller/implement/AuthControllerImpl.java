package com.irwi.assessment.controller.implement;

import com.irwi.assessment.application.dtos.reponses.AuthResponseDto;
import com.irwi.assessment.application.dtos.requests.AuthRequestDto;
import com.irwi.assessment.controller.interfaces.IAuthController;
import com.irwi.assessment.domain.ports.service.interfaces.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
@Tag(name = "Authentication", description = "Manages authentication request.")
@CrossOrigin("*")
public class AuthControllerImpl implements IAuthController {

    @Autowired
    private final IAuthService authService;

    @PostMapping("login")
    @Operation(
            summary = "Authenticate a user.",
            description = "Provides a user's data to generate a token that allows them to use private endpoints if their role allows it."
    )
    @Override
    public ResponseEntity<AuthResponseDto> login(@Validated @RequestBody AuthRequestDto requestDto) {
        return ResponseEntity.ok(this.authService.login(requestDto));
    }
}

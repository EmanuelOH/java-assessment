package com.irwi.assessment.controller.implement;

import com.irwi.assessment.application.dtos.reponses.AuthResponseDto;
import com.irwi.assessment.application.dtos.reponses.UserResponseDto;
import com.irwi.assessment.application.dtos.requests.DoctorRequestDto;
import com.irwi.assessment.application.dtos.requests.PatientRequestDto;
import com.irwi.assessment.application.dtos.requests.UserRequestDto;
import com.irwi.assessment.controller.interfaces.IUserController;
import com.irwi.assessment.domain.entities.UserEntity;
import com.irwi.assessment.domain.enums.Roles;
import com.irwi.assessment.domain.ports.service.interfaces.IDoctorService;
import com.irwi.assessment.domain.ports.service.interfaces.IPatientService;
import com.irwi.assessment.domain.ports.service.interfaces.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
@Tag(name = "User", description = "Manage user-related request.")
@CrossOrigin("*")
public class UserControllerImpl implements IUserController {

    @Autowired
    private final IUserService userService;

    @Autowired
    private final IDoctorService doctorService;

    @Autowired
    private final IPatientService patientService;

    //@SecurityRequirement(name = "bearerAuth")
    @GetMapping("/readAll")
    @Operation(
            summary = "List all users.",
            description = "Provide the token to validate the permissions and obtain the list of users."
    )
    @Override
    public ResponseEntity<List<UserResponseDto>> readAll() {
        return ResponseEntity.ok(this.userService.readAll());
    }

   // @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    @Operation(
            summary = "Get user by ID.",
            description = "Retrieve a user's details by their ID, with proper authentication."
    )
    @Override
    public ResponseEntity<UserResponseDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(this.userService.readById(id));
    }

    //@SecurityRequirement(name = "bearerAuth")
    @PostMapping("register/admin")
    @Operation(
            summary = "Create a user with specified role.",
            description = "Provides the user data and role to create it and validates the permissions."
    )
    @Override
    public ResponseEntity<AuthResponseDto> register(
            @Validated @RequestBody UserRequestDto requestDto) {
        return ResponseEntity.ok(this.userService.register(requestDto, Roles.ADMIN));
    }

    @SecurityRequirement(name = "bearerAuth")
    @Override
    public ResponseEntity<UserEntity> update(@Validated @RequestBody UserRequestDto userRequestDto,
    @PathVariable Long id) {
        UserEntity updatedUser = this.userService.update(userRequestDto, id);

        return ResponseEntity.ok(updatedUser);
    }

    //@SecurityRequirement(name = "bearerAuth")
    @PostMapping("register/doctor")
    @Operation(
            summary = "Create a DOCTOR user.",
            description = "Create a user with DOCTOR role."
    )
    public ResponseEntity<AuthResponseDto> registerDoctor(@Validated @RequestBody DoctorRequestDto requestDto) {
        return ResponseEntity.ok(this.doctorService.registerDoctor(requestDto));
    }

    //@SecurityRequirement(name = "bearerAuth")
    @PostMapping("register/patient")
    @Operation(
            summary = "Create a PATIENT user.",
            description = "Create a user with PATIENT role."
    )
    public ResponseEntity<AuthResponseDto> registerPatient(@Validated @RequestBody PatientRequestDto requestDto) {
        return ResponseEntity.ok(this.patientService.registerPatient(requestDto));
    }

}

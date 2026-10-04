package dev.matheushnt.toolboxapi.controller;

import dev.matheushnt.toolboxapi.documentation.AuthenticationControllerApi;
import dev.matheushnt.toolboxapi.dtos.SignInRequest;
import dev.matheushnt.toolboxapi.dtos.SignInResponse;
import dev.matheushnt.toolboxapi.dtos.SignUpRequest;
import dev.matheushnt.toolboxapi.dtos.SignUpResponse;
import dev.matheushnt.toolboxapi.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationControllerApi {

    private final AuthenticationService service;

    @PostMapping("/sign-up")
    @Override
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.signUp(request));
    }

    @PostMapping("/sign-in")
    @Override
    public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(service.signIn(request));
    }

}

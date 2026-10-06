package dev.matheushnt.toolboxapi.controller;

import dev.matheushnt.toolboxapi.dtos.ToolRequest;
import dev.matheushnt.toolboxapi.dtos.ToolResponse;
import dev.matheushnt.toolboxapi.entity.User;
import dev.matheushnt.toolboxapi.service.CreateToolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tools")
@RequiredArgsConstructor
public class ToolController {

    private final CreateToolService createToolService;

    @PostMapping
    public ResponseEntity<ToolResponse> create(
        @Valid @RequestBody ToolRequest request,
        @AuthenticationPrincipal UserDetails principal
    ) {
        User user = (User) principal;
        return ResponseEntity.status(HttpStatus.CREATED).body(createToolService.execute(user, request));
    }

}

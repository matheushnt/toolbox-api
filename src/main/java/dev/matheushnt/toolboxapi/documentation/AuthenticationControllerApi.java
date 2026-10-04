package dev.matheushnt.toolboxapi.documentation;

import dev.matheushnt.toolboxapi.dtos.SignInRequest;
import dev.matheushnt.toolboxapi.dtos.SignInResponse;
import dev.matheushnt.toolboxapi.dtos.SignUpRequest;
import dev.matheushnt.toolboxapi.dtos.SignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

@Tag(name = "Autenticação", description = "Operações relacionadas a Autenticação")
public interface AuthenticationControllerApi {

    @Operation(summary = "Registrar", description = "Registra um novo usuário")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Usuário registrado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SignUpResponse.class))
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Outro usuário já cadastrado com o mesmo e-mail",
            content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class))
        ),
        @ApiResponse(
            responseCode = "422",
            description = "A requisição não pôde ser processada devido a erro de validação ou regra de negócio",
            content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Solicitação enviada é inválida",
            content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class))
        )
    })
    ResponseEntity<SignUpResponse> signUp(SignUpRequest request);

    @Operation(summary = "Login", description = "Efetuar login")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Login efetuado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SignInResponse.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Crendenciais informadas são inválidas",
            content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class))
        ),
        @ApiResponse(
            responseCode = "422",
            description = "A requisição não pôde ser processada devido a erro de validação ou regra de negócio",
            content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Solicitação enviada é inválida",
            content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class))
        )
    })
    ResponseEntity<SignInResponse> signIn(SignInRequest request);

}

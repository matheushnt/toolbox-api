package dev.matheushnt.toolboxapi.documentation;

import dev.matheushnt.toolboxapi.dtos.ToolRequest;
import dev.matheushnt.toolboxapi.dtos.ToolResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

@Tag(name = "Ferramentas", description = "Operações relacionadas ao gerenciamento de ferramentas")
@SecurityRequirement(name = "bearerAuth")
public interface ToolControllerApi {

    @Operation(summary = "Adicionar", description = "Adicionar uma nova ferramenta")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Ferramenta adicionada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ToolResponse.class))
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
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Autenticação necessária para acessar este recurso",
            content = @Content(
                mediaType = "application/problem+json",
                schema = @Schema(implementation = ProblemDetail.class)
            )
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Usuário não possui permissão para acessar este recurso",
            content = @Content(
                mediaType = "application/problem+json",
                schema = @Schema(implementation = ProblemDetail.class)
            )
        )
    })
    ResponseEntity<ToolResponse> create(ToolRequest request, UserDetails principal);

    @Operation(summary = "Listar", description = "Listar as ferramentas do usuário")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista as ferramentas cadastradas",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = ToolResponse.class))
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Autenticação necessária para acessar este recurso",
            content = @Content(
                mediaType = "application/problem+json",
                schema = @Schema(implementation = ProblemDetail.class)
            )
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Usuário não possui permissão para acessar este recurso",
            content = @Content(
                mediaType = "application/problem+json",
                schema = @Schema(implementation = ProblemDetail.class)
            )
        )
    })
    ResponseEntity<List<ToolResponse>> list(@Parameter(name = "tag", description = "Tag utilizada para filtrar as ferramentas") String tag, UserDetails principal);

    @Operation(summary = "Excluir", description = "Excluir uma ferramenta do usuário")
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Exclui uma ferramenta pesquisada por ID"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Ferramenta não encontrada",
            content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Autenticação necessária para acessar este recurso",
            content = @Content(
                mediaType = "application/problem+json",
                schema = @Schema(implementation = ProblemDetail.class)
            )
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Usuário não possui permissão para acessar este recurso",
            content = @Content(
                mediaType = "application/problem+json",
                schema = @Schema(implementation = ProblemDetail.class)
            )
        )
    })
    ResponseEntity<Void> delete(@Parameter(name = "toolId", description = "ID da ferramenta", required = true, in = ParameterIn.PATH) UUID toolId, UserDetails principal);
}

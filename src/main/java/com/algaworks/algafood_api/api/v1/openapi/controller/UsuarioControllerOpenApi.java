package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.DTO.SenhaDTO;
import com.algaworks.algafood_api.api.v1.model.DTO.UsuarioComSenhaDTO;
import com.algaworks.algafood_api.api.v1.model.DTO.UsuarioDTO;
import com.algaworks.algafood_api.api.v1.model.UsuarioModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Usuários")
@SecurityRequirement(name = "security_auth")
public interface UsuarioControllerOpenApi {

    @Operation(summary = "Busca todos os usuários")
    CollectionModel<UsuarioModel> all();

    @Operation(summary = "Busca de um usuário por id", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404" ,
                    description = "Usuário não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError"))),
            @ApiResponse(responseCode = "400" ,
                    description = "Erro no id do usuário" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    UsuarioModel findById(
            @Parameter(example = "1", description = "Id do usuário" , required = true) Long usuarioId);

    @Operation(summary = "Cadastra um novo usuário", responses = {
            @ApiResponse(responseCode = "201" , description = "Usuário cadastrado")})
    UsuarioModel add(
            @RequestBody(description = "Representação de um novo usuário", required = true) UsuarioComSenhaDTO usuarioDTO);

    @Operation(summary = "Atualiza as informações do usuário por id", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404" ,
                    description = "Usuário não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    UsuarioModel save(
            @Parameter(example = "1", description = "Id do usuário" , required = true) Long usuarioId,
            @RequestBody(description = "Representação de um usuário com dados atualizados", required = true)UsuarioDTO usuarioDTO);

    @Operation(summary = "Salva uma senha de um usuário por id", responses = {
            @ApiResponse(responseCode = "204", description = "Senha salva"),
            @ApiResponse(responseCode = "404" ,
                    description = "Usuário não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    ResponseEntity<Void> savePassword(
            @Parameter(example = "1", description = "Id do usuário" , required = true)Long usuarioId,
            @RequestBody(description = "Representação da senha de um usuário", required = true) SenhaDTO senhaDTO);

}

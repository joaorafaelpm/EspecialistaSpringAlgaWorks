package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.DTO.FotoProdutoDTO;
import com.algaworks.algafood_api.api.v1.model.FotoProdutoModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@SecurityRequirement(name = "security_auth")
public interface RestauranteProdutoFotoControllerOpenApi {

    FotoProdutoModel adicionarFoto(Long restauranteId, Long produtoId, FotoProdutoDTO fotoProdutoDTO,
                                   MultipartFile arquivo) throws IOException;

    ResponseEntity<Void> removerFoto(Long restauranteId, Long produtoId);

    FotoProdutoModel pegarFoto(Long restauranteId,Long produtoId);

    ResponseEntity<?> servirFoto(Long restauranteId, Long produtoId, String acceptHeader)
            throws HttpMediaTypeNotAcceptableException;

}

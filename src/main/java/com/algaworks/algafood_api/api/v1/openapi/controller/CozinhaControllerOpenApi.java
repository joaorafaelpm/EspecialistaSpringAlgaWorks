package com.algaworks.algafood_api.api.v1.openapi.controller;
import com.algaworks.algafood_api.api.v1.model.CozinhaModel;
import com.algaworks.algafood_api.api.v1.model.DTO.CozinhaDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface CozinhaControllerOpenApi {

    PagedModel<CozinhaModel> all(Pageable pageable);

    CozinhaModel getById(Long cozinhaId);

    CozinhaModel add(CozinhaDTO cozinhaDTO);

    CozinhaModel save(Long cozinhaId, CozinhaDTO cozinhaDTO);

    ResponseEntity<Void> remove(Long cozinhaId);

}
package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.DTO.FormaPagamentoDTO;
import com.algaworks.algafood_api.api.v1.model.FormaPagamentoModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@SecurityRequirement(name = "security_auth")
public interface FormaPagamentoControllerOpenApi {

    ResponseEntity<CollectionModel<FormaPagamentoModel>> all(ServletWebRequest request);

    ResponseEntity<FormaPagamentoModel> getById(Long formaPagamentoId, ServletWebRequest request);

    FormaPagamentoModel add(FormaPagamentoDTO formaPagamentoDTO);

    FormaPagamentoModel save(Long formaPagamentoId,FormaPagamentoDTO formaPagamentoDTO);

    ResponseEntity<Void> remove(Long formaPagamentoId);

}
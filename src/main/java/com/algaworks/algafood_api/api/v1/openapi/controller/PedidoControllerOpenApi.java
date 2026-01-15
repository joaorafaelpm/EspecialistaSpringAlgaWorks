package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.DTO.PedidoDTO;
import com.algaworks.algafood_api.api.v1.model.PedidoModel;
import com.algaworks.algafood_api.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood_api.domain.filter.PedidoFilter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@SecurityRequirement(name = "security_auth")
public interface PedidoControllerOpenApi {

    PagedModel<PedidoResumoModel> pesquisar(PedidoFilter pedidoFilter, Pageable pageable);

    PedidoModel salvar(PedidoDTO pedidoDTO);

    PedidoModel pegarUm(String codigoPedido);

}

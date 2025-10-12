package com.algaworks.algafood_api.domain.repository.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Getter
@Setter
public class PedidoFilter {

    private Long clienteId ;
    private Long restauranteId ;

    private OffsetDateTime dataCriacaoInicio;
    private OffsetDateTime dataCriacaoFim;


}

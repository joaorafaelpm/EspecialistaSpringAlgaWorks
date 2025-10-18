package com.algaworks.algafood_api.api.model.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FormaPagamentoIdDTO {

    @NotNull
    private Long id ;

}

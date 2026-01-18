package com.algaworks.algafood_api.api.v1.model.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FormaPagamentoIdDTO {

    @Schema(example = "1")
    @NotNull
    private Long id ;

}

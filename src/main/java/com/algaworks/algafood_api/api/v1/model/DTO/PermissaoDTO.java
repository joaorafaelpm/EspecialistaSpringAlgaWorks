package com.algaworks.algafood_api.api.v1.model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PermissaoDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

}

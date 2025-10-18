package com.algaworks.algafood_api.api.model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SenhaDTO {

    @NotBlank
    private String senhaAtual ;
    @NotBlank
    private String novaSenha ;

}

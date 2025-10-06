package com.algaworks.algafood_api.api.model.input;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CozinhaDTO {

    @NotBlank
    private String nome ;

}

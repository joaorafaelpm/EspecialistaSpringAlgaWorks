package com.algaworks.algafood_api.api.model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GrupoDTO {

    @NotBlank
    private String nome ;

}

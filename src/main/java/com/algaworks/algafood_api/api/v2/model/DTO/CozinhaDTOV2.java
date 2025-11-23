package com.algaworks.algafood_api.api.v2.model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CozinhaDTOV2 {

    @NotBlank
    private String nomeCozinha ;

}

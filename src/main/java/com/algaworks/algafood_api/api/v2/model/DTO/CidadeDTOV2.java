package com.algaworks.algafood_api.api.v2.model.DTO;

import com.algaworks.algafood_api.api.v1.model.DTO.EstadoIdDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CidadeDTOV2 {

    @NotBlank
    private String nome  ;

    @NotNull
    private Long idEstado;

}

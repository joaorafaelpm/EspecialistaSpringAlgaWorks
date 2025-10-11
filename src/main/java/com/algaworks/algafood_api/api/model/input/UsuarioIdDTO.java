package com.algaworks.algafood_api.api.model.input;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioIdDTO {

    @NotNull
    private Long id;

}

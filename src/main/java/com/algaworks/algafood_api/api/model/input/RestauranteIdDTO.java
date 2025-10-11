package com.algaworks.algafood_api.api.model.input;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestauranteIdDTO {

    @NotNull
    private Long id ;

}

package com.algaworks.algafood_api.api.v1.model.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SenhaDTO {

    @Schema(example = "123" , type = "string")
    @NotBlank
    private String senhaAtual ;
    @Schema(example = "abc", type = "string")
    @NotBlank
    private String novaSenha ;

}

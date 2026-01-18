package com.algaworks.algafood_api.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EnderecoModel {

    @Schema(example = "13068-603")
    @NotBlank
    private String cep ;

    @Schema(example = "Rua Sta. Luzia")
    @NotBlank
    private String logradouro ;
    @Schema(example = "109")
    @NotBlank
    private String numero ;

    @Schema(example = "Caixa d'gua Sanasa")
    private String complemento ;

    @Schema(example = "Jardim Aparecida")
    @NotBlank
    private String bairro ;
    private CidadeResumoModel cidade ;

}

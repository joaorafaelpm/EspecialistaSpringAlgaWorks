package com.algaworks.algafood_api.api.v1.model.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
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

public class EnderecoDTO {

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

    @NotNull
    @Valid
    private CidadeIdDTO cidade ;


}

package com.algaworks.algafood_api.api.v1.model.DTO;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EnderecoDTO {

    @NotBlank
    private String cep ;

    @NotBlank
    private String logradouro ;
    @NotBlank
    private String numero ;

    private String complemento ;

    @NotBlank
    private String bairro ;

    @NotNull
    @Valid
    private CidadeIdDTO cidade ;


}

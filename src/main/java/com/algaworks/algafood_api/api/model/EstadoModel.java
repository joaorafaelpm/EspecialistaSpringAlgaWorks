package com.algaworks.algafood_api.api.model;


import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
public class EstadoModel extends RepresentationModel<EstadoModel> {

    private Long id ;
    private String nome ;

}


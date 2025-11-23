package com.algaworks.algafood_api.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
@AllArgsConstructor
public class RestauranteModel extends RepresentationModel<RestauranteModel> {

//    Retirei todos os Json view por que agora vamos usar classes de representação do modelo
    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaModel cozinha;

    private Boolean ativo ;
    private Boolean aberto ;
    private EnderecoModel endereco ;

}



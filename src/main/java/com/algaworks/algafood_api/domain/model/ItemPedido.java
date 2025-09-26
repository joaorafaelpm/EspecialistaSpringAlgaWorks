package com.algaworks.algafood_api.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id ;

    private Integer quantidade ;
    private BigDecimal precoUnitario ;
    private BigDecimal precoTotal ;
    private String observacao ;

    @JoinColumn(name = "produto_id" , nullable = false)
    private Produto produto;

    @JoinColumn(name = "pedido_id" , nullable = false)
    private Pedido pedido;


}

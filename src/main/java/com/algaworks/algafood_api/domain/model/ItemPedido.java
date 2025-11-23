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

    @ManyToOne
    @JoinColumn(name = "pedido_id" , nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id" , nullable = false)
    private Produto produto;

    public BigDecimal definirPrecoTotal () {
        BigDecimal precoUnitario = this.getPrecoUnitario();
        Integer quantidade = this.getQuantidade();

        if (precoUnitario == null) {
            precoUnitario = BigDecimal.ZERO;
        }

        if (quantidade == null) {
            quantidade = 0;
        }

        BigDecimal precoTotal = getPrecoUnitario().multiply(BigDecimal.valueOf(quantidade));
        setPrecoTotal(precoTotal);
        return precoTotal;
    }

}

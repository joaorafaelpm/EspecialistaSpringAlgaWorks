package com.algaworks.algafood_api.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nome ;

    @JoinColumn(name = "taxa_frete" , nullable = false)
    private BigDecimal taxaFrete;

    @ManyToOne
    @JoinColumn(name = "cozinha_id" , nullable = false)
    private Cozinha cozinha ;

    public Restaurante (String nome , BigDecimal taxaFrete) {
        this.nome = nome ;
        this.taxaFrete = taxaFrete ;
    }

}

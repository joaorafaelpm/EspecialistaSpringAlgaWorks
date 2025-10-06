package com.algaworks.algafood_api.domain.model;

import com.algaworks.algafood_api.core.validation.Groups;
import com.algaworks.algafood_api.core.validation.PositivoOuZero;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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

    @Embedded
    private Endereco endereco;

    @CreationTimestamp
    @Column(columnDefinition = "datetime" , name = "data_cadastro")
    private OffsetDateTime dataCadastro ;

    @UpdateTimestamp
    @Column(columnDefinition = "datetime" , name = "data_atualizacao" , nullable = false)
    private OffsetDateTime dataAtualizacao;

    @OneToMany(mappedBy = "restaurante" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Produto> produtos = new ArrayList<>();


    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento" ,
            joinColumns = @JoinColumn(name = "restaurante_id") ,
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private List<FormaPagamento> formasPagamento = new ArrayList<>();


}

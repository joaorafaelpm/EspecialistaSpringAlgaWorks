package com.algaworks.algafood_api.domain.model;

import com.algaworks.algafood_api.core.validation.Groups;
import com.algaworks.algafood_api.core.validation.PositivoOuZero;
import com.algaworks.algafood_api.core.validation.TaxaFrete;
import com.algaworks.algafood_api.core.validation.ValorZeroIncluiDescricao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

// Validando mais de um argumento na classe, por exemplo, a gente pode definir para um restaurante com taxaFrete gratis/0, sendo obrigada a colocar uma descrição no nome de "Frete Grátis".
// Um exemplo melhor seria um restaurante que não quer manter um nível de fidelidade com o cliente não deve ter a classe de fidelidade iniciada, ou deve ser nula
//@ValorZeroIncluiDescricao(valorField = "taxaFrete" ,
//        descricaoField="nome" , descricaoObrigatoria = "Frete grátis")

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

    @NotBlank
    private String nome ;

    @PositivoOuZero
    @JoinColumn(name = "taxa_frete" , nullable = false)
    private BigDecimal taxaFrete;

    @Valid
    @ConvertGroup(from = Default.class , to = Groups.CozinhaId.class)
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

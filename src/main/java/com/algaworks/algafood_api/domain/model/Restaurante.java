package com.algaworks.algafood_api.domain.model;

import com.algaworks.algafood_api.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

//    NotNull - não pode ser nulo
//    NotEmpty - não pode ser nulo e nem vazio ("")
//    NotBlank - não pode ser nulo e nem vazio ("") e nem com espaços ("    ")
    @NotBlank(groups = Groups.CozinhaId.class)
    private String nome ;

    @DecimalMin(value = "0" , groups = Groups.CozinhaId.class)
    @JoinColumn(name = "taxa_frete" , nullable = false)
    private BigDecimal taxaFrete;

    @Valid
//  Converto o padrão do Jakarta nas condições do meu CozinhaId, então todo parâmetro anotado lá dentro com (group = Group.CozinhaId.class) vai ser validado também
    @ConvertGroup(from = Default.class , to = Groups.CozinhaId.class)
    @NotNull
    @ManyToOne
    @JoinColumn(name = "cozinha_id" , nullable = false)
    private Cozinha cozinha ;

//    Isso significa que a classe Endereço faz parte de restaurante e vice-versa
    @Embedded
    @JsonIgnore
    private Endereco endereco;

    @CreationTimestamp
    @Column(columnDefinition = "datetime" , name = "data_cadastro")
//    @JsonIgnore
    private LocalDateTime dataCadastro ;

    @UpdateTimestamp
    @Column(columnDefinition = "datetime" , name = "data_atualizacao" , nullable = false)
//    @JsonIgnore
    private LocalDateTime dataAtualizacao;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurante" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Produto> produtos = new ArrayList<>();


    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento" ,
            joinColumns = @JoinColumn(name = "restaurante_id") ,
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private List<FormaPagamento> formasPagamento = new ArrayList<>();

    public Restaurante (String nome , BigDecimal taxaFrete) {
        this.nome = nome ;
        this.taxaFrete = taxaFrete ;
    }


}

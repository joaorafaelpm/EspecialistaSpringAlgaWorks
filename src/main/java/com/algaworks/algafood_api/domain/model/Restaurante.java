package com.algaworks.algafood_api.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @JoinColumn(nullable = false)
    private String nome ;

    @JoinColumn(name = "taxa_frete" , nullable = false)
    private BigDecimal taxaFrete;

    @ManyToOne
    @JoinColumn(name = "cozinha_id" , nullable = false)
    private Cozinha cozinha ;

    @Embedded
    private Endereco endereco;

    private Boolean ativo = Boolean.TRUE ;
    private Boolean aberto = Boolean.FALSE;

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
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "restaurante_usuario_responsavel" ,
            joinColumns = @JoinColumn(name = "restaurante_id") ,
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<Usuario> usuarios = new HashSet<>();

    public void ativar () {
        setAtivo(true);
    }
    public void inativar () {
        setAtivo(false);
    }
    public void abrir () {
        setAberto(true);
    }
    public void fechar () {
        setAberto(false);
    }

    public boolean associarFormaPagamento(FormaPagamento formaPagamento) {
        return getFormasPagamento().add(formaPagamento);
    }
    public boolean desassociarFormaPagamento (FormaPagamento formaPagamento) {
        return getFormasPagamento().remove(formaPagamento);
    }
    public boolean associarUsuarioResponsavel(Usuario usuario) {
        return getUsuarios().add(usuario);
    }
    public boolean desassociarUsuarioResponsavel(Usuario usuario) {
        return getUsuarios().remove(usuario);
    }

    public boolean adicionarProduto (Produto produto) {
        return getProdutos().add(produto);
    }
    public boolean removerProduto (Produto produto) {
        return getProdutos().remove(produto);
    }

    public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
        return getFormasPagamento().contains(formaPagamento);
    }

    public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
        return !aceitaFormaPagamento(formaPagamento);
    }

}

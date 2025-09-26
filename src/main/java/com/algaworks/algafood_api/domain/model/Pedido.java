package com.algaworks.algafood_api.domain.model;

import com.algaworks.algafood_api.domain.model.enuns.StatusPedido;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id ;

    private BigDecimal subTotal ;
    private BigDecimal taxaFrete ;
    private BigDecimal valorTotal ;

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    private LocalDateTime dataConfirmacao ;
    private LocalDateTime dataCancelamento;
    private LocalDateTime dataEntrega;

    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido = StatusPedido.CRIADO ;

    @JoinColumn(name = "forma_pagamento_id" , nullable = false)
    private FormaPagamento formaPagamento ;

    @JoinColumn(name = "restaurante_id" , nullable = false)
    private Restaurante restaurante ;

    @JoinColumn(name = "cliente_id" , nullable = false)
    private Usuario cliente ;

    @Embedded
    private Endereco enderecoEntrega ;

    @OneToMany(mappedBy = "pedido" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<ItemPedido> itens ;



}


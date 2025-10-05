package com.algaworks.algafood_api.domain.model;

import com.algaworks.algafood_api.domain.model.enuns.StatusPedido;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao ;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pedido")
    private StatusPedido statusPedido = StatusPedido.CRIADO ;

    @ManyToOne
    @JoinColumn(nullable = false)
    private FormaPagamento formaPagamento ;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(nullable = false , name = "cliente_usuario_id")
    private Usuario cliente ;

    @Embedded
    private Endereco enderecoEntrega ;

    @OneToMany(mappedBy = "pedido" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<ItemPedido> itens = new ArrayList<>();



}


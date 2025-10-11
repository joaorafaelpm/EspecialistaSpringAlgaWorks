package com.algaworks.algafood_api.domain.model;

import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.model.enuns.StatusPedido;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    private String codigo;

    private BigDecimal subtotal ;
    private BigDecimal taxaFrete ;
    private BigDecimal valorTotal ;

    @CreationTimestamp
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao ;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pedido" , nullable = false)
    private StatusPedido statusPedido = StatusPedido.CRIADO ;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @OneToMany(mappedBy = "pedido" , cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    public void calcularValorTotalPedido() {
        getItens().forEach(ItemPedido::definirPrecoTotal);

        this.subtotal = getItens().stream()
                .map(ItemPedido::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.valorTotal = this.subtotal.add(this.taxaFrete);
    }


    public void confirmar () {
        setStatusPedido(StatusPedido.CONFIRMADO);
        setDataConfirmacao(OffsetDateTime.now());
    }
    public void entregar () {
        setStatusPedido(StatusPedido.ENTREGUE);
        setDataEntrega(OffsetDateTime.now());
    }
    public void cancelar () {
        setStatusPedido(StatusPedido.CANCELADO);
        setDataCancelamento(OffsetDateTime.now());
    }

//    Sobrescrevendo o set para se tornar privado
    private void setStatusPedido (StatusPedido novoStatus){
//        Só posso alterar se o status atual receber o status antigo necessário, ou seja se o status atual for CONFIRMADO ele já deve ter recebido o status de CRIADO
        if (getStatusPedido().naoPodeAlterarPara(novoStatus)) {
            throw new NegocioException(String.format(
                    "Status do pedido '%s' não pode ser alterado de '%s' para '%s'" ,
                    getCodigo(), getStatusPedido().getDescricao() , novoStatus.getDescricao()
            ));
        }

        this.statusPedido = novoStatus;
    }

//    Antes de persistir essa entidade no banco de dados, execute essa função:
    @PrePersist
    private void gerarCodigo () {
        setCodigo(UUID.randomUUID().toString());
    }

}


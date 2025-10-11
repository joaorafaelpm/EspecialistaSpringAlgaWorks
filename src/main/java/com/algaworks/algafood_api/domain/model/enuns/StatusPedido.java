package com.algaworks.algafood_api.domain.model.enuns;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {
    CRIADO("Criado") ,
    CONFIRMADO("Confirmado" , CRIADO),
    ENTREGUE("Entregue" , CONFIRMADO),
    CANCELADO("Cancelado" , CRIADO , CONFIRMADO);

    private String descricao ;
    private List<StatusPedido> statusAnteriores ;

    public String getDescricao() {
        return this.descricao;
    }
    StatusPedido (String descricao , StatusPedido... statusAnteriores) {
        this.descricao = descricao;
        this.statusAnteriores = Arrays.asList(statusAnteriores);
    }

    public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
//        Se não receber um statusAnterior corretamente, não pode ser alterado
        return !novoStatus.statusAnteriores.contains(this) ;
    }

}

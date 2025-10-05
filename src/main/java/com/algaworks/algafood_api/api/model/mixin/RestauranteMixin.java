package com.algaworks.algafood_api.api.model.mixin;

import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.model.Endereco;
import com.algaworks.algafood_api.domain.model.FormaPagamento;
import com.algaworks.algafood_api.domain.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

// A dor atual é, a nossa classe de Restaurante estava usando métodos @JsonIgnore, o que é uma má prática, já que indiretamente a nossa classe de domínio teoricamente controla a resposta da API, por isso geramos um mixin entre a classe principal e essa
public abstract class RestauranteMixin {


        @JsonIgnoreProperties(value = "nome" , allowGetters = true)
        private Cozinha cozinha ;

        @JsonIgnore
        private Endereco endereco;

//        @JsonIgnore
        private OffsetDateTime dataCadastro ;

//        @JsonIgnore
        private OffsetDateTime dataAtualizacao;

        @JsonIgnore
        private List<Produto> produtos = new ArrayList<>();

        @JsonIgnore
        private List<FormaPagamento> formasPagamento = new ArrayList<>();

}

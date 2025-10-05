package com.algaworks.algafood_api.api.model.mixin;


import com.algaworks.algafood_api.domain.model.Cidade;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public abstract class EstadoMixin {

    @JsonIgnore
    private List<Cidade> cidades = new ArrayList<>();

}

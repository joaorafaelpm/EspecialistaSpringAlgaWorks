package com.algaworks.algafood_api.api.v1.assembler;

import com.algaworks.algafood_api.api.v1.AlgaLinks;
import com.algaworks.algafood_api.api.v1.assembler.mapper.ProdutoMapper;
import com.algaworks.algafood_api.api.v1.model.ProdutoModel;
import com.algaworks.algafood_api.core.security.AlgaSecurity;
import com.algaworks.algafood_api.domain.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class ProdutoAssembler extends RepresentationModelAssemblerSupport<Produto , ProdutoModel> {
    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private ProdutoMapper produtoMapper;

    @Autowired
    private AlgaSecurity algaSecurity;

    public ProdutoAssembler () {
        super(Produto.class , ProdutoModel.class);
    }


    @Override
    public ProdutoModel toModel(Produto entity) {
        ProdutoModel produtoModel = produtoMapper.toModel(entity);

        Long restauranteId = entity.getRestaurante().getId();

        if (algaSecurity.podeConsultarRestaurantes()) {
            produtoModel.add(algaLinks.linkToProdutosRestaurante(restauranteId, "produtos"));
            produtoModel.add(algaLinks.linkToProduto(restauranteId, entity.getId()));
            produtoModel.add(algaLinks.linkToFotoProduto(restauranteId, entity.getId() , "fotoProduto"));
        }


        return produtoModel;
    }

    public CollectionModel<ProdutoModel> toCollection(Collection<Produto> listaProdutos) {
        return CollectionModel.of(listaProdutos.stream().map(this::toModel).toList());
    }
}

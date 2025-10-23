package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.AlgaLinks;
import com.algaworks.algafood_api.api.assembler.mapper.ProdutoMapper;
import com.algaworks.algafood_api.api.model.ProdutoModel;
import com.algaworks.algafood_api.domain.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Component
public class ProdutoAssembler extends RepresentationModelAssemblerSupport<Produto , ProdutoModel> {
    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private ProdutoMapper produtoMapper;

    public ProdutoAssembler () {
        super(Produto.class , ProdutoModel.class);
    }


    @Override
    public ProdutoModel toModel(Produto entity) {
        ProdutoModel produtoModel = produtoMapper.toModel(entity);

        Long restauranteId = entity.getRestaurante().getId();
        produtoModel.add(algaLinks.linkToProdutosRestaurante(restauranteId, "produtos"));
        produtoModel.add(algaLinks.linkToProduto(restauranteId, entity.getId()));
        produtoModel.add(algaLinks.linkToFotoProduto(restauranteId, entity.getId() , "fotoProduto"));

        return produtoModel;
    }

    public List<ProdutoModel> toCollection(Collection<Produto> listaProdutos) {
        return listaProdutos.stream().map(this::toModel).toList();
    }
}

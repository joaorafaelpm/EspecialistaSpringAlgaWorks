package com.algaworks.algafood_api.api.v1.assembler;


import com.algaworks.algafood_api.api.v1.AlgaLinks;
import com.algaworks.algafood_api.api.v1.assembler.mapper.FotoProdutoMapper;
import com.algaworks.algafood_api.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood_api.domain.model.FotoProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoAssembler extends RepresentationModelAssemblerSupport<FotoProduto , FotoProdutoModel> {
    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private FotoProdutoMapper fotoProdutoMapper;

    public FotoProdutoAssembler () {
        super(FotoProduto.class , FotoProdutoModel.class);
    }


    @Override
    public FotoProdutoModel toModel(FotoProduto entity) {
        FotoProdutoModel fotoProdutoModel = fotoProdutoMapper.toModel(entity);
        Long restauranteId = entity.getProduto().getRestaurante().getId();

        fotoProdutoModel.add(algaLinks.linkToProduto(restauranteId, entity.getId()));
        fotoProdutoModel.add(algaLinks.linkToFotoProduto(restauranteId, entity.getId() , "fotoProduto"));

        return fotoProdutoModel;
    }

}

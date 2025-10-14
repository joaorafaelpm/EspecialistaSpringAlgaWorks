package com.algaworks.algafood_api.domain.repository;

import com.algaworks.algafood_api.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

    FotoProduto save (FotoProduto foto) ;
    void delete (FotoProduto foto) ;

}

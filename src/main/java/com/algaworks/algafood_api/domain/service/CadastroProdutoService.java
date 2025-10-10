package com.algaworks.algafood_api.domain.service;


import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood_api.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood_api.domain.model.Produto;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.ProdutoRepository;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CadastroProdutoService {

    CadastroRestauranteService restauranteService ;
    ProdutoRepository produtoRepository;

    public List<Produto> findAll () {
        return produtoRepository.findAll();
    }

    public List<Produto> findByRestaurante (Restaurante restaurante) {
        return produtoRepository.findByRestaurante(restaurante);
    }

    public Produto findById (Long restauranteId , Long produtoId ) {
        restauranteService.findById(restauranteId);
        return produtoRepository.findById(restauranteId , produtoId).orElseThrow(() ->
                new ProdutoNaoEncontradoException(restauranteId , produtoId));
    }

    @Transactional
    public Produto save (Long restauranteId , Produto produto) {
        Restaurante restaurante = restauranteService.findById(restauranteId);
        produto.setRestaurante(restaurante);
        restaurante.adicionarProduto(produto);
        return produtoRepository.save(produto) ;
    }

    @Transactional
    public void remove (Long restauranteId , Long produtoId) {
        Restaurante restaurante = restauranteService.findById(restauranteId);
        Produto produto = findById(restauranteId , produtoId);

        restaurante.removerProduto(produto);
        produtoRepository.delete(produto);
        produtoRepository.flush();
    }


}

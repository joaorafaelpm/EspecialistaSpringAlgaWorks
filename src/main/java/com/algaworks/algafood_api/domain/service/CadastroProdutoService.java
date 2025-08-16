package com.algaworks.algafood_api.domain.service;


import com.algaworks.algafood_api.domain.exception.EntidadeInvalida;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.model.Produto;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.ProdutoRepository;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class CadastroProdutoService {

    RestauranteRepository restauranteRepository ;
    ProdutoRepository produtoRepository;

    public Produto save (Produto produto) {
        Long restauranteId = produto.getRestaurante().getId();
        Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow(() ->
                new EntidadeNaoEncontradaException(
                        String.format("Não existe restaurante com o código de %d!" ,restauranteId ))
        );
        produto.setRestaurante(restaurante);
        restaurante.getProdutos().add(produto);
        return produtoRepository.save(produto) ;
    }


    public Produto save (Long produtoId , Produto produto) {
        Produto produtoAntigo = produtoRepository.findById(produtoId).orElse(null);
        if (produtoAntigo != null) {
            Restaurante restaurante = produto.getRestaurante();
            if (produtoAntigo.getRestaurante().equals(produto.getRestaurante())) {
                BeanUtils.copyProperties(produto , produtoAntigo , "id" , "restaurante");
            }else {
                BeanUtils.copyProperties(produto , produtoAntigo , "id");
            }
            var produtoAtualizado = produtoAntigo;
            return save(produtoAtualizado);
        }
        throw new EntidadeInvalida("Produto inválido!");
    }


}

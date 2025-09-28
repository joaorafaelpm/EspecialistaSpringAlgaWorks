package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.EntidadeInvalida;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.model.Produto;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.ProdutoRepository;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import com.algaworks.algafood_api.domain.service.CadastroProdutoService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@AllArgsConstructor
@Slf4j
public class ProdutoController {

    ProdutoRepository produtoRepository ;
    CadastroProdutoService produtoService ;

    @GetMapping
    public List<Produto> findAll () {
        return produtoRepository.findAll();
    }
    @GetMapping("/{produtoId}")
    public ResponseEntity<Produto> findById (@PathVariable Long produtoId) {
        Produto produto = produtoService.findById(produtoId);

        return ResponseEntity.ok(produto);
    }

    @PostMapping
    public ResponseEntity<?> save (@RequestBody Produto produto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(produtoService.save(produto));
        }
        catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }

    }

    @PutMapping("/{produtoId}")
    public ResponseEntity<?> atualizar (@PathVariable Long produtoId ,@RequestBody Produto produto) {
        Produto produtoAtualizado = produtoService.save(produtoId, produto);
        try {
            return ResponseEntity.ok(produtoAtualizado);
        }
        catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{produtoId}")
    public void delete (@PathVariable Long id) {
        Produto produto = produtoService.findById(id);
            produtoRepository.delete(produto);
    }

}

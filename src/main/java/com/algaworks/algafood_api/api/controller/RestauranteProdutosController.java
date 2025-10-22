package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.assembler.ProdutoAssembler;
import com.algaworks.algafood_api.api.assembler.mapper.ProdutoMapper;
import com.algaworks.algafood_api.api.assembler.disassambler.ProdutoDisassembler;
import com.algaworks.algafood_api.api.model.ProdutoModel;
import com.algaworks.algafood_api.api.model.DTO.ProdutoDTO;
import com.algaworks.algafood_api.domain.model.Produto;
import com.algaworks.algafood_api.domain.service.CadastroProdutoService;
import com.algaworks.algafood_api.domain.service.CadastroRestauranteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
@AllArgsConstructor
public class RestauranteProdutosController {

    private CadastroProdutoService produtoService;
    private CadastroRestauranteService restauranteService;

    private ProdutoAssembler produtoAssembler;
    private ProdutoDisassembler produtoDisassembler;

    @GetMapping
    public List<ProdutoModel> pegarTodosDeUmRestaurante (@PathVariable Long restauranteId , @RequestParam(required = false) Boolean incluirInativos) {
        List<Produto> produtos = produtoService.findAtivosByRestaurante(restauranteService.findById(restauranteId));
        if (incluirInativos != null && incluirInativos) {
            produtos = produtoService.findByRestaurante(restauranteService.findById(restauranteId));
        }
        return produtoAssembler.toCollection(produtos);
    }

    @GetMapping("/{produtoId}")
    public ProdutoModel pegarUnico (@PathVariable Long restauranteId , @PathVariable Long produtoId) {
        Produto produto = produtoService.findById(restauranteId, produtoId);
        return produtoAssembler.toModel(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel salvar (@PathVariable Long restauranteId , @RequestBody @Valid ProdutoDTO produtoDTO) {
        Produto produto = produtoDisassembler.produtoDTOToProduto(produtoDTO);
        produtoService.save(restauranteId , produto);
        return produtoAssembler.toModel(produto);
    }
    @PutMapping("/{produtoId}")
    public ProdutoModel salvar (@PathVariable Long restauranteId , @PathVariable Long produtoId,@RequestBody @Valid ProdutoDTO produtoDTO) {
        Produto produtoAntigo = produtoService.findById(restauranteId, produtoId);
        produtoDisassembler.updateProdutoFromDto(produtoDTO , produtoAntigo);
        return produtoAssembler.toModel(produtoService.save(restauranteId , produtoAntigo));
    }

    @DeleteMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar (@PathVariable Long restauranteId , @PathVariable Long produtoId) {
        produtoService.remove(restauranteId , produtoId);
    }


}

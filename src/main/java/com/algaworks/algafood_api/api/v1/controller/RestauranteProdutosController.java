package com.algaworks.algafood_api.api.v1.controller;

import com.algaworks.algafood_api.api.v1.assembler.ProdutoAssembler;
import com.algaworks.algafood_api.api.v1.assembler.disassambler.ProdutoDisassembler;
import com.algaworks.algafood_api.api.v1.model.ProdutoModel;
import com.algaworks.algafood_api.api.v1.model.DTO.ProdutoDTO;
import com.algaworks.algafood_api.core.security.CheckSecurity;
import com.algaworks.algafood_api.domain.model.Produto;
import com.algaworks.algafood_api.domain.service.CadastroProdutoService;
import com.algaworks.algafood_api.domain.service.CadastroRestauranteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/restaurantes/{restauranteId}/produtos")
@AllArgsConstructor
public class RestauranteProdutosController {

    private CadastroProdutoService produtoService;
    private CadastroRestauranteService restauranteService;

    private ProdutoAssembler produtoAssembler;
    private ProdutoDisassembler produtoDisassembler;

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping
    public List<ProdutoModel> pegarTodosDeUmRestaurante (@PathVariable Long restauranteId , @RequestParam(required = false) Boolean incluirInativos) {
        List<Produto> produtos = produtoService.findAtivosByRestaurante(restauranteService.findById(restauranteId));
        if (incluirInativos != null && incluirInativos) {
            produtos = produtoService.findByRestaurante(restauranteService.findById(restauranteId));
        }
        return produtoAssembler.toCollection(produtos);
    }
    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping("/{produtoId}")
    public ProdutoModel pegarUnico (@PathVariable Long restauranteId , @PathVariable Long produtoId) {
        Produto produto = produtoService.findById(restauranteId, produtoId);
        return produtoAssembler.toModel(produto);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel salvar (@PathVariable Long restauranteId , @RequestBody @Valid ProdutoDTO produtoDTO) {
        Produto produto = produtoDisassembler.produtoDTOToProduto(produtoDTO);
        produtoService.save(restauranteId , produto);
        return produtoAssembler.toModel(produto);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping("/{produtoId}")
    public ProdutoModel salvar (@PathVariable Long restauranteId , @PathVariable Long produtoId,@RequestBody @Valid ProdutoDTO produtoDTO) {
        Produto produtoAntigo = produtoService.findById(restauranteId, produtoId);
        produtoDisassembler.updateProdutoFromDto(produtoDTO , produtoAntigo);
        return produtoAssembler.toModel(produtoService.save(restauranteId , produtoAntigo));
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @DeleteMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar (@PathVariable Long restauranteId , @PathVariable Long produtoId) {
        produtoService.remove(restauranteId , produtoId);
    }


}

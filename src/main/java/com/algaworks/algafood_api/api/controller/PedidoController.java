package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.assembler.PedidoAssembler;
import com.algaworks.algafood_api.api.assembler.disassambler.PedidoDisassembler;
import com.algaworks.algafood_api.api.model.PedidoModel;
import com.algaworks.algafood_api.api.model.PedidoResumoModel;
import com.algaworks.algafood_api.api.model.input.PedidoDTO;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.model.Pedido;
import com.algaworks.algafood_api.domain.service.CadastroPedidoService;
import com.algaworks.algafood_api.domain.service.EmissaoPedidoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@AllArgsConstructor
public class PedidoController {

    private final CadastroPedidoService pedidoService;
    private final EmissaoPedidoService emitirPedidoService;

    private final PedidoAssembler pedidoAssembler;
    private final PedidoDisassembler pedidoDisassembler;

    @GetMapping
    public List<PedidoResumoModel> listar () {
        return pedidoAssembler.toCollection(pedidoService.findAll());
    }
    @GetMapping("/{codigo}")
    public PedidoModel pegarUm (@PathVariable String codigo) {
        return pedidoAssembler.pedidoToPedidoModel(pedidoService.findByIdMapperSolver(codigo));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public  PedidoModel salvar (@RequestBody @Valid PedidoDTO pedidoDTO) {
        try{
            Pedido pedido = pedidoDisassembler.pedidoDTOToPedido(pedidoDTO);
            return pedidoAssembler.pedidoToPedidoModel(emitirPedidoService.emitirPedido(pedido));
        }catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{codigo}")
    public  PedidoModel atualizar (@PathVariable String codigo , @RequestBody @Valid PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoService.findByIdMapperSolver(codigo);

        pedidoDisassembler.updatePedidoFromDto(pedidoDTO , pedido);

        return pedidoAssembler.pedidoToPedidoModel(pedidoService.save(pedido));
    }

    @DeleteMapping("/{codigo}")
    public void delete (@PathVariable String codigo) {
        pedidoService.remove(codigo);
    }



}

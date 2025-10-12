package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.assembler.PedidoAssembler;
import com.algaworks.algafood_api.api.assembler.disassambler.PedidoDisassembler;
import com.algaworks.algafood_api.api.model.PedidoModel;
import com.algaworks.algafood_api.api.model.PedidoResumoModel;
import com.algaworks.algafood_api.api.model.input.PedidoDTO;
import com.algaworks.algafood_api.core.data.PageableTranslator;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.model.Pedido;
import com.algaworks.algafood_api.domain.repository.filter.PedidoFilter;
import com.algaworks.algafood_api.domain.service.CadastroPedidoService;
import com.algaworks.algafood_api.domain.service.EmissaoPedidoService;
import com.algaworks.algafood_api.infrastructure.repository.spec.PedidoSpecs;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.google.common.collect.ImmutableMap;


@RestController
@RequestMapping("/pedidos")
@AllArgsConstructor
public class PedidoController {

    private final CadastroPedidoService pedidoService;
    private final EmissaoPedidoService emitirPedidoService;

    private final PedidoAssembler pedidoAssembler;
    private final PedidoDisassembler pedidoDisassembler;

    @GetMapping
    public Page<PedidoResumoModel> pesquisar(
            PedidoFilter pedidoFilter,
            @PageableDefault(size = 10) Pageable pageable) {
        pageable = traduzirPageable(pageable);
        Page<Pedido> paginaPedidos = pedidoService.findAll(PedidoSpecs.usandoFiltro(pedidoFilter), pageable);
        List<PedidoResumoModel> listaPedido = pedidoAssembler.toCollection(paginaPedidos.getContent());
        return new PageImpl<>(listaPedido, pageable, paginaPedidos.getTotalElements());
    }
//  Eu particularmente prefiro não precisar desse nível de especificação nas minhas consultas, por que acredito que fazer esse tipo de mapper é extremamente desagradável quando eu for especificar o ordenamento dos recursos
//  Então talvez seja uma prática mais agradável limitar isso dentro do DTO e não precisando dessa especificação.
//  É lógico que isso depende do pedido do cliente, e se for necessário é o que eu vou fazer, além de que talvez existam formas mais agradáveis de fazer esse map de "de-para" como o reflections do java e etc
    private Pageable traduzirPageable (Pageable pageable) {
        var mapeamento = ImmutableMap.of(
                "nomeCliente", "cliente.nome" ,
                "codigo", "codigo",
                "valorTotal", "valorTotal",
                "taxaFrete", "taxaFrete",
                "subtotal", "subtotal"
        ) ;
        return PageableTranslator.tanslate(pageable , mapeamento);
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

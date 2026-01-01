package com.algaworks.algafood_api.api.v1.controller;

import com.algaworks.algafood_api.api.v1.assembler.PedidoModelAssembler;
import com.algaworks.algafood_api.api.v1.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood_api.api.v1.assembler.disassambler.PedidoDisassembler;
import com.algaworks.algafood_api.api.v1.model.PedidoModel;
import com.algaworks.algafood_api.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood_api.api.v1.model.DTO.PedidoDTO;
import com.algaworks.algafood_api.core.data.PageWrapper;
import com.algaworks.algafood_api.core.data.PageableTranslator;
import com.algaworks.algafood_api.core.security.AlgaSecurity;
import com.algaworks.algafood_api.core.security.CheckSecurity;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.model.Pedido;
import com.algaworks.algafood_api.domain.filter.PedidoFilter;
import com.algaworks.algafood_api.domain.model.Usuario;
import com.algaworks.algafood_api.domain.service.CadastroPedidoService;
import com.algaworks.algafood_api.domain.service.EmissaoPedidoService;
import com.algaworks.algafood_api.infrastructure.repository.spec.PedidoSpecs;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/v1/pedidos")
@AllArgsConstructor
public class PedidoController {

    private CadastroPedidoService pedidoService;
    private EmissaoPedidoService emitirPedidoService;

    private PedidoModelAssembler pedidoModelAssembler;
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;
    private PedidoDisassembler pedidoDisassembler;
    private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;



    @CheckSecurity.Pedidos.PodeListar
    @GetMapping
    public PagedModel<PedidoResumoModel> pesquisar(
            PedidoFilter pedidoFilter, Pageable pageable) {
//    Pra fins educacionais eu vou alterar o sort de ordenamento do nome do restaurante de restaurante.nome para nomerestaurante durante esse commit simulando uma especificação de um cliente para resolver o problema de ordenação do linl
        Pageable pageableTraduzido = traduzirPageable(pageable);
        Page<Pedido> paginaPedidos = pedidoService.findAll(PedidoSpecs.usandoFiltro(pedidoFilter), pageableTraduzido);
        paginaPedidos = new PageWrapper<>(paginaPedidos , pageable);

        return pagedResourcesAssembler.toModel(paginaPedidos , pedidoResumoModelAssembler);
    }
    @CheckSecurity.Pedidos.PodeBuscar
    @GetMapping("/{codigo}")
    public PedidoModel pegarUm (@PathVariable String codigo) {
        return pedidoModelAssembler.toModel(pedidoService.findByIdMapperSolver(codigo));
    }

    @CheckSecurity.Pedidos.PodeCriar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public  PedidoModel salvar (@RequestBody @Valid PedidoDTO pedidoDTO) {
        try{
            Pedido pedido = pedidoDisassembler.pedidoDTOToPedido(pedidoDTO);
            return pedidoModelAssembler.toModel(emitirPedidoService.emitirPedido(pedido));
        }catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    private Pageable traduzirPageable(Pageable apiPageable) {
        var mapeamento = Map.of(
                "codigo", "codigo",
                "subtotal", "subtotal",
                "taxaFrete", "taxaFrete",
                "valorTotal", "valorTotal",
                "dataCriacao", "dataCriacao",
                "restaurante.nome", "restaurante.nome",
                "restaurante.id", "restaurante.id",
                "cliente.id", "cliente.id",
                "cliente.nome", "cliente.nome"
        );

        return PageableTranslator.translate(apiPageable, mapeamento);
    }

}

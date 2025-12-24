package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.core.security.AlgaSecurity;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.model.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmissaoPedidoService {

    private  final CadastroPedidoService pedidoService;
    private  final CadastroRestauranteService restauranteService;
    private  final CadastroFormaPagamentoService formaPagamentoService;
    private  final CadastroProdutoService produtoService;
    private  final CadastroCidadeService cidadeService;
    private  final CadastroUsuarioService usuarioService;
    private  final AlgaSecurity algaSecurity;

    @Transactional
    public Pedido emitirPedido(Pedido pedido) {

//        Atribuimos o restaurante, cliente e forma de pagamento ao Pedido
        atribuirObjetosRelacionaisPedido(pedido);

//        Atribuimos o preço unitário, o produto e o pedido ao itemPedido
        atribuirPrecoUnitarioEProdutoItemPedido(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotalPedido();


        return pedidoService.save(pedido);
    }

    public void atribuirObjetosRelacionaisPedido (Pedido pedido) {
        Long restauranteId = pedido.getRestaurante().getId();
        Long formaPagamentoId = pedido.getFormaPagamento().getId();
        Long cidadeId = pedido.getEnderecoEntrega().getCidade().getId();
        Long clienteId = algaSecurity.getUsuarioId();

        Cidade cidade = cidadeService.findById(cidadeId);
        Restaurante restaurante = restauranteService.findById(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.findById(formaPagamentoId);
        Usuario usuario = usuarioService.findById(clienteId);

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setRestaurante(restaurante);
        pedido.setCliente(usuario);
        if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formaPagamento.getDescricao()));
        }
        pedido.setFormaPagamento(formaPagamento);
    }

    public void atribuirPrecoUnitarioEProdutoItemPedido (Pedido pedido) {
        pedido.getItens().forEach( item -> {
                Produto produto = produtoService.findById(pedido.getRestaurante().getId() , item.getProduto().getId());
                item.setPedido(pedido);
                item.setProduto(produto);
                item.setPrecoUnitario(produto.getPreco());
        });
    }


}

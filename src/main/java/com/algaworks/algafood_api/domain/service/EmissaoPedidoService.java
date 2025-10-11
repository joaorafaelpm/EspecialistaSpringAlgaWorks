package com.algaworks.algafood_api.domain.service;

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
    private  final CadastroUsuarioService usuarioService;
    private  final CadastroFormaPagamentoService formaPagamentoService;
    private  final CadastroProdutoService produtoService;
    private  final CadastroCidadeService cidadeService;

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

        Cidade cidade = cidadeService.findById(cidadeId);
        Restaurante restaurante = restauranteService.findById(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.findById(formaPagamentoId);

//        O Professor pediu para usar um cliente fixo de Id 1, como a implementação de um clienteId é extremamente simples, eu fiz, mas a princípio nós vamos validar se o usuário é autenticado ou não.]
//        Isso é uma má prática, o código não deveria receber qualquer cliente, só estou fazendo isso pela práticidade mais a frente
        Long usuarioId = pedido.getCliente().getId();
        Usuario usuario = usuarioService.findById(usuarioId);

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

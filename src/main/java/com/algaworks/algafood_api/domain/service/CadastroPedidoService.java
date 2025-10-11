package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.api.model.input.PedidoDTO;
import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood_api.domain.model.Pedido;
import com.algaworks.algafood_api.domain.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CadastroPedidoService {

    private final PedidoRepository pedidoRepository;

    private  final CadastroRestauranteService restauranteService;
    private  final CadastroUsuarioService usuarioService;
    private  final CadastroFormaPagamentoService formaPagamentoService;
    private  final CadastroItemPedidoService itemPedidoService;

    public List<Pedido> findAll () {
        return pedidoRepository.findAll();
    }

    public Pedido findById(String codigo) {
        return pedidoRepository.findByCodigo(codigo).orElseThrow(
                () -> new PedidoNaoEncontradoException(codigo));
    }

    public Pedido findByIdMapperSolver (String codigo) {
        return pedidoRepository.findByIdMapperResolved(codigo).orElseThrow(
                () -> new PedidoNaoEncontradoException(codigo));
    }

    @Transactional
    public Pedido save(Pedido pedido) {
        pedidoRepository.saveAndFlush(pedido);
        return findByIdMapperSolver(pedido.getCodigo()) ;
    }

    @Transactional
    public void remove (String codigo) {
        try {
            Pedido pedido = findById(codigo);
            pedidoRepository.delete(pedido);
            pedidoRepository.flush();
        }
        catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(codigo);
        }

    }
}

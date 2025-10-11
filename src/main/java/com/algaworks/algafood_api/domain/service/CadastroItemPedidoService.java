package com.algaworks.algafood_api.domain.service;


import com.algaworks.algafood_api.api.model.input.ItemPedidoDTO;
import com.algaworks.algafood_api.domain.exception.ItemPedidoNaoEncontradoException;
import com.algaworks.algafood_api.domain.model.ItemPedido;
import com.algaworks.algafood_api.domain.model.Produto;
import com.algaworks.algafood_api.domain.repository.ItemPedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CadastroItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final CadastroProdutoService produtoService;

    public List<ItemPedido> findAll () {
        return itemPedidoRepository.findAll();
    }
    public ItemPedido findById (Long id) {
        return itemPedidoRepository.findById(id).orElseThrow(() ->
            new ItemPedidoNaoEncontradoException(id));
    }


}

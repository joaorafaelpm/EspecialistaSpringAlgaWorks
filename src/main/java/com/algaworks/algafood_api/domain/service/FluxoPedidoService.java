package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.model.Pedido;
import com.algaworks.algafood_api.domain.model.enuns.StatusPedido;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class FluxoPedidoService {

    private final EmissaoPedidoService emissaoPedidoService;

    private final CadastroPedidoService pedidoService;

    @Transactional
    public void confirmar (String pedidoId) {
        Pedido pedido = pedidoService.findById(pedidoId );
        pedido.confirmar();
    }

    @Transactional
    public void entregar (String pedidoId) {
        Pedido pedido = pedidoService.findById(pedidoId );
        pedido.entregar();
    }
    @Transactional
    public void cancelar (String pedidoId) {
        Pedido pedido = pedidoService.findById(pedidoId );
        pedido.cancelar();

    }

}

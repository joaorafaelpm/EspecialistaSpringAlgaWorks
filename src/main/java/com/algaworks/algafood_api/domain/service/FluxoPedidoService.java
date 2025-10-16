package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.model.Pedido;
import com.algaworks.algafood_api.domain.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;


@Service
@AllArgsConstructor
public class FluxoPedidoService {

    private final CadastroPedidoService pedidoService;

    private final PedidoRepository pedidoRepository ;


    @Transactional
    public void confirmar (String pedidoId) {
        Pedido pedido = pedidoService.findById(pedidoId );
        pedido.confirmar();

//        A gente faz um flush de proposito para lançar o evento, mesmo que ele fosse persistido eventualmente pelo JPA, a gente garante isso e dispara "manualmente"
        pedidoRepository.save(pedido);
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

        pedidoRepository.save(pedido);
    }

}

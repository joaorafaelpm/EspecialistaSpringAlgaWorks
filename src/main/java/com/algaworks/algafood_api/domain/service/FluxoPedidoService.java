package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.model.Pedido;
import com.algaworks.algafood_api.domain.model.enuns.StatusPedido;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class FluxoPedidoService {

    private final EnvioEmailService envioEmailService;
    private final CadastroPedidoService pedidoService;

    @Transactional
    public void confirmar (String pedidoId) {
        Pedido pedido = pedidoService.findById(pedidoId );
        pedido.confirmar();

        var menssagem = EnvioEmailService.Menssagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido Confirmado")
                .corpo("pedido-confirmado.html")
                .variavel("pedido" , pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();
        envioEmailService.enviar(menssagem);

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

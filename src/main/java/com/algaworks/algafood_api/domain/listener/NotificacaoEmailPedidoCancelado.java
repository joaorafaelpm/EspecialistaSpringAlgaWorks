package com.algaworks.algafood_api.domain.listener;

import com.algaworks.algafood_api.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood_api.domain.model.Pedido;
import com.algaworks.algafood_api.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoEmailPedidoCancelado {

    @Autowired
    private EnvioEmailService envioEmailService;

    @TransactionalEventListener
    private void aoCancelarPedido (PedidoCanceladoEvent event) {

        Pedido pedido = event.getPedido();
        var menssagem = EnvioEmailService.Menssagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido Cancelado")
                .corpo("pedido-cancelado.html")
                .variavel("pedido" , pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();
        envioEmailService.enviar(menssagem);
    }

}

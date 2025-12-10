package com.algaworks.algafood_api.domain.listener;


import com.algaworks.algafood_api.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood_api.domain.model.Pedido;
import com.algaworks.algafood_api.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoConfirmado {

    @Autowired
    private EnvioEmailService envioEmailService;


//    @EventListener
    @TransactionalEventListener
    private void aoConfirmarPedido (PedidoConfirmadoEvent event) {

        Pedido pedido = event.getPedido();
        var menssagem = EnvioEmailService.Menssagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido Confirmado")
                .corpo("pedido-confirmado.html")
                .variavel("pedido" , pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();
        envioEmailService.enviar(menssagem);
    }

}

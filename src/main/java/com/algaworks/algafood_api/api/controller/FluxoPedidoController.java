package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.domain.service.FluxoPedidoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{pedidoId}")
@AllArgsConstructor
public class FluxoPedidoController {

    private final FluxoPedidoService fluxoPedidoService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar (@PathVariable String pedidoId) {
        fluxoPedidoService.confirmar(pedidoId);
    }
    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar (@PathVariable String pedidoId) {
        fluxoPedidoService.entregar(pedidoId);
    }
    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar (@PathVariable String pedidoId) {
        fluxoPedidoService.cancelar(pedidoId);
    }

}

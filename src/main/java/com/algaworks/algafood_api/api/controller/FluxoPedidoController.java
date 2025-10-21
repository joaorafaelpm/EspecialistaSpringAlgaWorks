package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.domain.service.FluxoPedidoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{pedidoId}")
@AllArgsConstructor
public class FluxoPedidoController {

    private FluxoPedidoService fluxoPedidoService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> confirmar (@PathVariable String pedidoId) {
        fluxoPedidoService.confirmar(pedidoId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> entregar (@PathVariable String pedidoId) {
        fluxoPedidoService.entregar(pedidoId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> cancelar (@PathVariable String pedidoId) {
        fluxoPedidoService.cancelar(pedidoId);
        return ResponseEntity.noContent().build();
    }

}

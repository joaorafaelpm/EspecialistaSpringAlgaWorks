package com.algaworks.algafood_api.api.v1.controller;

import com.algaworks.algafood_api.core.security.CheckSecurity;
import com.algaworks.algafood_api.domain.service.FluxoPedidoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/pedidos/{codigo}")
@AllArgsConstructor
public class FluxoPedidoController {

    private FluxoPedidoService fluxoPedidoService;
    @CheckSecurity.Pedidos.PodeGerenciar
    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> confirmar (@PathVariable String codigo) {
        fluxoPedidoService.confirmar(codigo);
        return ResponseEntity.noContent().build();
    }
    @CheckSecurity.Pedidos.PodeGerenciar
    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> entregar (@PathVariable String codigo) {
        fluxoPedidoService.entregar(codigo);
        return ResponseEntity.noContent().build();
    }
    @CheckSecurity.Pedidos.PodeGerenciar
    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> cancelar (@PathVariable String codigo) {
        fluxoPedidoService.cancelar(codigo);
        return ResponseEntity.noContent().build();
    }

}

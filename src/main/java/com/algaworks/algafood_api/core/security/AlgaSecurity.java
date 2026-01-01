package com.algaworks.algafood_api.core.security;

import com.algaworks.algafood_api.domain.model.Pedido;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.PedidoRepository;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AlgaSecurity {

    private RestauranteRepository restauranteRepository;
    private PedidoRepository pedidoRepository;

    public Authentication getAuthentication () {
//        Pegando o objeto de autenticação que vai representar o token usado
        return SecurityContextHolder.getContext().getAuthentication();
    }
    public Long getUsuarioId () {
//        Como esse vai ser o único modelo de token eu não vou fazer uma
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();
//        A gente pega o id do usuário autenticado, se o usuário não estiver autenticado ele não consegue fazer o pedido
        return jwt.getClaim("usuario_id");
    }

    public boolean gerenciaRestaurante(Long restauranteId) {
        if (restauranteId == null) {
            return false;
        }
        return restauranteRepository.existsResponsavel(restauranteId , getUsuarioId());
    }
    public boolean gerenciaRestauranteDoPedido(String codigo) {
        if (codigo == null) {
            return false;
        }
        return pedidoRepository.isPedidoGerenciadoPor(codigo , getUsuarioId()) ;
    }

}

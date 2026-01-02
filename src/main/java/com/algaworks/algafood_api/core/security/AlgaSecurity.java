package com.algaworks.algafood_api.core.security;

import com.algaworks.algafood_api.domain.model.Pedido;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.PedidoRepository;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
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

    public boolean usuarioAutenticadoIgual(Long usuarioId) {
        return getUsuarioId() != null && usuarioId != null
                && getUsuarioId().equals(usuarioId);
    }

    public boolean hasAuthority(String authorityName) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(authorityName));
    }
    public boolean hasAuthorityWrite() {
        return hasAuthority("SCOPE_WRITE");
    }
    public boolean hasAuthorityRead() {
        return hasAuthority("SCOPE_READ");
    }
    public boolean isAuthenticated () {
        return getAuthentication().isAuthenticated();
    }
//    Cozinhas
    public boolean podeConsultarCozinhas () {
        return hasAuthorityRead() && isAuthenticated();
    }
    public boolean podeEditarCozinhas () {
        return hasAuthorityWrite() && hasAuthority("EDITAR_COZINHAS");
    }
//    Restaurantes
    public boolean podeConsultarRestaurantes () {
        return hasAuthorityRead() && isAuthenticated();
    }
    public boolean podeGerenciarCadastrosRestaurantes () {
        return hasAuthorityWrite() && hasAuthority("EDITAR_RESTAURANTES");
    }
    public boolean podeGerenciarFuncionamentoRestaurantes (Long restauranteId) {
        return hasAuthorityWrite() &&
                (hasAuthority("EDITAR_RESTAURANTES") || gerenciaRestaurante(restauranteId));
    }

//    Pedidos
    public boolean podeBuscarPedidos () {
        return hasAuthorityRead() && isAuthenticated();
    }
    public boolean podeBuscarPedidos (Long clienteId , Long restauranteId) {
        return hasAuthority("CONSULTAR_PEDIDOS") ||
                usuarioAutenticadoIgual(clienteId) || gerenciaRestaurante(restauranteId);
    }
    public boolean podeListarPedidos (Long clienteId , Long restauranteId) {
        return hasAuthorityRead() && podeBuscarPedidos(clienteId, restauranteId);
    }
    public boolean podeGerenciarPedidos(String codigoPedido) {
        return hasAuthorityWrite() && (hasAuthority("GERENCIAR_PEDIDOS")
                || gerenciaRestauranteDoPedido(codigoPedido));
    }
    public boolean podeCriarPedidos () {
        return hasAuthorityWrite() && isAuthenticated();
    }

//    FormasPagamento
    public boolean podeConsultarFormasPagamento () {
        return hasAuthorityRead() && isAuthenticated();
    }
    public boolean podeEditarFormasPagamento () {
        return hasAuthorityWrite() && hasAuthority("EDITAR_FORMAS_PAGAMENTO");
    }

//      Cidades
    public boolean podeConsultarCidades () {
        return hasAuthorityRead() && isAuthenticated();
    }
    public boolean podeEditarCidades () {
        return hasAuthorityWrite() && hasAuthority("EDITAR_CIDADES");
    }

//      Estados
    public boolean podeConsultarEstados () {
        return hasAuthorityRead() && isAuthenticated();
    }
    public boolean podeEditarEstados () {
        return hasAuthorityWrite() && hasAuthority("EDITAR_ESTADOS");
    }

//    UsuariosGruposPermissoes
    public boolean podeAlterarPropriaSenhaUsuariosGruposPermissoes (Long usuarioId) {
        return hasAuthorityWrite() && usuarioAutenticadoIgual(usuarioId);
    }
    public boolean podeAlterarUsuariosGruposPermissoes (Long usuarioId) {
        return hasAuthorityWrite() &&
                (hasAuthority("EDITAR_USUARIOS_GRUPOS_PERMISSOES") || usuarioAutenticadoIgual(usuarioId) );
    }
    public boolean podeConsultarUsuariosGruposPermissoes () {
        return hasAuthorityRead() && hasAuthority("CONSULTAR_USUARIOS_GRUPOS_PERMISSOES");
    }
    public boolean podeEditarUsuariosGruposPermissoes () {
        return hasAuthorityWrite() && hasAuthority("EDITAR_USUARIOS_GRUPOS_PERMISSOES");
    }

//    Estatisticas

    public boolean podeConsultarEstatisticas () {
        return hasAuthorityRead() && hasAuthority("GERAR_RELATORIOS");
    }

}

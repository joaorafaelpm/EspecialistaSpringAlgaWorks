package com.algaworks.algafood_api.api.v1;

import com.algaworks.algafood_api.api.v1.controller.*;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AlgaLinks {


    public static final TemplateVariables PAGE_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)
    );
    public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("projecao", TemplateVariable.VariableType.REQUEST_PARAM)
    );


    public Link linkToPedidos (String rel) {
        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("clientId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        String uri = linkTo(PedidoController.class).toUri().toString();

        return Link.of(UriTemplate.of(uri , PAGE_VARIABLES.concat(filtroVariables)) , LinkRelation.of(rel));
    }

    public Link linkToConfirmarPedido (String codigoPedido , String rel) {
        return linkTo(methodOn(FluxoPedidoController.class).confirmar(codigoPedido)).withRel(rel);
    }

    public Link linkToEntregarPedido (String codigoPedido , String rel) {
        return linkTo(methodOn(FluxoPedidoController.class).entregar(codigoPedido)).withRel(rel);
    }

    public Link linkToCancelarPedido (String codigoPedido , String rel) {
        return linkTo(methodOn(FluxoPedidoController.class).cancelar(codigoPedido)).withRel(rel);
    }

    public Link linkToPedido (String codigo) {
        return linkTo(methodOn(PedidoController.class).pegarUm(codigo)
        ).withSelfRel();
    }

    public Link linkToRestaurantes (String rel) {
        String uri = linkTo(RestauranteController.class).toUri().toString();
        return Link.of(UriTemplate.of(uri , PROJECAO_VARIABLES) , LinkRelation.of(rel));
    }
    public Link linkToRestaurante () {
        return linkToRestaurantes(IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteAtivacao(Long restauranteId , String rel) {
        return linkTo(methodOn(RestauranteController.class).ativar(restauranteId)).withRel(rel);
    }
    public Link linkToRestauranteAtivacao(Long restauranteId) {
        return linkToRestauranteAtivacao(restauranteId , IanaLinkRelations.SELF.value());
    }
    public Link linkToRestauranteInativacao(Long restauranteId , String rel) {
        return linkTo(methodOn(RestauranteController.class).inativar(restauranteId)).withRel(rel);
    }
    public Link linkToRestauranteInativacao(Long restauranteId) {
        return linkToRestauranteInativacao(restauranteId , IanaLinkRelations.SELF.value());
    }
    public Link linkToRestauranteAbertura(Long restauranteId , String rel) {
        return linkTo(methodOn(RestauranteController.class).abrir(restauranteId)).withRel(rel);
    }
    public Link linkToRestauranteAbertura(Long restauranteId) {
        return linkToRestauranteAbertura(restauranteId , IanaLinkRelations.SELF.value());
    }
    public Link linkToRestauranteFechamento(Long restauranteId , String rel) {
        return linkTo(methodOn(RestauranteController.class).fechar(restauranteId)).withRel(rel);
    }
    public Link linkToRestauranteFechamento(Long restauranteId) {
        return linkToRestauranteFechamento(restauranteId , IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteFormaPagamentoDesassociacao(Long restauranteId , Long formaPagamentoId , String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .desassociar(restauranteId , formaPagamentoId)).withRel(rel);
    }
    public Link linkToRestauranteFormaPagamentoAssociacao(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .associar(restauranteId , null)).withRel(rel);
    }

    public Link linkToRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .getById(restauranteId)).withRel(rel);
    }

    public Link linkToRestaurante(Long restauranteId) {
        return linkToRestaurante(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToResponsaveisRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteUsuarioController.class)
                .listar(restauranteId)).withRel(rel);
    }

    public Link linkToResponsaveisRestaurante(Long restauranteId) {
        return linkToResponsaveisRestaurante(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToResponsaveisRestauranteAssociacao(Long restauranteId , String rel) {
        return linkTo(methodOn(RestauranteUsuarioController.class)
                .associar(restauranteId , null)).withRel(rel);
    }
    public Link linkToResponsaveisRestauranteDesassociacao(Long restauranteId ,Long usuarioId, String rel) {
        return linkTo(methodOn(RestauranteUsuarioController.class)
                .desassociar(restauranteId , usuarioId)).withRel(rel);
    }

    public Link linkToProdutosRestaurante(Long restauranteId , String rel) {
        return linkTo(methodOn(RestauranteProdutosController.class).pegarTodosDeUmRestaurante(restauranteId , null)).withRel(rel);
    }
    public Link linkToProdutosRestaurante(Long restauranteId) {
        return linkToProdutosRestaurante(restauranteId , IanaLinkRelations.SELF.value());
    }




    public Link linkToUsuario(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioController.class)
                .findById(usuarioId)).withRel(rel);
    }

    public Link linkToUsuario(Long usuarioId) {
        return linkToUsuario(usuarioId, IanaLinkRelations.SELF.value());
    }

    public Link linkToUsuarios(String rel) {
        return linkTo(UsuarioController.class).withRel(rel);
    }

    public Link linkToUsuarios() {
        return linkToUsuarios(IanaLinkRelations.COLLECTION.value());
    }

    public Link linkToGruposUsuario(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class)
                .pegarTodosGruposDeUmUsuario(usuarioId)).withRel(rel);
    }

    public Link linkToGruposUsuario(Long usuarioId) {
        return linkToGruposUsuario(usuarioId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteFormasPagamento(Long restauranteId , String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .listar(restauranteId)).withRel(rel);
    }
    public Link linkToRestauranteFormasPagamento(Long restauranteId) {
        return linkToRestauranteFormasPagamento(restauranteId , IanaLinkRelations.SELF.value());
    }

    public Link linkToFormasPagamento(String rel) {
        return linkTo(FormaPagamentoController.class).withRel(rel);
    }
    public Link linkToFormasPagamento() {
        return linkToFormasPagamento(IanaLinkRelations.COLLECTION.value());
    }

    public Link linkToFormaPagamento(Long formaPagamentoId, String rel) {
        return linkTo(methodOn(FormaPagamentoController.class)
                .getById(formaPagamentoId, null)).withRel(rel);
    }

    public Link linkToFormaPagamento(Long formaPagamentoId) {
        return linkToFormaPagamento(formaPagamentoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCidade(Long cidadeId, String rel) {
        return linkTo(methodOn(CidadeController.class)
                .getById(cidadeId)).withRel(rel);
    }

    public Link linkToCidade(Long cidadeId) {
        return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCidades(String rel) {
        return linkTo(CidadeController.class).withRel(rel);
    }

    public Link linkToCidades() {
        return linkToCidades(IanaLinkRelations.COLLECTION.value());
    }

    public Link linkToEstado(Long estadoId, String rel) {
        return linkTo(methodOn(EstadoController.class)
                .getById(estadoId)).withRel(rel);
    }

    public Link linkToEstado(Long estadoId) {
        return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToEstados(String rel) {
        return linkTo(EstadoController.class).withRel(rel);
    }

    public Link linkToEstados() {
        return linkToEstados(IanaLinkRelations.COLLECTION.value());
    }

    public Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
        return linkTo(methodOn(RestauranteProdutosController.class)
                .pegarUnico(restauranteId, produtoId)).withRel(rel);
    }

    public Link linkToProduto(Long restauranteId, Long produtoId) {
        return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToFotoProduto (Long restauranteId, Long produtoId , String rel) {
        return linkTo(methodOn(RestauranteProdutoFotoController.class).pegarFoto(restauranteId, produtoId)).withRel(rel);
    }
    public Link linkToFotoProduto (Long restauranteId, Long produtoId) {
        return linkToFotoProduto(restauranteId , produtoId , IanaLinkRelations.SELF.value());
    }

    public Link linkToCozinha (Long cozinhaId , String rel) {
        return linkTo(methodOn(CozinhaController.class).getById(cozinhaId)).withRel(rel);
    }
    public Link linkToCozinha (Long cozinhaId) {
        return linkToCozinha(cozinhaId , IanaLinkRelations.SELF.value());
    }

    public Link linkToCozinhas(String rel) {
        return linkTo(CozinhaController.class).withRel(rel);
    }
    public Link linkToCozinhas() {
        return linkToCozinhas(IanaLinkRelations.COLLECTION.value());
    }
    public Link linkToGrupos(String rel) {
        return linkTo(GrupoController.class).withRel(rel);
    }
    public Link linkToGrupos() {
        return linkToGrupos(IanaLinkRelations.COLLECTION.value());
    }
    public Link linkToGrupo(Long grupoId,String rel) {
        return linkTo(methodOn(GrupoController.class).findById(grupoId)).withRel(rel);
    }
    public Link linkToGrupo(Long grupoId) {
        return linkToGrupo(grupoId , IanaLinkRelations.SELF.value());
    }

    public Link linkToGrupoPermissao (Long grupoId , String rel) {
        return linkTo(methodOn(GrupoPermissaoController.class).listarPermissao(grupoId)).withRel(rel);
    }

    public Link linkToAssociacaoGrupoPermissao (Long grupoId,Long permissaoId , String rel) {
        return linkTo(methodOn(GrupoPermissaoController.class).associarPermissao(grupoId , permissaoId)).withRel(rel);
    }

    public Link linkToDesassociacaoGrupoPermissao (Long grupoId,Long permissaoId , String rel) {
        return linkTo(methodOn(GrupoPermissaoController.class).desassociarPermissao(grupoId , permissaoId)).withRel(rel);
    }

    public Link linkToAssociacaoGrupoUsuario (Long usuarioId , Long grupoId, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class).associar(usuarioId , grupoId)).withRel(rel);
    }

    public Link linkToDesassociacaoGrupoUsuario (Long usuarioId , Long grupoId, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class).desassociar(usuarioId , grupoId)).withRel(rel);
    }

    public Link linkToPermissoes (String rel) {
        return linkTo(PermissaoController.class).withRel(rel);
    }
    public Link linkToPermissoes () {
        return linkToPermissoes(IanaLinkRelations.COLLECTION.value());
    }

    public Link linkToEstatisticas (String rel) {
        return linkTo(EstatisticasController.class).withRel(rel);
    }
    public Link linkToEstatisticasVendasDiarias (String rel) {
        TemplateVariables filtro = new TemplateVariables(
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("timeOffSet", TemplateVariable.VariableType.REQUEST_PARAM)
        );
        String link = linkTo(methodOn(EstatisticasController.class).consultarVendasJson(null, null)).toUri().toString();

        return Link.of(UriTemplate.of(link , filtro) , rel);
    }

}




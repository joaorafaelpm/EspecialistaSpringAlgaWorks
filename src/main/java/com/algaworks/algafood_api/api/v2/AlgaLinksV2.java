package com.algaworks.algafood_api.api.v2;

import com.algaworks.algafood_api.api.v1.controller.EstadoController;
import com.algaworks.algafood_api.api.v2.controller.CidadeControllerV2;
import com.algaworks.algafood_api.api.v2.controller.CozinhaControllerV2;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AlgaLinksV2 {

    public Link linkToCidade(Long cidadeId, String rel) {
        return linkTo(methodOn(CidadeControllerV2.class)
                .getById(cidadeId)).withRel(rel);
    }

    public Link linkToCidade(Long cidadeId) {
        return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCidades(String rel) {
        return linkTo(CidadeControllerV2.class).withRel(rel);
    }

    public Link linkToCidades() {
        return linkToCidades(IanaLinkRelations.COLLECTION.value());
    }

    public Link linkToEstado(Long idEstado, String rel) {
        return linkTo(methodOn(EstadoController.class)
                .getById(idEstado)).withRel(rel);
    }
    public Link linkToEstado(Long idEstado) {
        return linkTo(methodOn(EstadoController.class)
                .getById(idEstado)).withRel(IanaLinkRelations.SELF.value());
    }

    public Link linkToCozinha (Long cozinhaId , String rel) {
        return linkTo(methodOn(CozinhaControllerV2.class).getById(cozinhaId)).withRel(rel);
    }
    public Link linkToCozinha (Long cozinhaId) {
        return linkToCozinha(cozinhaId , IanaLinkRelations.SELF.value());
    }

    public Link linkToCozinhas(String rel) {
        return linkTo(CozinhaControllerV2.class).withRel(rel);
    }
    public Link linkToCozinhas() {
        return linkToCozinhas(IanaLinkRelations.COLLECTION.value());
    }
}




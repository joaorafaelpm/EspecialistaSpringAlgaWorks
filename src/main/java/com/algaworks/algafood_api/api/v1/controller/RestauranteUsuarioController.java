package com.algaworks.algafood_api.api.v1.controller;

import com.algaworks.algafood_api.api.v1.assembler.UsuarioModelAssembler;
import com.algaworks.algafood_api.api.v1.model.UsuarioModel;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.service.CadastroRestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
@AllArgsConstructor
@RequestMapping("/restaurantes/{restauranteId}/usuarios")
public class RestauranteUsuarioController {

    private CadastroRestauranteService restauranteService;

    private UsuarioModelAssembler usuarioModelAssembler;

    @GetMapping
    public CollectionModel<UsuarioModel> listar (@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.findById(restauranteId);
        return usuarioModelAssembler.toCollectionRefRestaurante(restauranteId , restaurante.getUsuarios());
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<Void> associar (@PathVariable Long restauranteId , @PathVariable Long usuarioId) {
        restauranteService.associarUsuarioResponsavel(restauranteId , usuarioId) ;
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> desassociar (@PathVariable Long restauranteId , @PathVariable Long usuarioId) {
        restauranteService.desassociarUsuarioResponsavel(restauranteId , usuarioId);
        return ResponseEntity.noContent().build();
    }



}



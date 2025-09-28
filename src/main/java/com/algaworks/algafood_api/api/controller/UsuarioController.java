package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.domain.exception.EntidadeInvalida;
import com.algaworks.algafood_api.domain.model.Produto;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.model.Usuario;
import com.algaworks.algafood_api.domain.repository.UsuarioRepository;
import com.algaworks.algafood_api.domain.service.CadastroUsuarioService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor

public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final CadastroUsuarioService usuarioService;

    @GetMapping
    public List<Usuario> findAll () {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<Usuario> findById (@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.findById(usuarioId);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario save (@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<?> save (@PathVariable Long usuarioId , @RequestBody Usuario usuario) {
        Usuario usuarioAntigo = usuarioService.findById(usuarioId);
            BeanUtils.copyProperties(usuario , usuarioAntigo , "id" , "dataCadastro");
            return ResponseEntity.ok(usuarioService.save(usuarioAntigo));
    }
}

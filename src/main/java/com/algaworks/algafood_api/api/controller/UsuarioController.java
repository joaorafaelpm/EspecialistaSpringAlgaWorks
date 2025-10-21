package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood_api.api.assembler.mapper.UsuarioMapper;
import com.algaworks.algafood_api.api.assembler.disassambler.UsuarioDisassembler;
import com.algaworks.algafood_api.api.model.UsuarioModel;
import com.algaworks.algafood_api.api.model.DTO.SenhaDTO;
import com.algaworks.algafood_api.api.model.DTO.UsuarioComSenhaDTO;
import com.algaworks.algafood_api.api.model.DTO.UsuarioDTO;
import com.algaworks.algafood_api.domain.model.Usuario;
import com.algaworks.algafood_api.domain.service.CadastroUsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor

public class UsuarioController {

    private CadastroUsuarioService usuarioService;

    private UsuarioModelAssembler usuarioModelAssembler;
    private UsuarioDisassembler usuarioDisassembler;

    @GetMapping
    public CollectionModel<UsuarioModel> findAll () {
        return usuarioModelAssembler.toCollection(usuarioService.findAll());
    }

    @GetMapping("/{usuarioId}")
    public UsuarioModel findById (@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.findById(usuarioId);
        return usuarioModelAssembler.toModel(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel save (@RequestBody @Valid UsuarioComSenhaDTO usuarioSenhaDTO) {
        Usuario usuario = usuarioDisassembler.usuarioComSenhaDTOToUsuario(usuarioSenhaDTO);
        usuarioService.save(usuario);
        return usuarioModelAssembler.toModel(usuario);
    }

    @PutMapping("/{id}")
    public UsuarioModel save (@PathVariable Long id , @RequestBody @Valid UsuarioDTO usuarioDTO) {
        Usuario usuarioAntigo = usuarioService.findById(id);
        usuarioDisassembler.updateUsuarioFromDto(usuarioDTO , usuarioAntigo);
        usuarioService.save(usuarioAntigo);
        return usuarioModelAssembler.toModel(usuarioAntigo);
    }

    @PutMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void savePassword (@PathVariable Long id , @RequestBody @Valid SenhaDTO senhaDTO) {
        Usuario usuarioSenhaAntiga = usuarioService.findById(id);
        usuarioService.savePassword(usuarioSenhaAntiga , senhaDTO.getSenhaAtual() , senhaDTO.getNovaSenha());
    }
}

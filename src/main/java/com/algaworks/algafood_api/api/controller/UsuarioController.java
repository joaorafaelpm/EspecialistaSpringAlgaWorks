package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.assembler.UsuarioAssembler;
import com.algaworks.algafood_api.api.assembler.disassambler.UsuarioDisassembler;
import com.algaworks.algafood_api.api.model.UsuarioModel;
import com.algaworks.algafood_api.api.model.DTO.SenhaDTO;
import com.algaworks.algafood_api.api.model.DTO.UsuarioComSenhaDTO;
import com.algaworks.algafood_api.api.model.DTO.UsuarioDTO;
import com.algaworks.algafood_api.domain.model.Usuario;
import com.algaworks.algafood_api.domain.service.CadastroUsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor

public class UsuarioController {

    private final CadastroUsuarioService usuarioService;

    private final UsuarioAssembler usuarioAssembler;
    private final UsuarioDisassembler usuarioDisassembler;

    @GetMapping
    public List<UsuarioModel> findAll () {
        return usuarioAssembler.toCollection(usuarioService.findAll());
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioModel> findById (@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.findById(usuarioId);
        return ResponseEntity.ok(usuarioAssembler.usuarioToUsuarioModel(usuario));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel save (@RequestBody @Valid UsuarioComSenhaDTO usuarioSenhaDTO) {
        Usuario usuario = usuarioDisassembler.usuarioComSenhaDTOToUsuario(usuarioSenhaDTO);
        usuarioService.save(usuario);
        return usuarioAssembler.usuarioToUsuarioModel(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> save (@PathVariable Long id , @RequestBody @Valid UsuarioDTO usuarioDTO) {
        Usuario usuarioAntigo = usuarioService.findById(id);
        usuarioDisassembler.updateUsuarioFromDto(usuarioDTO , usuarioAntigo);
        usuarioService.save(usuarioAntigo);
        return ResponseEntity.ok(usuarioAssembler.usuarioToUsuarioModel(usuarioAntigo));
    }

    @PutMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void savePassword (@PathVariable Long id , @RequestBody @Valid SenhaDTO senhaDTO) {
        Usuario usuarioSenhaAntiga = usuarioService.findById(id);
        usuarioService.savePassword(usuarioSenhaAntiga , senhaDTO.getSenhaAtual() , senhaDTO.getNovaSenha());
    }
}

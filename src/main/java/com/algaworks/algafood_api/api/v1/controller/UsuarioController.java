package com.algaworks.algafood_api.api.v1.controller;

import com.algaworks.algafood_api.api.v1.assembler.UsuarioModelAssembler;
import com.algaworks.algafood_api.api.v1.assembler.disassambler.UsuarioDisassembler;
import com.algaworks.algafood_api.api.v1.model.UsuarioModel;
import com.algaworks.algafood_api.api.v1.model.DTO.SenhaDTO;
import com.algaworks.algafood_api.api.v1.model.DTO.UsuarioComSenhaDTO;
import com.algaworks.algafood_api.api.v1.model.DTO.UsuarioDTO;
import com.algaworks.algafood_api.api.v1.openapi.controller.UsuarioControllerOpenApi;
import com.algaworks.algafood_api.core.security.CheckSecurity;
import com.algaworks.algafood_api.domain.model.Usuario;
import com.algaworks.algafood_api.domain.service.CadastroUsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/usuarios")
@AllArgsConstructor

public class UsuarioController implements UsuarioControllerOpenApi {

    private CadastroUsuarioService usuarioService;

    private UsuarioModelAssembler usuarioModelAssembler;
    private UsuarioDisassembler usuarioDisassembler;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<UsuarioModel> all () {
        return usuarioModelAssembler.toCollection(usuarioService.findAll());
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping("/{usuarioId}")
    public UsuarioModel findById (@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.findById(usuarioId);
        return usuarioModelAssembler.toModel(usuario);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel add (@RequestBody @Valid UsuarioComSenhaDTO usuarioSenhaDTO) {
        Usuario usuario = usuarioDisassembler.usuarioComSenhaDTOToUsuario(usuarioSenhaDTO);
        usuarioService.save(usuario);
        return usuarioModelAssembler.toModel(usuario);
    }
    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarUsuario
    @PutMapping("/{usuarioId}")
    public UsuarioModel save (@PathVariable Long usuarioId , @RequestBody @Valid UsuarioDTO usuarioDTO) {
        Usuario usuarioAntigo = usuarioService.findById(usuarioId);
        usuarioDisassembler.updateUsuarioFromDto(usuarioDTO , usuarioAntigo);
        usuarioService.save(usuarioAntigo);
        return usuarioModelAssembler.toModel(usuarioAntigo);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarPropriaSenha
    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> savePassword (@PathVariable Long usuarioId , @RequestBody @Valid SenhaDTO senhaDTO) {
        usuarioService.changePassword(usuarioId , senhaDTO.getSenhaAtual() , senhaDTO.getNovaSenha());
        return ResponseEntity.noContent().build();
    }

}

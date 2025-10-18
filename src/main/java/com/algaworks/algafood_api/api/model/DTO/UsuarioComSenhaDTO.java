package com.algaworks.algafood_api.api.model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioComSenhaDTO extends UsuarioDTO{
    @NotBlank
    private String senha;

    public UsuarioComSenhaDTO(String nome, String email , String senha) {
        super(nome, email);
        this.senha = senha;
    }
}

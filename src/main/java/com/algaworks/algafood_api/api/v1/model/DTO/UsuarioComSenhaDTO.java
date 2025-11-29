package com.algaworks.algafood_api.api.v1.model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioComSenhaDTO extends UsuarioDTO{
    @NotBlank
    private String senha;

    public UsuarioComSenhaDTO(String nome, String email , String senha) {
        super(nome, email);
        this.senha = senha;
    }
}

package com.algaworks.algafood_api.api.v1.model.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    @Schema(example = "Rodrigo")
    @NotBlank
    private String nome ;
    @Schema(example = "rodrigo@gmail.com")
    @NotBlank
    @Email
    private String email;

}

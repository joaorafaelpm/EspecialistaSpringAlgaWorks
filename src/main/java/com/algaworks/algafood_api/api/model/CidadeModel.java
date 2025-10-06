package com.algaworks.algafood_api.api.model;


import com.algaworks.algafood_api.core.validation.Groups;
import com.algaworks.algafood_api.domain.model.Estado;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class CidadeModel {

    private Long id ;
    private String nome ;
    private EstadoModel estado ;

}


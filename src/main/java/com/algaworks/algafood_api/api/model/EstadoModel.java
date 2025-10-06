package com.algaworks.algafood_api.api.model;


import com.algaworks.algafood_api.core.validation.Groups;
import com.algaworks.algafood_api.domain.model.Cidade;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EstadoModel {

    private Long id ;
    private String nome ;

}


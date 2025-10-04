package com.algaworks.algafood_api.domain.model;

import com.algaworks.algafood_api.Groups;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cidade {

        @Id
        @EqualsAndHashCode.Include
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id ;

        @NotBlank
        private String nome ;

        @Valid
        @ConvertGroup(from = Default.class , to = Groups.EstadoId.class)
        @ManyToOne
        @JoinColumn(name = "estado_id" , nullable = false)
        private Estado estado ;

}

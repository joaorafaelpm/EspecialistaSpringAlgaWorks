package com.algaworks.algafood_api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
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

        @Column(nullable = false)
        private String nome ;

        @Valid
        @ManyToOne
        @JoinColumn(name = "estado_id" , nullable = false)
        private Estado estado ;

}

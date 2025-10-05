package com.algaworks.algafood_api.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nome ;
    private String email;
    private String senha;

    @CreationTimestamp
    @Column(columnDefinition = "datetime" , name = "data_cadastro" , nullable = false)
    private OffsetDateTime dataCadastro ;

    @ManyToMany
    @JoinTable(name = "usuario_grupo" ,
            joinColumns = @JoinColumn(name = "usuario_id") ,
            inverseJoinColumns = @JoinColumn(name = "grupo_id"))
    private List<Grupo> grupos = new ArrayList<>();

}

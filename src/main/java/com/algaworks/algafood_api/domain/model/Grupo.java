package com.algaworks.algafood_api.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Grupo {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String nome ;

    @ManyToMany
    @JoinTable(name = "grupo_permissao" ,
            joinColumns = @JoinColumn(name = "grupo_id") ,
            inverseJoinColumns = @JoinColumn(name = "permissao_id"))
    private Set<Permissao> permissoes = new HashSet<>();

    public boolean associarPermissao (Permissao permissao) {
        return getPermissoes().add(permissao);
    }
    public boolean desassociarPermissao (Permissao permissao) {
        return getPermissoes().remove(permissao);
    }


}

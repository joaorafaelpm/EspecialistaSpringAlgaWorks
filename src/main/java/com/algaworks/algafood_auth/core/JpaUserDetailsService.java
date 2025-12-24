package com.algaworks.algafood_auth.core;

import com.algaworks.algafood_auth.main.model.Usuario;
import com.algaworks.algafood_auth.main.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository ;

//    Isso no contexto do JPA impede que ele feche a transação logo após consultar o nome e extenda isso até nós pegarmos a lista de grupo dentro da entidade
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com e-mail informado!"));
        return new User(usuario.getEmail() , usuario.getSenha(), getAuthorities(usuario));
    }

    private Collection<GrantedAuthority> getAuthorities (Usuario usuario) {
        return usuario.getGrupos().stream()
                .flatMap(grupo -> grupo.getPermissoes().stream()
                .map(permissao -> new SimpleGrantedAuthority(permissao.getNome())))
                .collect(Collectors.toSet());
    }
}

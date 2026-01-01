package com.algaworks.algafood_api.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {

   public @interface Cozinhas {
       @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
       @Retention(RetentionPolicy.RUNTIME)
       @Target(ElementType.METHOD)
       public @interface PodeConsultar {
       }
       @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
       @Retention(RetentionPolicy.RUNTIME)
       @Target(ElementType.METHOD)
       public @interface PodeEditar {
       }
   }

   public @interface Restaurantes {
       @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
       @Retention(RetentionPolicy.RUNTIME)
       @Target(ElementType.METHOD)
       public @interface PodeConsultar {
       }
       @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES')")
       @Retention(RetentionPolicy.RUNTIME)
       @Target(ElementType.METHOD)
       public @interface PodeGerenciarCadastro {
       }
       @PreAuthorize("hasAuthority('SCOPE_WRITE') and (" +
               "hasAuthority('EDITAR_RESTAURANTES') or " +
               "@algaSecurity.gerenciaRestaurante(#restauranteId))")
       @Retention(RetentionPolicy.RUNTIME)
       @Target(ElementType.METHOD)
       public @interface PodeGerenciarFuncionamento {
       }
   }

    public @interface Pedidos {
//   Para a consulta de pedidos a gente verifica os escopos padrão (se pode ler e se está autenticado)
//   Depois disso, antes de serializar em um objeto json e retornar ao cliente, a gente verifica se ele tem autoridade de consultar pedidos, se ele é o usuário correspondente do pedido, ou se ele é dono do restaurante onde o pedido foi feito
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or " +
                "@algaSecurity.getUsuarioId() == returnObject.cliente.id or " +
                "@algaSecurity.gerenciaRestaurante(returnObject.restaurante.id)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeBuscar {
        }
        @PreAuthorize("hasAuthority('SCOPE_READ') and (" +
                "hasAuthority('CONSULTAR_PEDIDOS') or " +
                "@algaSecurity.getUsuarioId() == #pedidoFilter.clienteId or " +
                "@algaSecurity.gerenciaRestaurante(#pedidoFilter.restauranteId))")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeListar {
        }
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (" +
                "hasAuthority('EDITAR_PEDIDO') or " +
                "@algaSecurity.gerenciaRestauranteDoPedido(#codigo))")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeGerenciar {
        }
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeCriar {
        }


    }

    public @interface FormasPagamento {
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeConsultar {
        }
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeEditar {
        }
    }
    public @interface Cidades {
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeConsultar {
        }
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeEditar {
        }
    }
    public @interface Estados {
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeConsultar {
        }
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeEditar {
        }
    }
    public @interface UsuariosGruposPermissoes {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and @algaSecurity.getUsuarioId() == #usuarioId")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeAlterarPropriaSenha {
        }
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or " +
                "@algaSecurity.getUsuarioId() == #usuarioId)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeAlterarUsuario {
        }
        @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('CONSULTAR_USUARIOS_GRUPOS_PERMISSOES')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeConsultar {
        }
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeEditar {
        }
    }

    public @interface Estatisticas {
        @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('GERAR_RELATORIOS')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeConsultar {
        }
    }


}

package com.algaworks.algafood_api.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {

   public @interface Cozinhas {
       @PreAuthorize("@algaSecurity.podeConsultarCozinhas()")
       @Retention(RetentionPolicy.RUNTIME)
       @Target(ElementType.METHOD)
       public @interface PodeConsultar {
       }
       @PreAuthorize("@algaSecurity.podeEditarCozinhas()")
       @Retention(RetentionPolicy.RUNTIME)
       @Target(ElementType.METHOD)
       public @interface PodeEditar {
       }
   }

   public @interface Restaurantes {
       @PreAuthorize("@algaSecurity.podeConsultarRestaurantes()")
       @Retention(RetentionPolicy.RUNTIME)
       @Target(ElementType.METHOD)
       public @interface PodeConsultar {
       }
       @PreAuthorize("@algaSecurity.podeGerenciarCadastrosRestaurantes()")
       @Retention(RetentionPolicy.RUNTIME)
       @Target(ElementType.METHOD)
       public @interface PodeGerenciarCadastro {
       }
       @PreAuthorize("@algaSecurity.podeGerenciarFuncionamentoRestaurantes(#restauranteId)")
       @Retention(RetentionPolicy.RUNTIME)
       @Target(ElementType.METHOD)
       public @interface PodeGerenciarFuncionamento {
       }
   }

    public @interface Pedidos {
//   Para a consulta de pedidos a gente verifica os escopos padrão (se pode ler e se está autenticado)
//   Depois disso, antes de serializar em um objeto json e retornar ao cliente, a gente verifica se ele tem autoridade de consultar pedidos, se ele é o usuário correspondente do pedido, ou se ele é dono do restaurante onde o pedido foi feito
        @PreAuthorize("@algaSecurity.podeBuscarPedidos()")
        @PostAuthorize("@algaSecurity.podeBuscarPedidos(" +
                "returnObject.cliente.id , returnObject.restaurante.id)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeBuscar {
        }
        @PreAuthorize("@algaSecurity.podeListarPedidos(" +
                "#pedidoFilter.clienteId , #pedidoFilter.restauranteId )")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeListar {
        }
        @PreAuthorize("@algaSecurity.podeGerenciarPedidos(#codigo)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeGerenciar {
        }
        @PreAuthorize("@algaSecurity.podeCriarPedidos()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeCriar {
        }
    }

    public @interface FormasPagamento {
        @PreAuthorize("@algaSecurity.podeConsultarFormasPagamento()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeConsultar {
        }
        @PreAuthorize("@algaSecurity.podeEditarFormasPagamento()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeEditar {
        }
    }
    public @interface Cidades {
        @PreAuthorize("@algaSecurity.podeConsultarCidades()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeConsultar {
        }
        @PreAuthorize("@algaSecurity.podeEditarCidades()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeEditar {
        }
    }
    public @interface Estados {
        @PreAuthorize("@algaSecurity.podeConsultarEstados()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeConsultar {
        }
        @PreAuthorize("@algaSecurity.podeEditarEstados()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeEditar {
        }
    }
    public @interface UsuariosGruposPermissoes {
        @PreAuthorize("@algaSecurity.podeAlterarPropriaSenhaUsuariosGruposPermissoes(#usuarioId)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeAlterarPropriaSenha {
        }
        @PreAuthorize("@algaSecurity.podeAlterarUsuariosGruposPermissoes(#usuarioId)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeAlterarUsuario {
        }
        @PreAuthorize("@algaSecurity.podeConsultarUsuariosGruposPermissoes()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeConsultar {
        }
        @PreAuthorize("@algaSecurity.podeEditarUsuariosGruposPermissoes()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeEditar {
        }
    }

    public @interface Estatisticas {
        @PreAuthorize("@algaSecurity.podeConsultarEstatisticas()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeConsultar {
        }
    }


}

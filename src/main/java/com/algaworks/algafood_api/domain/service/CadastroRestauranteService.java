package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood_api.domain.model.FormaPagamento;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.model.Usuario;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CadastroRestauranteService {

    RestauranteRepository restauranteRepository;
    CadastroCozinhaService cozinhaService ;
    CadastroCidadeService cidadeService ;
    CadastroFormaPagamentoService formaPagamentoService ;
    CadastroUsuarioService usuarioService ;

    public List<Restaurante> findAll() {
        return restauranteRepository.findAll();
    }

    public Restaurante findById (Long id ) {
        return restauranteRepository.findById(id).orElseThrow(() ->
                new RestauranteNaoEncontradoException(id));
    }
    public Restaurante findByIdWithAllDependencies (Long id ) {
        return restauranteRepository.findByIdMapperResolved(id).orElseThrow(() ->
                new RestauranteNaoEncontradoException(id));
    }



    @Transactional
    public Restaurante save (Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Long cidadeId = restaurante.getEndereco().getCidade().getId();
        restaurante.setCozinha(cozinhaService.findById(cozinhaId));
        restaurante.getEndereco().setCidade(cidadeService.findById(cidadeId));

//        Eu faço um flush, para gerar um id assim que o comando save for processado, dessa forma eu sigo para o próximo passo com a entidade completa e carregada
        Restaurante restauranteSalvo = restauranteRepository.saveAndFlush(restaurante);
//        O hibernate precisa carregar todos os objetos para que o mapStruct forme eles de acordo com o modelo, então é absolutamente necessário, preparar uma query específica que traga todas as dependências do restaurante.
        return  findByIdWithAllDependencies(restauranteSalvo.getId());

    }

    @Transactional
    public void remove (Long id) {
        try {
            Restaurante restaurante = findById(id);
            restauranteRepository.delete(restaurante);
        }
        catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Restaurante de código %d tem produtos ativos, logo, não pode ser removida!" , id)
            ) ;
        }
    }

//    Enquanto o restaurante for chamado pelo JPA o restaurante entra em processo de gerênciamento pelo próprio JPA e entende que quando houver alguma mudança ele deve atualizar no banco automaticamente, então não é necessário salvar de novo
    @Transactional
    public void ativar (Long id) {
        Restaurante restaurante = findById(id);
        restaurante.ativar();
    }
    @Transactional
    public void inativar (Long id) {
        Restaurante restaurante = findById(id);
        restaurante.inativar();
    }
    @Transactional
    public void abrir (Long id) {
        Restaurante restaurante = findById(id);
        restaurante.abrir();
    }
    @Transactional
    public void fechar (Long id) {
        Restaurante restaurante = findById(id);
        restaurante.fechar();
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId , Long formaDePagamentoId) {
        Restaurante restaurante = findById(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.findById(formaDePagamentoId);

        restaurante.desassociarFormaPagamento(formaPagamento);
    }
    @Transactional
    public void associarFormaPagamento(Long restauranteId , Long formaDePagamentoId) {
        Restaurante restaurante = findById(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.findById(formaDePagamentoId);

        restaurante.associarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void desassociarUsuarioResponsavel(Long restauranteId , Long usuarioId) {
        Restaurante restaurante = findById(restauranteId);
        Usuario usuario = usuarioService.findById(usuarioId);

        restaurante.desassociarUsuario(usuario);
    }
    @Transactional
    public void associarUsuarioResponsavel(Long restauranteId , Long usuarioId) {
        Restaurante restaurante = findById(restauranteId);
        Usuario usuario = usuarioService.findById(usuarioId);

        restaurante.associarUsuario(usuario);
    }


}

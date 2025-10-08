package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CadastroRestauranteService {

    RestauranteRepository restauranteRepository;
    CozinhaRepository cozinhaRepository ;

    public Restaurante findById (Long id ) {
        return restauranteRepository.findById(id).orElseThrow(() ->
                new RestauranteNaoEncontradoException(id));
    }

    @Transactional
    public Restaurante save (Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        restaurante.setCozinha(cozinhaRepository.findById(cozinhaId).orElseThrow(() ->
                new CozinhaNaoEncontradaException(cozinhaId)));
        return restauranteRepository.save(restaurante) ;

    }

    @Transactional
    public Restaurante save(Long id, Restaurante restauranteAtualizado) {
        Restaurante restauranteExistente = findById(id);
        Long cozinhaId = restauranteAtualizado.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
        restauranteExistente.setCozinha(cozinha);

        return restauranteRepository.save(restauranteExistente);
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



}

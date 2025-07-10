package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CadastroRestauranteService {

    RestauranteRepository restauranteRepository;
    CozinhaRepository cozinhaRepository ;

    public Restaurante save (Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.findById(cozinhaId).orElseThrow(() ->
                new EntidadeNaoEncontradaException(
                        String.format("Não existe cozinha com o código de %d!" , cozinhaId))
        );
        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante) ;
    }



}

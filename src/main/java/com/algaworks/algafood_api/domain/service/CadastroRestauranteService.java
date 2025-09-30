package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
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

    public Restaurante save (Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        restaurante.setCozinha(cozinhaRepository.findById(cozinhaId).orElseThrow(() ->
                new CozinhaNaoEncontradaException(cozinhaId)));
        return restauranteRepository.save(restaurante) ;

    }

    public Restaurante save (Long id ,Restaurante restaurante) {
        Restaurante restauranteAntigo = findById(id);
        BeanUtils.copyProperties(restaurante, restauranteAntigo ,
                "id" , "endereco" , "dataCadastro", "data_cadastro" , "formasPagamento");
        restaurante.setId(id);
        return save(restauranteAntigo);

    }

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



}

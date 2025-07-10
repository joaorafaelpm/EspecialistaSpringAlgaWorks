package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/testes")
public class TesteController {

    CozinhaRepository cozinhaRepository;

    RestauranteRepository restauranteRepository;

    @GetMapping("/cozinhas/por-nome")
    public  List<Cozinha> findAllByNome (String nome) {
        return cozinhaRepository.findByNomeContaining(nome);
    }

    @GetMapping("/cozinhas/existe-por-nome")
    public  boolean existeCozinhaPorNome (String nome) {
        return cozinhaRepository.existsByNome(nome);
    }

    @GetMapping("/restaurantes/por-taxafrete")
    public  List<Restaurante> restaurantePorTaxaFrete (BigDecimal taxaInicial , BigDecimal taxaFinal) {
        return restauranteRepository.findByTaxaFreteBetween(taxaInicial , taxaFinal);
    }

    @GetMapping("/restaurantes/por-nome")
    public Optional<Restaurante> restaurantePrimeiroPorNome (String nome) {
        return restauranteRepository.findFirstByNomeContaining(nome) ;
    }

    @GetMapping("/restaurantes/por-nomeoucozinhaid")
    public List<Restaurante> restaurantePorNomeOuIdDeCozinha (String nome , Long cozinhaId) {
        return restauranteRepository.consultarPorNome(nome , cozinhaId) ;
    }

    @GetMapping("/restaurantes/por-nomeprimeiro")
    public Optional<Restaurante> primeiroRestaurantePorNome (String nome) {
        return restauranteRepository.findFirstByNomeContaining(nome) ;
    }

    @GetMapping("/restaurantes/por-nometop2")
    public List<Restaurante> doisPrimeirosRestaurantesPorNome (String nome) {
        return restauranteRepository.findTop2ByNomeContaining(nome);
    }

    @GetMapping("/restaurantes/por-nome-e-taxafrete")
    public List<Restaurante> restaurantePorNomeEFrete (String nome , BigDecimal taxaInicial , BigDecimal taxaFinal) {
        return restauranteRepository.find(nome , taxaInicial , taxaFinal) ;
    }


    @GetMapping("/restaurantes/conte-numero-cozinha")
    public int contarCozinhaPorId (Long cozinhaId) {
        return restauranteRepository.countByCozinhaId(cozinhaId) ;
    }









}

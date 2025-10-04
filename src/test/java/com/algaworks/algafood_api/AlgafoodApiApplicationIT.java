package com.algaworks.algafood_api;

import com.algaworks.algafood_api.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.service.CadastroCozinhaService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class AlgafoodApiApplicationIT {

	@Autowired
	private CadastroCozinhaService cozinhaService;



	@Test
	public void deveDeveAtribuirIdNaCozinha_QuandoCadastrarCozinha () {
		Cozinha novaCozinha = new Cozinha() ;
		novaCozinha.setNome("Chinesa");

		cozinhaService.save(novaCozinha) ;

		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}

	@Test
	public void deveFalhar_QuandoAtribuirCozinhaSemNome () {
		Cozinha novaCozinha = new Cozinha() ;
		novaCozinha.setNome(null);

		ConstraintViolationException erroEsperado = assertThrows(ConstraintViolationException.class , () -> {
			cozinhaService.save(novaCozinha);
		});

		assertThat(erroEsperado).isNotNull();
	}

	@Test
	public void deveFalhar_QuandoRemoverCozinhaEmUso () {
		EntidadeEmUsoException erroEsperado =
				assertThrows(EntidadeEmUsoException.class, () -> {
					cozinhaService.remove(1L);
				});

		assertThat(erroEsperado).isNotNull();
	}

	@Test
	public void deveFalhar_QuandoRemoverCozinhaInexistente () {
		CozinhaNaoEncontradaException erroEsperado =
				assertThrows(CozinhaNaoEncontradaException.class, () -> {
					cozinhaService.remove(12321312L);
				});

		assertThat(erroEsperado).isNotNull();
	}

}

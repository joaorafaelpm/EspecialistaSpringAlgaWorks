package com.algafood.javaclient.client;

import java.math.BigDecimal;

import org.springframework.web.client.RestTemplate;

import com.algafood.javaclient.client.api.ClientAPIException;
import com.algafood.javaclient.client.api.RestauranteClient;
import com.algafood.javaclient.client.model.Problem;
import com.algafood.javaclient.client.model.RestauranteResumoModel;
import com.algafood.javaclient.client.model.input.CidadeIdInput;
import com.algafood.javaclient.client.model.input.CozinhaIdInput;
import com.algafood.javaclient.client.model.input.EnderecoInput;
import com.algafood.javaclient.client.model.input.RestauranteInput;



public class CadastroDeRestauranteMain {
    public static void main(String[] args) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			
			RestauranteClient restauranteClient = new RestauranteClient(
					restTemplate, "http://localhost:8080");
			
            CozinhaIdInput cozinha = new CozinhaIdInput();
            cozinha.setId(1L);
            CidadeIdInput cidade = new CidadeIdInput();
            cidade.setId(1L);

            EnderecoInput endereco = new EnderecoInput();
            endereco.setCep("38400-000");
            endereco.setLogradouro("Rua Floriano Peixoto");
            endereco.setNumero("123");
            endereco.setBairro("Centro");
            endereco.setComplemento("Apto 101");
            endereco.setCidade(cidade);

			RestauranteInput restauranteInput = new RestauranteInput();
            restauranteInput.setNome("Restaurante do João");
            restauranteInput.setTaxaFrete(BigDecimal.valueOf(10.0));
            restauranteInput.setCozinhaId(new CozinhaIdInput());
            restauranteInput.setCozinhaId(cozinha);
            restauranteInput.setEndereco(endereco);
            
            RestauranteResumoModel cadastrar = restauranteClient.cadastrar(restauranteInput);;
            System.out.println("Restaurante cadastrado com sucesso!" + cadastrar.toString());

		}catch (ClientAPIException e) {
            if (e.getProblem() != null) {
                System.out.println(e.getProblem().getUserMessage());
                
                e.getProblem().getObjects().stream()
                .forEach(object -> System.out.println("- " + object.getUserMessage()));
                
            } else {
                System.out.println("Erro desconhecido");
                e.printStackTrace();
      }
    }
	}
}

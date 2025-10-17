package com.algafood.javaclient.client;

import org.springframework.web.client.RestTemplate;

import com.algafood.javaclient.client.api.ClientAPIException;
import com.algafood.javaclient.client.api.RestauranteClient;

public class ListagemDeRestauranteMain {
    public static void main(String[] args) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			
			RestauranteClient restauranteClient = new RestauranteClient(
					restTemplate, "http://localhost:8080");
			
			restauranteClient.listar().stream()
				.forEach(restaurante -> System.out.println(restaurante.toString()));
		}
		catch (ClientAPIException e) {
			if (e.getProblem() != null) {
				System.out.println(e.getProblem());
			}
			else {
				System.out.println("Erro desconhecido!");
				e.printStackTrace();
			}
		}
	}
}

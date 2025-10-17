package com.algafood.javaclient.client.api;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.algafood.javaclient.client.model.RestauranteResumoModel;
import com.algafood.javaclient.client.model.input.RestauranteInput;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RestauranteClient {

	private static final String RESOURCE_PATH = "/restaurantes";
	
	private RestTemplate restTemplate;
	private String url;
	
	public List<RestauranteResumoModel> listar() {
		try {
			URI resourceUri = URI.create(url + RESOURCE_PATH);
			
			RestauranteResumoModel[] restaurantes = restTemplate
					.getForObject(resourceUri, RestauranteResumoModel[].class);
			
			return Arrays.asList(restaurantes);
		}
		catch (RestClientResponseException e) {
			throw new ClientAPIException(e.getMessage() , e) ;
		}
	}
	
	public RestauranteResumoModel cadastrar (RestauranteInput restauranteInput) {
		try {
			URI resourceUri = URI.create(url + RESOURCE_PATH);
			
			return restTemplate.postForObject(resourceUri, restauranteInput, RestauranteResumoModel.class);
		}
		catch (HttpClientErrorException e) {
    		throw new ClientAPIException(e.getMessage(), e);
  	}
	}
}
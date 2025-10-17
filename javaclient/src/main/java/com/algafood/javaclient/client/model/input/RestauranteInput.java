package com.algafood.javaclient.client.model.input;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RestauranteInput {
    private String nome ;
    private BigDecimal taxaFrete ;
    private CozinhaIdInput cozinhaId ;
    private EnderecoInput endereco ;
    

}

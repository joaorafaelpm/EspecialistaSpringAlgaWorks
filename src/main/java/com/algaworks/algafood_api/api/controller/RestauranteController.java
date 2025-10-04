package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.core.validation.ValidacaoException;
import com.algaworks.algafood_api.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import com.algaworks.algafood_api.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/restaurantes")
@Slf4j
public class RestauranteController {

    RestauranteRepository restauranteRepository;

    CadastroRestauranteService restauranteService;

    private SmartValidator validator;

    @GetMapping
    public List<Restaurante> all () {
        return restauranteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> getById (@PathVariable Long id) {
        Restaurante restaurante = restauranteService.findById(id);
        return ResponseEntity.ok(restaurante);
    }


    @PostMapping
    public ResponseEntity<?> add (@RequestBody @Valid  Restaurante restaurante) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(restauranteService.save(restaurante));
        }
        catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage() , e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> save (@PathVariable Long id , @RequestBody @Valid Restaurante restaurante) {
        try {
          return ResponseEntity.ok(restauranteService.save(id , restaurante));
        }
        catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage() , e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> savePatch (@PathVariable Long id , @RequestBody Map<String , Object> campos , HttpServletRequest request) {
        Restaurante restauranteAtual = restauranteService.findById(id);
        merge(campos , restauranteAtual , request);


        validate(restauranteAtual , "restaurante") ;
        return save(id , restauranteAtual);
    }

    private void validate(Restaurante restauranteAtual , String objectName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restauranteAtual , objectName);
        validator.validate(restauranteAtual , bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidacaoException(bindingResult);
        }

    }



    public void merge (Map<String , Object> dadosOrigem , Restaurante restauranteDestino , HttpServletRequest request) {
//        Precisamos desse parâmetro para usar no construtor da nossa exceção, já que o construtor atual dela está depreciado
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
        try{
            //        Instancia para mapear o objeto e modificar os valores do campo do body de acordo com os tipos da classe
            ObjectMapper objectMapper = new ObjectMapper();

//        Eu defino que eu quero lançar uma exceção caso tenha alguma propriedade ignorada pelo @JsonIgnore
//        Um problema que nós temos com isso é que a exception lançada é um IllegalArgumentException (que não faz parte do HttpMessageNotReadable então não é capturado pelo ExceptionHandler), então nós precisamos capturar com o try e fazer outro lançamento de um HttpMessageNotReadable
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES , true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES , true);
            Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem , Restaurante.class);

//        Mapeio todos os dados do body para reconhecer somente os valores especificados e altera-los
            dadosOrigem.forEach((chave , valor) -> {
                Field field = ReflectionUtils.findField(Restaurante.class , chave);
//            Permito que ele acesse instâncias privadas
                field.setAccessible(true);

                Object novoValor = ReflectionUtils.getField(field , restauranteOrigem);

//            Eu pego o meu restaurante antigo e defino que as instâncias que vão ser alterados nele são somente as que eu ofereci, usando o field como parâmetro.
//            E como valor para substituir eu uso os que eu modifiquei com base nos padrões da classe usando o "novoValor"
                ReflectionUtils.setField(field , restauranteDestino , novoValor);
            });
        }
        catch (IllegalArgumentException e) {
            Throwable cause = ExceptionUtils.getRootCause(e);
//            Fazemos isso para evitar usar um construtor depreciado
            throw new HttpMessageNotReadableException(e.getMessage() , cause , serverHttpRequest);
        }

    }





}

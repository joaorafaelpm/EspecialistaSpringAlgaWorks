package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.exceptionhandler.APIError;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.domain.repository.CidadeRepository;
import com.algaworks.algafood_api.domain.service.CadastroCidadeService;
import com.algaworks.algafood_api.domain.service.CadastroRestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/cidades")
public class CidadeController {

    CidadeRepository cidadeRepository;

    CadastroCidadeService cidadeService;

    @GetMapping
    public List<Cidade> all () {
        return cidadeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cidade> getById (@PathVariable Long id) {
        return ResponseEntity.ok(cidadeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> add (@RequestBody Cidade cidade) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cidadeService.save(cidade));
        }
        catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> save (@PathVariable Long id , @RequestBody  Cidade cidade) {
        try {
            Cidade cidadeAntiga = cidadeService.findById(id);
            BeanUtils.copyProperties(cidade , cidadeAntiga , "id");
            Cidade cidadeSalva = cidadeService.save(cidadeAntiga);
            return ResponseEntity.ok(cidadeSalva);
        }
        catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void remove (@PathVariable Long id) {
        cidadeService.remove(id);
    }

//    Embora esse tipo de exception handler capture as exceções do tipo EntidadeNaoEncontrada, no meu caso atual, em que eu passo a causa junto da menssagem, ele acaba capturando somente como EntidadeNaoEncontrada e não como NegocioException, basta eu retirar a causa de EstadoNaoEncontrado por exemplo, que ele já reconhece o NegocioException
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> resolveEstadoNaoEncontrado(EntidadeNaoEncontradaException e) {
        APIError apiError = APIError.builder()
                .dataHora(LocalDateTime.now())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> resolveNegocioException(NegocioException e) {
        APIError apiError = APIError.builder()
                .dataHora(LocalDateTime.now())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

}

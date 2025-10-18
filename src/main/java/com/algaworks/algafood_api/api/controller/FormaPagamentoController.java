package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood_api.api.assembler.disassambler.FormaPagamentoDisassembler;
import com.algaworks.algafood_api.api.model.FormaPagamentoModel;
import com.algaworks.algafood_api.api.model.DTO.FormaPagamentoDTO;
import com.algaworks.algafood_api.domain.model.FormaPagamento;
import com.algaworks.algafood_api.domain.service.CadastroFormaPagamentoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/formas-pagamento")
@AllArgsConstructor
public class FormaPagamentoController {

    CadastroFormaPagamentoService formaPagamentoService ;

    FormaPagamentoAssembler formaPagamentoAssembler ;
    FormaPagamentoDisassembler formaPagamentoDisassembler ;

    @GetMapping
    public ResponseEntity<List<FormaPagamentoModel>> all (ServletWebRequest request) {
//        Gerando eTag personalizado...
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
        String eTag = "0";
        OffsetDateTime dataUltimaAtualizacao = formaPagamentoService.getUltimaDataAtualizacao();

        if (dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }
//        Se a etag não alterar, já não precisamos continuar com o processamento, aqui a gente já tem condição se continua ou não o processamento
//        o checkNotModified == a if-none-match
        if (request.checkNotModified(eTag)) {
            return null;
        }
        List<FormaPagamentoModel> formaPagamentoModels = formaPagamentoAssembler
                .toCollection(formaPagamentoService.findAll());
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10 , TimeUnit.SECONDS).cachePublic())
                .eTag(eTag)
                .body(formaPagamentoModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoModel> getById (@PathVariable Long id , ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
        String eTag = "0";
        OffsetDateTime dataUltimaAtualizacao = formaPagamentoService.getUltimaDataAtualizacaoById(id);

        if (dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }
        FormaPagamentoModel formaPagamentoModels = formaPagamentoAssembler
                .formaPagamentoToFormaPagamentoModel(formaPagamentoService.findById(id));
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10 , TimeUnit.SECONDS).cachePublic())
                .eTag(eTag)
                .body(formaPagamentoModels);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel add (@RequestBody @Valid FormaPagamentoDTO formaPagamentoDTO) {
        FormaPagamento formaPagamento = formaPagamentoDisassembler.formaPagamentoDTOToFormaPagamento(formaPagamentoDTO);
        return formaPagamentoAssembler.formaPagamentoToFormaPagamentoModel(formaPagamentoService.save(formaPagamento)) ;
    }

    @PutMapping("/{id}")
    public  ResponseEntity<FormaPagamentoModel> save (@PathVariable Long id , @RequestBody @Valid FormaPagamentoDTO formaPagamentoDTO) {
        FormaPagamento formaPagamentoAntigo = formaPagamentoService.findById(id);
        formaPagamentoDisassembler.updateFormaPagamentoFromDto(formaPagamentoDTO , formaPagamentoAntigo);
        return ResponseEntity.ok(formaPagamentoAssembler
                .formaPagamentoToFormaPagamentoModel(formaPagamentoService
                        .save(id , formaPagamentoAntigo)));
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void remove (@PathVariable Long id) {
        formaPagamentoService.remove(id);
    }


}

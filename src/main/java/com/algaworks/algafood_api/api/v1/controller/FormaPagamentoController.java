package com.algaworks.algafood_api.api.v1.controller;

import com.algaworks.algafood_api.api.v1.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood_api.api.v1.assembler.disassambler.FormaPagamentoDisassembler;
import com.algaworks.algafood_api.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood_api.api.v1.model.DTO.FormaPagamentoDTO;
import com.algaworks.algafood_api.api.v1.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algaworks.algafood_api.core.security.CheckSecurity;
import com.algaworks.algafood_api.domain.model.FormaPagamento;
import com.algaworks.algafood_api.domain.service.CadastroFormaPagamentoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/v1/formas-pagamento")
@AllArgsConstructor
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

    private CadastroFormaPagamentoService formaPagamentoService ;

    private FormaPagamentoAssembler formaPagamentoAssembler ;
    private FormaPagamentoDisassembler formaPagamentoDisassembler ;

    @CheckSecurity.FormasPagamento.PodeConsultar
    @GetMapping
    public ResponseEntity<CollectionModel<FormaPagamentoModel>> all (ServletWebRequest request) {
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
        CollectionModel<FormaPagamentoModel> formaPagamentoModels = formaPagamentoAssembler
                .toCollection(formaPagamentoService.findAll());
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10 , TimeUnit.SECONDS).cachePublic())
                .eTag(eTag)
                .body(formaPagamentoModels);
    }
    @CheckSecurity.FormasPagamento.PodeConsultar
    @GetMapping("/{formaDePagamentoId}")
    public ResponseEntity<FormaPagamentoModel> getById (@PathVariable Long formaDePagamentoId , ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
        String eTag = "0";
        OffsetDateTime dataUltimaAtualizacao = formaPagamentoService.getUltimaDataAtualizacaoById(formaDePagamentoId);

        if (dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }
        FormaPagamentoModel formaPagamentoModels = formaPagamentoAssembler
                .toModel(formaPagamentoService.findById(formaDePagamentoId));
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10 , TimeUnit.SECONDS).cachePublic())
                .eTag(eTag)
                .body(formaPagamentoModels);
    }
    @CheckSecurity.FormasPagamento.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel add (@RequestBody @Valid FormaPagamentoDTO formaPagamentoDTO) {
        FormaPagamento formaPagamento = formaPagamentoDisassembler.formaPagamentoDTOToFormaPagamento(formaPagamentoDTO);
        return formaPagamentoAssembler.toModel(formaPagamentoService.save(formaPagamento)) ;
    }
    @CheckSecurity.FormasPagamento.PodeEditar
    @PutMapping("/{formaDePagamentoId}")
    public FormaPagamentoModel save (@PathVariable Long formaDePagamentoId , @RequestBody @Valid FormaPagamentoDTO formaPagamentoDTO) {
        FormaPagamento formaPagamentoAntigo = formaPagamentoService.findById(formaDePagamentoId);
        formaPagamentoDisassembler.updateFormaPagamentoFromDto(formaPagamentoDTO , formaPagamentoAntigo);
        return formaPagamentoAssembler
                .toModel(formaPagamentoService
                        .save(formaDePagamentoId , formaPagamentoAntigo));
    }
    @CheckSecurity.FormasPagamento.PodeEditar
    @DeleteMapping("/{formaDePagamentoId}")
    public ResponseEntity<Void> remove (@PathVariable Long formaDePagamentoId) {
        formaPagamentoService.remove(formaDePagamentoId);
        return ResponseEntity.noContent().build();
    }


}

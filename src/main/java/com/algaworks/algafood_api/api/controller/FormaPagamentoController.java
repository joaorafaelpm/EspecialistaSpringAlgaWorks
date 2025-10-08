package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood_api.api.assembler.disassambler.FormaPagamentoDisassembler;
import com.algaworks.algafood_api.api.model.FormaPagamentoModel;
import com.algaworks.algafood_api.api.model.input.FormaPagamentoDTO;
import com.algaworks.algafood_api.domain.model.FormaPagamento;
import com.algaworks.algafood_api.domain.service.CadastroFormaPagamentoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
@AllArgsConstructor
public class FormaPagamentoController {


    CadastroFormaPagamentoService formaPagamentoService ;

    FormaPagamentoAssembler formaPagamentoAssembler ;
    FormaPagamentoDisassembler formaPagamentoDisassembler ;

    @GetMapping
    public List<FormaPagamentoModel> all () {
        return formaPagamentoAssembler.toCollection(formaPagamentoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoModel> getById (@PathVariable Long id) {
        return ResponseEntity.ok().body(formaPagamentoAssembler.formaPagamentoToFormaPagamentoModel(formaPagamentoService.findById(id)));
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

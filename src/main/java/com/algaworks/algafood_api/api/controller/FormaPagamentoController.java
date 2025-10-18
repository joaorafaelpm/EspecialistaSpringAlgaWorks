package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood_api.api.assembler.disassambler.FormaPagamentoDisassembler;
import com.algaworks.algafood_api.api.model.FormaPagamentoModel;
import com.algaworks.algafood_api.api.model.input.FormaPagamentoDTO;
import com.algaworks.algafood_api.domain.model.FormaPagamento;
import com.algaworks.algafood_api.domain.service.CadastroFormaPagamentoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<FormaPagamentoModel>> all () {

        List<FormaPagamentoModel> formaPagamentoModels = formaPagamentoAssembler.toCollection(formaPagamentoService.findAll());
        return ResponseEntity.ok()
//*                Esse é o padrão, define o tempo de vida de 10 segundos e o cach por padrão é público
                //  .cacheControl(CacheControl.maxAge(10 , TimeUnit.SECONDS))
//*                O tempo continua em 10 segundos, porém esse .cachePrivate não permite cache compartilhado com outros servidores se não o local, é recomendado para informações "pessoais" de um único usuário, por que nesses casos não tem  necessidade de expor esse cache para a possibilidade de um proxy reverso, já que a informação só deve ser passada para 1 local
                //  .cacheControl(CacheControl.maxAge(10 , TimeUnit.SECONDS).cachePrivate())
//*                O padrão é publico, permite cache compartilhado
                .cacheControl(CacheControl.maxAge(10 , TimeUnit.SECONDS).cachePublic())
//*                É controverso pelo nome, mas basicamente esse tipo de cache determina que sempre que for feito uma requisição é necessário validar o cache. Mesmo que a gente use uma etag ou o maxAge ele sempre vai bater no servidor, porém ele ainda vai cachear a informação
                // .cacheControl(CacheControl.noCache())
//*               Esse sim não permite nenhum tipo de cache armazenável e sempre bate no servidor e retorna informações puramente do servidor
                // .cacheControl(CacheControl.noStore())
                .body(formaPagamentoModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoModel> getById (@PathVariable Long id) {
        FormaPagamentoModel formaPagamentoModel = formaPagamentoAssembler.formaPagamentoToFormaPagamentoModel(formaPagamentoService.findById(id));
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10 , TimeUnit.SECONDS).cachePublic())
                .body(formaPagamentoModel);
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

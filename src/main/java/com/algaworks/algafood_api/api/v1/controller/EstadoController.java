package com.algaworks.algafood_api.api.v1.controller;


import com.algaworks.algafood_api.api.v1.assembler.EstadoModelAssembler;
import com.algaworks.algafood_api.api.v1.assembler.disassambler.EstadoDisassembler;
import com.algaworks.algafood_api.api.v1.model.EstadoModel;
import com.algaworks.algafood_api.api.v1.model.DTO.EstadoDTO;
import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.EstadoRepository;
import com.algaworks.algafood_api.domain.service.CadastroEstadoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@AllArgsConstructor
@RequestMapping("/v1/estados")
public class EstadoController {

    private EstadoRepository estadoRepository ;

    private CadastroEstadoService estadoService;

    private EstadoModelAssembler estadoModelAssembler;
    private EstadoDisassembler estadoDisassembler ;

    @GetMapping
    public CollectionModel<EstadoModel> all () {
        return estadoModelAssembler.toCollection(estadoRepository.findAll());
    }

    @GetMapping("/{id}")
    public EstadoModel getById (@PathVariable Long id) {
        return estadoModelAssembler.toModel(estadoService.findById(id));
    }

    @PostMapping
    public EstadoModel add (@RequestBody @Valid EstadoDTO estadoDTO) {
        Estado estado = estadoDisassembler.estadoDTOToEstado(estadoDTO);
        return estadoModelAssembler.toModel(estadoService.save(estado));
    }

    @PutMapping("/{id}")
    public  EstadoModel save (@PathVariable Long id , @RequestBody @Valid EstadoDTO estadoDTO) {
        Estado estadoAntigo = estadoService.findById(id);
        estadoDisassembler.updateEstadoFromDto(estadoDTO , estadoAntigo);
        Estado estadoSalvo = estadoService.save(id, estadoAntigo);
        return estadoModelAssembler
                .toModel(estadoSalvo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove (@PathVariable Long id) {
        estadoService.remove(id);
    }
}

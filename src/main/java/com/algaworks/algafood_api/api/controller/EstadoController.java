package com.algaworks.algafood_api.api.controller;


import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.EstadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/estados")
@ResponseBody
@AllArgsConstructor
public class EstadoController {

    EstadoRepository estadoRepository ;

    @GetMapping
    public List<Estado> listar () {
        return estadoRepository.all();
    }

}

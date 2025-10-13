package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood_api.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter , String timeOffSet);

}

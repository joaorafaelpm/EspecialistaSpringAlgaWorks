package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.filter.VendaDiariaFilter;

public interface VendaReportService {

    byte[] emitirVendasDiarias (VendaDiariaFilter vendaDiariaFilter , String timeOffSet);

}

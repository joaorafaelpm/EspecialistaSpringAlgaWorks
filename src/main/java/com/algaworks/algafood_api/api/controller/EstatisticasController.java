package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.AlgaLinks;
import com.algaworks.algafood_api.api.model.EstatisticasModel;
import com.algaworks.algafood_api.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood_api.domain.model.dto.VendaDiaria;
import com.algaworks.algafood_api.domain.service.VendaQueryService;
import com.algaworks.algafood_api.domain.service.VendaReportService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estatisticas")
@AllArgsConstructor
public class EstatisticasController {

    private AlgaLinks algaLinks;
    private VendaQueryService vendaQueryService;
    private VendaReportService vendaReportService;

    @GetMapping
    public EstatisticasModel exporLinks () {
        EstatisticasModel estatisticasModel = new EstatisticasModel();

        estatisticasModel.add(algaLinks.linkToEstatisticasVendasDiarias("vendas-diarias"));

        return estatisticasModel;
    }





    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendaDiaria> consultarVendasJson (VendaDiariaFilter filter ,
                          @RequestParam(required = false , defaultValue = "+00:00") String timeOffSet) {
        return vendaQueryService.consultarVendasDiarias(filter , timeOffSet);
    }
    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultarVendas (VendaDiariaFilter filter ,
                                           @RequestParam(required = false , defaultValue = "+00:00") String timeOffSet) {

        byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filter , timeOffSet);

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION , "attachment; filename=vendas-diarias.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPdf);
    }
}

package com.algaworks.algafood_api.infrastructure.service.report;

import com.algaworks.algafood_api.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood_api.domain.service.VendaQueryService;
import com.algaworks.algafood_api.domain.service.VendaReportService;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
public class VendaReportServiceImpl implements VendaReportService {

    @Autowired
    private VendaQueryService vendaQueryService;

    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter vendaDiariaFilter, String timeOffSet){
        try {
            var inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");

            var parametros = new HashMap<String , Object>();
            parametros.put("REPORT_LOCALE" , Locale.of("pt" , "BR"));

            var vendasDiarias = vendaQueryService.consultarVendasDiarias(vendaDiariaFilter , timeOffSet);
            var dataSource = new JRBeanCollectionDataSource(vendasDiarias);

            var jasperPrint = JasperFillManager.fillReport(inputStream , parametros , dataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportException("Não foi possivel emitir relatório de vendas diárias." , e);
        }
    }
}

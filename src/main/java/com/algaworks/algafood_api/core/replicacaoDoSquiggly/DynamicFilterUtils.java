package com.algaworks.algafood_api.core.replicacaoDoSquiggly;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.util.List;
import java.util.stream.Collectors;

public class DynamicFilterUtils {

    private DynamicFilterUtils() {}

    @SuppressWarnings("unchecked")
    public static MappingJacksonValue applyFilter(Object data, String campos) {
        MappingJacksonValue wrapper = new MappingJacksonValue(data);

        // ✅ Se não há campos, retorna sem filtrar (mantém o tipo original)
        if (StringUtils.isBlank(campos)) {
            return wrapper;
        }

        List<String> fields = FieldParser.parse(campos);

        Object filteredData;

        // ✅ Caso especial: Page
        if (data instanceof Page<?>) {
            Page<?> page = (Page<?>) data;

            List<?> filteredContent = page.getContent().stream()
                    .map(item -> ObjectFilterUtil.filter(item, fields))
                    .collect(Collectors.toList());

            // mantém o tipo PageImpl (para o Jackson não se perder)
            filteredData = new PageImpl<>(filteredContent, page.getPageable(), page.getTotalElements());
        }

        // ✅ Caso o retorno seja lista
        else if (data instanceof List<?>) {
            filteredData = ((List<?>) data).stream()
                    .map(item -> ObjectFilterUtil.filter(item, fields))
                    .collect(Collectors.toList());
        }

        // ✅ Caso único objeto (DTO, entidade etc.)
        else {
            filteredData = ObjectFilterUtil.filter(data, fields);
        }

        // Garante que o tipo original seja preservado
        wrapper.setValue(filteredData);
        return wrapper;
    }
}

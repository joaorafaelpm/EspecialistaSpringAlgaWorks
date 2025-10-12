package com.algaworks.algafood_api.core.replicacaoDoSquiggly;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.util.List;

public class DynamicFilterUtils {

    private DynamicFilterUtils() {}

    public static MappingJacksonValue applyFilter(Object data, String campos) {
        MappingJacksonValue wrapper = new MappingJacksonValue(data);

        if (StringUtils.isNotBlank(campos)) {
            List<String> fields = FieldParser.parse(campos);
            Object filteredData = ObjectFilterUtil.filter(data, fields);
            wrapper.setValue(filteredData);
        }

        return wrapper;
    }
}

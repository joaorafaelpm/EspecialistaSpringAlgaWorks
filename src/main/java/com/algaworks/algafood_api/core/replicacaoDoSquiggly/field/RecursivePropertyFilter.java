package com.algaworks.algafood_api.core.replicacaoDoSquiggly.field;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class RecursivePropertyFilter implements PropertyFilter {

    private final Map<String, Set<String>> allowedFieldsTree;

    public RecursivePropertyFilter(Set<String> allowedFields) {
        allowedFieldsTree = new HashMap<>();
        for (String path : allowedFields) {
            String[] parts = path.split("\\.", 2);
            String key = parts[0];
            String rest = parts.length > 1 ? parts[1] : null;

            allowedFieldsTree.computeIfAbsent(key, k -> new java.util.HashSet<>());
            if (rest != null) {
                allowedFieldsTree.get(key).add(rest);
            }
        }
    }

    @Override
    public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {
        String name = writer.getName();
        if (!allowedFieldsTree.containsKey(name)) return; // ignora campo não permitido

        // Se tem subcampos, filtragem recursiva
        Set<String> nested = allowedFieldsTree.get(name);
        if (nested != null && !nested.isEmpty()) {
            Object value = null;
            try {
                Method getter = pojo.getClass().getMethod("get" + capitalize(name));
                value = getter.invoke(pojo);
            } catch (NoSuchMethodException ignored) { }

            if (value != null) {
                // Aplica o filtro recursivo na propriedade aninhada
                jgen.writeFieldName(name);
                com.fasterxml.jackson.databind.ObjectMapper mapper = (com.fasterxml.jackson.databind.ObjectMapper) jgen.getCodec();
                com.fasterxml.jackson.databind.ser.FilterProvider originalFilters = mapper.getSerializationConfig().getFilterProvider();
                com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider filters = new com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider();
                filters.addFilter(name, new RecursivePropertyFilter(nested));
                mapper.writer(filters).writeValue(jgen, value);
                return;
            }
        }

        // Caso simples, serializa normalmente
        writer.serializeAsField(pojo, jgen, provider);
    }

    @Override
    public void serializeAsElement(Object elementValue, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {
        writer.serializeAsElement(elementValue, jgen, provider);
    }

    @Override
    public void depositSchemaProperty(PropertyWriter writer, com.fasterxml.jackson.databind.node.ObjectNode propertiesNode, SerializerProvider provider) { }

    @Override
    public void depositSchemaProperty(PropertyWriter writer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor objectVisitor, SerializerProvider provider) { }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}

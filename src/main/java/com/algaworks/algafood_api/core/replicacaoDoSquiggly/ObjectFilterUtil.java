package com.algaworks.algafood_api.core.replicacaoDoSquiggly;

import com.algaworks.algafood_api.domain.exception.NegocioException;

import java.lang.reflect.Method;
import java.util.*;

public class ObjectFilterUtil {

    public static Object filter(Object data, List<String> allowedFields) {
        if (data == null) return null;

        if (data instanceof Collection<?>) {
            List<Object> list = new ArrayList<>();
            for (Object item : (Collection<?>) data) {
                list.add(filterObject(item, allowedFields));
            }
            return list;
        } else {
            return filterObject(data, allowedFields);
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    private static String decapitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }

    private static Map<String, Object> filterObject(Object obj, List<String> allowedFields) {
        Map<String, Object> result = new HashMap<>();
        if (obj == null) return result;

        Map<String, List<String>> includeTree = new HashMap<>();
        Set<String> excludeFields = new HashSet<>();
        boolean isExclusionOnly = false;

        // Preprocessa allowedFields
        for (String path : allowedFields) {
            if (path.startsWith("-")) {
                isExclusionOnly = true;
                String clean = path.substring(1);
                String topField = clean.split("\\.")[0];
                excludeFields.add(topField);
            } else {
                String[] parts = path.split("\\.", 2);
                String key = parts[0];
                String rest = parts.length > 1 ? parts[1] : null;
                includeTree.computeIfAbsent(key, k -> new ArrayList<>());
                if (rest != null) includeTree.get(key).add(rest);
            }
        }

        // Se for exclusão, pegar todos os campos do objeto menos os excluídos
        if (isExclusionOnly) {
            for (Method method : obj.getClass().getMethods()) {
                if (!method.getName().startsWith("get") || method.getParameterCount() > 0 || method.getName().equals("getClass")) continue;

                String fieldName = decapitalize(method.getName().substring(3));
                if (excludeFields.contains(fieldName)) continue;
                try {
                    Object value = method.invoke(obj);
                    result.put(fieldName, value);
                } catch (Exception e) {
                    throw new RuntimeException("Erro ao acessar campo " + fieldName, e);
                }
            }
        }


        // Processa inclusões específicas e recursão
        for (Map.Entry<String, List<String>> entry : includeTree.entrySet()) {
            String fieldName = entry.getKey();
            List<String> subFields = entry.getValue();
            try {
                Method getter = obj.getClass().getMethod("get" + capitalize(fieldName));
                Object value = getter.invoke(obj);
                if (value == null) continue;

                if (!subFields.isEmpty()) {
                    result.put(fieldName, filter(value, subFields));
                } else {
                    result.put(fieldName, value);
                }
            } catch (NoSuchMethodException e) {
                throw new NegocioException("Campo não encontrado: " + fieldName);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao acessar campo " + fieldName, e);
            }
        }

        return result;
    }
}

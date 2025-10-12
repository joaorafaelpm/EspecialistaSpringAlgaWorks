package com.algaworks.algafood_api.core.replicacaoDoSquiggly;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

@ControllerAdvice
public class DynamicFieldResponseAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private HttpServletRequest request;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // intercepta todas as respostas (poderia filtrar por pacote, se quiser)
        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            org.springframework.http.server.ServerHttpRequest serverHttpRequest,
            org.springframework.http.server.ServerHttpResponse serverHttpResponse) {

        if (body == null) {
            return null;
        }

        String campos = request.getParameter("campos");
        return DynamicFilterUtils.applyFilter(body, campos);
    }
}


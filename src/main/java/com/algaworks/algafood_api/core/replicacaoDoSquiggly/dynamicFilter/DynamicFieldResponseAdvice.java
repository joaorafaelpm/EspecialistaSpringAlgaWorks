package com.algaworks.algafood_api.core.replicacaoDoSquiggly.dynamicFilter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class DynamicFieldResponseAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private HttpServletRequest request;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // Aqui não limitamos ainda, apenas deixamos o beforeBodyWrite decidir com base no Content-Type
        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest serverHttpRequest,
            ServerHttpResponse serverHttpResponse) {

        if (body == null) {
            return null;
        }

        // 🔒 Garante que o filtro só seja aplicado a respostas JSON
        if (!isJsonMediaType(selectedContentType)) {
            return body;
        }

        // Aplica o filtro de campos (replicação do Squiggly)
        String campos = request.getParameter("campos");
        return DynamicFilterUtils.applyFilter(body, campos);
    }

    /**
     * Verifica se o Content-Type é JSON (application/json ou variações).
     */
    private boolean isJsonMediaType(MediaType mediaType) {
        return mediaType != null &&
                (MediaType.APPLICATION_JSON.includes(mediaType) ||
                        MediaType.APPLICATION_JSON_UTF8.includes(mediaType) ||
                        (mediaType.getSubtype() != null && mediaType.getSubtype().contains("json")));
    }
}

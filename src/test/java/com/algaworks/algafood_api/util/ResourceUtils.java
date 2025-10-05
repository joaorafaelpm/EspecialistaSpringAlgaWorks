package com.algaworks.algafood_api.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.util.StreamUtils;

public class ResourceUtils {

//    Função para consumir um arquivo json
    public static String getContentFromResource(String resourceName) {
        try {
//            Pegando as informações como texto e passando para o padrão UTF-8
            InputStream stream = ResourceUtils.class.getResourceAsStream(resourceName);
            return StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
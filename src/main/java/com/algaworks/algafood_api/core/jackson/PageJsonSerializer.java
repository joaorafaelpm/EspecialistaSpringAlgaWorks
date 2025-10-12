package com.algaworks.algafood_api.core.jackson;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>> {

    @Override
    public void serialize(Page<?> page, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//        Inicia o objeto ({)
        jsonGenerator.writeStartObject();

//        Como conteúdo do objeto eu passo "content" com o valor da lista dentro do page
        jsonGenerator.writeObjectField("content" , page.getContent());
        jsonGenerator.writeNumberField("size" , page.getSize());
        jsonGenerator.writeNumberField("totalElements" , page.getTotalElements());
        jsonGenerator.writeNumberField("totalPage" , page.getTotalPages());
        jsonGenerator.writeNumberField("currentPage" , page.getNumber());


//        Inicia o fim do objeto (})
        jsonGenerator.writeEndObject();

    }
}

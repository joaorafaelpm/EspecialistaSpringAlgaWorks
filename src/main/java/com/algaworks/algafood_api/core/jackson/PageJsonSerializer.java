package com.algaworks.algafood_api.core.jackson;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>> {

//    A partir de agora (agora que adaptamos o padrão de HATEOAS nos endpoints) não precisamos mais disso, por que o próprio PagedRepresentation do HATEOAS aplica esse padrão. Eu só não tiro isso agora por que a gente ainda usa em alguns pontos do projeto e quebraria muita coisa por enquanto, porém eu não vou manter no projeto!

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

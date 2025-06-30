package com.algaworks.algafood_api.api.model;

import com.algaworks.algafood_api.domain.model.Cozinha;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.annotation.Nonnull;
import lombok.Data;

import java.util.List;

@JacksonXmlRootElement(localName = "cozinhas")
@Data
public class CozinhasXMLWrapper {

    @JsonProperty("cozinha")
    @JacksonXmlElementWrapper(useWrapping = false)
//    isso impede que duplique o empacotamento a mais que é feito de forma automática, assim ao invéz do caminho ser cozinhas[{cozinhas{cozinhas}}] fica cozinhas[{cozinha}]
    @Nonnull
    List<Cozinha> cozinhas;

}

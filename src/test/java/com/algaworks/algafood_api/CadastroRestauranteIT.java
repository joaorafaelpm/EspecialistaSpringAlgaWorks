package com.algaworks.algafood_api;

import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import com.algaworks.algafood_api.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static com.algaworks.algafood_api.util.ResourceUtils.getContentFromResource;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
class CadastroRestauranteIT {

    private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Houve uma violação da regra de negócio.";
    private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Algum dado foi inserido de forma incorreta.";
    private static final String ERRO_INESPERADO_PROBLEM_TITLE = "Menssagem Incompreenssível.";

    private static final Long RESTAURANTE_ID_INEXISTENTE = 10000L ;
    private int numeroRestaurantes ;

    private Restaurante restaurante1 = new Restaurante();
    private Restaurante restaurante2 = new Restaurante();
    private Cozinha cozinha1 = new Cozinha();
    private Cozinha cozinha2 = new Cozinha();

    private String testeRestauranteCorreto;
    private String testeRestauranteCozinhaVazia;
    private String testeRestauranteCozinhaFaltando;
    private String testeRestauranteCozinhaIdInexistente;
    private String testeRestauranteFreteNegativo;

    @Autowired
    private DatabaseCleaner databaseCleaner ;
    @Autowired
    private RestauranteRepository restauranteRepository ;
    @Autowired
    private CozinhaRepository cozinhaRepository ;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp () {
        enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurantes" ;

        testeRestauranteCorreto = getContentFromResource("/json/correto/test-restaurante.json");
        testeRestauranteCozinhaIdInexistente = getContentFromResource("/json/incorreto/test-restaurante-cozinha-inexistente.json");
        testeRestauranteCozinhaFaltando = getContentFromResource("/json/incorreto/test-restaurante-cozinha-faltando.json");
        testeRestauranteCozinhaVazia = getContentFromResource("/json/incorreto/test-restaurante-cozinha-vazia.json");
        testeRestauranteFreteNegativo = getContentFromResource("/json/incorreto/test-restaurante-frete-negativo.json");
        databaseCleaner.clearTables();
        prepararDados();
    }

    @Test
    public void deveConter2Restaurantes_QuandoConsultarRestaurante () {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .body("" , hasSize(numeroRestaurantes));
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarRestaurante () {
        given()
            .body(testeRestauranteCorreto)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaVazia () {
        given()
            .body(testeRestauranteCozinhaVazia)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title" , equalTo(ERRO_INESPERADO_PROBLEM_TITLE));
    }
    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaFaltando () {
        given()
            .body(testeRestauranteCozinhaVazia)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title" , equalTo(ERRO_INESPERADO_PROBLEM_TITLE));
    }
    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaInexistente () {
        given()
            .body(testeRestauranteCozinhaIdInexistente)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title" , equalTo(VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE));
    }
    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteComFreteNegativo () {
        given()
            .body(testeRestauranteFreteNegativo)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title" , equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

    @Test
    public void deveRetornarRespostaEStatusCorreto_QuandoConsultarRestauranteExistente () {
        given()
                .pathParam("restauranteId" , restaurante1.getId())
                .accept(ContentType.JSON)
                .when()
                .get("/{restauranteId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome" , equalTo(restaurante1.getNome()));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente () {
        given()
                .pathParam("restauranteId" , RESTAURANTE_ID_INEXISTENTE)
                .accept(ContentType.JSON)
        .when()
            .get("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados() {
        cozinha1.setNome("Tailandesa");
        cozinhaRepository.save(cozinha1);

        cozinha2.setNome("Indiana");
        cozinhaRepository.save(cozinha2);

        restaurante1.setNome("Restaurante 1");
        restaurante1.setTaxaFrete(new BigDecimal(12));
        restaurante1.setCozinha(cozinha1);
        restauranteRepository.save(restaurante1);

        restaurante2.setNome("Restaurante 2");
        restaurante2.setTaxaFrete(new BigDecimal(14));
        restaurante2.setCozinha(cozinha1);
        restauranteRepository.save(restaurante2);

        numeroRestaurantes = restauranteRepository.findAll().size();
    }

}


package com.algaworks.algafood_api;

import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
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

import static com.algaworks.algafood_api.util.ResourceUtils.getContentFromResource;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
class CadastroCozinhaIT {

	private static final Long COZINHA_ID_INEXISTENTE = 10000L ;
	private static final String PATH_PADRAO_REQUISICAO ="/cozinhas";
	private int numeroCozinhas ;
//	@Autowired
//	private Flyway flyway ;

	@Autowired
	private DatabaseCleaner databaseCleaner ;
	@Autowired
	private CozinhaRepository cozinhaRepository ;

	@LocalServerPort
	private int port;

	private Cozinha cozinha1 = new Cozinha();
	private Cozinha cozinha2 = new Cozinha();

//	Definindo comandos padrões antes de cada teste
	@BeforeEach
	public void setUp () {
//		Log nos erros para debug
		enableLoggingOfRequestAndResponseIfValidationFails();
//		Definindo a porta e o caminho padrão dos testes
		RestAssured.port = port;
		RestAssured.basePath = PATH_PADRAO_REQUISICAO ;

//		Isso reseta o banco para um estado conhecido por nós (afterMigrate). Pense comigo, os testes integrados são executados fora de sequência, então se eu primeiro gerar uma nova cozinha e depois procurar somente 4 cozinhas vai gerar uma incongruência e dar erro no teste de 4 cozinhas. Por isso damos um callback no flyway antes de cada etapa
//		flyway.migrate() ;

//		Limpando o banco e adicionando alguns dados de teste
		databaseCleaner.clearTables();
		prepararDados();
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinha () {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveConter4Cozinhas_QuandoConsultarCozinha () {
		given()
				.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("" , hasSize(numeroCozinhas));
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha () {
		given()
				.body(getContentFromResource("/json/correto/testdata.json"))
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarRespostaEStatusCorreto_QuandoConsultarCozinhaExistente () {
		given()
			.pathParam("cozinhaId" , cozinha1.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome" , equalTo(cozinha1.getNome()));
	}

	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente () {
		given()
			.pathParam("cozinhaId" , COZINHA_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

	private void prepararDados() {
		cozinha1.setNome("Tailandesa");
		cozinhaRepository.save(cozinha1);

		cozinha2.setNome("Indiana");
		cozinhaRepository.save(cozinha2);

		numeroCozinhas = cozinhaRepository.findAll().size();
	}

}

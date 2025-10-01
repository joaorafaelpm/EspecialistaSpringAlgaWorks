package com.algaworks.algafood_api.api.exceptionhandler;

import com.algaworks.algafood_api.api.model.enuns.ProblemType;
import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * Classe responsável por capturar e tratar exceções de forma centralizada.
 *
 * A anotação @RestControllerAdvice indica ao Spring que esta classe será
 * aplicada globalmente a todos os controladores REST da aplicação.
 *
 * Isso garante respostas consistentes para erros e evita duplicação
 * de código de tratamento em cada controlador.
 *
 * O uso do ProblemDetail segue a especificação RFC 7807,
 * que define um formato padronizado para representar erros HTTP.
 */
@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

//    Definindo um padrão de respostas para o tratamento de erro, seguindo o padrão do ResponseEntityExceptionHandler
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
//        Se não tiver nenhum corpo de resposta disponível, a gente padroniza um.
        if (body == null) {
            body = APIError.builder()
                    .tittle(ex.getLocalizedMessage())
                    .status(statusCode.value())
                    .build();
        }
//        Se existir um corpo e for um texto vindo diretamente da exceção, a gente passa ele como corpo.
        else if (body instanceof String) {
            body = APIError.builder()
                    .tittle((String) body)
                    .status(statusCode.value())
                    .build();
        }
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handlEntidadeNaoEncontrado(
            EntidadeNaoEncontradaException ex , WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND ;
        APIError apiError = createAPIErrorBuilder(
                status ,ProblemType.ENTIDADE_NAO_ENCONTRADA , ex.getLocalizedMessage()
        ).build();
        return handleExceptionInternal(ex , apiError , new HttpHeaders(), status , request );
    }
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(
            NegocioException ex , WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST ;

        APIError apiError = createAPIErrorBuilder(
                status ,ProblemType.NEGOCIO_EXCEPTION , ex.getLocalizedMessage()
        ).build();

        return handleExceptionInternal(ex , apiError , new HttpHeaders(), status , request );
    }
    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUso(
            EntidadeEmUsoException ex , WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT ;

        APIError apiError = createAPIErrorBuilder(
                status ,ProblemType.ENTIDADE_EM_USO , ex.getLocalizedMessage()
        ).build();
        return handleExceptionInternal(ex , apiError , new HttpHeaders(), status , request );
    }

//    Criando um builder padrão para a classe de Erro para sofistificar o código
    private APIError.APIErrorBuilder createAPIErrorBuilder (
            HttpStatus status , ProblemType problemType , String detail) {
        return APIError.builder()
                .status(status.value())
                .type(problemType.getPath())
                .tittle(problemType.getTittle())
                .detail(detail);
    }
//    Fazendo uma reescrita de função por que as classes de HttpStatus são diferentes (a do ResponseEntityExceptionHandler -> usa HttpStatusCode enquanto o padrão da aplicação é HttpStatus)
    private APIError.APIErrorBuilder createAPIErrorBuilder (
            HttpStatusCode status , ProblemType problemType , String detail) {
        return APIError.builder()
                .status(status.value())
                .type(problemType.getPath())
                .tittle(problemType.getTittle())
                .detail(detail);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String detail = "O corpo da requisição é inválido. Tente verificar a sintaxe do texto digitado.";

        APIError apiError = createAPIErrorBuilder(
                status ,ProblemType.MENSSAGEM_INCOMPREESSIVEL , detail
        ).build();

        return handleExceptionInternal(ex, apiError ,headers, status, request);
    }
}

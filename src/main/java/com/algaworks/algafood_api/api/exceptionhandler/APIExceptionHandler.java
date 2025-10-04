package com.algaworks.algafood_api.api.exceptionhandler;

import com.algaworks.algafood_api.api.model.enuns.ProblemType;
import com.algaworks.algafood_api.core.validation.ValidacaoException;
import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe responsável por capturar e tratar exceções de forma centralizada.
 * <p>
 * A anotação @RestControllerAdvice indica ao Spring que esta classe será
 * aplicada globalmente a todos os controladores REST da aplicação.
 * <p>
 * Isso garante respostas consistentes para erros e evita duplicação
 * de código de tratamento em cada controlador.
 * <p>
 * O uso do ProblemDetail segue a especificação RFC 7807,
 * que define um formato padronizado para representar erros HTTP.
 */
@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String SYSTEM_ERROR_MESSAGE = String.format("Ocorreu um erro interno inesperado no sistema. Tente novamente mais tarde ou contate o administrador do sistema.");

    @Autowired
    private MessageSource messageSource ;

    private ResponseEntity<Object> handleMultipleErrorsValidation(Exception ex ,BindingResult bindingResult , HttpHeaders headers,HttpStatus status , WebRequest request) {
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
//        A gente faz um mapeamento simples para pegar cada campo e passar para a nossa classe
        List<APIError.Object> problemObjects = bindingResult.getAllErrors()
                .stream().map(objectError -> {
                    String message = messageSource.getMessage(objectError , LocaleContextHolder.getLocale());
                    String name = objectError.getObjectName() ;
                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField() ;
                    }
                    return APIError.Object.builder()
                            .name(name)
                            .userMessage(message)
                            .build() ;
                }).collect(Collectors.toList());
        APIError apiError = createAPIErrorBuilder(status, ProblemType.DADOS_INVALIDOS, detail, detail)
                .objects(problemObjects)
                .build();
        return handleExceptionInternal(ex , apiError , headers ,status , request);
    }
//    Vamos capturar e mapear todas as violações das especificações do BeanValidation e mostrar ao usuário
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleMultipleErrorsValidation(ex, ex.getBindingResult() , headers ,HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<?> handlValidacao(
            ValidacaoException ex , WebRequest request) {
        return handleMultipleErrorsValidation(ex , ex.getBindingResult() , new HttpHeaders(),HttpStatus.BAD_REQUEST , request );
    }

    //    Definindo um padrão de respostas para o tratamento de erro, seguindo o padrão do ResponseEntityExceptionHandler
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
//        Se não tiver nenhum corpo de resposta disponível, a gente padroniza um.
        if (body == null) {
            body = APIError.builder()
                    .timestamp(LocalDateTime.now())
                    .title(ex.getLocalizedMessage())
                    .status(statusCode.value())
                    .build();
        }
//        Se existir um corpo e for um texto vindo diretamente da exceção, a gente passa ele como corpo.
        else if (body instanceof String) {
            body = APIError.builder()
                    .timestamp(LocalDateTime.now())
                    .userMessage(SYSTEM_ERROR_MESSAGE)
                    .title((String) body)
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
                status ,ProblemType.RECURSO_NAO_ENCONTRADO , ex.getMessage() , ex.getMessage()
        ).build();
        return handleExceptionInternal(ex , apiError , new HttpHeaders(), status , request );
    }
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(
            NegocioException ex , WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST ;

        APIError apiError = createAPIErrorBuilder(
                status ,ProblemType.NEGOCIO_EXCEPTION , ex.getMessage() , SYSTEM_ERROR_MESSAGE
        ).build();

        return handleExceptionInternal(ex , apiError , new HttpHeaders(), status , request );
    }
    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUso(
            EntidadeEmUsoException ex , WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT ;
        String detail = ex.getMessage() ;
        APIError apiError = createAPIErrorBuilder(
                status ,ProblemType.ENTIDADE_EM_USO , detail , detail
        )
                .build();
        return handleExceptionInternal(ex , apiError , new HttpHeaders(), status , request );
    }

//    Criando um builder padrão para a classe de Erro para sofistificar o código
    private APIError.APIErrorBuilder createAPIErrorBuilder (
            HttpStatus status , ProblemType problemType , String detail , String userMessage) {
        return APIError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .type(problemType.getPath())
                .title(problemType.getTittle())
                .detail(detail)
                .userMessage(userMessage);
    }
//    Fazendo uma reescrita de função por que as classes de HttpStatus são diferentes (a do ResponseEntityExceptionHandler -> usa HttpStatusCode enquanto o padrão da aplicação é HttpStatus)
    private APIError.APIErrorBuilder createAPIErrorBuilder (
            HttpStatusCode status , ProblemType problemType , String detail) {
        return APIError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .type(problemType.getPath())
                .title(problemType.getTittle())
                .detail(detail)
                .userMessage(APIExceptionHandler.SYSTEM_ERROR_MESSAGE);
    }

//    Verificando se o consumidor digitou o parâmetro da URL corretamente
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String detail = String.format("O parâmetro da URL '%s' recebeu o valor de '%s', que é um tipo inválido. Corrija e informe um valor compatível ao tipo '%s'." , ex.getPropertyName() , ex.getValue() , ex.getRequiredType().getSimpleName());


        APIError apiError = createAPIErrorBuilder(status, ProblemType.PARAMETRO_INVALIDO , detail).build();
        return handleExceptionInternal(ex,apiError , headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String detail = String.format("O recurso '%s' que você tentou acessar, é inexistente." , ex.getResourcePath());

        APIError apiError = createAPIErrorBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADO , detail).build();
        return handleExceptionInternal(ex, apiError , headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
//        Verificando se o tipo da exceção é InvalidFormatException que é um tipo pai do HttpMessageNotReadable e nós temos que tratar de um jeito diferente,
        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause , headers, status , request);
        }
        else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingExceptions((PropertyBindingException) rootCause , headers , status , request) ;
        }

        String detail = "O corpo da requisição é inválido. Tente verificar a sintaxe do texto digitado.";


        APIError apiError = createAPIErrorBuilder(
                status ,ProblemType.MENSSAGEM_INCOMPREESSIVEL , detail
        )
        .build();

        return handleExceptionInternal(ex, apiError ,headers, status, request);
    }

//    Quando alguém que está consumindo a API tenta adicionar uma propriedade que não faz parte da classe, nós podemos passar essa exceção, e também podemos diferenciar as exceções para as duas seguintes situações
//    Caso o consumidor adicione uma propriedade que não existe nós podemos informar a partir da exception UnrecognizedPropertieException, porém se o usuário adicioar uma propriedade que está sendo ignorada pelo JsonIgnore, nós podemos específicar pela exception IgnoredPropertieException
//    Neste caso eu estou simplificando a minha própria vida usando o PropertyBindingException, que é a classe que as duas outras herdam, porém para respostas mais claras é interessante ter isso em mente. Vale ressaltar que é perigoso informar o que está sendo usado ou não na classe, e desta forma ele não consegue distinguir entre o que está sendo usado e ignorado ou simplesmente não existe
    private ResponseEntity<Object> handlePropertyBindingExceptions(PropertyBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String path = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.MENSSAGEM_INCOMPREESSIVEL;
        String detail = String.format("A propriedade '%s' não consta na entidade original do tipo '%s' . Corrija ou remova e informe um valor compatível com " +
                "a entidade original." , path , ex.getReferringClass().getSimpleName()) ;

        APIError apiError = createAPIErrorBuilder(status, problemType, detail)
                .build();

        return handleExceptionInternal(ex ,apiError , headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        Pegamos os elementos que causaram o problema e caso seja um elemento pai, uso o "." para separar
        String path = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.MENSSAGEM_INCOMPREESSIVEL;
        String detail = String.format("A propriedade '%s' recebeu o valor " +
                "'%s' que é um tipo inválido. Corrija e informe um valor compatível com " +
                "o tipo '%s'" , path , ex.getValue() , ex.getTargetType().getSimpleName()) ;
        APIError apiError = createAPIErrorBuilder(status, problemType, detail)
                .build();

        return handleExceptionInternal(ex ,apiError , headers, status, request);
    }

//    Todas as exceções do MUNDO são uma Exception.class, então esse é um handler que recebe tudo que não foi capturado até agora
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleDefaultException(
            Exception ex , WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR ;

        // Usando a StackTrace visivel durante o tempo de desenvolvimento para verificarmos as exceções e podemos trata-las mais facilmente
        ex.printStackTrace();

        APIError apiError = createAPIErrorBuilder(
                status ,ProblemType.ERRO_DE_SISTEMA , SYSTEM_ERROR_MESSAGE , SYSTEM_ERROR_MESSAGE
        ).build();
        return handleExceptionInternal(ex , apiError , new HttpHeaders(), status , request );
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
    }




}

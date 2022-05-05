package br.com.magiccards.api.player;

import br.com.magiccards.shared.dto.ErroDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class PlayerExceptionHandler {

    private final MessageSource messageSource;

    @Autowired
    public PlayerExceptionHandler(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PlayerAlreadyExistException.class)
    public ErroDto playerAlreadyExist(PlayerAlreadyExistException ex){
        return ErroDto.builder().erro(ex.getMessage()).campo("username").build();
    }


    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroDto> handler(MethodArgumentNotValidException exception){
        List<ErroDto> erros = new ArrayList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(erro -> {
            String mensagem = messageSource.getMessage(erro, LocaleContextHolder.getLocale());
            ErroDto err = new ErroDto(erro.getField(),mensagem);
            erros.add(err);
        });
        return erros;
    }
}

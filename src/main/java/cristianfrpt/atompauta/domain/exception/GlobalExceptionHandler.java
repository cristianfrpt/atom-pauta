package cristianfrpt.atompauta.domain.exception;

import cristianfrpt.atompauta.domain.response.ErroResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.Date;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErroResponse handleConstraintViolationException(HttpServletRequest request, Exception ex) {
        ErroResponse erro = new ErroResponse();

        erro.setTimestamp(new Date());
        erro.setStatus(HttpStatus.BAD_REQUEST.value());
        erro.addError(ex.getMessage());
        erro.setPath(request.getServletPath());

        log.error(ex.getMessage(), ex);

        return erro;
    }
}

package sk.ukf.restapi.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.ui.Model;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Objekt nenájdený (404)
    @ExceptionHandler(ObjectNotFoundException.class)
    public String handleObjectNotFound(ObjectNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "not-found";
    }

    // Email už existuje (409)
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public String handleEmailExists(EmailAlreadyExistsException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "email-exists";
    }

    // Validačné chyby (400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidation(MethodArgumentNotValidException ex, Model model) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getDefaultMessage())
                .toList();

        model.addAttribute("errors", errors);

        return "validation";
    }

    // Ostatné chyby (500)
    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "server-error";
    }
}
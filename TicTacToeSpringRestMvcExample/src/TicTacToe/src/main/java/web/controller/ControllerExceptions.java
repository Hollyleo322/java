package web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import web.exception.*;

@RestControllerAdvice
public class ControllerExceptions {
    @ExceptionHandler(UiidNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String uiidNotFound(UiidNotFoundException e)
    {
        return e.getMessage();
    }
    @ExceptionHandler(ChangedPreviousMovesException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    String changedPreviousMoves(ChangedPreviousMovesException e)
    {
        return e.getMessage();
    }

    @ExceptionHandler(UiidExistsException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    String uiidIsExists(UiidExistsException e)
    {
        return e.getMessage();
    }

    @ExceptionHandler(UiidDoesNotMatchException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    String uuiddDoesNotMatch(UiidDoesNotMatchException e)
    {
        return e.getMessage();
    }
    @ExceptionHandler(EndOfGame.class)
    @ResponseStatus(HttpStatus.OK)
    String EndOfGame(EndOfGame e)
    {
        return e.getMessage();
    }
    @ExceptionHandler(IncorrectDataInBodyException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    String incorrectDataInBody(IncorrectDataInBodyException e)
    {
        return e.getMessage();
    }

    @ExceptionHandler(NotAvailaibleGame.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    String NotAvailableGame(NotAvailaibleGame e) { return  e.getMessage(); }
}

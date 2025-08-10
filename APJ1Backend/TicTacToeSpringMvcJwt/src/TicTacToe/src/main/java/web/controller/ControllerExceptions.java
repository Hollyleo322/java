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

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    String UserNotFound(UserNotFoundException e) {return e.getMessage();}

    @ExceptionHandler(TokenInvalidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String TokenInvalid(TokenInvalidException e) {return e.getMessage();}

    @ExceptionHandler(LoginIsUnavailable.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    String LoginIsUnvailable (LoginIsUnavailable e) {return e.getMessage();}

    @ExceptionHandler(MaxUsersConnectedException.class)
    @ResponseStatus(HttpStatus.OK)
    String MaxUsersConnected(MaxUsersConnectedException e) { return e.getMessage();}

    @ExceptionHandler(NotYourTurn.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    String NotYourTurn(NotYourTurn e) { return e.getMessage();}

    @ExceptionHandler(WaitingForSecondPlayerException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    String WaitingForPlayer(WaitingForSecondPlayerException e) {return e.getMessage();}
}

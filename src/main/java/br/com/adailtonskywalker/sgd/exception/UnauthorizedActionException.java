package br.com.adailtonskywalker.sgd.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnauthorizedActionException extends ControlledException {
    public Short status = (short) HttpStatus.UNAUTHORIZED.value();

    public UnauthorizedActionException() {
        super("Unauthorized Action");
    }
}

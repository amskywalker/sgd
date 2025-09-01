package br.com.adailtonskywalker.sgd.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityExistsException extends ControlledException {
    public Short status = (short) HttpStatus.BAD_REQUEST.value();

    public EntityExistsException(String entityName) {
        super(entityName + " is Registered");
    }
}

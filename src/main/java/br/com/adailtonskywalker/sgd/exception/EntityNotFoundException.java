package br.com.adailtonskywalker.sgd.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityNotFoundException extends ControlledException {
    public Short status = (short) HttpStatus.NOT_FOUND.value();

    public EntityNotFoundException(String entityName) {
        super(entityName + " Not found");
    }
}

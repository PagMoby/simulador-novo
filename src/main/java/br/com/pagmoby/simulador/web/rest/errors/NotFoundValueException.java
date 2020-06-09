package br.com.pagmoby.simulador.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class NotFoundValueException extends AbstractThrowableProblem {
    private static final long serialVersionUID = 1L;

    public NotFoundValueException() {
        super(ErrorConstants.DEFAULT_TYPE, "Valor inválido", Status.NOT_FOUND);
    }
}

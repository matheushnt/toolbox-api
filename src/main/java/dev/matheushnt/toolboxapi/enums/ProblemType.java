package dev.matheushnt.toolboxapi.enums;

import lombok.Getter;

@Getter
public enum ProblemType {

    VALIDATION_ERROR("validation-error"),
    BAD_CREDENTIALS("bad-credentials"),
    UNAUTHORIZED("unauthorized"),
    CONFLICT("conflict"),
    NOT_FOUND("not-found"),
    INVALID_DATA("invalid-data"),
    INVALID_PARAMETER("invalid-parameter"),
    INTERNAL_ERROR("internal-error"),
    FORBIDDEN("forbidden");

    private final String value;

    ProblemType(String value) {
        this.value = value;
    }

}

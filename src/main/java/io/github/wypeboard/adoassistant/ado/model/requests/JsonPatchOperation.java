package io.github.wypeboard.adoassistant.ado.model.requests;

import com.fasterxml.jackson.annotation.JsonValue;

public enum JsonPatchOperation {
    ADD("add"),
    COPY("copy"),
    MOVE("move"),
    REMOVE("remove"),
    REPLACE("replace"),
    TEST("test"),
    ;
    private final String value;

    JsonPatchOperation(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}

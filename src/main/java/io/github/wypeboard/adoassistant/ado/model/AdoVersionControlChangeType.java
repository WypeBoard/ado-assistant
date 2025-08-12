package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AdoVersionControlChangeType {
    ADD("add"),
    ALL("all"),
    BRANCH("branch"),
    DELETE("delete"),
    EDIT("edit"),
    EDIT_RENAME("edit, rename"), // undocumented, combined state
    ENCODING("encoding"),
    LOCK("lock"),
    MERGE("merge"),
    NONE("none"),
    PROPERTY("property"),
    RENAME("rename"),
    ROLLBACK("rollback"),
    SOURCE_RENAME("sourceRename"),
    TARGET_RENAME("targetRename"),
    UNDELETE("undelete"),
    ;
    private final String value;

    AdoVersionControlChangeType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}

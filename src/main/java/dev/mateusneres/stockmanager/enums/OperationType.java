package dev.mateusneres.stockmanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OperationType {

    CREATE("Create"), UPDATE("Update");

    private final String name;

}

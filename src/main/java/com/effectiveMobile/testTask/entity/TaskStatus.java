package com.effectiveMobile.testTask.entity;

import com.effectiveMobile.testTask.Exception.EnumValuesNotFoundException;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@ToString
public enum TaskStatus {

    IN_ANTICIPATION("In Anticipation"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed");

    private final String statusName;

    public static TaskStatus findByValue(String value) {
        return Arrays.stream(values()).filter(status -> status.name().equalsIgnoreCase(value)).findFirst().orElseThrow(() -> new EnumValuesNotFoundException("Статуса задачи: " + value + " не существует. Попробуйте {IN_ANTICIPATION, IN_PROGRESS, COMPLETED}"));
    }

}

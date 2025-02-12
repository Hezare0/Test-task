package com.effectiveMobile.testTask.entity;

import com.effectiveMobile.testTask.Exception.EnumValuesNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@ToString
public enum TaskPriority {

    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low");

    private final String priorityName;

    public static TaskPriority findByValue(String value) {
        return Arrays.stream(values()).filter(priority -> priority.name().equalsIgnoreCase(value)).findFirst().orElseThrow(() -> new EnumValuesNotFoundException("Приоритета задачи: " + value + " не существует. Попробуйте {HIGH, MEDIUM, LOW}"));
    }
}

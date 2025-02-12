package com.effectiveMobile.testTask.DTO.taskDTO.crudAction;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на изменение задачи")
public class AdminUpdateTaskRequest {

    @Schema(description = "Заголовок задачи", example = "Task title")
    private String title;

    @Schema(description = "Описание задачи", example = "Task description")
    private String description;

    @Schema(description = "Комментарий к задаче", example = "Task comment")
    private String comment;

    @Schema(description = "Статус задачи", allowableValues  = {"IN_ANTICIPATION","IN_PROGRESS","COMPLETED"})
    private String taskStatus;

    @Schema(description = "Приоритет задачи", allowableValues  = {"HIGH","MEDIUM","LOW"})
    private String taskPriority;

    @Schema(description = "Адрес электронной почты автора задачи", example = "ivan@gmail.com")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String authorEmail;

    @Schema(description = "Адрес электронной почты исполнителя задачи", example = "ivan@gmail.com")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String performerEmail;

}

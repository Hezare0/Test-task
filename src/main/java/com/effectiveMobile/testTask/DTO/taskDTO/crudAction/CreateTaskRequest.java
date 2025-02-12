package com.effectiveMobile.testTask.DTO.taskDTO.crudAction;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Schema(description = "Запрос на создание задачи")
public class CreateTaskRequest {

    @Schema(description = "Заголовок задачи", example = "Task title")
    @NotBlank(message = "Заголовок не может быть пустыми")
    private String title;

    @Schema(description = "Описание задачи", example = "Task description")
    @NotBlank(message = "Описание не может быть пустыми")
    private String description;

    @Schema(description = "Комментарий к задаче", example = "Task comment")
    private String comment;

    @Schema(description = "Статус задачи", allowableValues  = {"IN_ANTICIPATION","IN_PROGRESS","COMPLETED"})
    @NotBlank(message = "Статус задачи не может быть пустыми")
    private String taskStatus;

    @Schema(description = "Приоритет задачи", allowableValues  = {"HIGH","MEDIUM","LOW"})
    @NotBlank(message = "Приоритет задачи не может быть пустыми")
    private String taskPriority;

    @Schema(description = "Адрес электронной почты автора задачи", example = "ivan@gmail.com")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты автора не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String authorEmail;

    @Schema(description = "Адрес электронной почты исполнителя задачи", example = "ivan@gmail.com")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты автора не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String performerEmail;
}

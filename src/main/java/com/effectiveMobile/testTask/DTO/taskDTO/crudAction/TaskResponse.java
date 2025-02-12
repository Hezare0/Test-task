package com.effectiveMobile.testTask.DTO.taskDTO.crudAction;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ о статусе выполнения запроса")
public class TaskResponse {

    @Schema(description = "Идентификатор задачи", example = "1")
    private Long taskId;

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
    private String authorEmail;

    @Schema(description = "Адрес электронной почты исполнителя задачи", example = "ivan@gmail.com")
    private String performerEmail;
}

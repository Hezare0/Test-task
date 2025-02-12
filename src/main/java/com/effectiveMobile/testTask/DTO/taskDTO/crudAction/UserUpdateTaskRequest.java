package com.effectiveMobile.testTask.DTO.taskDTO.crudAction;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на изменение задачи")
public class UserUpdateTaskRequest {

    @Schema(description = "Комментарий к задаче", example = "Task comment")
    private String comment;

    @Schema(description = "Статус задачи", allowableValues  = {"IN_ANTICIPATION","IN_PROGRESS","COMPLETED"})
    private String taskStatus;
}

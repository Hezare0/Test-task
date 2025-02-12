package com.effectiveMobile.testTask.DTO.taskDTO.crudAction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Запрос на удаление задачи")
public class DeleteTaskRequest {

    private Long id;


    /*@Schema(description = "Адрес электронной почты", example = "ivan@gmail.com")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    @Schema(description = "Пароль", example = "my_password")
    @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
    private String password;*/

}

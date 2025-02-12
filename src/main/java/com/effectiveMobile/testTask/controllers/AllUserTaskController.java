package com.effectiveMobile.testTask.controllers;

import com.effectiveMobile.testTask.DTO.taskDTO.crudAction.GetListOfTasksResponse;
import com.effectiveMobile.testTask.DTO.taskDTO.crudAction.TaskResponse;
import com.effectiveMobile.testTask.DTO.taskDTO.crudAction.AdminUpdateTaskRequest;
import com.effectiveMobile.testTask.DTO.taskDTO.crudAction.UserUpdateTaskRequest;
import com.effectiveMobile.testTask.services.servicesAssociatedTask.CrudTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Работа пользователя с задачами")
@SecurityRequirement(name = "JWT")
public class AllUserTaskController {

    private final CrudTaskService taskService;

    @Operation(summary = "Получение данных всех задач текущего пользователя")
    @GetMapping("/get-task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Авторизуйтесь под другим пользователем")
    })
    public GetListOfTasksResponse getAllTask(@RequestParam(value = "offset", defaultValue = "0") @Min(0) @Parameter(description = "Текущая страница") Integer offset,
                                             @RequestParam(value = "limit", defaultValue = "20") @Min(1) @Max(100) @Parameter(description = "Количество задач на странице") Integer limit) {
        return taskService.getAllTask(SecurityContextHolder.getContext().getAuthentication().getName(), offset, limit);
    }

    @Operation(summary = "Получение данных всех задач пользователя")
    @GetMapping("/get-task/{userEmail}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Авторизуйтесь под другим пользователем"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public GetListOfTasksResponse getAllTask(@PathVariable @Parameter(description = "Email пользователя", required = true) String userEmail,
                                             @RequestParam(value = "offset", defaultValue = "0") @Min(0) @Parameter(description = "Текущая страница") Integer offset,
                                             @RequestParam(value = "limit", defaultValue = "20") @Min(1) @Max(100) @Parameter(description = "Количество задач на странице") Integer limit) {
        return taskService.getAllTask(userEmail, offset, limit);
    }

    @Operation(summary = "Обновление данных задачи")
    @PutMapping("/update-task/{taskId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "401", description = "Авторизуйтесь под другим пользователем"),
            @ApiResponse(responseCode = "404", description = "Задача не существует")})
    public TaskResponse updateTask(@RequestBody @Valid UserUpdateTaskRequest request, @PathVariable @Parameter(description = "Идентификатор задачи", required = true) Long taskId) {
        return taskService.updateTask(request, taskId);
    }
}

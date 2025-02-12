package com.effectiveMobile.testTask.controllers;


import com.effectiveMobile.testTask.DTO.taskDTO.crudAction.CreateTaskRequest;
import com.effectiveMobile.testTask.DTO.taskDTO.crudAction.GetListOfTasksResponse;
import com.effectiveMobile.testTask.DTO.taskDTO.crudAction.TaskResponse;
import com.effectiveMobile.testTask.DTO.taskDTO.crudAction.AdminUpdateTaskRequest;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "Работа администратора с задачами")
@SecurityRequirement(name = "JWT")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminTaskController {

    private final CrudTaskService taskService;

    @Operation(summary = "Создание новой задачи")
    @PostMapping("/create-task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "401", description = "Авторизуйтесь под другим пользователем")
    })
    public TaskResponse createTask(@RequestBody @Valid CreateTaskRequest request) {
        return taskService.createTask(request);
    }

    @Operation(summary = "Обновление данных задачи")
    @PutMapping("/update-task/{taskId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "404", description = "Задача не существует"),
            @ApiResponse(responseCode = "401", description = "Авторизуйтесь под другим пользователем")})
    public TaskResponse updateTask(@RequestBody @Valid AdminUpdateTaskRequest request, @PathVariable @Parameter(description = "Идентификатор задачи", required = true) Long taskId) {
        return taskService.updateTask(request, taskId);
    }

    @Operation(summary = "Получение данных всех задач")
    @GetMapping("/get-task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Авторизуйтесь под другим пользователем")
    })
    public GetListOfTasksResponse getAllTask(@RequestParam(value = "offset", defaultValue = "0") @Min(0) @Parameter(description = "Текущая страница") Integer offset,
                                             @RequestParam(value = "limit", defaultValue = "20") @Min(1) @Max(100) @Parameter(description = "Количество задач на странице") Integer limit) {
        return taskService.getAllTask(offset,limit);
    }

    @DeleteMapping("/delete-task/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Задача не существует"),
            @ApiResponse(responseCode = "401", description = "Авторизуйтесь под другим пользователем")
    })
    public void deleteTask(@PathVariable @Parameter(description = "Идентификатор задачи", required = true) Long taskId) {
        taskService.deleteTask(taskId);
    }

}

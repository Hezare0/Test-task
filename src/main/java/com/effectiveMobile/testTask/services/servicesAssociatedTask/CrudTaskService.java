package com.effectiveMobile.testTask.services.servicesAssociatedTask;

import com.effectiveMobile.testTask.DTO.taskDTO.crudAction.*;
import com.effectiveMobile.testTask.entity.Task;
import com.effectiveMobile.testTask.entity.TaskPriority;
import com.effectiveMobile.testTask.entity.TaskStatus;
import com.effectiveMobile.testTask.services.servicesAssociatedUser.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CrudTaskService {
    private final TaskService taskService;
    private final UserService userService;

    /**
     * Создание задачи
     *
     * @param request данные задачи
     * @return данные созданой задачи
     */
    public TaskResponse createTask(CreateTaskRequest request) {
        var task = new Task(
                request.getTitle(),
                request.getDescription(),
                request.getComment(),
                TaskStatus.findByValue(request.getTaskStatus()),
                TaskPriority.findByValue(request.getTaskPriority()),
                userService.getByEmail(request.getAuthorEmail()),
                userService.getByEmail(request.getPerformerEmail())
        );
        return taskService.createTaskResponse(taskService.create(task));
    }

    /**
     * Обновление задачи администратором
     *
     * @param request данные задачи
     *        taskId  идентификатор задачи
     * @return данные обновленной задачи
     */
    public TaskResponse updateTask(AdminUpdateTaskRequest request, Long taskId) {
        var task = taskService.getTaskById(taskId);

        if (request.getTitle() != null && !request.getTitle().isEmpty()){
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null && !request.getDescription().isEmpty()){
            task.setDescription(request.getDescription());
        }
        if (request.getComment() != null && !request.getComment().isEmpty()){
            task.setComment(request.getComment());
        }
        if (request.getTaskStatus() != null && !request.getTaskStatus().isEmpty()){
            task.setTaskStatus(TaskStatus.findByValue(request.getTaskStatus()));
        }
        if (request.getTaskPriority() != null && !request.getTaskPriority().isEmpty()){
            task.setTaskPriority(TaskPriority.findByValue(request.getTaskPriority()));
        }
        if (request.getAuthorEmail() != null && !request.getAuthorEmail().isEmpty()){
            task.setAuthor(userService.getByEmail(request.getAuthorEmail()));
        }
        if (request.getPerformerEmail() != null && !request.getPerformerEmail().isEmpty()){
            task.setPerformer(userService.getByEmail(request.getPerformerEmail()));
        }
        taskService.save(task);
        return taskService.createTaskResponse(task);

    }

    /**
     * Обновление задачи пользователем
     *
     * @param request данные задачи
     *        taskId  идентификатор задачи
     * @return данные обновленной задачи
     */
    public TaskResponse updateTask(UserUpdateTaskRequest request, Long taskId) {
        var task = taskService.getTaskById(taskId);

        if (request.getComment() != null && !request.getComment().isEmpty()){
            task.setComment(request.getComment());
        }
        if (request.getTaskStatus() != null && !request.getTaskStatus().isEmpty()){
            task.setTaskStatus(TaskStatus.findByValue(request.getTaskStatus()));
        }

        taskService.save(task);
        return taskService.createTaskResponse(task);
    }

    /**
     * Удаление задачи по идентификатору
     *
     *@param taskId  идентификатор задачи
     *
     */
    public void deleteTask(Long taskId) {
        taskService.deleteTaskById(taskId);
    }

    /**
     * Получение всех задач
     *
     * @return Список задач
     */
    public GetListOfTasksResponse getAllTask(Integer offset,Integer limit) {
        var tasks = taskService.getAllTask(offset,limit);
        GetListOfTasksResponse response = new GetListOfTasksResponse(new ArrayList<TaskResponse>());
        for (Task task : tasks) {
            response.getTaskResponseList().add(taskService.createTaskResponse(task));
        }
        return response;
    }

    /**
     * Получение всех задач пользователя
     *
     * @param userEmail  идентификатор пользователя
     * @return Список задач
     */
    public GetListOfTasksResponse getAllTask(String userEmail,Integer offset,Integer limit) {
        var tasks = taskService.getAllTaskByUser(userService.getByEmail(userEmail), offset, limit);
        GetListOfTasksResponse response = new GetListOfTasksResponse(new ArrayList<TaskResponse>());
        for (Task task : tasks) {
            response.getTaskResponseList().add(taskService.createTaskResponse(task));
        }
        return response;
    }

}

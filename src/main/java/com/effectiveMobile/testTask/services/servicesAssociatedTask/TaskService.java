package com.effectiveMobile.testTask.services.servicesAssociatedTask;

import com.effectiveMobile.testTask.DTO.taskDTO.crudAction.TaskResponse;
import com.effectiveMobile.testTask.Exception.TaskNotFoundException;
import com.effectiveMobile.testTask.entity.Task;
import com.effectiveMobile.testTask.entity.User;
import com.effectiveMobile.testTask.repositories.TaskRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepo repository;

    /**
     * Сохранение задачи
     *
     * @return сохраненная задача
     */
    public Task save(Task task) {
        return repository.save(task);
    }


    /**
     * Создание задачи
     *
     * @return созданная задача
     */
    public Task create(Task task) {
        return save(task);
    }

    /**
     * Получение всех задач
     *
     * @return список всех задач
     */
    public List<Task> getAllTask(Integer offset,Integer limit) {
        return  StreamSupport.stream(repository.findAll(PageRequest.of(offset,limit)).spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Получение всех задач пользователя
     *
     * @return список всех задач
     */
    public List<Task> getAllTaskByUser(User user, Integer offset,Integer limit) {
        return  StreamSupport.stream(repository.findAllByAuthorOrPerformer(user,user,PageRequest.of(offset,limit)).spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Получение задачи по идентификатору
     *
     * @return список всех задач
     */
    public Task getTaskById(long taskId) {
       return repository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Задача с таким идентификатором: " + taskId + " не найдена"));
    }

    /**
     * Удаление задачи
     */
    public void deleteTaskById(long taskId) {
        if (repository.existsById(taskId)) {
            repository.deleteById(taskId);
        }else {
            throw new TaskNotFoundException("Задача с таким идентификатором: " + taskId + " не найдена");
        }

    }

    /**
     * Преобразование Task в TaskResponse
     *
     * @return TaskResponse
     */
    public TaskResponse createTaskResponse(Task task) {
        return new TaskResponse(
                task.getTaskId(),
                task.getTitle(),
                task.getDescription(),
                task.getComment(),
                task.getTaskStatus().name(),
                task.getTaskPriority().name(),
                task.getAuthor().getUserEmail(),
                task.getPerformer().getUserEmail());
    }



}

package com.effectiveMobile.testTask.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_id_seq")
    @SequenceGenerator(name = "task_id_seq", sequenceName = "task_id_seq", allocationSize = 1,initialValue = 10)
    private Long taskId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    private String comment;

    //Создание псевдо-таблицы статуса задачи
    @CollectionTable(name = "task_status", joinColumns = @JoinColumn(name = "task_id"))
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    //Создание псевдо-таблицы приоритета задачи
    @CollectionTable(name = "task_priority", joinColumns = @JoinColumn(name = "task_id"))
    @Enumerated(EnumType.STRING)
    private TaskPriority taskPriority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "performer_id")
    private User performer;

    public Task(String title, String description,
                String comment, TaskStatus taskStatus,
                TaskPriority taskPriority,
                User author, User performer) {
        this.title = title;
        this.description = description;
        this.comment = comment;
        this.taskStatus = taskStatus;
        this.taskPriority = taskPriority;
        this.author = author;
        this.performer = performer;
    }
}

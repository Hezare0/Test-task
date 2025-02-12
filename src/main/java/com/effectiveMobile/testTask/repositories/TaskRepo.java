package com.effectiveMobile.testTask.repositories;

import com.effectiveMobile.testTask.entity.Task;
import com.effectiveMobile.testTask.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    boolean existsByTaskId(Long id);
    List<Task> findAllByAuthorOrPerformer(User author, User performer, Pageable pageable);
}

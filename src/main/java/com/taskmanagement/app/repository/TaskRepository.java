package com.taskmanagement.app.repository;

import com.taskmanagement.app.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectId(Long projectId);
    List<Task> findByAssignedToId(Long employeeId);
    List<Task> findByStatus(Task.TaskStatus status);
}

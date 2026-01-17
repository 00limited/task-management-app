package com.taskmanagement.app.service;

import com.taskmanagement.app.entity.Employee;
import com.taskmanagement.app.entity.Project;
import com.taskmanagement.app.entity.Task;
import com.taskmanagement.app.repository.EmployeeRepository;
import com.taskmanagement.app.repository.ProjectRepository;
import com.taskmanagement.app.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
    
    public List<Task> getTasksByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }
    
    public List<Task> getTasksByEmployeeId(Long employeeId) {
        return taskRepository.findByAssignedToId(employeeId);
    }
    
    public List<Task> getTasksByStatus(Task.TaskStatus status) {
        return taskRepository.findByStatus(status);
    }
    
    public Task createTask(Task task) {
        if (task.getProject() != null && task.getProject().getId() != null) {
            Project project = projectRepository.findById(task.getProject().getId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            task.setProject(project);
        }
        
        if (task.getAssignedTo() != null && task.getAssignedTo().getId() != null) {
            Employee employee = employeeRepository.findById(task.getAssignedTo().getId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            task.setAssignedTo(employee);
        }
        
        return taskRepository.save(task);
    }
    
    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        task.setPriority(taskDetails.getPriority());
        task.setDueDate(taskDetails.getDueDate());
        
        if (taskDetails.getProject() != null && taskDetails.getProject().getId() != null) {
            Project project = projectRepository.findById(taskDetails.getProject().getId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            task.setProject(project);
        }
        
        if (taskDetails.getAssignedTo() != null && taskDetails.getAssignedTo().getId() != null) {
            Employee employee = employeeRepository.findById(taskDetails.getAssignedTo().getId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            task.setAssignedTo(employee);
        }
        
        return taskRepository.save(task);
    }
    
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}

package org.example.dao;
import org.example.entity.Task;
import java.util.List;
public interface ITaskDAO {
    public boolean addTask(Task task);

    public List<Task> getAllTasks();

    public boolean deleteTask(Long taskId);

    public boolean markTaskAsCompleted(Long taskId);
    public List<Task> getTasksByUserId(Long userId);
}

package org.example.dao;
import org.example.entity.Category;
import org.example.entity.Task;

import java.util.List;

public interface ICategoryDAO {
    public boolean addCategory(Category category);
    boolean getCategoryById(Long categoryId);
    public List<Category> getAllCategories();
    public void deleteCategory(Long categoryId);
    List<Task> getTasksByCategory(Category category);
    void addTaskToCategory(Task task, Category category);
    void removeTaskFromCategory(Task task, Category category);
    String getCategoryName(Long categoryId);
}

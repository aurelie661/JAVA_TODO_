package org.example.dao;
import org.example.entity.Category;
import java.util.List;

public interface ICategoryDAO {
    public boolean addCategory(Category category);
   /* boolean getCategoryById(Long categoryId);*/
    public List<Category> getAllCategories();
    public void deleteCategory(Long categoryId);
}

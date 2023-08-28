package org.example.impl;
import org.example.dao.ICategoryDAO;
import org.example.entity.Category;
import org.example.entity.Task;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class CategoryDAOImpl implements ICategoryDAO {
    EntityManagerFactory entityManagerFactory;

    public CategoryDAOImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    @Override
    public boolean addCategory(Category category) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.persist(category);
            entityTransaction.commit();
            return true;
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            entityManager.close();
        }
    }
    @Override
    public boolean getCategoryById(Long categoryId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.createQuery("SELECT c FROM Category c WHERE c.category.id = :id", Category.class).setParameter("id",categoryId);
        entityManager.close();
        return true;
    }
    @Override
    public List<Category> getAllCategories() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Category> categories = entityManager.createQuery("SELECT c FROM Category c",Category.class).getResultList();
        entityManager.close();
        return categories;
    }
    @Override
    public void deleteCategory(Long categoryId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            Category category = entityManager.find(Category.class, categoryId);
            if(category != null) {
                entityManager.remove(category);
            }
            entityTransaction.commit();
        }catch (Exception e){
            if(entityTransaction.isActive()){
                entityTransaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
    }

    @Override
    public List<Task> getTasksByCategory(Category category) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String jpql = "SELECT t FROM Task t JOIN t.categories c WHERE c = :category";
        TypedQuery<Task> query = entityManager.createQuery(jpql, Task.class);
        query.setParameter("category", category);
        List<Task> tasks = query.getResultList();
        entityManager.close();
        return tasks;
    }

    @Override
    public void addTaskToCategory(Task task, Category category) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        task.getCategories().add(category);
        entityManager.merge(task);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void removeTaskFromCategory(Task task, Category category) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        task.getCategories().removeIf(category1 -> (category1.getName().equals(category.getName())));
        entityManager.merge(task);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public String getCategoryName(Long categoryId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String nameCategory = entityManager.find(Category.class,categoryId).getName();
        entityManager.close();
        return nameCategory;
    }
}

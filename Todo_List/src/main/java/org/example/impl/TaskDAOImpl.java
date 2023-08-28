package org.example.impl;
import org.example.dao.ITaskDAO;
import org.example.entity.Category;
import org.example.entity.Task;
import org.example.entity.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class TaskDAOImpl implements ITaskDAO {
    private EntityManagerFactory entityManagerFactory;

    public TaskDAOImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public boolean addTask(Task task) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(task);
            transaction.commit();
            return true;
        }catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }finally {
            entityManager.close();
        }
    }
    public boolean addTaskOfUser(Task task, Long userId, String categoryName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            User user = entityManager.find(User.class,userId);
            task.setUser(user);
            task.setCategories(categoryName);
            user.getTasks().add(task);
            //category.setTasks(category.getTasks());
            entityManager.persist(task);
            transaction.commit();
            return true;
        }catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }finally {
            entityManager.close();
        }
    }

    public List getTaskOfUser(Long userId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createQuery("SELECT t FROM Task t WHERE t.user.id = :id")
                .setParameter("id",userId)
                .getResultList();
    }
    @Override
    public List<Task> getAllTasks() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Task> tasks = entityManager.createQuery("SELECT t FROM Task t",Task.class).getResultList();
        entityManager.close();
        return tasks;
    }

    @Override
    public boolean deleteTask(Long taskId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Task task = entityManager.find(Task.class,taskId);
            if(task != null){
                entityManager.remove(task);
                transaction.commit();
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }finally {
            entityManager.close();
        }
    }

    @Override
    public boolean markTaskAsCompleted(Long taskId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Task task = entityManager.find(Task.class,taskId);
            if(task != null){
                task.setCompleted(true);
                transaction.commit();
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }finally {
            entityManager.close();
        }
    }
}

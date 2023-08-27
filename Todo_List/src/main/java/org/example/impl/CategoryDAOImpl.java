package org.example.impl;
import org.example.dao.ICategoryDAO;
import org.example.entity.Category;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
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
}

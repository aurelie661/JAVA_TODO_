package org.example.controller;
import org.example.entity.Category;
import org.example.entity.InfoTask;
import org.example.entity.Task;
import org.example.entity.User;
import org.example.impl.CategoryDAOImpl;
import org.example.impl.TaskDAOImpl;
import org.example.impl.UserDAOImpl;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ToDoListAppConsole {
    private static EntityManagerFactory entityManagerFactory;
    private static TaskDAOImpl taskDAO;
    private static UserDAOImpl userDAO;
    private  static CategoryDAOImpl categoryDAO;

    public static void start() {
        entityManagerFactory = Persistence.createEntityManagerFactory("todolist");
        taskDAO = new TaskDAOImpl(entityManagerFactory);
        userDAO = new UserDAOImpl(entityManagerFactory);
        categoryDAO = new CategoryDAOImpl(entityManagerFactory);

        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("#### To Do List ####");
            System.out.println("1. Ajouter un utilisateur");
            System.out.println("2. Afficher les tâches d'un utilisateur");
            System.out.println("3. Ajouter une tâche à la liste");
            System.out.println("4. Afficher toutes les tâches de la liste");
            System.out.println("5. Marquer une tâche comme terminée");
            System.out.println("6. Supprimer un utilisateur et ses tâches associées");
            System.out.println("7. Supprimer une tâche de la liste");
            System.out.println("8. Ajouter une catégorie");
            System.out.println("9. Supprimer une catégorie");
            System.out.println("10. Afficher les tâches d'une catégorie");
            System.out.println("11. Ajouter une tâche à une catégorie");
            System.out.println("12. Supprimer une tâche à une catégorie");
            System.out.println("13. Quitter l'application");
            System.out.println("Choix : ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1 -> addUser(scanner);
                case 2 -> displayTasksOfUser(scanner);
                case 3 -> addTask(scanner);
                case 4 -> displayTasks();
                case 5 -> markTaskAsCompleted(scanner);
                case 6 -> deleteUser(scanner);
                case 7 -> deleteTask(scanner);
                case 8 -> addCategory(scanner);
                case 9 -> deleteCategory(scanner);
                case 10 -> System.out.println();
                case 11 -> System.out.println();
                case 12 -> System.out.println();
                case 13 -> {
                    System.out.println("Bye");
                    entityManagerFactory.close();
                }
                default -> System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }while (choice != 13);
    }
    private static void addUser(Scanner scanner){
        System.out.println("Entrer le nom de l'utilisateur : ");
        String name = scanner.nextLine();

        User user = new User();
        user.setName(name);

        if(userDAO.addUser(user)){
            System.out.println("Utilisateur ajoutée avec succès !");
            System.out.println(user.getName());
            System.out.println();
        }else {
            System.out.println("Erreur");
            System.out.println();
        }
    }
    private static void addTask(Scanner scanner){
        System.out.print("Dans quelle catégorie voulez-vous enregistrer cette tâche : ");
        String categoryName = scanner.nextLine();
        System.out.print("Entrer le titre de la tâche : ");
        String title = scanner.nextLine();
        System.out.print("Entrer la description de la tâche : ");
        String description = scanner.nextLine();
        System.out.print("Entrer la date d'échéance de la tâche (dd.MM.yyyy): ");
        String dueDateStr = scanner.nextLine();
        LocalDate dueDate = LocalDate.parse(dueDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        System.out.print("Entrer le niveau de priorité de la tâche (1 à 10): ");
        Integer priority = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        displayUsers();
        System.out.println();
        System.out.print("Entrez la personne pour qui cette tâche est attribuée: (id)");
        Long userId = scanner.nextLong();

        Category category = new Category(categoryName);
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        Task task = new Task();
        task.setTitle(title);
        task.setCompleted(false);
        task.setCategories(categories);

        InfoTask infoTask = new InfoTask(description,dueDate,priority);

        task.setInfoTask(infoTask);
        infoTask.setTask(task);

        if(taskDAO.addTaskOfUser(task,userId,categoryName)){
            System.out.println("Tâche ajoutée avec succès !");
            System.out.println("Titre : "+task.getTitle() +" "+ infoTask+" "+task.getCategories());
            System.out.println();
        }else {
            System.out.println("Erreur");
            System.out.println();
        }
    }
    public static void addCategory(Scanner scanner){
        System.out.println("Entrer le nom de la catégorie : ");
        String name = scanner.nextLine();

        Category category = new Category();
        category.setName(name);

        if(categoryDAO.addCategory(category)){
            System.out.println("Catégorie ajoutée avec succès !");
            System.out.println(category.getName());
            System.out.println();
        }else {
            System.out.println("Erreur");
            System.out.println();
        }
    }
    private static void displayTasksOfUser(Scanner scanner){
        if(userDAO.getAllUsers().isEmpty()){
            System.out.println("Aucune utilisateur actuellement dans l'application.");
            System.out.println();
        }else{
            displayUsers();
            System.out.println();
            System.out.println("Pour quel utilisateur :");
            Long userId = scanner.nextLong();
            scanner.nextLine();
            if(taskDAO.getTaskOfUser(userId).isEmpty()){
                System.out.println("Aucune tâche trouvée pour cette utilisateur.");
                System.out.println();
            }else{
                List<Task> tasks = taskDAO.getTaskOfUser(userId);
                for (Task task : tasks) {
                    System.out.println(task.getId() + ". " + task.getTitle() + " (" + (task.isCompleted() ? "Terminée" : "En cours") + ") "+ task.getInfoTask().toString()+ " Catégorie : "+
                    task.getCategories());
                }
                System.out.println();
            }
        }
    }
    private static void displayUsers() {
        List<User> users = userDAO.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("Aucun utilisateur trouvé.");
            System.out.println();
        } else {
            System.out.println("=== Liste des utilisateurs ===");
            for (User user : users) {
                System.out.println(user.getId() + ". " + user.getName());
            }
            System.out.println();
        }
    }
    private static void displayTasks() {
        List<Task> tasks = taskDAO.getAllTasks();

        if (tasks.isEmpty()) {
            System.out.println("Aucune tâche trouvée.");
            System.out.println();
        } else {
            System.out.println("=== Liste des tâches ===");
            for (Task task : tasks) {
                System.out.println(task.getId() + ". " + task.getTitle() + " (" + (task.isCompleted() ? "Terminée" : "En cours") + ") "+ task.getInfoTask().toString()+ " Catégorie : "+
                        task.getCategories());
            }
            System.out.println();
        }
    }
    private static void deleteTask(Scanner scanner){
        if(taskDAO.getAllTasks().isEmpty()){
            System.out.println("Aucune tâche actuellement dans l'application.");
            System.out.println();
        }else{
            displayTasks();
            System.out.println("Entrez l'ID de la tâche à supprimer : ");
            Long taskId  = scanner.nextLong();
            scanner.nextLine();

            if (taskDAO.deleteTask(taskId)){
                System.out.println("Suppression OK");
                System.out.println();
            }else {
                System.out.println("Erreur");
                System.out.println();
            }
        }
    }
    private static void deleteUser(Scanner scanner){
        if(userDAO.getAllUsers().isEmpty()){
            System.out.println("Aucune utilisateur actuellement dans l'application.");
            System.out.println();
        }else{
            displayUsers();
            System.out.println();
            System.out.println("Entrez l'ID de la personne à suppimer : ");
            Long userId  = scanner.nextLong();
            scanner.nextLine();

            if (userDAO.getUserById(userId)){
                userDAO.deleteUser(userId);
                System.out.println("Suppression OK");
                System.out.println();
            }else {
                System.out.println("Erreur");
                System.out.println();
            }
        }
    }
    public static void deleteCategory(Scanner scanner){
        if(categoryDAO.getAllCategories().isEmpty()){
            System.out.println("Aucune catégorie actuellement dans l'application.");
            System.out.println();
        }else{
            System.out.println("Entrez l'ID de la catégorie à suppimer : ");
            Long categoryId  = scanner.nextLong();
            scanner.nextLine();

            if (categoryDAO.getCategoryById(categoryId)){
                categoryDAO.deleteCategory(categoryId);
                System.out.println("Suppression OK");
                System.out.println();
            }else {
                System.out.println("Erreur");
                System.out.println();
            }
        }
    }
    private static void markTaskAsCompleted(Scanner scanner){
        if(taskDAO.getAllTasks().isEmpty()){
            System.out.println("Aucune tâche actuellement dans l'application.");
            System.out.println();
        }else{
            displayTasks();
            System.out.println("Entrez l'ID de la tâche terminer : ");
            Long taskId  = scanner.nextLong();
            scanner.nextLine();

            if (taskDAO.markTaskAsCompleted(taskId)){
                System.out.println("Modification OK");
                System.out.println();
            }else {
                System.out.println("Erreur");
                System.out.println();
            }
        }
    }
}

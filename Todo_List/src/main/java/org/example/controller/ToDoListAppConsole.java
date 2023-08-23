package org.example.controller;
import org.example.entity.InfoTask;
import org.example.entity.Task;
import org.example.entity.User;
import org.example.impl.TaskDAOImpl;
import org.example.impl.UserDAOImpl;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ToDoListAppConsole {
    private static EntityManagerFactory entityManagerFactory;
    private static TaskDAOImpl taskDAO;
    private static UserDAOImpl userDAO;

    public static void start() {
        entityManagerFactory = Persistence.createEntityManagerFactory("todolist");
        taskDAO = new TaskDAOImpl(entityManagerFactory);
        userDAO = new UserDAOImpl(entityManagerFactory);


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
                case 6 -> System.out.println();
                case 7 -> deleteTask(scanner);
                case 8 -> System.out.println();
                case 9 -> System.out.println();
                case 10 -> System.out.println();
                case 11 -> System.out.println();
                case 12 -> System.out.println();
                case 13 -> {
                    System.out.println("Bye");
                    entityManagerFactory.close();
                    break;
                }
                default -> System.out.println("Choix invalide. Veuillez réessayer.");

            }

        }while (choice != 13);
    }
    private static void addUser(Scanner scanner){
        System.out.println("Entrer le nom de l'utilisateur' : ");
        String name = scanner.nextLine();

        User user = new User();
        user.setName(name);

        if(userDAO.addUser(user)){
            System.out.println("Utilisateur ajoutée avec succès !");
            System.out.println(user.getName());
        }else {
            System.out.println("Erreur");
        }
    }

    private static void addTask(Scanner scanner){
        System.out.println("Entrer le titre de la tâche : ");
        String title = scanner.nextLine();
        System.out.println("Entrer la description de la tâche : ");
        String description = scanner.nextLine();
        System.out.println("Entrer la date d'échéance de la tâche (dd.MM.yyyy): ");
        String dueDateStr = scanner.nextLine();
        LocalDate dueDate = LocalDate.parse(dueDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        System.out.println("Entrer le niveau de priorité de la tâche (1 à 10): ");
        Integer priority = scanner.nextInt();
        scanner.nextLine();

        Task task = new Task();
        task.setTitle(title);
        task.setCompleted(false);

        InfoTask infoTask = new InfoTask(description,dueDate,priority);

        task.setInfoTask(infoTask);
        infoTask.setTask(task);

        if(taskDAO.addTask(task)){
            System.out.println("Tâche ajoutée avec succès !");
            System.out.println(task.getTitle() +" "+ infoTask);
        }else {
            System.out.println("Erreur");
        }
    }

    private static void displayTasksOfUser(Scanner scanner){
        displayUsers();
        System.out.println();
        System.out.println("Pour quel utilisateur :");
        Long userId = scanner.nextLong();

        List<Task> tasks = taskDAO.getAllTasks();
        if(userDAO.getUserById(userId)){
            if (tasks.isEmpty()) {
                System.out.println("Aucune tâche trouvée.");
            } else {
                System.out.println("=== Liste des tâches pour l'utilisateur "+userId+" ===");
                for (Task task : tasks) {
                    System.out.println(task.getId() + ". " + task.getTitle() + " (" + (task.isCompleted() ? "Terminée" : "En cours") + ") "+ task.getInfoTask().toString());
                }
            }
        }else{
            System.out.println("Aucun utilisateur trouvé.");
        }
    }
    private static void displayUsers() {
        List<User> users = userDAO.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("Aucun utilisateur trouvé.");
        } else {
            System.out.println("=== Liste des utilisateurs ===");
            for (User user : users) {
                System.out.println(user.getId() + ". " + user.getName());
            }
        }
    }
    private static void displayTasks() {
        List<Task> tasks = taskDAO.getAllTasks();

        if (tasks.isEmpty()) {
            System.out.println("Aucune tâche trouvée.");
        } else {
            System.out.println("=== Liste des tâches ===");
            for (Task task : tasks) {
                System.out.println(task.getId() + ". " + task.getTitle() + " (" + (task.isCompleted() ? "Terminée" : "En cours") + ") "+ task.getInfoTask().toString());
            }
        }
    }

    private static void deleteTask(Scanner scanner){
        System.out.println("Entrez l'ID de la tâche à supprimer : ");
        Long taskId  = scanner.nextLong();
        scanner.nextLine();

        if (taskDAO.deleteTask(taskId)){
            System.out.println("Suppression OK");
        }else {
            System.out.println("Erreur");
        }
    }

    private static void markTaskAsCompleted(Scanner scanner){
        System.out.println("Entrez l'ID de la tâche à supprimer : ");
        Long taskId  = scanner.nextLong();
        scanner.nextLine();

        if (taskDAO.markTaskAsCompleted(taskId)){
            System.out.println("Modification OK");
        }else {
            System.out.println("Erreur");
        }
    }
}

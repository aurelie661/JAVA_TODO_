package org.example.controller;
import org.example.entity.InfoTask;
import org.example.entity.Task;
import org.example.impl.TaskDAOImpl;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ToDoListAppConsole {
    private static EntityManagerFactory entityManagerFactory;
    private static TaskDAOImpl taskDAO;

    public static void start() {
        entityManagerFactory = Persistence.createEntityManagerFactory("todolist");
        taskDAO = new TaskDAOImpl(entityManagerFactory);

        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("#### To Do List ####");
            System.out.println("1. Ajouter une tâche à la liste");
            System.out.println("2. Afficher toutes les tâches de la liste");
            System.out.println("3. Marquer une tâche comme terminée");
            System.out.println("4. Supprimer une tâche de la liste");
            System.out.println("5. Quitter l'application");
            System.out.println("Choix : ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1:
                    addTask(scanner);
                    break;
                case 2:
                    displayTasks();
                    break;
                case 3:
                    markTaskAsCompleted(scanner);
                    break;
                case 4:
                    deleteTask(scanner);
                    break;
                case 5:
                    System.out.println("Bye");
                    entityManagerFactory.close();
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");

            }

        }while (choice != 5);
    }

    private static void addTask(Scanner scanner){
        System.out.println("Entrer le titre de la tâche : ");
        String title = scanner.nextLine();
        System.out.println("Entrer la description de la tâche : ");
        String description = scanner.nextLine();
        System.out.println("Entrer la date d'échéance de la tâche : ");
        LocalDate dueDate = LocalDate.parse(scanner.next());
        System.out.println("Entrer le niveau de priorité de la tâche (1 à 10): ");
        Integer priority = scanner.nextInt();
        scanner.nextLine();

        InfoTask infoTask = new InfoTask();
        infoTask.setDescription(description);
        infoTask.setDueDate(dueDate);
        infoTask.setPriority(priority);

        Task task = new Task(title,false,infoTask);

        if(taskDAO.addTask(task)){
            System.out.println("Tâche ajoutée avec succès !");
            System.out.println(task +" "+ infoTask);
        }else {
            System.out.println("Erreur");
        }
    }

    private static void displayTasks() {
        List<Task> tasks = taskDAO.getAllTasks();

        if (tasks.isEmpty()) {
            System.out.println("Aucune tâche trouvée.");
        } else {
            System.out.println("=== Liste des tâches ===");
            for (Task task : tasks) {
                System.out.println(task.getId() + ". " + task.getTitle() + " (" + (task.isCompleted() ? "Terminée" : "En cours") + ") "+ task.getInfoTask());
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

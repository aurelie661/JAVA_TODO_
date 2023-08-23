package org.example.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name= "info_task")
public class InfoTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private LocalDate dueDate;
    private int priority;
    @OneToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public InfoTask() {
    }

    public InfoTask(String description, LocalDate dueDate, Integer priority) {
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "InfoTask => " +
                "id = " + id +
                ", description = '" + description + '\'' +
                ", dueDate = " + dueDate +
                ", priority = " + priority +
                ", task_id = " + task.getId() +
                '.';
    }
}

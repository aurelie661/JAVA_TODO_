package org.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;
    private String title;
    private boolean isCompleted;
    @OneToOne(mappedBy = "task")
    private InfoTask infoTask;

    public Task() {
    }

    public Task(String title, boolean isCompleted, InfoTask infoTask) {
        this.title = title;
        this.isCompleted = isCompleted;
        this.infoTask = infoTask;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public InfoTask getInfoTask() {
        return infoTask;
    }

    public void setInfoTask(InfoTask infoTask) {
        this.infoTask = infoTask;
    }

    @Override
    public String toString() {
        return "Task => " +
                "id = " + id +
                ", title = '" + title + '\'' +
                ", isCompleted = " + isCompleted +
                ", infoTask = " + infoTask +
                '.';
    }
}

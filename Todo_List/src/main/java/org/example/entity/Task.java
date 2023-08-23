package org.example.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private boolean isCompleted;
    @OneToOne(mappedBy = "task",cascade = CascadeType.ALL,orphanRemoval = true)
    private InfoTask infoTask;
    @OneToMany(mappedBy = "task")
    private List<User> users;

    public Task() {
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


}

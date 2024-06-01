package ru.practicum.task_manager.task;

import java.util.ArrayList;
import java.util.List;


public class Epic extends Task {
    private List<Subtask> subTasks = new ArrayList<>();

    public Epic(Integer id, String name, String description, Status status, List<Subtask> subTasks) {
        super(id, name, description, status);
        this.subTasks = subTasks;
    }

    public Epic(String name, String description, Status status) {
        super(name, description, status);
        this.subTasks = subTasks;
    }


    public List<Subtask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<Subtask> subTasks) {
        this.subTasks = subTasks;
    }


    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", subTasks=" + subTasks +
                '}';
    }
}

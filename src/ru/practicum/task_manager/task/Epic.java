package ru.practicum.task_manager.task;

import java.util.ArrayList;
import java.util.List;


public class Epic extends Task {
    private List<Subtask> subTasks = new ArrayList<>();

    public Epic(Integer id, String name, String description, Status status) {
        super(id, name, description, Status.NEW, Type.EPIC);
    }

    public Epic(String name, String description, Status status, Type type) {
        super(name, description, Status.NEW, Type.EPIC);
    }


    public List<Subtask> getSubTasks() {
        return subTasks;
    }

    @Override
    public Type getType() {
        return super.getType();
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

package ru.practicum.task_manager.task;

import java.util.Objects;

public class Task {
    private Integer id;
    private String name;
    private String description;
    private Status status;
    private Type type;

    public Task(Integer id, String name, String description, Status status, Type type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = type;
    }

    public Task(String name, String description, Status status, Type type) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public Type getType() {
        return type;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getEpicId() {
        return null;
    }


    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", type=" + type +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                status == task.status &&
                type == task.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status, type);
    }


}

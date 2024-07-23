package ru.practicum.task_manager.task;

public class Subtask extends Task {
    private Integer epicId;

    public Subtask(Integer id, String name, String description, Status status, Integer epicId) {
        super(id, name, description, status, Type.SUBTASK);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, Status status, Integer epicId) {
        super(name, description, status, Type.SUBTASK);
        this.epicId = epicId;
    }

    @Override
    public Integer getId() {
        return super.getId();
    }

    @Override
    public Type getType() {
        return super.getType();
    }

    @Override
    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", epicId=" + epicId +
                '}';
    }
}

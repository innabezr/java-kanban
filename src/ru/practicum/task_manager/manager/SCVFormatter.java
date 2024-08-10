package ru.practicum.task_manager.manager;
import ru.practicum.task_manager.task.*;

public class SCVFormatter {

    public static String toString(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(task.getId()).append(",");
        sb.append(task.getType()).append(",");
        sb.append(task.getName()).append(",");
        sb.append(task.getStatus()).append(",");
        sb.append(task.getDescription()).append(",");

        if (task.getType() == Type.SUBTASK) {
            Subtask subtask = (Subtask) task;
            sb.append(subtask.getEpicId());
        }

        return sb.toString();
    }


    public static Task fromString(String csvRaw) {
        String[] parts = csvRaw.split(",", -1);

        Integer id = Integer.valueOf(parts[0].trim());
        Type type = Type.valueOf(parts[1].trim());
        String name = parts[2].trim();
        Status status = Status.valueOf(parts[3].trim());
        String description = parts[4].trim();
        Integer epicId = null;
        if (parts.length > 5 && !parts[5].trim().isEmpty()) {
            epicId = Integer.valueOf(parts[5].trim());
        }

        switch (type) {
            case SUBTASK:
                return new Subtask(id, name, description, status, epicId);
            case EPIC:
                return new Epic(id, name, description, status);
            case TASK:
                return new Task(id, name, description, status, type);
            default:
                throw new IllegalArgumentException("Неизвестный тип задачи: " + type);
        }
    }

    static String getHeaders() {
        return "id, type, name, status, description, epic";
    }
}

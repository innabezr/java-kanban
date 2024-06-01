package ru.practicum.task_manager.manager;

import ru.practicum.task_manager.task.Epic;
import ru.practicum.task_manager.task.Status;
import ru.practicum.task_manager.task.Subtask;
import ru.practicum.task_manager.task.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class TaskManager {
    private int nextId;
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();

    private int getNextId() {
        return nextId++;
    }

    //а. Получение списка задач:
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public List<Subtask> getSubtasks() {
        List<Subtask> subtasks = new ArrayList<>();
        for (Epic epic : epics.values()) {
            subtasks.addAll(epic.getSubTasks());
        }
        return subtasks;
    }

    //b. Удаление всех задач:
    public boolean deleteAllTask() {
        tasks.clear();
        return true;
    }

    public boolean deleteAllEpic() {
        epics.clear();
        return true;
    }

    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getSubTasks().clear();
        }
    }

    //c. Получение по идентификатору
    public Task getTaskWithId(int taskId) {
        return tasks.get(taskId);
    }

    public List<Subtask> getEpicSubtask(int epicId) {
        List<Subtask> getEpicSubtask = new ArrayList<>();
        for (Epic epic : epics.values()) {
            if (epic.getId() == epicId) {
                getEpicSubtask.addAll(epic.getSubTasks());
                break;
            }
        }
        return getEpicSubtask;
    }

    public Epic getEpicWithId(int epicId) {
        return epics.get(epicId);
    }

    //d. Создание
    public Task createTask(Task task) {
        task.setId(getNextId());
        tasks.put(task.getId(), task);
        return task;
    }

    public Epic createEpicTask(Epic epic) {
        epic.setId(getNextId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    public Subtask createSubtask(int epicId, Subtask subtask) {
        if (epics.containsKey(epicId)) {
            Epic epic = epics.get(epicId);
            subtask.setId(getNextId());
            epic.getSubTasks().add(subtask);
            updateEpicStatus(epic);
            return subtask;
        }
        return null;
    }

    //e. Обновление
    public Task updateTask(Task task) {
        Integer taskId = task.getId();
        if (taskId == null || !tasks.containsKey(taskId)) {
            return null;
        }
        tasks.put(task.getId(), task);
        return task;
    }

    public Epic updateEpic(Epic epic) {
        Integer epicId = epic.getId();
        if (epicId == null || !epics.containsKey(epicId)) {
            return null;
        }
        Epic existEpic = epics.get(epicId);
        existEpic.setName(epic.getName());
        existEpic.setDescription(epic.getDescription());
        return existEpic;
    }

    public Subtask updateSubtask(Subtask subtask) {
        Integer epicId = subtask.getEpicId();
        Integer subtaskId = subtask.getId();

        if (subtaskId == null || !epics.containsKey(epicId)) {
            return null;
        }
        Epic epic = epics.get(epicId);
        for (Subtask existSubtask : epic.getSubTasks()) {
            if (existSubtask.getId().equals(subtaskId)) {
                existSubtask.setName(subtask.getName());
                existSubtask.setDescription(subtask.getDescription());
                existSubtask.setStatus(subtask.getStatus());
                updateEpicStatus(epic);
                return existSubtask;
            }
        }
        return null;
    }

    private void updateEpicStatus(Epic epic) {
        boolean allDone = true;
        boolean allNew = true;

        for (Subtask subtask : epic.getSubTasks()) {
            if (subtask.getStatus() != Status.DONE) {
                allDone = false;
            }
            if (subtask.getStatus() != Status.NEW) {
                allNew = false;
            }
        }
        if (allDone) {
            epic.setStatus(Status.DONE);
        } else if (allNew) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    //f. Удаление по идентификатору
    public boolean deleteTaskWithId(int taskId) {
        return tasks.remove(taskId) != null;
    }

    public boolean deleteEpicWithId(int epicId) {
        return epics.remove(epicId) != null;
    }

    public boolean deleteSubtaskWithId(int subtaskId) {
        boolean deleted = false;
        for (Epic epic : epics.values()) {
            List<Subtask> subtasks = epic.getSubTasks();
            for (int i = 0; i < subtasks.size(); i++) {
                if (subtasks.get(i).getId() == subtaskId) {
                    subtasks.remove(i);
                    deleted = true;
                    break;
                }
            }
        }
        return deleted;
    }

    //g. Получение списка всех подзадач определённого эпика.
    public List<Subtask> getAllEpicSubtasks(int epicId) {
        return getEpicSubtask(epicId);
    }
}
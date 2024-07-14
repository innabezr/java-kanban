package ru.practicum.task_manager.manager;

import ru.practicum.task_manager.task.Epic;
import ru.practicum.task_manager.task.Status;
import ru.practicum.task_manager.task.Subtask;
import ru.practicum.task_manager.task.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int nextId;
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private final TreeSet<Integer> availableIdList = new TreeSet<>();


    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    private int getNextId() {
        if (!availableIdList.isEmpty()) {
            int id = availableIdList.first();
            availableIdList.remove(id);
            return id;
        }
        return nextId++;
    }

    //а. Получение списка задач:
    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getSubtasks() {
        List<Subtask> subtasks = new ArrayList<>();
        for (Epic epic : epics.values()) {
            subtasks.addAll(epic.getSubTasks());
        }
        return subtasks;
    }

    //b. Удаление всех задач:
    @Override
    public boolean deleteAllTask() {
        tasks.clear();
        return true;
    }

    @Override
    public boolean deleteAllEpic() {
        epics.clear();
        return true;
    }

    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getSubTasks().clear();
            epic.setStatus(Status.NEW);
        }
    }

    //c. Получение по идентификатору
    @Override
    public Task getTaskWithId(int taskId) {
        Task taskForHistory = tasks.get(taskId);
        if (taskForHistory != null) {
            historyManager.add(taskForHistory);
        }
        return taskForHistory;
    }


    @Override
    public Subtask getSubtaskWithId(int subtaskId) {
        for (Epic epic : epics.values()) {
            for (Subtask subtask : epic.getSubTasks()) {
                if (subtask.getId() == subtaskId) {
                    historyManager.add(subtask);
                    return subtask;
                }
            }
        }
        return null;
    }


    @Override
    public Epic getEpicWithId(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    //d. Создание
    @Override
    public Task createTask(Task task) {
        task.setId(getNextId());
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Epic createEpicTask(Epic epic) {
        epic.setId(getNextId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public Subtask createSubtask(int epicId, Subtask subtask) {
        if (epics.containsKey(epicId)) {
            Epic epic = epics.get(epicId);
            subtask.setId(getNextId());
            epic.getSubTasks().add(subtask);
            updateEpic(epic);
            return subtask;
        }
        return null;
    }

    //e. Обновление
    @Override
    public Task updateTask(Task task) {
        Integer taskId = task.getId();
        if (taskId == null || !tasks.containsKey(taskId)) {
            return null;
        }
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
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

    @Override
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
        List<Subtask> subtasks = epic.getSubTasks();

        for (Subtask subtask : subtasks) {
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
    @Override
    public boolean deleteTaskWithId(int taskId) {
        boolean del = tasks.remove(taskId) != null;
        if (del) {
            availableIdList.add(taskId);
            historyManager.remove(taskId);
        }
        return del;
    }

    @Override
    public boolean deleteEpicWithId(int epicId) {
        Epic del = epics.remove(epicId);
        if (del != null) {
            for (Subtask subtask : del.getSubTasks()) {
                availableIdList.add(subtask.getId());
                historyManager.remove(subtask.getId());
            }
            availableIdList.add(epicId);
            historyManager.remove(epicId);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteSubtaskWithId(int subtaskId) {
        boolean del = false;
        List<Epic> epicsToDeleteFrom = new ArrayList<>();
        for (Epic epic : epics.values()) {
            List<Subtask> subtasks = epic.getSubTasks();
            List<Subtask> subtasksToDelete = new ArrayList<>();
            for (Subtask subtask : subtasks) {
                if (subtask.getId() == subtaskId) {
                    subtasksToDelete.add(subtask);
                    del = true;
                    break;
                }
            }
            subtasks.removeAll(subtasksToDelete);
            if (subtasks.isEmpty()) {
                epicsToDeleteFrom.add(epic);
            } else {
                updateEpicStatus(epic);
            }
        }
        for (Epic epic : epicsToDeleteFrom) {
            epics.remove(epic.getId());
        }
        if (del) {
            availableIdList.add(subtaskId);
        }
        return del;
    }

    //g. Получение списка всех подзадач определённого эпика.
    @Override
    public List<Subtask> getAllEpicSubtasks(int epicId) {
        List<Subtask> epicSubtasks = new ArrayList<>();
        Epic epic = epics.get(epicId);
        if (epic != null) {
            epicSubtasks.addAll(epic.getSubTasks());

        }
        return epicSubtasks;
    }
}

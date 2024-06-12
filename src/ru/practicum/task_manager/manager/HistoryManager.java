package ru.practicum.task_manager.manager;

import ru.practicum.task_manager.task.Task;

import java.util.List;

public interface HistoryManager {

    void add(Task task);

    List<Task> getHistory();

    void clearHistory();
}

package ru.practicum.task_manager.manager;

import ru.practicum.task_manager.task.Task;

import java.util.List;

public interface HistoryManager {

    void historyAdd(Task task);

    List<Task> getHistory();
}

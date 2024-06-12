package ru.practicum.task_manager.manager;

import ru.practicum.task_manager.task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    //История просмотра задач
    private static final List<Task> history = new ArrayList<>();
    @Override
    public void historyAdd(Task task) {
        if (history.size() >= 10) {
            history.remove(0);
        }
        history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }
    public void clearHistory() {
        history.clear();
    }

}

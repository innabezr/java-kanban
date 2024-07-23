package ru.practicum.task_manager.manager;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Managers {
    private Managers() {
    }

    public static TaskManager getDefault() {
        Path filePath = Paths.get("src/path/test.csv");
        return new FileBackedTaskManager(filePath);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }


}

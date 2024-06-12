import ru.practicum.task_manager.manager.HistoryManager;
import ru.practicum.task_manager.manager.InMemoryHistoryManager;
import ru.practicum.task_manager.manager.InMemoryTaskManager;
import ru.practicum.task_manager.manager.TaskManager;

public class Managers {
    private Managers() {
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}

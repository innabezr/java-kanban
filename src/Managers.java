import ru.practicum.task_manager.manager.HistoryManager;
import ru.practicum.task_manager.manager.InMemoryHistoryManager;
import ru.practicum.task_manager.manager.InMemoryTaskManager;
import ru.practicum.task_manager.manager.TaskManager;

public class Managers {
    private Managers() {
    }

    public static InMemoryTaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}

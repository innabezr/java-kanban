import ru.practicum.task_manager.manager.HistoryManager;
import ru.practicum.task_manager.manager.TaskManager;
import ru.practicum.task_manager.task.Epic;
import ru.practicum.task_manager.task.Status;
import ru.practicum.task_manager.task.Subtask;
import ru.practicum.task_manager.task.Task;


public class Main {
    public static void main(String[] args) {
        System.out.println("Поехали!");
        testTasks();
    }

    private static void testTasks() {
        TaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();
        //1.Создайте две задачи, эпик с тремя подзадачами и эпик без подзадач.
        Task task1 = new Task("Задача первая", "Описание первой задачи", Status.NEW);
        Task task2 = new Task("Задача вторая", "Описание второй задачи", Status.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        Epic epic1 = new Epic("Эпическая задача 1", "Есть 3 подзадачи");
        taskManager.createEpicTask(epic1);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, epic1.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", Status.NEW, epic1.getId());
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", Status.NEW, epic1.getId());
        taskManager.createSubtask(epic1.getId(), subtask1);
        taskManager.createSubtask(epic1.getId(), subtask2);
        taskManager.createSubtask(epic1.getId(), subtask3);
        Epic epic2 = new Epic("Эпическая задача 2", "нет подзадач");
        taskManager.createEpicTask(epic2);
        //2.Запросите созданные задачи несколько раз в разном порядке
        taskManager.getTaskWithId(task1.getId());
        taskManager.getSubtaskWithId(subtask2.getId());
        taskManager.getEpicWithId(epic1.getId());
        taskManager.getTaskWithId(epic2.getId());
        taskManager.getSubtaskWithId(subtask3.getId());
        taskManager.getTaskWithId(task2.getId());
        taskManager.getSubtaskWithId(subtask1.getId());
        //3.После каждого запроса выведите историю и убедитесь, что в ней нет повторов.
        System.out.println(historyManager.getHistory());
        System.out.println();

        taskManager.getSubtaskWithId(subtask3.getId());
        taskManager.getSubtaskWithId(subtask1.getId());
        taskManager.getTaskWithId(task1.getId());
        taskManager.getSubtaskWithId(subtask2.getId());
        taskManager.getEpicWithId(epic1.getId());
        taskManager.getTaskWithId(task2.getId());
        taskManager.getTaskWithId(epic2.getId());

        System.out.println(historyManager.getHistory());
        System.out.println();

        taskManager.getTaskWithId(task1.getId());
        taskManager.getEpicWithId(epic1.getId());
        taskManager.getSubtaskWithId(subtask3.getId());
        taskManager.getSubtaskWithId(subtask2.getId());
        taskManager.getSubtaskWithId(subtask1.getId());
        taskManager.getTaskWithId(task2.getId());
        taskManager.getEpicWithId(epic2.getId());

        System.out.println(historyManager.getHistory());
        System.out.println();
        //4. Удалите задачу, которая есть в истории, и проверьте, что при печати она не будет выводиться.
        taskManager.deleteTaskWithId(task2.getId());
        System.out.println(historyManager.getHistory());
        System.out.println();
        //5. Удалите эпик с тремя подзадачами и убедитесь, что из истории удалился как сам эпик, так и все его подзадачи.
        taskManager.deleteEpicWithId(epic1.getId());
        System.out.println(historyManager.getHistory());
        System.out.println();
    }
}
import ru.practicum.task_manager.manager.InMemoryHistoryManager;
import ru.practicum.task_manager.manager.InMemoryTaskManager;
import ru.practicum.task_manager.task.Epic;
import ru.practicum.task_manager.task.Status;
import ru.practicum.task_manager.task.Subtask;
import ru.practicum.task_manager.task.Task;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        System.out.println("Поехали!");
        testTasks();
    }

    private static void testTasks() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        InMemoryHistoryManager historyManager = Managers.getDefaultHistory();
        Task task1 = new Task("Задача первая", "Описание первой задачи", Status.NEW);
        Task task2 = new Task("Задача вторая", "Описание второй задачи", Status.NEW);

        taskManager.createTask(task1);
        taskManager.createTask(task2);

        Epic epic1 = new Epic("Эпическая задача 1", "Описание первой эпической задачи");
        taskManager.createEpicTask(epic1);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, epic1.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", Status.NEW, epic1.getId());
        taskManager.createSubtask(epic1.getId(), subtask1);
        taskManager.createSubtask(epic1.getId(), subtask2);

        Epic epic2 = new Epic("Эпическая задача 2", "Описание второй эпической задачи");
        taskManager.createEpicTask(epic2);
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", Status.NEW, epic2.getId());
        taskManager.createSubtask(epic2.getId(), subtask3);

        System.out.println("Список задач:");
        for (Task task : taskManager.getTasks()) {
            System.out.println(task);
        }
        System.out.println();
        System.out.println("Список эпиков:");
        for (Epic epic : taskManager.getEpics()) {
            System.out.println(epic);
        }
        System.out.println();
        System.out.println("Список подзадач:");
        for (Subtask subtask : taskManager.getSubtasks()) {
            System.out.println(subtask);
        }
        System.out.println();

        task1.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(task1);
        subtask3.setStatus(Status.DONE);
        taskManager.updateSubtask(subtask3);
        System.out.println("Обновленные статусы:");
        System.out.println("Статус 1 задачи: " + task1.getStatus());
        System.out.println("Статус 3 подзадачи: " + subtask3.getStatus());
        System.out.println("Статус эпика: " + epic2.getStatus());
        System.out.println();

        taskManager.getTaskWithId(task1.getId());
        taskManager.getSubtaskWithId(subtask2.getId());
        taskManager.getEpicWithId(epic1.getId());
        System.out.println(historyManager.getHistory());
    }
}
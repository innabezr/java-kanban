import ru.practicum.task_manager.manager.InMemoryTaskManager;
import ru.practicum.task_manager.task.Epic;
import ru.practicum.task_manager.task.Status;
import ru.practicum.task_manager.task.Subtask;
import ru.practicum.task_manager.task.Task;


import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private InMemoryTaskManager taskManager;

    @org.junit.jupiter.api.BeforeEach
    public void BeforeEach() {
        taskManager = new InMemoryTaskManager();
    }

    @org.junit.jupiter.api.Test
    void EpicEqlByIdTest() {
        Task task1 = new Task(1, "Task 1", "Description 1", Status.NEW);
        Task task2 = new Task(1, "Task 1", "Description 1", Status.NEW);
        assertEquals(task1, task2);
    }

    @org.junit.jupiter.api.Test
    void TaskEqlByIdTest() {
        Epic epic1 = new Epic("Эпическая задача 1", "Описание первой эпической задачи");
        Epic epic2 = new Epic("Эпическая задача 1", "Описание первой эпической задачи");
        assertEquals(epic1, epic2);
    }

//   Эти 2 теста или я не понимаю задание или так и должно быть.
    //@org.junit.jupiter.api.Test
    //void EpicAddLikeSubtaskTest() {
    //InMemoryTaskManager taskManager = new InMemoryTaskManager();
    //Epic epic1 = new Epic("Эпическая задача 1", "Описание первой эпической задачи");
    //assertTrue(taskManager.createSubtask(epic1.getId(), epic1));
    // }

    // @org.junit.jupiter.api.Test
    //void SubtaskAddLikeEpicTest() {
    //InMemoryTaskManager taskManager = new InMemoryTaskManager();
    //Epic epic1 = new Epic("Эпическая задача 1", "Описание первой эпической задачи");
    //taskManager.createEpicTask(epic1);
    //Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, epic1.getId());
    //taskManager.createSubtask(epic1.getId(), epic1);
    // }

    @org.junit.jupiter.api.Test
    void classManagerTest() {
        taskManager = Managers.getDefault();
        assertNotNull(Managers.getDefault());
    }

    @org.junit.jupiter.api.Test
    void searchByIdTMTest() {
        Task task = new Task("Task", "Description", Status.NEW);
        Epic epic = new Epic("Epic", "Description");
        Subtask subtask = new Subtask("Subtask", "Description", Status.NEW, epic.getId());
        taskManager.createTask(task);
        taskManager.createEpicTask(epic);
        taskManager.createSubtask(epic.getId(), subtask);
        assertEquals(task, taskManager.getTaskWithId(task.getId()));
        assertEquals(epic, taskManager.getEpicWithId(epic.getId()));
        assertEquals(subtask, taskManager.getSubtaskWithId(subtask.getId()));
    }

    @org.junit.jupiter.api.Test
    void taskWithIdAndAutoIdDontConflictTest() {
        Task task1 = new Task(0, "Задача1", "Описание", Status.NEW);
        Task task2 = new Task("Задача2", "Описание", Status.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        assertEquals(task1.getId(), 0);
        assertNotEquals(task1.getId(), task2.getId());
    }

    @org.junit.jupiter.api.Test
    void compareAllTaskFieldTest() {
        Task task1 = new Task(0, "Задача1", "Описание", Status.NEW);
        taskManager.createTask(task1);
        assertEquals(task1.getId(), 0);
        assertEquals(task1.getName(), "Задача1");
        assertEquals(task1.getDescription(), "Описание");
        assertEquals(task1.getStatus(), Status.NEW);
    }
}
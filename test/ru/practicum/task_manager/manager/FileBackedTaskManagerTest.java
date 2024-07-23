package ru.practicum.task_manager.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.task_manager.exceptions.ManagerException;
import ru.practicum.task_manager.task.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {
    private static final Path TEST_FILE_PATH = Path.of("src/path/test.csv");
    private FileBackedTaskManager taskManager;

    @BeforeEach
    public void setUp() throws ManagerException {
        taskManager = new FileBackedTaskManager(TEST_FILE_PATH);
    }


    @Test
    void testSaveAndLoadTasks() throws ManagerException {
        // Создание и сохранение задач
        Task task = new Task("Task", "Описание задачи 1", Status.NEW, Type.TASK);
        Task task1 = new Task("Task 2", "Описание задачи 2", Status.NEW, Type.TASK);

        taskManager.createTask(task);
        taskManager.createTask(task1);

        Epic epic = new Epic("Epic 1", "Описание Epic", Status.NEW, Type.EPIC);
        taskManager.createEpicTask(epic);

        Subtask subtask = new Subtask(1, "Subtask 1", "Описание Subtask", Status.NEW, epic.getId());
        taskManager.createSubtask(epic.getId(), subtask);

        // Сохранение данных в файл
        taskManager.save();

        // Перезагрузка менеджера из файла
        FileBackedTaskManager loadedTaskManager = FileBackedTaskManager.loadFromFile(TEST_FILE_PATH.toFile());

        List<Task> loadedTasks = loadedTaskManager.getTasks();
        List<Epic> loadedEpics = loadedTaskManager.getEpics();
        List<Subtask> loadedSubtasks = loadedTaskManager.getSubtasks();

        System.out.println("Loaded tasks: " + loadedTasks);
        System.out.println("Loaded epics: " + loadedEpics);
        System.out.println("Loaded subtasks: " + loadedSubtasks);

        assertEquals(2, loadedTasks.size(), "Некорректное количество задач загружено");
        assertEquals(1, loadedEpics.size(), "Некорректное количество эпиков загружено");
        assertEquals(1, loadedSubtasks.size(), "Некорректное количество подзадач загружено");

        assertEquals("Task", loadedTasks.get(0).getName(), "Название задачи не совпадает");
        assertEquals("Task 2", loadedTasks.get(1).getName(), "Название задачи не совпадает");
        assertEquals("Subtask 1", loadedSubtasks.get(0).getName(), "Название подзадачи не совпадает");
    }
}

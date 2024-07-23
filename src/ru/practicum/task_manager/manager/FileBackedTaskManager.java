package ru.practicum.task_manager.manager;

import ru.practicum.task_manager.exceptions.ManagerException;
import ru.practicum.task_manager.task.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {


    private final Path file;
    private static final String PATH_TO_FILES = "src/path/test.csv";

    public FileBackedTaskManager(Path file) {
        super();
        this.file = file;
    }

    @Override
    public Task createTask(Task task) {
        Task createdTask = super.createTask(task);
        save();
        return createdTask;
    }

    @Override
    public Epic createEpicTask(Epic epic) {
        Task createdEpic = super.createEpicTask(epic);
        save();
        return (Epic) createdEpic;
    }

    @Override
    public Subtask createSubtask(int epicId, Subtask subtask) {
        Task createdSubtask = super.createSubtask(epicId, subtask);
        if (createdSubtask != null) {
            save();
        }
        return (Subtask) createdSubtask;
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file.toPath());
        try {
            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (line.isEmpty()) {
                    continue;
                }
                Task task = SCVFormatter.fromString(line);
                switch (task.getType()) {
                    case TASK:
                        fileBackedTaskManager.createTask(task);
                        break;
                    case EPIC:
                        fileBackedTaskManager.createEpicTask((Epic) task);
                        break;
                    case SUBTASK:
                        Subtask subtask = (Subtask) task;
                        fileBackedTaskManager.createSubtask(subtask.getEpicId(), subtask);
                        break;
                }
            }
        } catch (IOException e) {
            throw ManagerException.loadException(e);
        }
        return fileBackedTaskManager;
    }


    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.toFile()), StandardCharsets.UTF_8))) {
            // Заголовки
            bw.write(SCVFormatter.getHeaders());
            bw.newLine();
            // Сохранение задач
            List<Task> tasks = getTasks();
            List<Epic> epics = getEpics();
            List<Subtask> subtasks = getSubtasks();
            if (tasks.isEmpty() && epics.isEmpty() && subtasks.isEmpty()) {
                System.out.println("Задач пока нет");
            } else {
                for (Task task : tasks) {
                    bw.write(SCVFormatter.toString(task));
                    bw.newLine();
                }
                for (Epic epic : epics) {
                    bw.write(SCVFormatter.toString(epic));
                    bw.newLine();
                }
                for (Subtask subtask : subtasks) {
                    bw.write(SCVFormatter.toString(subtask));
                    bw.newLine();
                }
                System.out.println("Задачи успешно сохранены.");
            }
        } catch (
                IOException e) {
            throw ManagerException.saveException(e);
        }

    }

    public static void main(String[] args) {
        TaskManager fileBackedTaskManager = Managers.getDefault();
        TaskManager taskManager = fileBackedTaskManager;

        // Создание задач
        Task task1 = new Task("Задача первая", "Описание первой задачи", Status.NEW, Type.TASK);
        Task task2 = new Task("Задача вторая", "Описание второй задачи", Status.NEW, Type.TASK);
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        // Создание эпика и подзадач
        Epic epic1 = new Epic("Эпическая задача 1", "Есть 3 подзадачи", Status.NEW, Type.EPIC);
        taskManager.createEpicTask(epic1);

        Subtask subtask1 = new Subtask(1, "Подзадача 1", "Описание подзадачи 1", Status.NEW, epic1.getId());
        Subtask subtask2 = new Subtask(2, "Подзадача 2", "Описание подзадачи 2", Status.NEW, epic1.getId());
        taskManager.createSubtask(epic1.getId(), subtask1);
        taskManager.createSubtask(epic1.getId(), subtask2);

        // Сохранение задач
        ((FileBackedTaskManager) fileBackedTaskManager).save();

        // Загрузка задач из файла
        TaskManager newTaskManager = FileBackedTaskManager.loadFromFile(new File("src/path/test.csv"));
        List<Task> loadedTasks = newTaskManager.getTasks();
        List<Epic> loadedEpics = newTaskManager.getEpics();
        List<Subtask> loadedSubtasks = newTaskManager.getSubtasks();

        // Вывод всех задач
        for (Task task : loadedTasks) {
            System.out.println(task);
        }
        for (Epic epic : loadedEpics) {
            System.out.println(epic);
        }
        for (Subtask subtask : loadedSubtasks) {
            System.out.println(subtask);
        }
    }

}




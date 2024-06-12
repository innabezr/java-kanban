package ru.practicum.task_manager.manager;

import ru.practicum.task_manager.task.Epic;
import ru.practicum.task_manager.task.Subtask;
import ru.practicum.task_manager.task.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    //а. Получение списка задач:
    List<Task> getTasks();

    List<Epic> getEpics();

    List<Subtask> getSubtasks();


    //b. Удаление всех задач:
    boolean deleteAllTask();

    boolean deleteAllEpic();

    void deleteAllSubtasks();

    //c. Получение по идентификатору
    Task getTaskWithId(int taskId);

    Subtask getSubtaskWithId(int subtaskId);

    Epic getEpicWithId(int epicId);

    //d. Создание
    Task createTask(Task task);

    Epic createEpicTask(Epic epic);

    Subtask createSubtask(int epicId, Subtask subtask);

    //e. Обновление
    Task updateTask(Task task);

    Epic updateEpic(Epic epic);

    Subtask updateSubtask(Subtask subtask);

    //f. Удаление по идентификатору
    boolean deleteTaskWithId(int taskId);

    boolean deleteEpicWithId(int epicId);

    boolean deleteSubtaskWithId(int subtaskId);

    //g. Получение списка всех подзадач определённого эпика.
    List<Subtask> getAllEpicSubtasks(int epicId);


}



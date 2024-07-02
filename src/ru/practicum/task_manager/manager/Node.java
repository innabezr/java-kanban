package ru.practicum.task_manager.manager;
import ru.practicum.task_manager.task.Task;

public class Node {
    Node prev;
    Node next;
    Task value;

    public Node(Task value) {
        this.value = value;
    }

}

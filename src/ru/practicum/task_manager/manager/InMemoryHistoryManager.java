package ru.practicum.task_manager.manager;

import ru.practicum.task_manager.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private Node first;
    private Node last;
    private Map<Integer, Node> nodeMap = new HashMap<>();

    @Override
    public void add(Task task) {
        remove(task.getId());
        linkLast(task);
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node current = first;
        while (current != null) {
            history.add(current.value);
            current = current.next;
        }
        return history;
    }

    @Override
    public void remove(int id) {
        Node node = nodeMap.remove(id);
        if (node != null) {
            removeNode(node);
        }
    }

    private void linkLast(Task task) {
        if (task != null) {
            Node oldNode = nodeMap.get(task.getId());
            if (oldNode != null) {
                removeNode(oldNode);
            }
            Task taskCopy = new Task(task.getId(), task.getName(), task.getDescription(), task.getStatus(), task.getType());
            Node newNode = new Node(taskCopy);
            if (first == null) {
                first = last = newNode;
            } else {
                last.next = newNode;
                newNode.prev = last;
                last = newNode;
            }
            nodeMap.put(task.getId(), newNode);
        }
    }


    private void removeNode(Node node) {
        if (node != null) {
            if (node.prev == null) {
                first = node.next;
            } else {
                node.prev.next = node.next;
            }
            if (node.next == null) {
                last = node.prev;
            } else {
                node.next.prev = node.prev;
            }
        }
    }
}

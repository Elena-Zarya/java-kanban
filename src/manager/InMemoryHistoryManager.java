package manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tasks.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final CustomLinkedList<Task> history = new CustomLinkedList<>();
    private final HashMap<Integer, Node> historyMap = new HashMap<>();

    @Override
    public void add(Task task) {
        Node newNode;
        if (historyMap.containsKey(task.getId())) {
            Node node = historyMap.get(task.getId());
            history.removeNode(node);
        }
        newNode = history.linkLast(task);
        historyMap.put(task.getId(), newNode);
    }

    @Override
    public void remove(int id) {
        if (historyMap.containsKey(id)) {
            Node node = historyMap.get(id);
            history.removeNode(node);
            historyMap.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return history.getTasks(history.size);
    }

    private class CustomLinkedList<Task> {
        private Node first;
        private Node last;
        private int size = 0;


        /**
         * Добавление задачи в конец списка
         */
        public Node linkLast(tasks.Task task) {
            final Node newNode = new Node(last, task, null);
            if (first == null) {
                first = newNode;
            } else {
                last.setNext(newNode);
            }
            last = newNode;
            size++;
            return newNode;
        }

        public List<tasks.Task> getTasks(int size) {
            ArrayList<tasks.Task> historyList = new ArrayList<>();
            Node x = first;
            for (int i = 0; i < size; i++) {
                historyList.add(x.getTask());
                x = x.getNext();
            }
            return historyList;
        }

        private void removeNode(Node node) {
            Node next = node.getNext();
            Node prev = node.getPrev();

            if (prev == null) {
                first = next;
            } else {
                prev.setNext(next);
                node.setPrev(null);
            }
            if (next == null) {
                last = prev;
            } else {
                next.setPrev(prev);
                node.setNext(null);
            }
            node.setTask(null);
            size--;
        }
    }
}


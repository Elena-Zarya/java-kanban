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
        Node<Task> newNode = history.linkLast(task);
        if (historyMap.containsKey(task.getId())) {
            Node<Task> node = historyMap.get(task.getId());
            history.removeNode(node);
        }
        historyMap.put(task.getId(), newNode);
    }

    @Override
    public void remove(int id) {
        if (historyMap.containsKey(id)) {
            Node<Task> node = historyMap.get(id);
            history.removeNode(node);
            historyMap.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        ArrayList<Task> historyList = new ArrayList<>();
        for (int i = 0; i < history.size; i++) {
            historyList.add(history.getTasks(i));
        }
        return historyList;
    }

    private class CustomLinkedList<Task> {
        private Node<Task> first;
        private Node<Task> last;
        private int size = 0;


        /**
         * Добавление задачи в конец списка
         */
        public Node<Task> linkLast(Task task) {
            final Node<Task> l = last;
            final Node<Task> newNode = new Node<>(l, task, null);
            last = newNode;
            if (l == null) {
                first = newNode;
            } else {
                l.setNext(newNode);
            }
            size++;
            return newNode;
        }

        public Task getTasks(int index) {
            Node<Task> x = first;
            for (int i = 0; i < index; i++)
                x = x.getNext();
            return x.getTask();
        }

        private void removeNode(Node<Task> node) {
            Node<Task> next = node.getNext();
            Node<Task> prev = node.getPrev();

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


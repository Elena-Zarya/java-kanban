package manager;

import java.util.LinkedList;
import java.util.List;
import tasks.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final LinkedList<Task> history = new LinkedList<>();

    static final Integer SIZE_LIST = 10;

    @Override
    public void add(Task task) {
        history.add(task);
        if (history.size() > SIZE_LIST) {
            history.removeFirst();
        }
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }
}

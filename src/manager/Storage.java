package manager;

import java.util.HashMap;
import java.util.TreeSet;

import tasks.*;

public class Storage {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final TreeSet<Task> prioritizedTasks = new TreeSet<>((a, b) -> {
        if ((a.getStartTime() == null) && (b.getStartTime() == null)) {
            return a.getId().compareTo(b.getId());
        } else if (a.getStartTime() == null) {
            return 1;
        } else if (b.getStartTime() == null) {
            return -1;
        } else {
            return a.getStartTime().compareTo(b.getStartTime());
        }
    });

    private Integer id = 0;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public void setTasks(int id, Task task) {
        tasks.put(id, task);
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public void setEpics(int id, Epic epic) {
        epics.put(id, epic);
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(int id, Subtask subtask, int epicId) {
        if (epics.containsKey(epicId)) {
            epics.get(epicId).addSubtasksId(id);
            subtasks.put(id, subtask);
        }
    }

    public TreeSet<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

}

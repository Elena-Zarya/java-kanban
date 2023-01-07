package manager;

import java.util.HashMap;

import tasks.*;

public class Storage {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

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
}

import java.util.ArrayList;
import java.util.HashMap;

public class Storage {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private Integer taskId = 0;
    private Integer epicId = 0;
    private Integer subtaskId = 0;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public Integer getSubtaskId() {
        return subtaskId;
    }

    public void setSubtaskId(int subtaskId) {
        this.subtaskId = subtaskId;
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

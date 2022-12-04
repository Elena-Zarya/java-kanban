import java.util.HashMap;

public class Storage {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private Integer taskId = 0;
    private Integer epicId = 0;
    private Integer subtaskId = 0;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

    public Integer getSubtaskId() {
        return subtaskId;
    }

    public void setSubtaskId(Integer subtaskId) {
        this.subtaskId = subtaskId;
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public void setTasks(Integer id, Task task) {
        tasks.put(id, task);
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public void setEpics(Integer id, Epic epic) {
        epics.put(id, epic);
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(Integer id, Subtask subtask, Integer epicId) {
        epics.get(epicId).setSubtasksList(id);
        subtasks.put(id, subtask);
    }
}

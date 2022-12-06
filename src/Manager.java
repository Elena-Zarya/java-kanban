import java.util.ArrayList;

public class Manager {
    private Storage storage = new Storage();

    public ArrayList<Task> getAllTask() {                                        //получить списки задач
        return new ArrayList<>(storage.getTasks().values());
    }

    public ArrayList<Epic> getAllEpic() {
        return new ArrayList<>(storage.getEpics().values());
    }

    public ArrayList<Subtask> getAllSubtask() {
        return new ArrayList<>(storage.getSubtasks().values());
    }

    public ArrayList<Subtask> getAllSubtask(int epicId) {               //Получение списка всех подзадач определённого эпика.
        if (storage.getEpics().containsKey(epicId)) {
            ArrayList<Subtask> subtasksList = new ArrayList<>();
            for (Integer subtaskId: storage.getEpics().get(epicId).getSubtasksList()) {
                subtasksList.add(storage.getSubtasks().get(subtaskId));
            }

            return subtasksList;
        } else {
            return null;
        }
    }

    public void clearTask() {                            //удаление всех задач
        storage.getTasks().clear();
    }

    public void clearEpics() {
        storage.getEpics().clear();
        clearSubtasks();
    }

    public void clearSubtasks() {
        storage.getSubtasks().clear();
        for (Integer i : storage.getEpics().keySet()) {
            storage.getEpics().get(i).getSubtasksList().clear();
            storage.getEpics().get(i).setStatus("NEW");
        }
    }

    public Task getTask(int id) {                      //получение по идентификатору
        return storage.getTasks().get(id);
    }

    public Epic getEpic(int id) {
        return storage.getEpics().get(id);
    }

    public Subtask getSubtask(int id) {
        return storage.getSubtasks().get(id);
    }

    public Integer addTask(Task task) {                           //добавление задач
        int id = storage.getTaskId() + 1;
        storage.setTaskId(id);
        task.setId(id);
        storage.setTasks(id, task);
        return task.getId();
    }

    public Integer addEpic(Epic epic) {
        int id = storage.getEpicId() + 1;
        storage.setEpicId(id);
        epic.setId(id);
        storage.setEpics(id, epic);
        return  epic.getId();
    }

    public Integer addSubtask(Subtask subtask, int epicId) {
        int id = storage.getSubtaskId() + 1;
        storage.setSubtaskId(id);
        subtask.setId(id);
        storage.setSubtasks(id, subtask, epicId);
        subtask.setEpicId(epicId);
        updateEpicStatus(epicId);
        return subtask.getId();
    }

    public Task updateTask(Task task, int id) {                   //обновление задач
        if (storage.getTasks().containsKey(id)) {
            storage.setTasks(id, task);
            storage.getTasks().get(id).setId(id);
        }
        return getTask(id);

    }

    public Epic updateEpic(String nameTask, String description, int id) {
        if (storage.getEpics().containsKey(id)) {
            storage.getEpics().get(id).setNameTask(nameTask);
            storage.getEpics().get(id).setDescription(description);
        }
        return getEpic(id);
    }

    public Subtask updateSubtask(Subtask subtask, int id, int epicId) {
        if (storage.getSubtasks().containsKey(id)) {
            storage.setSubtasks(id, subtask, epicId);
            storage.getSubtasks().get(id).setId(id);
            subtask.setEpicId(epicId);
            updateEpicStatus(epicId);
        }
        return getSubtask(id);
    }

    public void deleteTask(int id) {                      //удаление по идентификатору
        storage.getTasks().remove(id);
    }

    public void deleteEpic(int id) {
        for (Integer i : storage.getEpics().get(id).getSubtasksList()) {
            storage.getSubtasks().remove(i);
        }
        storage.getEpics().remove(id);
    }

    public void deleteSubtask(Integer id, int epicId) {           //Если заменить Integer id на int id, то remove(id) ужаляет по индексу, а не по значению
        if (storage.getEpics().containsKey(epicId)) {
            storage.getSubtasks().remove(id);
            if (storage.getEpics().get(epicId).getSubtasksList().contains(id)) {
                storage.getEpics().get(epicId).getSubtasksList().remove(id);
                updateEpicStatus(epicId);
            }
        }
    }

    private void updateEpicStatus(int epicId) {                                      //расчет статуса для эпика
           ArrayList<String> statuss = new ArrayList<>();

            for (Integer s : storage.getEpics().get(epicId).getSubtasksList()) {
                statuss.add(storage.getSubtasks().get(s).getStatus());
            }
            if ((storage.getEpics().get(epicId).getSubtasksList() == null) || (!statuss.contains("DONE") && !statuss.contains("IN_PROGRESS"))) {
                storage.getEpics().get(epicId).setStatus("NEW");
            } else if (!statuss.contains("NEW") && !statuss.contains("IN_PROGRESS")) {
                storage.getEpics().get(epicId).setStatus("DONE");
            } else
                storage.getEpics().get(epicId).setStatus("IN_PROGRESS");
    }
}

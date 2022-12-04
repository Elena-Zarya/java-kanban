import java.util.ArrayList;

public class Manager {
    Storage storage = new Storage();

    public ArrayList<Task> getAllTask() {                                        //получить списки задач

        ArrayList<Task> tasksList = new ArrayList<>();

        for (Task task : storage.getTasks().values()) {
            tasksList.add(task);
        }
        return tasksList;
    }

    public ArrayList<Epic> getAllEpic() {
        ArrayList<Epic> epicsList = new ArrayList<>();
        for (Epic epic : storage.getEpics().values()) {
            epicsList.add(epic);
        }
        return epicsList;
    }

    public ArrayList<Subtask> getAllSubtask() {
        ArrayList<Subtask> subtasksList = new ArrayList<>();
        for (Subtask subtask : storage.getSubtasks().values()) {
            subtasksList.add(subtask);
        }
        return subtasksList;
    }

    public ArrayList<Subtask> getAllSubtask(Integer epicId) {               //Получение списка всех подзадач определённого эпика. ПРОВЕРИТЬ
        ArrayList<Subtask> subtasksList = new ArrayList<>();
        if (storage.getEpics().get(epicId) != null) {
            for (int i = 0; i < storage.getEpics().get(epicId).getSubtasksList().size(); i++) {
                Integer idSub = storage.getEpics().get(epicId).getSubtasksList().get(i);
                subtasksList.add(storage.getSubtasks().get(idSub));
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
        }
        setEpicStatus();
    }

    public Task getTask(Integer id) {                      //получение по идентификатору
        return storage.getTasks().get(id);
    }

    public Epic getEpic(Integer id) {
        return storage.getEpics().get(id);
    }

    public Subtask getSubtask(Integer id) {
        return storage.getSubtasks().get(id);
    }

    public void addTask(Task task) {                           //добавление задач
        int id = storage.getTaskId() + 1;
        storage.setTaskId(id);
        storage.setTasks(id, task);
    }

    public void addEpic(Epic epic) {
        int id = storage.getEpicId() + 1;
        storage.setEpicId(id);
        storage.setEpics(id, epic);
    }

    public void addSubtask(Subtask subtask, Integer epicId) {
        int id = storage.getSubtaskId() + 1;
        storage.setSubtaskId(id);
        storage.setSubtasks(id, subtask, epicId);
        subtask.setEpicId(epicId);
        setEpicStatus();
    }

    public void updateTask(Task task, Integer id) {                   //обновление задач
        if (storage.getTasks().containsKey(id)) {
            storage.setTasks(id, task);
        } else {
            System.out.println("Задача с таким дентификатором не найдена");
        }
    }

    public void updateEpic(Epic epic, Integer id) {
        if (storage.getEpics().containsKey(id)) {
            storage.setEpics(id, epic);
        } else {
            System.out.println("Задача с таким дентификатором не найдена");
        }
    }

    public void updateSubtask(Subtask subtask, Integer id, Integer epicId) {
        if (storage.getSubtasks().containsKey(id)) {
            // storage.setSubtasks(id, subtask, epicId);
            subtask.setEpicId(epicId);
            setEpicStatus();
        } else {
            System.out.println("Задача с таким дентификатором не найдена");
        }
    }

    public void deleteTask(Integer id) {                      //удаление по идентификатору
        storage.getTasks().remove(id);
    }

    public void deleteEpic(Integer id) {
        for (Integer i : storage.getEpics().get(id).getSubtasksList()) {
            storage.getSubtasks().remove(i);
        }
        storage.getEpics().remove(id);
    }

    public void deleteSubtask(Integer id, Integer epicId) {
        storage.getSubtasks().remove(id);
        storage.getEpics().get(epicId).getSubtasksList().remove(id);
        setEpicStatus();
    }

    public void setEpicStatus() {                                      //расчет статуса для эпика

        for (Integer i : storage.getEpics().keySet()) {
            ArrayList<String> statuss = new ArrayList<>();

            for (Integer s : storage.getEpics().get(i).getSubtasksList()) {
                statuss.add(storage.getSubtasks().get(s).getStatus());
            }
            if ((storage.getEpics().get(i).getSubtasksList() == null) || (!statuss.contains("DONE") && !statuss.contains("IN_PROGRESS"))) {
                storage.getEpics().get(i).setStatus("NEW");
            } else if (!statuss.contains("NEW") && !statuss.contains("IN_PROGRESS")) {
                storage.getEpics().get(i).setStatus("DONE");
            } else
                storage.getEpics().get(i).setStatus("IN_PROGRESS");
        }
    }
}

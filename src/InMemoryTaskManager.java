import java.util.ArrayList;

public class InMemoryTaskManager implements TaskManager {
    private Storage storage = new Storage();
    HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    @Override
    public ArrayList<Task> getAllTask() {                                        //получить списки задач
        return new ArrayList<>(storage.getTasks().values());
    }

    @Override
    public ArrayList<Epic> getAllEpic() {
        return new ArrayList<>(storage.getEpics().values());
    }

    @Override
    public ArrayList<Subtask> getAllSubtask() {
        return new ArrayList<>(storage.getSubtasks().values());
    }

    @Override
    public ArrayList<Subtask> getAllSubtask(int epicId) {               //Получение списка всех подзадач определённого эпика.
        if (storage.getEpics().containsKey(epicId)) {
            ArrayList<Subtask> subtasksList = new ArrayList<>();
            for (Integer subtaskId : storage.getEpics().get(epicId).getSubtasksList()) {
                subtasksList.add(storage.getSubtasks().get(subtaskId));
            }
            return subtasksList;
        } else {
            return null;
        }
    }

    @Override
    public void clearTask() {                            //удаление всех задач
        storage.getTasks().clear();
    }

    @Override
    public void clearEpics() {
        storage.getEpics().clear();
        clearSubtasks();
    }

    @Override
    public void clearSubtasks() {
        storage.getSubtasks().clear();
        for (Integer i : storage.getEpics().keySet()) {
            storage.getEpics().get(i).getSubtasksList().clear();
            updateEpicStatus(i);
        }
    }

    @Override
    public Task getTask(int id) {                      //получение по идентификатору
        Task task = storage.getTasks().get(id);
        inMemoryHistoryManager.add(task);
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = storage.getEpics().get(id);
        inMemoryHistoryManager.add(epic);
        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = storage.getSubtasks().get(id);
        inMemoryHistoryManager.add(subtask);
        return subtask;
    }

    @Override
    public Integer addTask(Task task) {                           //добавление задач
        int id = storage.getTaskId() + 1;
        storage.setTaskId(id);
        task.setId(id);
        storage.setTasks(id, task);
        return task.getId();
    }

    @Override
    public Integer addEpic(Epic epic) {
        int id = storage.getEpicId() + 1;
        storage.setEpicId(id);
        epic.setId(id);
        storage.setEpics(id, epic);
        return epic.getId();
    }

    @Override
    public Integer addSubtask(Subtask subtask, int epicId) {
        if (storage.getEpics().containsKey(epicId)) {
            int id = storage.getSubtaskId() + 1;
            storage.setSubtaskId(id);
            subtask.setId(id);
            storage.setSubtasks(id, subtask, epicId);
            subtask.setEpicId(epicId);
            updateEpicStatus(epicId);
            return subtask.getId();
        } else {
            return null;
        }
    }

    @Override
    public Task updateTask(Task task, int id) {                   //обновление задач
        if (storage.getTasks().containsKey(id)) {
            storage.setTasks(id, task);
            storage.getTasks().get(id).setId(id);
        }
        return storage.getTasks().get(id);

    }

    @Override
    public Epic updateEpic(String nameTask, String description, int id) {
        if (storage.getEpics().containsKey(id)) {
            storage.getEpics().get(id).setNameTask(nameTask);
            storage.getEpics().get(id).setDescription(description);
        }
        return storage.getEpics().get(id);
    }

    @Override
    public Subtask updateSubtask(Subtask subtask, int id, int epicId) {
        if (storage.getEpics().containsKey(epicId)) {
            if (storage.getSubtasks().containsKey(id)) {
                storage.setSubtasks(id, subtask, epicId);
                storage.getSubtasks().get(id).setId(id);
                subtask.setEpicId(epicId);
                updateEpicStatus(epicId);
            }
            return storage.getSubtasks().get(id);
        } else {
            return null;
        }
    }

    @Override
    public void deleteTask(int id) {                      //удаление по идентификатору
        storage.getTasks().remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        for (Integer i : storage.getEpics().get(id).getSubtasksList()) {
            storage.getSubtasks().remove(i);
        }
        storage.getEpics().remove(id);
    }

    @Override
    public void deleteSubtask(int id, int epicId) {
        Integer idS = Integer.valueOf(id);
        if (storage.getEpics().containsKey(epicId)) {
            storage.getSubtasks().remove(idS);
            if (storage.getEpics().get(epicId).getSubtasksList().contains(idS)) {
                storage.getEpics().get(epicId).getSubtasksList().remove(idS);
                updateEpicStatus(epicId);
            }
        }
    }

    @Override
    public void updateEpicStatus(int epicId) {                                      //расчет статуса для эпика
        ArrayList<Status> status = new ArrayList<>();
        Epic epicsId = storage.getEpics().get(epicId);

        for (Integer s : epicsId.getSubtasksList()) {
            status.add(storage.getSubtasks().get(s).getStatus());
        }
        if ((epicsId.getSubtasksList() == null) || (!status.contains("DONE") && !status.contains("IN_PROGRESS"))) {
            epicsId.setStatus(Status.NEW);
        } else if (!status.contains("NEW") && !status.contains("IN_PROGRESS")) {
            epicsId.setStatus(Status.DONE);
        } else
            epicsId.setStatus(Status.IN_PROGRESS);
    }

    @Override
    public ArrayList<Task> getHistory() {                                 //показать историю просмотров
        return inMemoryHistoryManager.getHistory();
    }
}


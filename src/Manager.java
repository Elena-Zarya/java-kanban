import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    Storage storage = new Storage();
//    HashMap<Integer, Task> t = storage.getTasks();
//    HashMap<Integer, Epic> e = storage.getEpics();
//    HashMap<Integer, Subtask> s = storage.getSubtasks();

 //   private HashMap <Integer, Epic> epics = new HashMap<>();
    public ArrayList<Task> getAllTask() {                                        //получить списки задач

            ArrayList<Task> tasksList = new ArrayList<>();

            for (Task task : storage.getTasks().values()) {
                tasksList.add(task);
            }
            return tasksList;
    }

    public ArrayList<Epic> getAllEpic(){
        ArrayList<Epic> epicsList = new ArrayList<>();
        for (Epic epic: storage.getEpics().values()) {
            epicsList.add(epic);
        }
        return epicsList;
    }

    public ArrayList<Subtask> getAllSubtask(){
        ArrayList<Subtask> subtasksList = new ArrayList<>();
        for (Subtask subtask: storage.getSubtasks().values()) {
            subtasksList.add(subtask);
        }
        return subtasksList;
    }
    public ArrayList<Subtask> getAllSubtask(Integer epicId){               //Получение списка всех подзадач определённого эпика. ПРОВЕРИТЬ
        ArrayList<Subtask> subtasksList = new ArrayList<>();
        for (Integer id: storage.getEpics().keySet()) {
            if (id == epicId){
               Epic d = storage.getEpics().get(id);
               subtasksList = d.getSubtasksList();
            }
        }
        return subtasksList;
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
        for (Integer i: storage.getEpics().keySet()) {
            storage.getEpics().get(i).getSubtasksList().clear();
        }
        setEpicStatus();
    }


    public Task getTask(Integer id){                      //получение по идентификатору
        return storage.getTasks().get(id);
    }

    public Epic getEpic(Integer id){
        return storage.getEpics().get(id);
    }

    public Subtask getSubtask(Integer id){
        return storage.getSubtasks().get(id);
    }



    public void addTask(Task task){                           //добавление задач
        int id = storage.getTaskId() + 1;
        storage.setTaskId(id);
        storage.setTasks(id, task);

    }

    public void addEpic(Epic epic){
        int id = storage.getEpicId() + 1;
        storage.setEpicId(id);
        storage.setEpics(id, epic);
    }

    public void addSubtask(Subtask subtask, Integer epicId){
        int id = storage.getSubtaskId() + 1;                                     //проверить эпик на 0
        storage.setSubtaskId(id);
        storage.setSubtasks(id, subtask, epicId);
        subtask.setEpicId(epicId);
        setEpicStatus();
    }



    public void updateTask(Task task, Integer id){                   //обновление задач
        if (storage.getTasks().containsKey(id)) {
            storage.setTasks(id, task);
        } else {
            System.out.println("Задача с таким дентификатором не найдена");
        }
    }
    public void updateEpic(Epic epic, Integer id){
        if (storage.getEpics().containsKey(id)) {
            storage.setEpics(id, epic);
        } else {
            System.out.println("Задача с таким дентификатором не найдена");
        }
    }
    public void updateSubtask(Subtask subtask, Integer id, Integer epicId){
        if (storage.getSubtasks().containsKey(id)) {
            storage.setSubtasks(id, subtask, epicId);
            subtask.setEpicId(epicId);
            setEpicStatus();
        } else {
            System.out.println("Задача с таким дентификатором не найдена");
        }
    }


    public void deleteTask(Integer id){                      //удаление по идентификатору
        storage.getTasks().remove(id);
    }

  public void deleteEpic(Integer id){
        storage.getEpics().remove(id);
    }

  public void deleteSubtask(Integer id){
        storage.getSubtasks().remove(id);
      setEpicStatus();
    }

    public void setEpicStatus(){

        for (Integer i: storage.getEpics().keySet()){
            ArrayList<String> statuss = new ArrayList<>();
//            String statusEpic;

            for (Subtask s: storage.getEpics().get(i).getSubtasksList()) { // == null){
                statuss.add(s.getStatus());
            }
            if ((storage.getEpics().get(i).getSubtasksList() == null) || (!statuss.contains("DONE") && !statuss.contains("IN_PROGRESS"))){
                storage.getEpics().get(i).setStatus("NEW");
            }
            else if (!statuss.contains("NEW") && !statuss.contains("IN_PROGRESS")) {
                storage.getEpics().get(i).setStatus("DONE");
            }
            else
                storage.getEpics().get(i).setStatus("IN_PROGRESS");

//            if (s.getStatus().equals("NEW")){                                 //ДОПИСАТЬ
//                    epics.get(i).setStatus("NEW");
//                }
        }
    }
}
//    Кроме классов для описания задач, вам нужно реализовать класс для объекта-менеджера. Он будет запускаться на старте программы и управлять всеми задачами. В нём должны быть реализованы следующие функции:
//        Возможность хранить задачи всех типов. Для этого вам нужно выбрать подходящую коллекцию.
//        Методы для каждого из типа задач(Задача/Эпик/Подзадача):
//  123   Получение списка всех задач.
//  123   Удаление всех задач.
//  123   Получение по идентификатору.
//  123   Создание. Сам объект должен передаваться в качестве параметра.
//  123   Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
//  123   Удаление по идентификатору.
//        Дополнительные методы:
//    ?3  Получение списка всех подзадач определённого эпика.
//        Управление статусами осуществляется по следующему правилу:
//        Менеджер сам не выбирает статус для задачи. Информация о нём приходит менеджеру вместе с информацией о самой задаче. По этим данным в одних случаях он будет сохранять статус, в других будет рассчитывать.
//        Для эпиков:
//        если у эпика нет подзадач или все они имеют статус NEW, то статус должен быть NEW.
//        если все подзадачи имеют статус DONE, то и эпик считается завершённым — со статусом DONE.
//        во всех остальных случаях статус должен быть IN_PROGRESS.
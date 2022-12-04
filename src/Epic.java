import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Epic extends Task{
    private ArrayList<Subtask> subtasksList;

    public Epic(String nameTask, String description, String status) {
        super(nameTask, description, status);
        subtasksList = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtasksList, epic.subtasksList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasksList);
    }

    @Override
    public String toString() {
        return "Epic{"  +
                "nameTask='" + getNameTask() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", subtasksList.size=" + subtasksList.size() +
                '}';
    }

    public ArrayList<Subtask> getSubtasksList() {
        return subtasksList;
    }

    public void setSubtasksList(Subtask subtask) {
        for (int i = 0; i < subtasksList.size(); i++) {
            if (subtasksList.get(i).getStatus().equals(subtask.getStatus())) {
                subtasksList.set(i, subtask);
            } else {
                subtasksList.add(subtask);
            }
        }
    }
}
/*
    Этими объектами и заполняй. Список сабтасков в эпике нужно пополнять в мэнеджере в момент создания саба.
        В мейне вообще никакой логики быть не должно, кроме тестирования методов мэнеджера
    Создаётся всегда эпик с пустым списком сабов. При добавлении нового саба, мы его добавляем в общий реестр и в список эпика, которому он принадлежит
        Но когда идёт апдейт эпика, то обновлённый эпик прилетает со списком сабов
Потому что это один из тех эпиков, которые были отправлены методом "Получение списка всех задач."
*/

